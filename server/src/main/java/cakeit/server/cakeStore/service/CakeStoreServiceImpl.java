package cakeit.server.cakeStore.service;

import cakeit.server.cakeStore.dto.*;
import cakeit.server.cakeStore.repository.CakeStoreRepository;
import cakeit.server.entity.CakeStoreEntity;
import cakeit.server.file.service.S3Service;
import com.amazonaws.util.IOUtils;
import com.squareup.okhttp.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class CakeStoreServiceImpl implements CakeStoreService {

    private final CakeStoreRepository cakeStoreRepository;
    private final S3Service s3Service;

    @Value("${your-api-key}")
    private String YOUR_API_KEY;

    @Value("${target-path}")
    private String targetPath;


    @Override
    public List<GetCakeStoreListResponseDto> getCakeStoreListByLatitudeAndLongitude(GetCakeStoreListRequestDto getCakeStoreListRequestDto) throws IOException, JSONException {

        Double longitude = getCakeStoreListRequestDto.getLongitude();
        Double latitude = getCakeStoreListRequestDto.getLatitude();

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
        log.info(jsonObj.toString());
        String storeAddress = (String) jsonObj.get("formatted_address");        // 주소
        String uploadUrl = null;                                                // 가게 이미지

        if (jsonObj.has("photos")){
            JSONArray photos = (JSONArray) jsonObj.get("photos");               // photo reference 가져오기
            String photo_reference = (String) ((JSONObject) photos.get(0)).get("photo_reference");
            uploadUrl = getCakeStoreImageFromGoogleAPI(photo_reference);
        }

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
        String storeIntroduce = storeName + "입니다.";
        Double storeRating = Double.valueOf(storeRatingStr);

        CakeStoreEntity cse = CakeStoreEntity.builder()
                .storeName(storeName)
                .storePhonenumber(storeTel)
                .storeScore(storeRating)
                .storeTime(weekText)
                .latitude(latitude)
                .longitude(longitude)
                .storeImage(uploadUrl)
                .storeIntroduce(storeIntroduce)
                .placeId(placeId).build();

        cakeStoreRepository.save(cse);

    }

    @Override
    public String getCakeStoreImageFromGoogleAPI(String photoReference) throws IOException {

        int maxWidth = 400;
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://maps.googleapis.com/maps/api/place/photo?" + "maxwidth=" + maxWidth + "&photo_reference=" + photoReference + "&key=" + YOUR_API_KEY)
                .build();

        Response response = client.newCall(request).execute();
        String responseString = response.toString();
        String[] spliter = responseString.split("url=");
        String imageUrl = spliter[1].substring(0, spliter[1].length() - 1);
        log.info("imageUrl =============== " + imageUrl);

//        String targetPath = "-"; // 임시 저장소

        try (InputStream in = new URL(imageUrl).openStream()) {

            Files.copy(in, Paths.get(targetPath), StandardCopyOption.REPLACE_EXISTING);
            File file = new File(targetPath);
            FileItem fileItem = new DiskFileItem("mainFile", Files.probeContentType(file.toPath()), false, file.getName(), (int) file.length(), file.getParentFile());
            IOUtils.copy(new FileInputStream(file), fileItem.getOutputStream());
            MultipartFile multipartFile = new CommonsMultipartFile(fileItem);
            String uploadUrl = s3Service.uploadFileWithUUID(multipartFile);

            return uploadUrl;

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return null;

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
