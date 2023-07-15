package cakeit.server.user.dto;

import cakeit.server.entity.AbstractEntity;
import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long userId;
    private String loginId;
    private String password;
    private String nickname;
    private Long age;
    private String gender;
    private String profileImage;
}
