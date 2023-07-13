package cakeit.server.user.service;

import cakeit.server.entity.UserEntity;
import cakeit.server.user.dto.UserDto;
import cakeit.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Long join(UserDto userDto) {
        UserEntity userEntity = UserEntity.builder()

                .loginId(UserDto.getLoginId())
                .password(UserDto.getPassword())
                .nickname(UserDto.getNickname())
                .age(UserDto.getAge())
                .gender(UserDto.getGender())
                .profileImage(UserDto.getProfileImage())
                .build();

        return userRepository.save(userEntity).getUserId();
    }
}