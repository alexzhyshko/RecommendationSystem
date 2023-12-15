package io.github.zhyshko.dao.product;

import io.github.zhyshko.model.product.Category;
import io.github.zhyshko.model.product.ProductAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductAttributeDao extends JpaRepository<ProductAttribute, Long> {

    Optional<ProductAttribute> findByExternalIdAndStoreId(UUID externalId, UUID storeId);

    @Query(value = """
SELECT pa.* FROM product_attributes as pa 
JOIN product_productattributes as ppa ON ppa.product_attribute_id = pa.id
JOIN products as p ON ppa.product_id=p.id
JOIN order_entries as oe ON oe.product_id = p.id
JOIN orders as o ON oe.order_id=o.id
JOIN stores as s ON o.store_id = s.id
JOIN users as u ON o.owner_id = u.id
WHERE u.external_id = :userExternalId
AND s.id = :storeId
""", nativeQuery = true)
    List<ProductAttribute> findMostPopularUserProductAttributes(UUID storeId, UUID userExternalId);

}
