package cakeit.server.cakeStore.service;

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
        String s = cakeStoreService.getNearbyCakeStoreListFromGoogleAPI(Double.valueOf("37.4992131"), Double.valueOf("127.0280048")).get(0);
        cakeStoreService.getCakeStoreInfoFromGoogleAPI(s);
    }
}