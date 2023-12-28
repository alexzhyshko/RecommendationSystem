package io.github.zhyshko.facade.impl;

import io.github.zhyshko.dto.order.OrderEntryData;
import io.github.zhyshko.mapper.order.OrderMapper;
import io.github.zhyshko.dto.order.OrderData;
import io.github.zhyshko.dto.review.ReviewEntryData;
import io.github.zhyshko.facade.IndexerFacade;
import io.github.zhyshko.model.review.ReviewEntry;
import io.github.zhyshko.service.order.OrderService;
import io.github.zhyshko.service.review.ReviewEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefaultIndexerFacade implements IndexerFacade {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    private final ReviewEntryService reviewEntryService;

    @Autowired
    public DefaultIndexerFacade(OrderService orderService, OrderMapper orderMapper,
                                ReviewEntryService reviewEntryService) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
        this.reviewEntryService = reviewEntryService;
    }

    public void indexOrder(OrderData orderData) {
        orderService.save(orderMapper.toModel(orderData));
    }

    public void indexReview(OrderData orderData) {
        orderData.getOrderEntries().stream()
                .map(OrderEntryData::getReviewEntry)
                .forEach(this::updateReview);
    }

    private void updateReview(ReviewEntryData reviewEntryData) {
        ReviewEntry existing = reviewEntryService.getReviewEntry(reviewEntryData.getExternalId(),
                reviewEntryData.getStore().getExternalId());
        existing.setMark(reviewEntryData.getMark());
        reviewEntryService.save(existing);
    }

}
