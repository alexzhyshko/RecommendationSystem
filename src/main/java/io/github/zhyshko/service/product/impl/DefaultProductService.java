package io.github.zhyshko.service.product.impl;

import io.github.zhyshko.dao.product.ProductDao;
import io.github.zhyshko.model.order.Order;
import io.github.zhyshko.model.product.Product;
import io.github.zhyshko.service.DefaultService;
import io.github.zhyshko.service.product.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DefaultProductService extends DefaultService<Product> implements ProductService {

    private ProductDao productDao;

    public DefaultProductService(ProductDao productDao) {
        super(productDao);
        this.productDao = productDao;
    }

    @Override
    public List<Product> getOrderedProducts(UUID userExternalId) {
        return productDao.findAllProductsOrderedByUser(userExternalId);
    }
}
