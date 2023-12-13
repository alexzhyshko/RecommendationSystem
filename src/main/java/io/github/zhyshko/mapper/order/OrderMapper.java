package io.github.zhyshko.mapper.order;

import io.github.zhyshko.dao.order.OrderDao;
import io.github.zhyshko.dto.order.OrderData;
import io.github.zhyshko.dto.order.OrderEntryData;
import io.github.zhyshko.mapper.StoreMapper;
import io.github.zhyshko.mapper.user.UserMapper;
import io.github.zhyshko.model.order.Order;
import io.github.zhyshko.model.order.OrderEntry;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {UserMapper.class, OrderEntryMapper.class, StoreMapper.class})
public abstract class OrderMapper {

    @Autowired
    protected UserMapper userMapper;
    @Autowired
    protected OrderEntryMapper orderEntryMapper;
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
        order.orderEntries(createOrderEntryDataList(orderData.getOrderEntries()));
        order.createdTime(orderData.getCreatedTime());

        return order.build();
    }

    protected List<OrderEntry> createOrderEntryDataList(List<OrderEntryData> list) {
        if ( list == null ) {
            return null;
        }

        List<OrderEntry> list1 = new ArrayList<OrderEntry>( list.size() );
        for ( OrderEntryData orderEntryData : list ) {
            list1.add( orderEntryMapper.toModel( orderEntryData ) );
        }

        return list1;
    }

}
