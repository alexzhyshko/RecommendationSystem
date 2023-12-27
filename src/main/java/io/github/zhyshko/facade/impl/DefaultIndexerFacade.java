package io.github.zhyshko.facade.impl;

import io.github.zhyshko.mapper.order.OrderMapper;
import io.github.zhyshko.dto.order.OrderData;
import io.github.zhyshko.facade.IndexerFacade;
import io.github.zhyshko.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefaultIndexerFacade implements IndexerFacade {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @Autowired
    public DefaultIndexerFacade(OrderService orderService, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    public void indexOrder(OrderData orderData) {
        orderService.save(orderMapper.toModel(orderData));
    }

    public void indexReview(OrderData orderData) {
        orderService.save(orderMapper.toModel(orderData));
    }

}
