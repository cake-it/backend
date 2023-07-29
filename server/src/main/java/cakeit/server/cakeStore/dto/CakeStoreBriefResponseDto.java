package cakeit.server.cakeStore.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CakeStoreBriefResponseDto {

    private String storeName;

    private Double rating;

    private String weekday_text;

    private String storeImage;

    private String likeYn;

}
