package io.github.zhyshko.mapper.order;

import io.github.zhyshko.dao.order.OrderDao;
import io.github.zhyshko.dto.order.OrderData;
import io.github.zhyshko.dto.review.ReviewData;
import io.github.zhyshko.mapper.StoreMapper;
import io.github.zhyshko.mapper.review.ReviewMapper;
import io.github.zhyshko.mapper.user.UserMapper;
import io.github.zhyshko.model.order.Order;
import io.github.zhyshko.model.review.Review;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {UserMapper.class, ReviewMapper.class, StoreMapper.class})
public abstract class OrderMapper {

    @Autowired
    protected UserMapper userMapper;
    @Autowired
    protected ReviewMapper reviewMapper;
    @Autowired
    protected StoreMapper storeMapper;
    @Autowired
    protected OrderDao orderDao;

    public Order toModel(OrderData orderData) {
        if (orderData == null) {
            return null;
        }

        return orderDao.findByExternalIdAndStoreId(orderData.getExternalId(), orderData.getStore().getExternalId())
                .orElseGet(() -> orderDao.save(createOrder(orderData)));

    }

    public abstract OrderData toDto(Order order);

    public List<OrderData> toDtoList(List<Order> orders) {
        return orders.stream().map(this::toDto).toList();
    }

    private Order createOrder(OrderData orderData) {
        Order.OrderBuilder<?, ?> order = Order.builder();

        order.id(orderData.getId());
        order.externalId(orderData.getExternalId());
        order.store(storeMapper.toModel(orderData.getStore()));
        order.owner(userMapper.toModel(orderData.getOwner()));
        order.review(reviewMapper.toModel(orderData.getReview()));
        order.createdTime(orderData.getCreatedTime());

        return order.build();
    }

}
