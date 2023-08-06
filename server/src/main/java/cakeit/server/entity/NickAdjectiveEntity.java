package cakeit.server.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "NIC_ADJECTIVE")
public class NickAdjectiveEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NICK_ADJ_ID")
    private Long nickAdjId;

    @Column(name = "WORD")
    private String word;
}
