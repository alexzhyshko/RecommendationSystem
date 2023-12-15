package io.github.zhyshko.strategy.impl;

import io.github.zhyshko.dto.product.AuthorData;
import io.github.zhyshko.dto.product.ProductData;
import io.github.zhyshko.dto.product.PublisherData;
import io.github.zhyshko.mapper.product.PublisherMapper;
import io.github.zhyshko.service.product.PublisherService;
import io.github.zhyshko.strategy.ProductRatingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class PublisherProductRatingStrategy implements ProductRatingStrategy {

    @Autowired
    private PublisherMapper publisherMapper;
    @Autowired
    private PublisherService publisherService;

    @Override
    public void recalculateRating(UUID storeId, UUID userExternalId,
                                  Map.Entry<ProductData, Long> entry) {
        List<PublisherData> mostPopularAttributes = publisherMapper.toDtolist(
                publisherService.getMostPopularUserPublishers(storeId, userExternalId));

        List<PublisherData> productAttributes = entry.getKey().getPublishers();

        long count = productAttributes
                .stream()
                .filter(mostPopularAttributes::contains)
                .distinct()
                .count();

        entry.setValue(entry.getValue() + count);
    }

}
