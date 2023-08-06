package cakeit.server.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "NIC_CAKE")
public class NickCakeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NICK_CAKE_ID")
    private Long nickCakeId;

    @Column(name = "WORD")
    private String word;
}
