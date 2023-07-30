package cakeit.server.cakeStore.service;

import cakeit.server.cakeStore.dto.*;
import cakeit.server.cakeStore.repository.CakeStoreRepository;
import cakeit.server.entity.CakeStoreEntity;
import com.squareup.okhttp.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CakeStoreServiceImpl implements CakeStoreService {

    private final CakeStoreRepository cakeStoreRepository;
    private static final String YOUR_API_KEY = "-";

    @Override
    public List<GetCakeStoreListResponseDto> getCakeStoreListByLatitudeAndLongitude(GetCakeStoreListRequestDto getCakeStoreListRequestDto) throws IOException, JSONException {

        Double longitude = getCakeStoreListRequestDto.getLongitude();
        Double latitude = getCakeStoreListRequestDto.getLatitude();
        log.info(" ================================ " + longitude);
        List<String> placeIdList = getNearbyCakeStoreListFromGoogleAPI(latitude, longitude);
        log.info(placeIdList.toString());
        List<GetCakeStoreListResponseDto> getCakeStoreListResponseDtos = new ArrayList<>();

        for (String placeId : placeIdList) {
            Optional<CakeStoreEntity> cseOptional = cakeStoreRepository.findByPlaceId(placeId);

            if (cseOptional.isEmpty()) {
                continue;
            }

            CakeStoreEntity cse = cseOptional.get();
            System.out.println(cse.toString());
            GetCakeStoreListResponseDto cakeStoreListResponseDto = GetCakeStoreListResponseDto.builder()
                    .cakeId(cse.getStoreId())
                    .longitude(cse.getLongitude())
                    .latitude(cse.getLatitude()).build();
            getCakeStoreListResponseDtos.add(cakeStoreListResponseDto);
            log.info("cakeStoreListResponseDto =============" + cakeStoreListResponseDto.toString());
        }

        return getCakeStoreListResponseDtos;
    }

    @Override
    public List<String> getNearbyCakeStoreListFromGoogleAPI(Double latitude, Double longitude) throws IOException, JSONException {

        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://maps.googleapis.com/maps/api/place/nearbysearch/json?keyword=수제 케이크&" +
                        "location=" + latitude + "%2C" + longitude + "&radius=5000&key=" + YOUR_API_KEY)
                .build();
        log.info(request.toString());
        Response response = client.newCall(request).execute();

        //Response형 => String형
        String cakeStoreString = response.body().string();

        // String형 => json형
        JSONObject cakeStoreJson = new JSONObject(cakeStoreString);

        JSONArray jsonArr = (JSONArray) cakeStoreJson.get("results");

        List<String> placeIdList = new ArrayList<>();

        for (int i = 0; i < jsonArr.length(); i++) {
            JSONObject jsonObj = (JSONObject) jsonArr.get(i);
            placeIdList.add((String) jsonObj.get("place_id"));
        }

        return placeIdList;
    }

    @Override
    public void getCakeStoreInfoFromGoogleAPI(String placeId) throws IOException, JSONException {

        if (cakeStoreRepository.existsByPlaceId(placeId))   {
            return;
        }

        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://maps.googleapis.com/maps/api/place/details/json?" + "place_id=" + placeId + "&language=ko" + "&key=" + YOUR_API_KEY)
                .build();
        Response response = client.newCall(request).execute();

        // Response to String
        String cakeStoreString = response.body().string();

        // String to Json
        JSONObject cakeStoreJson = new JSONObject(cakeStoreString);

        JSONObject jsonObj = cakeStoreJson.getJSONObject("result");

        String storeAddress = (String) jsonObj.get("formatted_address");        // 주소
        Double latitude = (Double) ((JSONObject)((JSONObject) jsonObj.get("geometry")).get("location")).get("lat");         // 위도
        Double longitude = (Double) ((JSONObject)((JSONObject) jsonObj.get("geometry")).get("location")).get("lng");        // 경도

        JSONArray weekTextJson = ((JSONObject) jsonObj.get("opening_hours")).getJSONArray("weekday_text");
        ArrayList<String> weekTextList = new ArrayList<>();                     // 영업시간
        for (int i =0; i<weekTextJson.length(); i++) {
            weekTextList.add((String) weekTextJson.get(i));
        }

        String weekText = weekTextList.toString();
        String storeTel = (String) jsonObj.get("formatted_phone_number");               // 전화번호
        String storeName = (String) jsonObj.get("name");                                // 가게명
        String storeRatingStr = jsonObj.get("rating").toString();                       // 별점
        Double storeRating = Double.valueOf(storeRatingStr);

        CakeStoreEntity cse = CakeStoreEntity.builder()
                .storeName(storeName)
                .storePhonenumber(storeTel)
                .storeScore(storeRating)
                .storeTime(weekText)
                .latitude(latitude)
                .longitude(longitude)
                .placeId(placeId).build();

        cakeStoreRepository.save(cse);

    }

    /**
     * 추후 수정 및 고민할 부분
     *  1. 마커 눌렀을 때 storeId 다시 주는 게 좋나?
     *  2. likeYn 더미데이터 추후 변경 필요!!!
     *  3. likeYn 가져올 수 있는 서비스 확인하고 가져오기
     */
    @Override
    public CakeStoreBriefResponseDto getCakeStoreBriefDetail(CakeStoreBriefRequestDto requestDto) {

        Long storeId = requestDto.getStoreId();
        Long userId = requestDto.getUserId();

        CakeStoreEntity cse = cakeStoreRepository.findById(storeId).orElseThrow(() -> new NoSuchElementException("등록되지 않은 케이크점입니다!"));
        CakeStoreBriefResponseDto csbrDto = CakeStoreBriefResponseDto.builder()
                .storeName(cse.getStoreName())
                .rating(cse.getStoreScore())
                .weekday_text(cse.getStoreTime())
                .storeImage(cse.getStoreImage())
                .likeYn("N")
                .build();

        return csbrDto;
    }


    /**
     * 추후 수정 및 고민할 부분
     *  1. 마커 눌렀을 때 storeId 다시 주는 게 좋나?
     *  2. likeYn, categories, review 더미데이터 추후 변경 필요!!!
     *  3. likeYn 가져올 수 있는 서비스 확인하고 가져오기
     */
    @Override
    public CakeStoreDetailResponseDto getCakeStoreInfoDetail(CakeStoreDetailRequestDto requestDto) {

        Long storeId = requestDto.getStoreId();
        Long userId = requestDto.getUserId();

        CakeStoreEntity cse = cakeStoreRepository.findById(storeId).orElseThrow(() -> new NoSuchElementException("등록되지 않은 케이크점입니다!"));

        CakeStoreDetailResponseDto csdrDto = CakeStoreDetailResponseDto.builder()
                .storeName(cse.getStoreName())
                .rating(cse.getStoreScore())
                .weekday_text(cse.getStoreTime())
                .storeImage(cse.getStoreImage())
                .storeIntro(cse.getStoreIntroduce())
                .telNum(cse.getStorePhonenumber())
                .likeYn("N")
                .categories(null)
                .review(null)
                .build();

        return csdrDto;
    }


}
