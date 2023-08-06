package cakeit.server.entity;

import javax.persistence.*;

import cakeit.server.user.service.Role;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
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

	@Column(name = "PURPOSE")
	private String purpose;

	@Column(name = "IS_DELETED")
	private boolean isDeleted;

	@Column(name = "DELETED_AT")
	private LocalDateTime deletedAt;

	@Builder
	public UserEntity(String loginId, String password, String nickname) {
		this.loginId = loginId;
		this.password = password;
		this.nickname = nickname;
	}

}