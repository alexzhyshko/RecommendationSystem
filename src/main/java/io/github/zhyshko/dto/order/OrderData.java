package io.github.zhyshko.dto.order;

import io.github.zhyshko.dto.review.ReviewData;
import io.github.zhyshko.dto.user.UserData;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import io.github.zhyshko.dto.StoreData;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Jacksonized
public class OrderData {

    private Long id;
    private UUID externalId;
    private StoreData store;

    private UserData owner;
    private ReviewData review;
    private LocalDateTime createdTime;

}
