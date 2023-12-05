package io.github.zhyshko.facade.impl;

import io.github.zhyshko.mapper.order.OrderMapper;
import io.github.zhyshko.dto.order.OrderData;
import io.github.zhyshko.dto.review.ReviewData;
import io.github.zhyshko.facade.IndexerFacade;
import io.github.zhyshko.mapper.review.ReviewMapper;
import io.github.zhyshko.service.order.OrderService;
import io.github.zhyshko.service.review.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefaultIndexerFacade implements IndexerFacade {

    private final OrderService orderService;
    private final ReviewService reviewService;
    private final OrderMapper orderMapper;
    private final ReviewMapper reviewMapper;

    @Autowired
    public DefaultIndexerFacade(OrderService orderService, ReviewService reviewService, OrderMapper orderMapper,
                                ReviewMapper reviewMapper) {
        this.orderService = orderService;
        this.reviewService = reviewService;
        this.orderMapper = orderMapper;
        this.reviewMapper = reviewMapper;
    }

    public void indexOrder(OrderData orderData) {
        System.out.println("Saving "+orderMapper.toModel(orderData));
        orderService.save(orderMapper.toModel(orderData));
    }

    public void indexReview(ReviewData reviewData) {
        reviewService.save(reviewMapper.toModel(reviewData));
    }

}
