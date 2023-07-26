package cakeit.server.reservation.service;

import cakeit.server.reservation.dto.PostReservationDetailDto;

public interface ReservationService {

    /**
     * 케이크 예약 저장 메서드
     */
    public void saveReservationDetail(PostReservationDetailDto detailDto);

}
