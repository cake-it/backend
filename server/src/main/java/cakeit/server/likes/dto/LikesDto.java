package cakeit.server.likes.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LikesDto {
       // private Long likeId;
        private Long storeId;
        private Long userId;

    public LikesDto(Long storeId, Long userId) {
        this.storeId = storeId;
        this.userId = userId;
    }
}
