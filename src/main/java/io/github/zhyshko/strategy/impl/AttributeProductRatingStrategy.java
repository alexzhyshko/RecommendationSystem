package io.github.zhyshko.strategy.impl;

import io.github.zhyshko.dto.product.CategoryData;
import io.github.zhyshko.dto.product.ProductAttributeData;
import io.github.zhyshko.dto.product.ProductData;
import io.github.zhyshko.mapper.product.ProductAttributeMapper;
import io.github.zhyshko.service.product.ProductAttributeService;
import io.github.zhyshko.strategy.ProductRatingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class AttributeProductRatingStrategy implements ProductRatingStrategy {

    @Autowired
    private ProductAttributeMapper productAttributeMapper;
    @Autowired
    private ProductAttributeService productAttributeService;

    @Override
    public void recalculateRating(UUID storeId, UUID userExternalId,
                                  Map.Entry<ProductData, Long> entry) {
        List<ProductAttributeData> mostPopularAttributes = productAttributeMapper.toDtolist(
                productAttributeService.getMostPopularUserProductAttributes(storeId, userExternalId));

        List<ProductAttributeData> productAttributes = entry.getKey().getProductAttributes();

        long count = productAttributes
                .stream()
                .filter(mostPopularAttributes::contains)
                .distinct()
                .count();

        entry.setValue(entry.getValue() + count * 2);
    }
}
