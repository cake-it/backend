package cakeit.server.cakeStore.controller;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cake-store")
public class CakeStoreController {

    private final CakeStoreServiceImpl cakeStoreService;

    @GetMapping("/test")
    public String test() {
        return "hello";
    }

    @GetMapping("/")
    public ResponseEntity<CommonResponse<List<GetCakeStoreListResponseDto>>> findCakeStoreList(@Valid @RequestBody GetCakeStoreListRequestDto reqDto) {

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

}
