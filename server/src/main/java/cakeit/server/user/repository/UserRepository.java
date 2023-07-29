package cakeit.server.user.repository;

import cakeit.server.entity.UserEntity;
import cakeit.server.user.dto.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByNickname(String nickname);

    UserEntity findByNickname(String nickname);

    Optional<UserEntity> findByLoginId(String username);

    // 네이티브 SQL로 조회
    @Query(value = "select login_id from users where login_id = :loginId", nativeQuery = true)
    String idcheck(@Param(value = "loginId") String loginId);

    void deleteAllByloginId(String loginId);

    Optional<UserEntity> findByUserId(String UserId);
}
