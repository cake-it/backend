package cakeit.server.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table (name = "HASHTAGS")
public class HashtagEntity extends AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "HASHTAG_ID")
	private Long hashtagId;

   	/** FK setting */
	 @ManyToOne(fetch = FetchType.LAZY)
	// @OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(referencedColumnName = "STORE_ID", name = "STORE_ID")
	private CakeStoreEntity storeId;

   	@Column(name = "NAME")
	private String name;


}
