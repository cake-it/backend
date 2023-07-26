package cakeit.server.reservation.controller;

import cakeit.server.global.CommonResponse;
import cakeit.server.global.exception.ErrorEnum;
import cakeit.server.global.exception.ErrorResponse;
import cakeit.server.reservation.dto.PostReservationDetailDto;
import cakeit.server.reservation.service.ReservationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationServiceImpl reservationService;

    @PostMapping("/")
    public ResponseEntity<CommonResponse<String>> addReservation(@Valid @RequestBody PostReservationDetailDto reqDto){

        try {
            reservationService.saveReservationDetail(reqDto);
            return new ResponseEntity<>(CommonResponse.success("예약 성공"), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(CommonResponse.fail("예약 실패", ErrorResponse.builder()
                    .errorCode(ErrorEnum.ALREADY_BOOKED.getCode()).build()), HttpStatus.SERVICE_UNAVAILABLE);
        }

    }

}
