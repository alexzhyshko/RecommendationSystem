package io.github.zhyshko.service.order.impl;

import io.github.zhyshko.dao.order.OrderDao;
import io.github.zhyshko.model.order.Order;
import io.github.zhyshko.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultOrderService implements OrderService {

    private OrderDao orderDao;

    @Autowired
    public DefaultOrderService(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public void save(Order order) {
        this.orderDao.save(order);
    }
}
