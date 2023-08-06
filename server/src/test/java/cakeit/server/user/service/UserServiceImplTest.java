package cakeit.server.user.service;

import cakeit.server.entity.CakeStoreEntity;
import cakeit.server.entity.UserEntity;
import cakeit.server.reservation.dto.PostReservationDetailDto;
import cakeit.server.user.dto.UserDto;
import cakeit.server.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private UserRepository userRepository;

    @Test
    void join() {

        UserDto userDto = new UserDto();
        userDto.setAge((long) 94012420L);
        String juminNo = String.valueOf(userDto.getAge());
        System.out.println("juminNo >>>" + juminNo);

        String gender= String.valueOf(juminNo.charAt(6));
        System.out.println("gender >>>" + gender);

        //if 문
        if (gender.equals("1") || gender.equals("3")) {
            gender = "남";
        } else if (gender.equals("2") || gender.equals("4")) {
            gender = "여";
        } else {
            gender = "에러";
        }
        System.out.println("성별 알려줘 >>>>"+gender);

        //만 나이 계산
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);

        String birthYear = juminNo.substring(0,2);

        char ch = juminNo.charAt(7);
        Long age;

        if(ch < '3'){
            age = Long.valueOf(year - (1900 + Integer.parseInt(birthYear)) + 1);
        }else {
            age = Long.valueOf(year - (2000 + Integer.parseInt(birthYear)) + 1);
        }
        System.out.println("계산한 만 나이 >>>>>"+age);
        // Given

        userDto = UserDto.builder()
                .loginId("hhh")
                .password("123456")
                .nickname("귀여운망고케이크")
                .age(age)
                .profileImage("test")
                .gender(gender)
                .build();

//        UserEntity ue = userRepository.save(userDto);
//        System.out.println("테스트 결과>>>>>>"   +    ue);
    }

    @Test
    void login() {
    }
}