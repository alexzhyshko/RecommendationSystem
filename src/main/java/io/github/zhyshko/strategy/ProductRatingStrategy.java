package io.github.zhyshko.strategy;

import io.github.zhyshko.dto.product.ProductData;

import java.util.Map;
import java.util.UUID;

public interface ProductRatingStrategy {

    void recalculateRating(UUID storeId, UUID userExternalId,
                           Map.Entry<ProductData, Long> entry);

}
