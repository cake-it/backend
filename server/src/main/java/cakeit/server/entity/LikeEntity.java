package cakeit.server.entity;

import javax.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table (name = "LIKES")
public class LikeEntity extends AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "LIKE_ID")
	private Long likeId;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "USER_ID")
	private UserEntity userEntity;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "STORE_ID")
	private CakeStoreEntity storeEntity;

	 public LikeEntity(CakeStoreEntity storeEntity, UserEntity userEntity){
         this.storeEntity = storeEntity;
         this.userEntity = userEntity;
     }
}
