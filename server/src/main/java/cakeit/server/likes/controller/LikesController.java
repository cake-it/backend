package cakeit.server.likes.controller;

import cakeit.server.entity.UserEntity;
import cakeit.server.global.CommonResponse;
import cakeit.server.global.exception.ErrorEnum;
import cakeit.server.global.exception.ErrorResponse;
import cakeit.server.likes.dto.LikesDto;
import cakeit.server.likes.service.LikesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.Objects;

import static cakeit.server.global.CommonResponse.success;

@Log4j2
@RestController
@RequiredArgsConstructor
public class LikesController {

    private final LikesService likesService;

    //케이크가게 찜
    @PostMapping("/cake/addLike/{storeId}")
    public ResponseEntity<CommonResponse<String>> addLike(@RequestBody UserEntity userEntity, @PathVariable Long storeId) {
        boolean result = false;
        log.info("환영해~~~" + userEntity);
        log.info("storeId>>>>>>>>>" + storeId);

        if (Objects.nonNull(userEntity)) {
            if (likesService.addLike(userEntity, storeId)) {
                return new ResponseEntity<>(CommonResponse.success("찜 성공"), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(CommonResponse.fail("찜 실패", ErrorResponse.builder()
                        .errorCode(ErrorEnum.ALREADY_BOOKED.getCode()).build()), HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(CommonResponse.fail("찜 실패", ErrorResponse.builder()
                    .errorCode(ErrorEnum.ALREADY_BOOKED.getCode()).build()), HttpStatus.OK);
        }
    }


    //케이크가게 찜 취소
    @DeleteMapping("/cake/cancelLike/{storeId}")
    public ResponseEntity<CommonResponse<String>> cancelLike(@RequestBody UserEntity userEntity, @PathVariable Long storeId) {
        boolean result = false;
        log.info("환영해~~~" + userEntity);
        log.info("storeId>>>>>>>>>" + storeId);

        if (Objects.nonNull(userEntity)) {
            if (likesService.cancelLike(userEntity, storeId)) {
                return new ResponseEntity<>(CommonResponse.success("찜 취소 성공"), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(CommonResponse.fail("찜 취소 실패", ErrorResponse.builder()
                        .errorCode(ErrorEnum.ALREADY_BOOKED.getCode()).build()), HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(CommonResponse.fail("찜 취소 실패", ErrorResponse.builder()
                    .errorCode(ErrorEnum.ALREADY_BOOKED.getCode()).build()), HttpStatus.OK);
        }
    }
} //- 목표 :

