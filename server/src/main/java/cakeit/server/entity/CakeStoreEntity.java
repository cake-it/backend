package cakeit.server.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table (name = "CAKE_STORES")
public class CakeStoreEntity extends AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "STORE_ID")
	private Long storeId;

	@Column(name = "PLACE_ID")
	private String placeId;

   	@Column(name = "STORE_NAME")
	private String storeName;

   	@Column(name = "STORE_SCORE")
	private Double storeScore;

   	@Column(name = "STORE_TIME")
	private String storeTime;

   	@Column(name = "STORE_PHONENUMBER")
	private String storePhonenumber;

   	@Column(name = "STORE_INTRODUCE")
	private String storeIntroduce;

   	@Column(name = "LATITUDE")
	private Double latitude;

   	@Column(name = "LONGITUDE")
	private Double longitude;

   	@Column(name = "STORE_IMAGE")
	private String storeImage;


}
