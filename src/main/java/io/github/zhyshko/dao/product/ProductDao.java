package io.github.zhyshko.dao.product;

import io.github.zhyshko.model.order.Order;
import io.github.zhyshko.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

@Repository
public interface ProductDao extends JpaRepository<Product, Long> {

    Optional<Product> findByExternalId(UUID externalId);

    Optional<Product> findByExternalIdAndStoreId(UUID externalId, UUID storeId);

    @Query(value = """
SELECT p.* FROM products as p 
JOIN review_entries as re ON re.product_id=p.id
JOIN reviews as r ON re.review_id=r.id
JOIN orders as o ON o.review_id=r.id
JOIN users as u ON o.owner_id=u.id
WHERE u.external_id=:externalId
""", nativeQuery = true)
    List<Product> findAllProductsOrderedByUser(UUID externalId);

}
