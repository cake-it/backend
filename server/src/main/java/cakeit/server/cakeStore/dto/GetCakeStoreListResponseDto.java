package cakeit.server.cakeStore.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetCakeStoreListResponseDto {

    private Long cakeId;        // 케이크점 ID
    private Double latitude;    // 위도 double
    private Double longitude;   // 경도 double

}
