package cakeit.server.cakeStore.service;

import cakeit.server.cakeStore.dto.GetCakeStoreListRequestDto;
import cakeit.server.cakeStore.dto.GetCakeStoreListResponseDto;
import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public interface CakeStoreService {

    /**
     * 위도, 경도로 케이크점 리스트 받는 메서드
     */
    public List<GetCakeStoreListResponseDto> getCakeStoreListByLatitudeAndLongitude(GetCakeStoreListRequestDto getCakeStoreListRequestDto) throws IOException, JSONException;

    /**
     * 구글 API를 통해 주변 케이크점 place_id 리스트 추출하는 메서드
     */
    public List<String> getNearbyCakeStoreListFromGoogleAPI(Double latitude, Double longitude) throws IOException, JSONException;

    /**
     * 구글 API 사용해서 place_id로 특정 케이크점 정보 추출해서 CakeStoreEntity 생성하는 메서드
     */
    public void getCakeStoreInfoFromGoogleAPI(String placeId) throws IOException, JSONException;


}
