package cakeit.server.auth;

import cakeit.server.entity.TokenEntity;
import cakeit.server.user.repository.TokenRepository;
import cakeit.server.user.service.UserDetailService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private String secretKey = "cakeitProj";
    private long accessTokenValidation = 1000L * 10 * 60;                       // 엑세스 토큰 = 10분
//    private long accessTokenValidation = 1000L * 60;                            // test용 엑세스 토큰 = 1분
    private long refreshTokenValidation = 1000L * 60 * 60 * 24 * 7 * 4;         // 리프레시 토큰 = 4주(28일)

    private final UserDetailService userDetailService;
    private final TokenRepository tokenRepository;

    /**
     * 스크릿키 base64 encoding
     */
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    /**
     * jwt 토큰 생성 (accessToken, refreshToken)
     * 클레임 : 로그인 ID, 생성 시간, 유효 기간
     */
    public TokenEntity createToken(String loginId) {
        Claims claimLoginId = Jwts.claims().setSubject(loginId);
        Date now = new Date();

        return TokenEntity.builder()
                .accessToken(Jwts.builder()
                        .setClaims(claimLoginId)
                        .setIssuedAt(now)
                        .setExpiration(new Date(now.getTime() + accessTokenValidation))
                        .signWith(SignatureAlgorithm.HS256, secretKey)
                        .compact())
                .refreshToken(Jwts.builder()
                        .setClaims(claimLoginId)
                        .setIssuedAt(now)
                        .setExpiration(new Date(now.getTime() + refreshTokenValidation))
                        .signWith(SignatureAlgorithm.HS256, secretKey)
                        .compact()).build();

    }

    /**
     * access 토큰 재생성 및 반환
     * 클레임 : 로그인 ID, 생성 시간, 유효 기간
     */
    public String recreateAccessToken(String loginId) {

        Claims claimLoginId = Jwts.claims().setSubject(loginId);
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claimLoginId)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessTokenValidation))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    /**
     * JWT 토큰에서 인증 정보 조회
     */
    public Authentication getAuthentication(String token) {

        UserDetails userDetails = userDetailService.loadUserByUsername(this.getLoginId(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    /**
     * JWT 토큰에서 회원 정보 추출 - 로그인 ID
     */
    public String getLoginId(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * 헤더에서 토큰값 가져오기
     */
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    /**
     * 토큰 유효성 확인
     * 유효하면 - true 반환
     */
    public boolean verifyToken(String token) {

        try {
            // Bearer 검증
            if (!token.substring(0, "BEARER ".length()).equalsIgnoreCase("BEARER ")) {
                return false;
            } else {
                token = token.split(" ")[1].trim();
            }
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            // 만료되었을 시 false
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }

    }

    /**
     * refresh 토큰 유효성 확인
     * 유효하면 - 새 access token 생성 및 반환
     * 유효하지 않으면 - null
     */
    public String verifyRefreshToken(String refreshToken) {

        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(refreshToken);
            log.info("verify refresh = {}",claims.getBody().getExpiration().after(new Date()));
            if (claims.getBody().getExpiration().after(new Date())) {
                return recreateAccessToken(getLoginId(refreshToken));
            }
        } catch (Exception e) {
            return "";
        }

        return "";
    }


}
