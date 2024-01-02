package io.github.zhyshko.strategy.impl;

import io.github.zhyshko.dto.product.AuthorData;
import io.github.zhyshko.dto.product.ProductAttributeData;
import io.github.zhyshko.dto.product.ProductData;
import io.github.zhyshko.mapper.product.AuthorMapper;
import io.github.zhyshko.service.product.AuthorService;
import io.github.zhyshko.strategy.ProductRatingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class AuthorProductRatingStrategy implements ProductRatingStrategy {

    @Autowired
    private AuthorMapper authorMapper;
    @Autowired
    private AuthorService authorService;

    @Override
    public void recalculateRating(UUID storeId, UUID userExternalId,
                                  Map.Entry<ProductData, Long> entry, Integer mark) {
        List<AuthorData> mostPopularAttributes = authorMapper.toDtolist(
                authorService.getMostPopularUserAuthors(storeId, userExternalId));

        List<AuthorData> productAttributes = entry.getKey().getAuthors();

        long count = productAttributes
                .stream()
                .filter(mostPopularAttributes::contains)
                .distinct()
                .count();

        entry.setValue(entry.getValue() + count * (5 * getMarkCoefficient(mark)));
    }

    private int getMarkCoefficient(Integer mark) {
        return mark >= 0 ? 1 : -1;
    }
}
