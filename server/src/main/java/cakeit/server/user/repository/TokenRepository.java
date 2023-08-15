package cakeit.server.user.repository;

import cakeit.server.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<TokenEntity, Long> {

    TokenEntity findByRefreshToken(String refreshToken);
    TokenEntity findByAccessToken(String accessToken);

}
