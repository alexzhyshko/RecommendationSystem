package io.github.zhyshko.service.product.impl;

import io.github.zhyshko.dao.product.ProductAttributeDao;
import io.github.zhyshko.model.product.ProductAttribute;
import io.github.zhyshko.service.DefaultService;
import io.github.zhyshko.service.product.ProductAttributeService;
import org.springframework.stereotype.Service;

@Service
public class DefaultProductAttributeService extends DefaultService<ProductAttribute> implements ProductAttributeService {

    public DefaultProductAttributeService(ProductAttributeDao productAttributeDao) {
        super(productAttributeDao);
    }

}
