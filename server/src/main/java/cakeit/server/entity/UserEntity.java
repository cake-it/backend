package cakeit.server.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table (name = "USERS")
public class UserEntity extends AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USER_ID")
	private Long userId;

	@Column(name = "LOGIN_ID")
	private String loginId;

   	@Column(name = "PASSWORD")
	private String password;

   	@Column(name = "NICKNAME")
	private String nickname;

   	@Column(name = "AGE")
	private Long age;

   	@Column(name = "GENDER")
	private String gender;

   	@Column(name = "PROFILE_IMAGE")
	private String profileImage;

	@Builder
	public UserEntity(Long userId, String loginId, String password, String nickname, Long age, String profileImage,
					  String gender) {
		this.userId = userId;
		this.loginId = loginId;
		this.password = password;
		this.nickname = nickname;
		this.age = age;
		this.profileImage = profileImage;
		this.gender = gender;
	}
}

