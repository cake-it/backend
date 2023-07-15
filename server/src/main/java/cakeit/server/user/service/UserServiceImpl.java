package cakeit.server.user.service;

import cakeit.server.entity.UserEntity;
import cakeit.server.user.dto.UserDto;
import cakeit.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Long join(UserDto userDto) {
        log.info("여기는 오나? UserServiceImpl");
        UserEntity userEntity = UserEntity.builder()
                .loginId(userDto.getLoginId())
                .password(userDto.getPassword())
                .nickname(userDto.getNickname())
                .age(Long.valueOf(userDto.getAge()))
                .profileImage(userDto.getProfileImage())//
                .gender(userDto.getGender())
        .build();

        return userRepository.save(userEntity).getUserId();
    }
}