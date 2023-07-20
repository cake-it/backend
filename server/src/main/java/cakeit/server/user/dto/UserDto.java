package cakeit.server.user.dto;

import cakeit.server.entity.UserEntity;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {

    private Long userId;
    private String loginId;
    private String password;
    private String nickname;
    private Long age;
    private String gender;
    private String profileImage;

    //User 객체로 변환
    public UserEntity toEntity() {
        return UserEntity.builder()
                .userId(userId)
                .loginId(loginId)
                .password(password)
                .nickname(nickname)
                .age(age)
                .profileImage(profileImage)//
                .gender(gender)
                .build();
    }

    @Builder
    public UserDto(Long userId, String loginId, String password, String nickname, Long age, String profileImage,
                   String gender){
        this.userId = userId;
        this.loginId = loginId;
        this.password = password;
        this.nickname = nickname;
        this.age = age;
        this.profileImage = profileImage;
        this.gender = gender;
    }
}
