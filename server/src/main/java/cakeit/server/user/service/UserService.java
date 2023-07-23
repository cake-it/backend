package cakeit.server.user.service;

import cakeit.server.entity.UserEntity;
import cakeit.server.user.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public interface UserService {
//    String join(UserDto userDto);
    void join(UserDto userDto);

    UserDetails login(String loginId);

    UserDetails loadUserByLoginId(String loginId) throws UsernameNotFoundException;

    Optional<UserEntity> findByLoginId(String loginId);

}
