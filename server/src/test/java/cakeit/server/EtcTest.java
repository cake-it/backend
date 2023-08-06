package cakeit.server;

import cakeit.server.user.repository.NickAdjectiveRepository;
import cakeit.server.user.repository.NickCakeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EtcTest {

    @Autowired
    private NickCakeRepository nickCakeRepository;

    @Test
    public void etcTest() {

        Long totalNickCakeEntityNumber = nickCakeRepository.findTotalNickCakeEntityNumber();
        System.out.println(totalNickCakeEntityNumber);

    }

}
