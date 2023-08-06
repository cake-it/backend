package cakeit.server.reservation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostReservationDetailDto {

    // 유저 ID
    private Long userId;
    // 케이크점 ID
    private Long cakeStoreId;
    // 예약일시
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm")
    private LocalDateTime reservationDate;
    // 케이크 이미지
    private String reservationImage;
    // 세부 설명
    private String detail;

}
