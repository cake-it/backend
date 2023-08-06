package cakeit.server.user.repository;

import cakeit.server.entity.NickAdjectiveEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NickAdjectiveRepository extends JpaRepository<NickAdjectiveEntity, Long> {

    @Query("select count(nae.nickAdjId) from NickAdjectiveEntity nae")
    Long findTotalNickAdjectiveEntityNumber();

}
