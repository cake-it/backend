package cakeit.server.user.controller;

import cakeit.server.file.service.S3Service;
import cakeit.server.global.CommonResponse;
import cakeit.server.global.exception.ErrorEnum;
import cakeit.server.global.exception.ErrorResponse;
import cakeit.server.user.dto.UserDto;
import cakeit.server.user.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Log4j2
//@RestController("/user")
public class UserControllerV0 {

//    private final UserService userService;
//    private final S3Service s3Service;
//
//    public UserControllerV0(UserService userService, S3Service s3Service) {
//        this.userService = userService;
//        this.s3Service = s3Service;
//    }
//
//    @GetMapping("/")
//    public String test(){
//        return "로그인 성공~! 홈!!";
//    }
//
//    //회원가입
//    @PostMapping("/join")
//    public ResponseEntity<CommonResponse<String>> join(UserDto reqDto)throws java.io.IOException{
//    //@RequestBody serDto reqDto 어노테이션이 빠져서 json형식으로 테스트x. 파일 객체가 있으므로 form-data 형식으로 테스트 진행
//        log.info("회원가입 폼 입력값 dto 알려줘>>>>>" + reqDto);
//
//        if (userService.join(reqDto)) {
//            return new ResponseEntity<>(CommonResponse.success("회원가입 성공"), HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(CommonResponse.fail("회원가입 실패", ErrorResponse.builder()
//                    .errorCode(ErrorEnum.ALREADY_BOOKED.getCode()).build()), HttpStatus.OK);
//        }
//    }
//
//
//    //아이디 중복체크
//    @PostMapping(value = "/user/idcheck")
//    public ResponseEntity<CommonResponse<String>> idCheck(@RequestBody UserDto reqDto) {
//        String idcheckYn = userService.idcheck(reqDto.getLoginId());
//        log.info("idcheckYn>>>>"+idcheckYn);
//
//    if (idcheckYn == null) { //중복된 id가 없으면 N
//        return new ResponseEntity<>(CommonResponse.success("아이디 중복체크 성공", "N"), HttpStatus.OK);
//    } else if (idcheckYn != null){ //중복된 id가 있으면 Y
//        return new ResponseEntity<>(CommonResponse.success("아이디 중복체크 성공", "Y"), HttpStatus.OK);
//    } else {
//                return new ResponseEntity<>(CommonResponse.fail("아이디 중복체크 실패", ErrorResponse.builder()
//                        .errorCode(ErrorEnum.ALREADY_BOOKED.getCode()).build()), HttpStatus.OK);
//            }
//        }
//
//
//    @RequestMapping(value = "/user/login", method = RequestMethod.GET)
//    public String login() {
//        log.info("로그인 API (GET) 성공");
//
//        return "로그인 API (GET) 성공";
//    }

}