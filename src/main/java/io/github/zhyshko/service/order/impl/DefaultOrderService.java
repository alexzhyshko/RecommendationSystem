package io.github.zhyshko.service.order.impl;

import io.github.zhyshko.dao.order.OrderDao;
import io.github.zhyshko.dto.order.OrderData;
import io.github.zhyshko.model.order.Order;
import io.github.zhyshko.model.product.Product;
import io.github.zhyshko.service.DefaultService;
import io.github.zhyshko.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DefaultOrderService extends DefaultService<Order> implements OrderService {

    private OrderDao orderDao;

    @Autowired
    public DefaultOrderService(OrderDao orderDao) {
        super(orderDao);
        this.orderDao = orderDao;
    }

}
