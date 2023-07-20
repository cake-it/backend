package cakeit.server.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table (name = "LIKES")
public class LikeEntity extends AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "LIKE_ID")
	private Long likeId;

	/** FK setting */
	@ManyToOne(fetch = FetchType.LAZY)
	// @OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(referencedColumnName = "STORE_ID", name = "STORE_ID")
	private CakeStoreEntity storeId;

   	/** FK setting */
	 @ManyToOne(fetch = FetchType.LAZY)
	// @OneToOne(fetch = FetchType.LAZY)
	 @JoinColumn(referencedColumnName = "USER_ID", name = "USER_ID")
	private UserEntity userId;

   	@Column(name = "LIKE_YN")
	private String likeYn;


}
