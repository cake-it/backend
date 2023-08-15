package cakeit.server.user.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponseDto {

    private Long userId;
    private String loginId;
    private String nickname;
    private String profileImage;
    private String gender;
    private String accessToken;
    private String refreshToken;

}
