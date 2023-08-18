package cakeit.server.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor

public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        // JWT 토큰 가져오기
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        log.info("JwtAuthenticationFilter check token ========= " + token);
        // 유효한 토큰인지 확인합니다.
        if (token != null && jwtTokenProvider.verifyToken(token)) {
            // 토큰이 유효하면 토큰으로부터 유저 정보를 받아옵니다.
            token = token.split(" ")[1].trim();
            log.info("JwtAuthenticationFilter verifying token ========= " + token);
            Authentication authentication = jwtTokenProvider.getAuthentication(token);  // 토큰으로부터 유저 정보를 가져오기
            SecurityContextHolder.getContext().setAuthentication(authentication);       // SecurityContext에 Authentication 객체 저장
        }
        chain.doFilter(request, response);

    }
}
