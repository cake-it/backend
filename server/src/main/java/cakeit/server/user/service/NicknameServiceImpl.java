package cakeit.server.user.service;

import cakeit.server.user.repository.NickAdjectiveRepository;
import cakeit.server.user.repository.NickCakeRepository;
import cakeit.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
public class NicknameServiceImpl implements NicknameService {

    private final NickAdjectiveRepository nickAdjectiveRepository;
    private final NickCakeRepository nickCakeRepository;
    private final UserRepository userRepository;


    @Transactional
    @Override
    public String addRandomNickname() {

        Long nickCakeTotalNum = nickCakeRepository.findTotalNickCakeEntityNumber();
        Long nickAdjTotalNum = nickAdjectiveRepository.findTotalNickAdjectiveEntityNumber();

        String nickname = "";
        boolean checkNickname = true;

        while (checkNickname) {
            String nickCake = nickCakeRepository.findById((Long.valueOf((int) (Math.random() * nickCakeTotalNum) + 1))).get().getWord();
            String nickAdjective = nickAdjectiveRepository.findById((Long.valueOf((int) (Math.random() * nickAdjTotalNum) + 1))).get().getWord();
            nickname = nickAdjective + " " + nickCake;
            checkNickname = isDuplicatedNickname(nickname);
        }

        return nickname;
    }

    @Transactional
    @Override
    public boolean isDuplicatedNickname(String nickname) {

        if (userRepository.existsByNickname(nickname)) {
            return true;
        }
        return false;
    }
}
