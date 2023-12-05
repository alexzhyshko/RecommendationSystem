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
public class ProductData {

    private Long id;
    private UUID externalId;

    private List<ProductAttributeData> productAttributes;
    private List<AuthorData> authors;
    private List<PublisherData> publishers;
    private List<CategoryData> categories;
    private StoreData store;

}
