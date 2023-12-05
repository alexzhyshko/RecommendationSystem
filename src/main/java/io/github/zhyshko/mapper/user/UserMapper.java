package io.github.zhyshko.mapper.user;

import io.github.zhyshko.dao.user.UserDao;
import io.github.zhyshko.dto.user.UserData;
import io.github.zhyshko.mapper.StoreMapper;
import io.github.zhyshko.model.user.User;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {StoreMapper.class})
@Component
public abstract class UserMapper {

    @Autowired
    protected UserDao userDao;
    @Autowired
    protected StoreMapper storeMapper;

    public User toModel(UserData orderData) {
        if (orderData == null) {
            return null;
        }

        return userDao.findByExternalIdAndStoreId(orderData.getExternalId(), orderData.getStore().getExternalId())
                .orElseGet(() -> userDao.save(createUser(orderData)));
    }

    public abstract UserData toDto(User order);

    private User createUser(UserData userData) {
        return User.builder()
                .id(userData.getId())
                .externalId(userData.getExternalId())
                .store(storeMapper.toModel(userData.getStore()))
                .build();
    }

}
