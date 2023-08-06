package cakeit.server.cakeStore.service;

import cakeit.server.cakeStore.dto.CakeStoreBriefResponseDto;
import cakeit.server.cakeStore.dto.GetCakeStoreListRequestDto;
import cakeit.server.cakeStore.dto.GetCakeStoreListResponseDto;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CakeStoreServiceImplTest {

    @Autowired
    private CakeStoreServiceImpl cakeStoreService;

    @Test
    void getNearbyCakeStoreListFromGoogleAPI() throws IOException, JSONException {

        GetCakeStoreListRequestDto getCakeStoreListRequestDto = GetCakeStoreListRequestDto.builder().
                latitude(Double.valueOf("37.4992131")).
                longitude(Double.valueOf("127.0280048")).
                build();
        List<GetCakeStoreListResponseDto> cakeStoreListByLatitudeAndLongitude = cakeStoreService.getCakeStoreListByLatitudeAndLongitude(getCakeStoreListRequestDto);

    }


    @Test
    void getCakeStoreInfoFromGoogleAPI() throws JSONException, IOException {
        String s = cakeStoreService.getNearbyCakeStoreListFromGoogleAPI(Double.valueOf("37.4992131"), Double.valueOf("127.0280048")).get(1);
        cakeStoreService.getCakeStoreInfoFromGoogleAPI(s);
    }

    @Test
    void getCakeStoreBriefDetail() {

//        CakeStoreBriefResponseDto cakeStoreBriefDetail = cakeStoreService.getCakeStoreBriefDetail(4L);
//        System.out.println(cakeStoreBriefDetail.getStoreName());
//        System.out.println(cakeStoreBriefDetail.getWeekday_text());
    }

    @Test
    void getCakeStoreImageFromGoogleAPI() throws JSONException, IOException {

        String photoReference = "Aaw_FcKUrRa_n7Xuwk0X6OyKjFFHKOh1jQoz8AdBPjIY-MfKKJsPuXcgMvsDiZ3X8tky6FhnD3QtK3UnfSPW5a_U9pwHFZJ0SHuBrX7Fuoy0OJcigBOi83YSFkXCVAvehvLGW8lWDWlghtujJQvwoIu-XaPqJWLx1v3Ehr196pzxmTzuDybD";
        String api = cakeStoreService.getCakeStoreImageFromGoogleAPI(photoReference);
    }

}