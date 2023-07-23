package cakeit.server.user.controller;

import cakeit.server.entity.UserEntity;
import cakeit.server.file.dto.FileDto;
import cakeit.server.file.service.FileService;
import cakeit.server.file.service.S3Service;
import cakeit.server.user.dto.UserDto;
import cakeit.server.user.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;



@Log4j2
@RestController
public class UserController {

    private final UserService userService;
    private final S3Service s3Service;

    public UserController(UserService userService, S3Service s3Service) {
        this.userService = userService;
        this.s3Service = s3Service;
    }


    @GetMapping("/")
    public String test(){
        return "로그인 성공~! 홈!!";
    }


    @RequestMapping(value = "/user/join", method = RequestMethod.GET)
    public String join() {
        log.info("회원가입 API (GET) 성공");

        return "회원가입 API (GET) 성공";
    }

    @RequestMapping(value = "/user/join", method = RequestMethod.POST)
    public void join(UserDto userDto, MultipartFile file) throws java.io.IOException{
        log.info("회원가입 폼 입력값 dto 알려줘>>>>>" + userDto);
        log.info("file 알려줘>>>>>" + file);
        String url = s3Service.uploadFile(file);

        userDto.setProfileImage(url);
        userService.join(userDto);
    }

    @RequestMapping(value = "/user/login", method = RequestMethod.GET)
    public String login() {
        log.info("로그인 API (GET) 성공");

        return "로그인 API (GET) 성공";
    }

    //ID중복체크
    @RequestMapping(value = "/user/idcheck", method = RequestMethod.POST)
    String idcheck(String loginId) {
        String idCheckYn = userService.idcheck(loginId);

        if(idCheckYn == null){
            return "N";
        }
        else {
            return "y";
        }
    }
//
//    @RequestMapping(value = "/user/delete", method = RequestMethod.POST)
//    public void deleteUser(UserDto userDto) {
//        log.info("request>>>>>" + userDto);
//
//        // UserEntity userEntity = (UserEntity) session.getAttribute("userDTO");
//
//        userService.deleteUser(userDto);
//    }

    //프로필 사진 등록
//    @PostMapping("/user/image")
//    public String uploadFile(FileDto fileDto, String loginId) throws IOException {
//        log.info("업로드 포스트 >>>");
//
//        //fileDto.setTilte(loginId);
//        String url = s3Service.uploadFile(fileDto.getFile());
//
//        log.info("업로드 포스트 url>>>" + url);
//
//        fileDto.setUrl(url);
//
//        log.info("업로드 포스트 fileDto>>>" + fileDto);
//        fileService.save(fileDto);
//
//        return "redirect:/api/list";
//    }
}