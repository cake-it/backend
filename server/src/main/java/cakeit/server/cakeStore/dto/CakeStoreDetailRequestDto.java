package cakeit.server.cakeStore.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CakeStoreDetailRequestDto {

    private Long storeId;
    private Long userId;
}
