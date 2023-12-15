package io.github.zhyshko.service.product;

import io.github.zhyshko.model.product.Product;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ProductService {

    List<Product> getOrderedProducts(UUID userExternalId);

    List<Product> getProductsOrderedAfter(LocalDateTime dateAfter);

    Product saveOrUpdate(Product product);
}
