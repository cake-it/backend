package cakeit.server.cakeStore.service;

import cakeit.server.cakeStore.dto.GetCakeStoreListRequestDto;
import cakeit.server.cakeStore.dto.GetCakeStoreListResponseDto;
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
    void getnearbyCakeStoreListFromGoogleAPI() throws IOException {

        GetCakeStoreListRequestDto getCakeStoreListRequestDto = GetCakeStoreListRequestDto.builder().
                latitude(Double.valueOf("123.123")).
                longitude(Double.valueOf("123.123")).
                build();
        List<GetCakeStoreListResponseDto> cakeStoreListByLatitudeAndLongitude = cakeStoreService.getCakeStoreListByLatitudeAndLongitude(getCakeStoreListRequestDto);

    }
}