package cakeit.server.cakeStore.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetCakeStoreListRequestDto {

    private Double latitude;    // 위도 double
    private Double longitude;   // 경도 double

}
