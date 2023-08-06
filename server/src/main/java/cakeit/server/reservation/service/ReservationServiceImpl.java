package cakeit.server.reservation.service;

import cakeit.server.cakeStore.repository.CakeStoreRepository;
import cakeit.server.entity.CakeStoreEntity;
import cakeit.server.entity.ReservationEntity;
import cakeit.server.entity.UserEntity;
import cakeit.server.reservation.dto.PostReservationDetailDto;
import cakeit.server.reservation.repository.ReservationRepository;
import cakeit.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final UserRepository userRepository;
    private final CakeStoreRepository cakeStoreRepository;
    private final ReservationRepository reservationRepository;

    @Transactional
    @Override
    public void saveReservationDetail(PostReservationDetailDto detailDto){

        try {

            ReservationEntity reservationEntity = checkDuplication(detailDto);
            if (reservationEntity.getUserCnt() > 5) {
                throw new RuntimeException("예약 인원이 다 찼습니다!!!!!!!!!!");
            }
            reservationRepository.save(reservationEntity);
        } catch (Exception e) {
            log.info(e.getMessage());
            throw e;
        }
    }

    private ReservationEntity checkDuplication(PostReservationDetailDto detailDto) {

        UserEntity userId = userRepository.findById(detailDto.getUserId()).orElseThrow(() -> new RuntimeException("존재하지 않는 유저ID입니다."));
        CakeStoreEntity cakeStoreId = cakeStoreRepository.findById(detailDto.getCakeStoreId()).orElseThrow(() -> new NoSuchElementException("존재하지 않는 케이크점ID입니다."));

        LocalDateTime reservationDate = detailDto.getReservationDate();
        String reservationCheck = detailDto.getCakeStoreId().toString() + " || " + reservationDate.toString();

        Optional<ReservationEntity> reservationEntity = reservationRepository.findByStoreIdAndReservationDate(cakeStoreId, reservationDate);

        if (reservationEntity.isPresent()) {
            reservationEntity.get().increaseUserCnt();
            return reservationEntity.get();
        } else {
            ReservationEntity createReservationEntity = ReservationEntity.builder()
                    .storeId(cakeStoreId)
                    .reservationDate(reservationDate)
                    .reservationDetail(detailDto.getDetail())
                    .reservationCheck(reservationCheck)
                    .userCnt(1)
                    .reservationImage(detailDto.getReservationImage()).build();

            return createReservationEntity;
        }

    }

}
