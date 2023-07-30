package cakeit.server.cakeStore.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CakeStoreDetailResponseDto {

    private String storeName;

    private Double rating;

    private String weekday_text;

    private String telNum;

    private String storeIntro;

    private List<String> categories;

    private List<String> review;

    private String storeImage;

    private String likeYn;

}
