package io.github.zhyshko.mapper.order;

import io.github.zhyshko.dao.order.OrderEntryDao;
import io.github.zhyshko.dao.review.ReviewEntryDao;
import io.github.zhyshko.dto.order.OrderEntryData;
import io.github.zhyshko.dto.review.ReviewEntryData;
import io.github.zhyshko.mapper.StoreMapper;
import io.github.zhyshko.mapper.product.ProductMapper;
import io.github.zhyshko.mapper.review.ReviewEntryMapper;
import io.github.zhyshko.model.order.OrderEntry;
import io.github.zhyshko.model.review.ReviewEntry;
import io.github.zhyshko.service.order.OrderEntryService;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {ProductMapper.class, ReviewEntryMapper.class, StoreMapper.class})
public abstract class OrderEntryMapper {

    @Autowired
    protected ReviewEntryMapper reviewEntryMapper;
    @Autowired
    protected StoreMapper storeMapper;
    @Autowired
    protected ProductMapper productMapper;
    @Autowired
    protected OrderEntryService orderEntryService;

    public OrderEntry toModel(OrderEntryData orderEntryData) {
        if (orderEntryData == null) {
            return null;
        }

        return orderEntryService.saveOrUpdate(createOrderEntry(orderEntryData));
    }

    public abstract OrderEntryData toDto(OrderEntry order);

    public List<OrderEntryData> toDtoList(List<OrderEntry> entries) {
        return entries.stream().map(this::toDto).toList();
    }

    private OrderEntry createOrderEntry(OrderEntryData orderEntryData) {
        OrderEntry.OrderEntryBuilder<?, ?> orderEntry = OrderEntry.builder();

        orderEntry.id(orderEntryData.getId());
        orderEntry.externalId(orderEntryData.getExternalId());
        orderEntry.store(storeMapper.toModel(orderEntryData.getStore()));
        orderEntry.reviewEntry(reviewEntryMapper.toModel(orderEntryData.getReviewEntry()));
        orderEntry.product(productMapper.toModel(orderEntryData.getProduct()));
        orderEntry.timeCreated(orderEntryData.getTimeCreated());

        return orderEntry.build();
    }

}
