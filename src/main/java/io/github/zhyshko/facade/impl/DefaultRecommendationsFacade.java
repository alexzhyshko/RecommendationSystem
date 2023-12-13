package io.github.zhyshko.facade.impl;

import io.github.zhyshko.dto.order.OrderEntryData;
import io.github.zhyshko.dto.product.ProductData;
import io.github.zhyshko.dto.review.ReviewEntryData;
import io.github.zhyshko.facade.RecommendationsFacade;
import io.github.zhyshko.mapper.order.OrderEntryMapper;
import io.github.zhyshko.mapper.product.ProductMapper;
import io.github.zhyshko.mapper.review.ReviewEntryMapper;
import io.github.zhyshko.service.order.OrderEntryService;
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
    private OrderEntryService orderEntryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private OrderEntryMapper orderEntryMapper;

    @Override
    public Map<ProductData, Long> getForUser(UUID userExternalId) {
        List<ProductData> userOrderedProducts = productMapper.toDtoList(productService.getOrderedProducts(userExternalId));

        return userOrderedProducts
                .stream()
                .flatMap(p -> findInBatch(p).entrySet().stream())
                .map(e -> decreaseAlreadyBoughtProductRating(e, userOrderedProducts))
                .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.summingLong(Map.Entry::getValue)));
    }

    private Map.Entry<ProductData, Long> decreaseAlreadyBoughtProductRating(
            Map.Entry<ProductData, Long> productDataLongEntry,
            List<ProductData> userOrderedProducts) {
       if(userOrderedProducts.contains(productDataLongEntry.getKey())) {
           productDataLongEntry.setValue(productDataLongEntry.getValue()>0?-1L:productDataLongEntry.getValue());
       }
       return productDataLongEntry;
    }

    private Map<ProductData, Long> findInBatch(ProductData productData) {
        List<OrderEntryData> orderEntries = orderEntryMapper.toDtoList(
                orderEntryService.getAllOrderEntriesOfOrdersWithThisProduct(productData.getExternalId()));

        Map<ProductData, Long> aggregatedDataByPopularity = orderEntries.stream()
                .collect(Collectors.groupingBy(OrderEntryData::getProduct, Collectors.counting()));

        Map<ProductData, Long> aggregatedDataByRating = orderEntries.stream()
                .collect(Collectors.groupingBy(OrderEntryData::getProduct, Collectors.summingLong(oe->oe.getReviewEntry().getMark())))
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        aggregatedDataByRating
                .forEach((key, value) -> aggregatedDataByPopularity.merge(key, value, Long::sum));

        return aggregatedDataByRating;
    }


}
