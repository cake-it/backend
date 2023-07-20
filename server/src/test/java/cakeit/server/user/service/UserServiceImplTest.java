package cakeit.server.user.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class UserServiceImplTest {

    @Autowired
    public UserServiceImpl userService;

    @Test
    public void HelloTest() {
        System.out.println("HELLOOO!!");
    }
}