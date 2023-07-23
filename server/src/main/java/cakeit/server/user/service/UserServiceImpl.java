package cakeit.server.user.service;

import cakeit.server.entity.UserEntity;
import cakeit.server.user.dto.UserDto;
import cakeit.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.Column;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;

    //회원가입 start
    @Transactional
    public void join(UserDto userDto) {

        //password 암호화
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));

        //주민번호로 나이 변환
        String juminNo = String.valueOf(userDto.getAge());
        String gender = userDto.getGender();

        log.info("juminNo >>>" + juminNo);
        log.info("주민번호 성별 >>>" + gender);

        if (gender.equals("1") || gender.equals("3")) {
            gender = "남";
        } else if (gender.equals("2") || gender.equals("4")) {
            gender = "여";
        } else {
            gender = "에러";
        }

        //나이 계산
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);

        String birthYear = juminNo.substring(0, 2);

        log.info("birthYear>>>" + birthYear);

        char ch = juminNo.charAt(7);
        log.info("ch>>>" + ch);

        Long age;

        if (ch < '3') {
            age = Long.valueOf(year - (1900 + Integer.parseInt(birthYear)) + 1);
        } else {
            age = Long.valueOf(year - (2000 + Integer.parseInt(birthYear)) + 1);
        }

        UserEntity userEntity = UserEntity.builder()
                .loginId(userDto.getLoginId())
                .password(userDto.getPassword())
                .nickname(userDto.getNickname())
                .age(age)
                .profileImage(userDto.getProfileImage())//
                .gender(gender)
                .build();

        userRepository.save(userEntity);
    }
//회원가입 end


    //로그인 start
    @Override
    public UserDetails login(String loginId) {
        return null;
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
//로그인 end

    //중복id 체크 start
    @Override
    public String idcheck(String loginId) {
        log.info("로그인 아이디>>" + loginId);
        String idcheckYn = userRepository.idcheck(loginId);
        log.info("idcheckYn>>" + idcheckYn);

        return idcheckYn;
    }
//중복id 체크 end

    @Override
    public void deleteUser(UserDto userDto) {
//
//        // 비밀번호 확인
//        String password = requestDto.getPassword();
//        if (!passwordEncoder.matches(password, user.getPassword())) {
//            throw new RestApiException(ErrorType.NOT_MATCHING_PASSWORD);
//        }

        userRepository.deleteAllByloginId(userDto.getLoginId());

        //return ResponseEntity.ok(MessageResponseDto.of(HttpStatus.OK, "회원탈퇴 완료"));
    }
}