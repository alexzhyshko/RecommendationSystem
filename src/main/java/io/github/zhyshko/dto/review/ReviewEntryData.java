package io.github.zhyshko.dto.review;

import io.github.zhyshko.dto.product.ProductData;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;
import java.util.UUID;

import io.github.zhyshko.dto.StoreData;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Jacksonized
public class ReviewEntryData {

    private Long id;
    private UUID externalId;
    private StoreData store;

    private LocalDateTime timeCreated;
    private Integer mark;
    private ProductData product;

}
