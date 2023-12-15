package io.github.zhyshko.dao.product;

import io.github.zhyshko.model.product.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryDao extends JpaRepository<Category, Long> {

    Optional<Category> findByExternalIdAndStoreId(UUID externalId, UUID storeId);

    @Query(value = """
SELECT c.* FROM categories as c 
JOIN product_categories as pc ON pc.category_id = c.id
JOIN products as p ON pc.product_id=p.id
JOIN order_entries as oe ON oe.product_id = p.id
JOIN orders as o ON oe.order_id=o.id
JOIN stores as s ON o.store_id = s.id
JOIN users as u ON o.owner_id = u.id
WHERE u.external_id = :userExternalId
AND s.id = :storeId
""", nativeQuery = true)
    List<Category> findMostPopularUserCategories(UUID storeId, UUID userExternalId);
}
