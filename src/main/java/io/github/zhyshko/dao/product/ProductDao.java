package io.github.zhyshko.dao.product;

import io.github.zhyshko.model.order.Order;
import io.github.zhyshko.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

@Repository
public interface ProductDao extends JpaRepository<Product, Long> {

    Optional<Product> findByExternalIdAndStoreId(UUID externalId, UUID storeId);

    @Query(value = """
SELECT DISTINCT p.*, o.time_created FROM products as p 
JOIN order_entries as oe ON oe.product_id=p.id
JOIN orders as o ON oe.order_id=o.id
JOIN users as u ON o.owner_id=u.id
WHERE u.external_id=:externalId
ORDER BY o.time_created DESC
""", nativeQuery = true)
    List<Product> findAllProductsOrderedByUser(UUID externalId);

    @Query(value = """
SELECT DISTINCT p.*, o.time_created FROM products as p 
JOIN order_entries as oe ON oe.product_id=p.id
JOIN orders as o ON oe.order_id=o.id
WHERE o.time_created >= :dateAfter
ORDER BY o.time_created DESC
""", nativeQuery = true)
    List<Product> findProductsOrderedAfter(LocalDateTime dateAfter);


}
