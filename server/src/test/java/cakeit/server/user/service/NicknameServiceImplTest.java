package cakeit.server.user.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NicknameServiceImplTest {

    @Autowired
    private NicknameServiceImpl nicknameService;

    @Test
    void addRandomNickname() {

        String nickname = nicknameService.addRandomNickname();
        System.out.println(nickname);
        boolean isduplicated = nicknameService.isDuplicatedNickname(nickname);
        System.out.println(isduplicated);
    }
}