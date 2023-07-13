package cakeit.server.user.controller;

import cakeit.server.user.dto.UserDto;
import cakeit.server.user.repository.UserRepository;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private fnal UserService userService;
    @PostMapping("/join")
    public String join(UserDto userDto) {
        Long userId = UserService.join(userDto);
        return "회원가입 API 성공";
    }
}