package cakeit.server.cakeStore.repository;

import cakeit.server.entity.CakeStoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CakeStoreRepository extends JpaRepository<CakeStoreEntity, Long> {

    CakeStoreEntity findByStoreName(String storeName);

    Optional<CakeStoreEntity> findByPlaceId(String placeId);
    boolean existsByPlaceId(String placeId);

}
