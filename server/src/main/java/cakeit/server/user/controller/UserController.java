package cakeit.server.user.controller;

import cakeit.server.user.dto.UserDto;
import cakeit.server.user.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
//@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String Home() {
        log.info("홈 >>>");
        return "home";
    }

    @RequestMapping(value="/user/join", method= RequestMethod.GET)
    public String join() {
        log.info("조인 겟>>>");
        return "회원가입 API 겟";
    }
    @RequestMapping(value="/user/join", method= RequestMethod.POST)
    public Long join(UserDto userDto) {
        log.info("조인 >>>");

        Long userId = (long) (Math.random()*1000);
        userDto.setUserId(userId);
        userDto.setLoginId("hayeon");
        userDto.setPassword("1234");
        userDto.setNickname("상큼한유자케이크");
        userDto.setProfileImage("test.jpg");
        userDto.setAge(77L);
        userDto.setGender("여");

        log.info("dto 알려줘>>>>" + userDto);
        userId = userService.join(userDto);
        return userId;
    }


//    @RequestMapping(value="/user/join")
//    public String join(UserDto userDto) {
//        log.info("조인 >>>");
//        Long userId = userService.join(userDto);
//        return "회원가입 API 성공";
//    }
}