package io.github.zhyshko.service.product.impl;

import io.github.zhyshko.dao.product.ProductDao;
import io.github.zhyshko.model.product.Product;
import io.github.zhyshko.service.product.ProductService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class DefaultProductService implements ProductService {

    private ProductDao productDao;

    public DefaultProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public List<Product> getOrderedProducts(UUID userExternalId) {
        return productDao.findAllProductsOrderedByUser(userExternalId);
    }

    @Override
    public List<Product> getProductsOrderedAfter(LocalDateTime dateAfter) {
        return productDao.findProductsOrderedAfter(dateAfter);
    }

    @Override
    public Product saveOrUpdate(Product product) {
        return productDao.findByExternalIdAndStoreId(product.getExternalId(), product.getStore().getId())
                .map(p -> {
                    product.setId(p.getId());
                    return productDao.save(product);
                })
                .orElseGet(() -> productDao.save(product));
    }
}
