package cakeit.server.user.service;

import cakeit.server.entity.UserEntity;
import cakeit.server.user.dto.LoginRequestDto;
import cakeit.server.user.dto.LoginResponseDto;
import cakeit.server.user.dto.SignUpRequestDto;
import cakeit.server.user.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public interface UserService {
//    String join(UserDto userDto);

    public boolean signUp(SignUpRequestDto signUpDto);

    LoginResponseDto login(LoginRequestDto reqDto);

    UserDetails loadUserByLoginId(String loginId) throws UsernameNotFoundException;

    Optional<UserEntity> findByLoginId(String loginId);

    String idcheck(String loginId);

    void deleteUser(UserDto userDto);

}
