package cakeit.server.user.service;

import cakeit.server.auth.JwtTokenProvider;
import cakeit.server.entity.TokenEntity;
import cakeit.server.entity.UserEntity;
import cakeit.server.user.dto.LoginRequestDto;
import cakeit.server.user.dto.LoginResponseDto;
import cakeit.server.user.dto.SignUpRequestDto;
import cakeit.server.user.dto.UserDto;
import cakeit.server.user.repository.TokenRepository;
import cakeit.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final NicknameServiceImpl nicknameService;

    @Transactional
    @Override
    public boolean signUp(SignUpRequestDto signUpDto) {

        //주민번호로 나이 변환
        String juminNo = String.valueOf(signUpDto.getAge());
        String gender = juminNo.substring(6);

        if (gender.equals("1") || gender.equals("3")) {
            gender = "남";
        } else if (gender.equals("2") || gender.equals("4")) {
            gender = "여";
        } else {
            return false;
        }

        //나이 계산
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);

        String birthYear = juminNo.substring(0, 2);

        char ch = juminNo.charAt(6);
        Long age = 0L;

        if (ch < '3') {
            age = Long.valueOf(year - (1900 + Integer.parseInt(birthYear)) + 1);
        } else {
            age = Long.valueOf(year - (2000 + Integer.parseInt(birthYear)) + 1);
        }

        //닉네임 자동생성
        String nickname = nicknameService.addRandomNickname();

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        UserEntity userEntity = UserEntity.builder()
                .loginId(signUpDto.getLoginId())
                .password(passwordEncoder.encode(signUpDto.getPassword()))
                .nickname(nickname)
                .age(age)
                .profileImage(signUpDto.getProfileImage())
                .gender(gender)
                .purpose(signUpDto.getPurpose())
                .build();

        userRepository.save(userEntity);

        return true;
    }


    @Override
    public LoginResponseDto login(LoginRequestDto reqDto) {
        UserEntity user = userRepository.findByLoginId(reqDto.getLoginId()).orElseThrow(() -> new NoSuchElementException("등록되지 않은 유저입니다!"));

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(reqDto.getPassword(), user.getPassword())) {
            throw new NoSuchElementException("등록되지 않은 유저입니다!");
        }

        TokenEntity token = jwtTokenProvider.createToken(user.getLoginId());
        tokenRepository.save(token);

        return LoginResponseDto.builder()
                .userId(user.getUserId())
                .loginId(user.getLoginId())
                .nickname(user.getNickname())
                .profileImage(user.getProfileImage())
                .gender(user.getGender())
                .accessToken(token.getAccessToken())
                .refreshToken(token.getRefreshToken())
                .build();
    }

    @Override
    public UserDetails loadUserByLoginId(String loginId) throws UsernameNotFoundException {
        // 로그인을 하기 위해 가입된 user정보를 조회하는 메서드
        Optional<UserEntity> memberWrapper = userRepository.findByLoginId(loginId);
        UserEntity userEntity = memberWrapper.get();

        List<GrantedAuthority> authorities = new ArrayList<>();

        if ("admin".equals(loginId)) {
            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(Role.MEMBER.getValue()));
        }

        // 아이디, 비밀번호, 권한리스트를 매개변수로 User를 만들어 반환해준다.
        return new User(userEntity.getLoginId(), userEntity.getPassword(), authorities);
    }

    @Override
    public Optional<UserEntity> findByLoginId(String username) {

        return userRepository.findByLoginId(username);
    }


    //중복id 체크 start
    @Override
    public String idcheck(String loginId) {
        log.info("로그인 아이디>>" + loginId);
        String idcheckYn = userRepository.idcheck(loginId);
        log.info("idcheckYn>>" + idcheckYn);

        return idcheckYn;
    }

    @Override
    public void deleteUser(UserDto userDto) {
        userRepository.deleteAllByloginId(userDto.getLoginId());
    }

}