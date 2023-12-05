package io.github.zhyshko.service.product;

import io.github.zhyshko.model.order.Order;
import io.github.zhyshko.model.product.Product;
import io.github.zhyshko.service.Service;

import java.util.List;
import java.util.UUID;

public interface ProductService extends Service<Product> {

    List<Product> getOrderedProducts(UUID userExternalId);

}
