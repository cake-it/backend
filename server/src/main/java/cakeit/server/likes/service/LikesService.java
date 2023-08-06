package cakeit.server.likes.service;

import cakeit.server.entity.CakeStoreEntity;
import cakeit.server.entity.UserEntity;
import cakeit.server.likes.dto.LikesDto;
import cakeit.server.user.dto.UserDto;

import java.util.Optional;

public interface LikesService {

   boolean addLike(UserEntity userEntity, Long storeId);

    boolean cancelLike(UserEntity userEntity, Long recipeId);



}