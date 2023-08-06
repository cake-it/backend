package cakeit.server.user.repository;

import cakeit.server.entity.NickCakeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

public interface NickCakeRepository extends JpaRepository<NickCakeEntity, Long> {

    @Query("select count(nce.nickCakeId) from NickCakeEntity nce")
    Long findTotalNickCakeEntityNumber();

}
