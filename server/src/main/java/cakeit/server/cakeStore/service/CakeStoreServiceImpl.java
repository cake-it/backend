package cakeit.server.cakeStore.service;

import cakeit.server.cakeStore.dto.GetCakeStoreListRequestDto;
import cakeit.server.cakeStore.dto.GetCakeStoreListResponseDto;
import cakeit.server.cakeStore.repository.CakeStoreRepository;
import cakeit.server.entity.CakeStoreEntity;
import com.squareup.okhttp.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

        List<String> placeIdList = getNearbyCakeStoreListFromGoogleAPI(longitude, latitude);

        List<GetCakeStoreListResponseDto> getCakeStoreListResponseDtos = new ArrayList<>();

        for (String placeId : placeIdList) {
            CakeStoreEntity byPlaceId = cakeStoreRepository.findByPlaceId(placeId);
            GetCakeStoreListResponseDto cakeStoreListResponseDto = GetCakeStoreListResponseDto.builder()
                    .cakeId(byPlaceId.getStoreId())
                    .longitude(byPlaceId.getLongitude())
                    .latitude(byPlaceId.getLatitude()).build();
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

}
