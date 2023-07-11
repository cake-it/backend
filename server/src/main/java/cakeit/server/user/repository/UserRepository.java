package cakeit.server.user.repository;

import cakeit.server.entity.CakeStoreEntity;
import cakeit.server.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

}