package cakeit.server.reservation.repository;

import cakeit.server.entity.CakeStoreEntity;
import cakeit.server.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {


    //    @Lock(LockModeType.OPTIMISTIC)
//    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select r from ReservationEntity as r where r.reservationId = :reservationId")
    Optional<ReservationEntity> findByReservationId(@Param("reservationId") String reservationId);

    @Lock(LockModeType.OPTIMISTIC)
//    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select r from ReservationEntity as r where r.reservationCheck = :reservationCheck")
    Optional<ReservationEntity> findByReservationCheck(@Param("reservationCheck") String reservationCheck);


    //    @Lock(LockModeType.OPTIMISTIC)
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select r from ReservationEntity as r where r.storeId = :storeId and r.reservationDate = :reservationDate")
    Optional<ReservationEntity> findByStoreIdAndReservationDate(@Param("storeId") CakeStoreEntity storeId, @Param("reservationDate") LocalDateTime reservationDate);

    boolean existsByReservationCheck(String reservationCheck);

}
