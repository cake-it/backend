package cakeit.server.user.service;

import cakeit.server.entity.UserEntity;
import cakeit.server.user.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
//import org.springframework.security.core.userdetails.User;

public interface UserService {
    void join(UserDto userDto);

    UserDetails login(String loginId);

    UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException;

    Optional<UserEntity> findByLoginId(String loginId);
}
