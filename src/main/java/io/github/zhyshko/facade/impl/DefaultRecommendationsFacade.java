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
        List<ProductData> userOrderedProducts = productMapper.toDtoList(productService.getOrderedProducts(userExternalId));

        return userOrderedProducts
                .stream()
                .flatMap(p -> this.findInBatch(storeId, p).entrySet().stream())
                .map(e -> this.processStrategies(storeId, userExternalId, e))
                .map(e -> this.decreaseAlreadyBoughtProductRating(e, userOrderedProducts))
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
            List<ProductData> userOrderedProducts) {
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
                                                           Map.Entry<ProductData, Long> entry) {
        productRatingStrategies.forEach(strategy -> strategy.recalculateRating(storeId, userExternalId, entry));

        return entry;
    }

}
