package io.github.zhyshko.facade.impl;

import io.github.zhyshko.dto.product.ProductData;
import io.github.zhyshko.dto.review.ReviewEntryData;
import io.github.zhyshko.facade.RecommendationsFacade;
import io.github.zhyshko.mapper.product.ProductMapper;
import io.github.zhyshko.mapper.review.ReviewEntryMapper;
import io.github.zhyshko.service.product.ProductService;
import io.github.zhyshko.service.review.ReviewEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class DefaultRecommendationsFacade implements RecommendationsFacade {

    @Autowired
    private ReviewEntryService reviewEntryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ReviewEntryMapper reviewEntryMapper;

    @Override
    public Map<Long, ProductData> getForUser(UUID userExternalId) {
        List<ProductData> userOrderedProducts = productMapper.toDtoList(productService.getOrderedProducts(userExternalId));

        Map<ProductData, Long> resultMap = userOrderedProducts
                .stream()
                .flatMap(p -> findInBatch(p).entrySet().stream())
                .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.summingLong(Map.Entry::getValue)));

        Map<Long, ProductData> reversedMap = resultMap
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));

        Map<Long, ProductData> productsSortedByScore = new TreeMap<>(Comparator.reverseOrder());
        productsSortedByScore.putAll(reversedMap);

        return productsSortedByScore;
    }

    private Map<ProductData, Long> findInBatch(ProductData productData) {
        List<ReviewEntryData> reviewEntries = reviewEntryMapper.toDtoList(
                reviewEntryService.getAllRangedByReviewRatingAndPopularity(productData.getExternalId()));

        Map<ProductData, Long> aggregatedDataByPopularity = reviewEntries.stream()
                .collect(Collectors.groupingBy(ReviewEntryData::getProduct, Collectors.counting()));

        Map<ProductData, Long> aggregatedDataByRating = reviewEntries.stream()
                .collect(Collectors.groupingBy(ReviewEntryData::getProduct, Collectors.summingLong(ReviewEntryData::getMark)))
                .entrySet().stream()
                .filter(e -> e.getValue() >= 0)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        aggregatedDataByRating.forEach((key, value) -> aggregatedDataByPopularity.merge(key, value, Long::sum));

        return aggregatedDataByRating;
    }


}
