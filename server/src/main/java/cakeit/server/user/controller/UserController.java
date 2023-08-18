package cakeit.server.user.controller;

import cakeit.server.file.service.S3Service;
import cakeit.server.global.CommonResponse;
import cakeit.server.global.exception.ErrorEnum;
import cakeit.server.global.exception.ErrorResponse;
import cakeit.server.user.dto.LoginRequestDto;
import cakeit.server.user.dto.LoginResponseDto;
import cakeit.server.user.dto.SignUpRequestDto;
import cakeit.server.user.dto.UserDto;
import cakeit.server.user.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserServiceImpl userService;
    private final S3Service s3Service;

    @GetMapping("")
    public String test(){
        return "유저 테스트";
    }

    @PostMapping("/signUp")
    public ResponseEntity<CommonResponse<String>> signUp(@RequestBody SignUpRequestDto reqDto){

        if (userService.signUp(reqDto)) {
            return new ResponseEntity<>(CommonResponse.success("회원가입 성공"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(CommonResponse.fail("회원가입 실패", ErrorResponse.builder()
                    .errorCode(ErrorEnum.BAD_REQUEST.getCode()).build()), HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/login")
    public ResponseEntity<CommonResponse<LoginResponseDto>> login(@RequestBody LoginRequestDto reqDto) {
        try {
            LoginResponseDto resDto = userService.login(reqDto);
            return new ResponseEntity<>(CommonResponse.success("로그인 성공", resDto), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(CommonResponse.fail("로그인 실패", ErrorResponse.builder()
                    .errorCode(ErrorEnum.NOT_FOUND.getCode()).build()), HttpStatus.NOT_FOUND);
        }
    }


    //아이디 중복체크
    @PostMapping(value = "/idcheck")
    public ResponseEntity<CommonResponse<String>> idCheck(@RequestBody UserDto reqDto) {
        String idcheckYn = userService.idcheck(reqDto.getLoginId());
        log.info("idcheckYn>>>>"+idcheckYn);

    if (idcheckYn == null) { //중복된 id가 없으면 N
        return new ResponseEntity<>(CommonResponse.success("아이디 사용이 가능합니다.", "N"), HttpStatus.OK);
    } else if (idcheckYn != null){ //중복된 id가 있으면 Y
        return new ResponseEntity<>(CommonResponse.success("이미 존재하는 아이디입니다.", "Y"), HttpStatus.OK);
    } else {
                return new ResponseEntity<>(CommonResponse.fail("아이디 중복체크 실패", ErrorResponse.builder()
                        .errorCode(ErrorEnum.ALREADY_BOOKED.getCode()).build()), HttpStatus.SERVICE_UNAVAILABLE);
            }
        }

}
