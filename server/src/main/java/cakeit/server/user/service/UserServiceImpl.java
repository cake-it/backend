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
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;

    @Transactional
    public void join(UserDto userDto) {
        log.info("여기는 오나? UserServiceImpl");
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));

        // password를 암호화 한 뒤 dp에 저장

        UserEntity userEntity = UserEntity.builder()
                .loginId(userDto.getLoginId())
                .password(userDto.getPassword())
                .nickname(userDto.getNickname())
                .age(userDto.getAge())
                .profileImage(userDto.getProfileImage())//
                .gender(userDto.getGender())
                .build();

        userRepository.save(userEntity);
    }

    @Override
    public UserDetails login(String loginId) {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        // 로그인을 하기 위해 가입된 user정보를 조회하는 메서드
        Optional<UserEntity> memberWrapper = userRepository.findByLoginId(loginId);
        UserEntity userEntity = memberWrapper.get();

        List<GrantedAuthority> authorities = new ArrayList<>();

        if("admin".equals(loginId)){
            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(Role.MEMBER.getValue()));
        }

        // 아이디, 비밀번호, 권한리스트를 매개변수로 User를 만들어 반환해준다.
        return new User(userEntity.getLoginId(), userEntity.getPassword(), authorities);
    }

    @Override
    public Optional<UserEntity> findByLoginId(String loginId){
        return userRepository.findByLoginId(loginId);
    }



//    public boolean login(UserDto userDto) {
//        UserDto findUser = userRepository.findByLoginId(userDto.getLoginId());
//        log.info("로그인 서비스 >>" + userDto.getLoginId());
//        log.info("로그인 서비스 findUser>>" + findUser);
//
//        if (findUser == null) {
//            return false;
//        }
//
//        if (!findUser.getPassword().equals(userDto.getPassword())) {
//            return false;
//        }
//        return true;
//    }

//    public Long login(UserDto userDto) {
//        UserDto userDto = userRepository.save(userDto);
//        return userDto.getUserId();
//}
}
