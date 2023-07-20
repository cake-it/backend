package cakeit.server.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table (name = "REVIEWS")
public class ReviewEntity extends AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "REVIEW_ID")
	private Long reviewId;

	/** FK setting */
	@ManyToOne(fetch = FetchType.LAZY)
	// @OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(referencedColumnName = "STORE_ID", name = "STORE_ID")
	private CakeStoreEntity storeId;

   	@Column(name = "CONTENT")
	private String content;


}
