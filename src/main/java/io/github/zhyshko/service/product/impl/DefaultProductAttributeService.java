package io.github.zhyshko.service.product.impl;

import io.github.zhyshko.dao.product.ProductAttributeDao;
import io.github.zhyshko.model.product.ProductAttribute;
import io.github.zhyshko.service.product.ProductAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class DefaultProductAttributeService implements ProductAttributeService {

    @Autowired
    private ProductAttributeDao productAttributeDao;

    @Override
    public List<ProductAttribute> getMostPopularUserProductAttributes(UUID storeId, UUID userExternalId) {
        return productAttributeDao.findMostPopularUserProductAttributes(storeId, userExternalId);
    }
}
