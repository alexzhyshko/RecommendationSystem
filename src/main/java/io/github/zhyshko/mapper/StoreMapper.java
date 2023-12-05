package io.github.zhyshko.mapper;

import io.github.zhyshko.dao.StoreDao;
import io.github.zhyshko.dto.StoreData;
import io.github.zhyshko.model.Store;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
@Component
public abstract class StoreMapper {

    @Autowired
    protected StoreDao storeDao;

    public Store toModel(StoreData orderData) {
        return storeDao.findById(orderData.getExternalId())
                .orElseGet(() -> storeDao.save(Store.builder().id(orderData.getExternalId()).build()));
    }

    public abstract StoreData toDto(Store order);

}
