package io.github.zhyshko.strategy.impl;

import io.github.zhyshko.dto.product.CategoryData;
import io.github.zhyshko.dto.product.ProductData;
import io.github.zhyshko.mapper.product.CategoryMapper;
import io.github.zhyshko.service.product.CategoryService;
import io.github.zhyshko.strategy.ProductRatingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class CategoryProductRatingStrategy implements ProductRatingStrategy {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public void recalculateRating(UUID storeId, UUID userExternalId,
                                  Map.Entry<ProductData, Long> entry, Integer mark) {
        List<CategoryData> mostPopularCategories
                = categoryMapper.toDtolist(categoryService.getMostPopularUserCategories(storeId, userExternalId));

        List<CategoryData> productCategories = entry.getKey().getCategories();

        long count = productCategories
                .stream()
                .filter(mostPopularCategories::contains)
                .distinct()
                .count();

        entry.setValue(entry.getValue() + count * (2 * getMarkCoefficient(mark)));
    }

    private int getMarkCoefficient(Integer mark) {
        return mark >= 0 ? 1 : -1;
    }
}
