package cakeit.server.likes.repository;

import cakeit.server.entity.CakeStoreEntity;
import cakeit.server.entity.LikeEntity;
import cakeit.server.entity.UserEntity;
import cakeit.server.user.dto.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Transactional
public interface LikesRepository extends JpaRepository<LikeEntity, Long> {
    Optional<LikeEntity> findByUserEntityAndStoreEntity(UserEntity userEntity, CakeStoreEntity storeEntity);

    @Modifying
    @Query(value = "delete from likes where user_entity_user_id = :userId and store_entity_store_id = :storeId", nativeQuery = true)
    void likeCancel(@Param(value = "storeId") Long storeId, @Param(value = "userId") Long userId);
}
