package cakeit.server.user.dto;

import cakeit.server.entity.UserEntity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpRequestDto {

    private String loginId;

    private String password;

    private String profileImage;

    private Long age;

    private String purpose;

}