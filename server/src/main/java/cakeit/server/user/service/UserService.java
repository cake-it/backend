package cakeit.server.user.service;

import cakeit.server.entity.UserEntity;
import cakeit.server.user.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface UserService {
//    String join(UserDto userDto);

    public boolean join(UserDto userDto);

    UserDetails login(String loginId);

    UserDetails loadUserByLoginId(String loginId) throws UsernameNotFoundException;

    Optional<UserEntity> findByLoginId(String loginId);

    String idcheck(String loginId);

    void deleteUser(UserDto userDto);

}
