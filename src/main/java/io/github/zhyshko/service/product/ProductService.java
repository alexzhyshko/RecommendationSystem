package io.github.zhyshko.service.product;

import io.github.zhyshko.model.product.Product;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    List<Product> getOrderedProducts(UUID userExternalId);

    Product saveOrUpdate(Product product);
}
