package cakeit.server.user.dto;

import cakeit.server.entity.AbstractEntity;
import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String userid;
    private String loginId;
    private String password;
    private String nickname;
    private String age;
    private String gender;
    private String profileImage;
}
