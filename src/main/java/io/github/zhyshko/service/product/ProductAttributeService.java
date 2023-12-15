package io.github.zhyshko.service.product;

import io.github.zhyshko.model.product.ProductAttribute;

import java.util.List;
import java.util.UUID;

public interface ProductAttributeService {

    List<ProductAttribute> getMostPopularUserProductAttributes(UUID storeId, UUID userExternalId);

}
