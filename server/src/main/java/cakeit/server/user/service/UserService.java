package cakeit.server.user.service;

import cakeit.server.user.dto.UserDto;

public interface UserService {
    Long join(UserDto userDto);
}
