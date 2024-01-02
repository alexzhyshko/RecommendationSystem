package io.github.zhyshko.facade.impl;

import io.github.zhyshko.dto.order.OrderEntryData;
import io.github.zhyshko.dto.product.ProductData;
import io.github.zhyshko.facade.RecommendationsFacade;
import io.github.zhyshko.mapper.order.OrderEntryMapper;
import io.github.zhyshko.mapper.product.ProductMapper;
import io.github.zhyshko.service.order.OrderEntryService;
import io.github.zhyshko.service.product.ProductService;
import io.github.zhyshko.strategy.ProductRatingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
    @Autowired
    private List<ProductRatingStrategy> productRatingStrategies;

    @Override
    public Map<ProductData, Long> getForUser(UUID storeId, UUID userExternalId) {
        Map<ProductData, Integer> userOrderedProducts = orderEntryMapper.toDtoList(orderEntryService
                .getUserOrderEntries(userExternalId)).stream()
                .collect(Collectors.toMap(OrderEntryData::getProduct, (oe) -> oe.getReviewEntry().getMark()));

        return userOrderedProducts
                .keySet()
                .stream()
                .flatMap(p -> this.findInBatch(storeId, p).entrySet().stream())
                .map(e -> this.processStrategies(storeId, userExternalId, e, getMark(userOrderedProducts.get(e.getKey()))))
                .map(e -> this.decreaseAlreadyBoughtProductRating(e, userOrderedProducts.keySet()))
                .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.summingLong(Map.Entry::getValue)));
    }

    @Override
    public Map<ProductData, Long> getGeneral(UUID storeId) {
        List<ProductData> recentlyOrderedProducts =
                productMapper.toDtoList(productService.getProductsOrderedAfter(LocalDateTime.now().minusDays(30)));

        return recentlyOrderedProducts
                .stream()
                .flatMap(p -> findInBatch(storeId, p).entrySet().stream())
                .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.summingLong(Map.Entry::getValue)));
    }

    private Map.Entry<ProductData, Long> decreaseAlreadyBoughtProductRating(
            Map.Entry<ProductData, Long> productDataLongEntry,
            Collection<ProductData> userOrderedProducts) {
       if(userOrderedProducts.contains(productDataLongEntry.getKey())) {
           productDataLongEntry.setValue(productDataLongEntry.getValue()>0?-1L:productDataLongEntry.getValue());
       }
       return productDataLongEntry;
    }

    private Map<ProductData, Long> findInBatch(UUID storeId, ProductData productData) {
        List<OrderEntryData> orderEntries = orderEntryMapper.toDtoList(
                orderEntryService.getAllOrderEntriesOfOrdersWithThisProduct(storeId, productData.getExternalId()));

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

    private Map.Entry<ProductData, Long> processStrategies(UUID storeId, UUID userExternalId,
                                                           Map.Entry<ProductData, Long> entry, Integer mark) {
        productRatingStrategies.forEach(strategy -> strategy.recalculateRating(storeId, userExternalId, entry, mark));

        return entry;
    }

    private Integer getMark(Integer mark) {
        return mark == null ? 0 : mark;
    }

}
