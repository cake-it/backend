package cakeit.server.user.service;

import cakeit.server.entity.UserEntity;
import cakeit.server.user.dto.UserDto;
import cakeit.server.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MemberService implements UserDetailsService {
    private UserRepository userRepository;

    // 회원가입
    @Transactional
    public Long signUp(UserDto userDto) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));

        // password를 암호화 한 뒤 dp에 저장

        return userRepository.save(userDto.toEntity()).getUserId();
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
}
