package io.github.zhyshko.dto.product;

import io.github.zhyshko.dto.StoreData;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Jacksonized
public class CategoryData {

    private Long id;
    private UUID externalId;
    private StoreData store;
    private List<CategoryData> subcategories;

}
