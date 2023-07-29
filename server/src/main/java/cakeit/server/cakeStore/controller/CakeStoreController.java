package cakeit.server.cakeStore.controller;

import cakeit.server.cakeStore.dto.CakeStoreBriefRequestDto;
import cakeit.server.cakeStore.dto.CakeStoreBriefResponseDto;
import cakeit.server.cakeStore.dto.GetCakeStoreListRequestDto;
import cakeit.server.cakeStore.dto.GetCakeStoreListResponseDto;
import cakeit.server.cakeStore.service.CakeStoreServiceImpl;
import cakeit.server.global.CommonResponse;
import cakeit.server.global.exception.ErrorEnum;
import cakeit.server.global.exception.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cake")
public class CakeStoreController {

    private final CakeStoreServiceImpl cakeStoreService;

    @GetMapping("/test")
    public String test() {
        return "hello";
    }

    @GetMapping("/")
    public ResponseEntity<CommonResponse<List<GetCakeStoreListResponseDto>>> findCakeStoreList(@Valid @ModelAttribute GetCakeStoreListRequestDto reqDto) {

        try {
            List<GetCakeStoreListResponseDto> getCakeStoreListResponseDtos = cakeStoreService.getCakeStoreListByLatitudeAndLongitude(reqDto);
            return new ResponseEntity<>(CommonResponse.success("주변 케이크점입니다.", getCakeStoreListResponseDtos), HttpStatus.OK);
        } catch (JSONException e ) {
            return new ResponseEntity<>(CommonResponse.fail("주변에 케이크점이 없습니다.", ErrorResponse.builder()
                    .errorCode(ErrorEnum.NOT_FOUND.getCode()).build()), HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            return new ResponseEntity<>(CommonResponse.fail("주변에 케이크점이 없습니다.", ErrorResponse.builder()
                    .errorCode(ErrorEnum.NOT_FOUND.getCode()).build()), HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/store")
    public ResponseEntity<CommonResponse<CakeStoreBriefResponseDto>> findCakeStoreBriefDetail(@Valid @ModelAttribute CakeStoreBriefRequestDto reqDto) {

        try {
            CakeStoreBriefResponseDto cakeStoreBriefResponseDto = cakeStoreService.getCakeStoreBriefDetail(reqDto.getStoreId());
            return new ResponseEntity<>(CommonResponse.success("케이크점 가게 정보입니다.", cakeStoreBriefResponseDto), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(CommonResponse.fail("케이크점 정보가 없습니다.", ErrorResponse.builder()
                    .errorCode(ErrorEnum.NOT_FOUND.getCode()).build()), HttpStatus.NOT_FOUND);
        }

    }

}
