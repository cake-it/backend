package cakeit.server.reservation.service;

import cakeit.server.cakeStore.repository.CakeStoreRepository;
import cakeit.server.entity.CakeStoreEntity;
import cakeit.server.entity.UserEntity;
import cakeit.server.reservation.dto.PostReservationDetailDto;
import cakeit.server.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
//@Transactional
class ReservationServiceImplTest {

    @Autowired
    private ReservationServiceImpl reservationService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CakeStoreRepository cakeStoreRepository;

    Logger log = (Logger) LoggerFactory.getLogger(ReservationServiceImplTest.class);

    //    @Transactional
    @Test
    public void saveReservationDetailTest() throws InterruptedException {

        LocalDateTime reservationDate = LocalDateTime.of(2023, 5, 31, 12, 35);
//        LocalDateTime reservationDate = LocalDateTime.now();

        log.info("동시성 테스트 시작");

        // Given
        log.info("동시성 테스트 준비");
        int numberOfThreads = 3;
        ExecutorService service = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);

        CakeStoreEntity cse = cakeStoreRepository.findByStoreName("케이크1");
        CakeStoreEntity cse2 = cakeStoreRepository.findByStoreName("케이크2");
        UserEntity ue = userRepository.findByNickname("초롱초롱");
        UserEntity ue2 = userRepository.findByNickname("초롱초롱2");
        UserEntity ue3 = userRepository.findByNickname("초롱초롱3");

        PostReservationDetailDto detailDto = PostReservationDetailDto.builder()
                .cakeStoreId(cse.getStoreId())
                .userId(ue.getUserId())
                .reservationDate(reservationDate)
                .detail("detail!!!")
                .reservationImage("image.1234").build();


        PostReservationDetailDto FalseDetailDto = PostReservationDetailDto.builder()
                .cakeStoreId(cse.getStoreId())
                .userId(ue2.getUserId())
                .reservationDate(reservationDate)
                .detail("detail22!!!")
                .reservationImage("image.22222").build();


        PostReservationDetailDto FalseDetailDto2 = PostReservationDetailDto.builder()
                .cakeStoreId(cse.getStoreId())
                .userId(ue3.getUserId())
                .reservationDate(reservationDate)
                .detail("detail33!!!")
                .reservationImage("image.33333").build();

        // then
        log.info("동시성 테스트 진행");

        service.execute(() -> {
            reservationService.saveReservationDetail(detailDto);
            latch.countDown();
            log.info("synchronized 11111111111111");
        });

        service.execute(() -> {
            reservationService.saveReservationDetail(FalseDetailDto);
            latch.countDown();
            log.info("synchronized 222222222222222222");
        });

        service.execute(() -> {
            reservationService.saveReservationDetail(FalseDetailDto2);
            latch.countDown();
            log.info("synchronized 333333333333333333333");
        });

        latch.await();

    }

    @Test
    public void saveReservationDetailNoConcurrencyTest() throws InterruptedException {

        log.info("동시성 테스트 시작");

        // Given
        log.info("동시성 테스트 준비");

        CakeStoreEntity cse = cakeStoreRepository.findByStoreName("케이크1");
        UserEntity ue = userRepository.findByNickname("초롱초롱");
        UserEntity ue2 = userRepository.findByNickname("초롱초롱2");
        UserEntity ue3 = userRepository.findByNickname("초롱초롱3");

        PostReservationDetailDto detailDto = PostReservationDetailDto.builder()
                .cakeStoreId(cse.getStoreId())
                .userId(ue.getUserId())
                .reservationDate(LocalDateTime.of(2023, 5, 31, 12, 33))
                .detail("detail!!!")
                .reservationImage("image.1234").build();


        PostReservationDetailDto FalseDetailDto = PostReservationDetailDto.builder()
                .cakeStoreId(cse.getStoreId())
                .userId(ue2.getUserId())
                .reservationDate(LocalDateTime.of(2023, 5, 31, 12, 33))
                .detail("detail22!!!")
                .reservationImage("image.54321").build();


        PostReservationDetailDto FalseDetailDto2 = PostReservationDetailDto.builder()
                .cakeStoreId(cse.getStoreId())
                .userId(ue3.getUserId())
                .reservationDate(LocalDateTime.of(2023, 5, 31, 12, 33))
                .detail("detail22!!!")
                .reservationImage("image.54321").build();

        // then
        log.info("동시성 테스트 진행");

        reservationService.saveReservationDetail(detailDto);
        log.info("synchronized 11111111111111");

        reservationService.saveReservationDetail(FalseDetailDto);
        log.info("synchronized 222222222222222222");

        reservationService.saveReservationDetail(FalseDetailDto2);
        log.info("synchronized 333333333333333333333");


    }

    //    @Transactional
    @Test
    public void saveReservationDetailNoThreadTest(){

        LocalDateTime reservationDate = LocalDateTime.of(2023, 5, 31, 12, 33);

        // Given
//        LocalDateTime reservationDate = LocalDateTime.now();
        CakeStoreEntity cse = cakeStoreRepository.findByStoreName("케이크1");
        UserEntity ue = userRepository.findByNickname("초롱초롱");

        PostReservationDetailDto detailDto = PostReservationDetailDto.builder()
                .cakeStoreId(cse.getStoreId())
                .userId(ue.getUserId())
                .reservationDate(reservationDate)
                .detail("detail!!!")
                .reservationImage("image.1234").build();


        PostReservationDetailDto FalsedetailDto = PostReservationDetailDto.builder()
                .cakeStoreId(cse.getStoreId())
                .userId(ue.getUserId())
                .reservationDate(reservationDate)
                .detail("detail22!!!")
                .reservationImage("image.54321").build();

//        // then
//        boolean check = reservationService.saveReservationDetail(detailDto);
//        System.out.println(check + " =================================================================== ");
//        assertThat(check).isEqualTo(true);
//
//        boolean Falsecheck = reservationService.saveReservationDetail(FalsedetailDto);
//        System.out.println(Falsecheck + " =================================================================== ");
//        assertThat(Falsecheck).isEqualTo(false);

    }

}