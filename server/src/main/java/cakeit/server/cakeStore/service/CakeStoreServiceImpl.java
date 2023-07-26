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

        List<String> placeIdList = getnearbyCakeStoreListFromGoogleAPI(longitude, latitude);

        List<GetCakeStoreListResponseDto> getCakeStoreListResponseDtos = new ArrayList<>();

        for (String placeId : placeIdList) {
            CakeStoreEntity byPlaceId = cakeStoreRepository.findByPlaceId(placeId);
            GetCakeStoreListResponseDto cakeStoreListResponseDto = GetCakeStoreListResponseDto.builder()
                    .cakeId(byPlaceId.getStoreId())
                    .longitude(byPlaceId.getLongitude())
                    .latitude(byPlaceId.getLatitude()).build();
            getCakeStoreListResponseDtos.add(cakeStoreListResponseDto);
        }

        return getCakeStoreListResponseDtos;
    }

//    public static List<GetCakeStoreListResponseDto> getnearbyCakeStoreListFromGoogleAPI() throws IOException {
    public static List<String> getnearbyCakeStoreListFromGoogleAPI(Double longitude,Double latitude) throws IOException, JSONException {

        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://maps.googleapis.com/maps/api/place/nearbysearch/json?keyword=수제 케이크&" +
                        "location=" + longitude + "%2C" + latitude + "&radius=5000&key=" + YOUR_API_KEY)
                .build();

        Response response = client.newCall(request).execute();

        //Response형 -> String형
        String cakeStoreString = response.body().string();

        // String형 ->json형
        JSONObject cakeStoreJson = new JSONObject(cakeStoreString);

        JSONArray jsonArr = (JSONArray)cakeStoreJson.get("results");

        List<String> placeIdList = new ArrayList<>();

        for(int i=0; i<jsonArr.length(); i++){
            JSONObject jsonObj = (JSONObject)jsonArr.get(i);
            placeIdList.add((String) jsonObj.get("place_id"));
        }


        return placeIdList;
    }

}
