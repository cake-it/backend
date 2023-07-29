package cakeit.server.likes.service;

import cakeit.server.cakeStore.repository.CakeStoreRepository;
import cakeit.server.entity.CakeStoreEntity;
import cakeit.server.entity.LikeEntity;
import cakeit.server.entity.UserEntity;
import cakeit.server.likes.dto.LikesDto;
import cakeit.server.likes.repository.LikesRepository;
import cakeit.server.user.repository.UserRepository;
import com.amazonaws.services.kms.model.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static org.springframework.security.core.userdetails.User.builder;

@Log4j2
@Service
@RequiredArgsConstructor

public class LikesServiceImpl implements LikesService {
    private final LikesRepository likesRepository;
    private final CakeStoreRepository storeRepository;
    private final UserRepository userRepository;

    public boolean addLike(UserEntity userEntity, Long storeId) {
        log.info("라이크 서비스" + storeId);
        CakeStoreEntity storeEntity = storeRepository.findById(storeId).orElseThrow();
        log.info("storeEntity>>>>>" + storeEntity);

        //중복 좋아요 방지
        if (isNotAlreadyLike(userEntity, storeEntity)) {

            log.info("좋아요 중복 방지");

            likesRepository.save(new LikeEntity(storeEntity, userEntity));
            return true;
        }
        return false;
    }

    //사용자가 이미 좋아요 한 게시물인지 체크
    private boolean isNotAlreadyLike(UserEntity userEntity, CakeStoreEntity storeEntity) {

        log.info("innot>>>>>"+userEntity);
        log.info("innot>>>>>"+storeEntity);
        return likesRepository.findByUserEntityAndStoreEntity(userEntity, storeEntity).isEmpty();
    }

    public boolean cancelLike(UserEntity userEntity, Long storeId) {

        CakeStoreEntity storeEntity = storeRepository.findById(storeId).orElseThrow();
        log.info("storeId>>>>>" + storeId);
        log.info("userId>>>>" + userEntity.getUserId());

        //중복 좋아요 방지
        if (isNotAlreadyLike(userEntity, storeEntity)) {

            log.info("존재하지않음>>> 좋아요 취소 중복 방지");
            return false;

        }
        log.info("취소할거야");
        likesRepository.likeCancel(storeId, userEntity.getUserId());
        return true;
    }
}