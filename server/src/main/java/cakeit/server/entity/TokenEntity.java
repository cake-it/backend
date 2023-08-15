package cakeit.server.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "TOKENS")
public class TokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TOKEN_ID")
    private Long tokenId;

    @Column(name = "ACCESS_TOKEN")
    private String accessToken;

    @Column(name = "REFRESH_TOKEN")
    private String refreshToken;

}
