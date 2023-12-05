package io.github.zhyshko.dto.product;

import io.github.zhyshko.dto.StoreData;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Jacksonized
public class ProductAttributeData {

    private Long id;
    private UUID externalId;
    private StoreData store;

}
