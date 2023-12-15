package io.github.zhyshko.dao.product;

import io.github.zhyshko.model.product.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PublisherDao extends JpaRepository<Publisher, Long> {

    Optional<Publisher> findByExternalIdAndStoreId(UUID externalId, UUID storeId);

    @Query(value = """
SELECT pu.* FROM publishers as pu 
JOIN product_publishers as pp ON pp.publisher_id = pu.id
JOIN products as p ON pp.product_id=p.id
JOIN order_entries as oe ON oe.product_id = p.id
JOIN orders as o ON oe.order_id=o.id
JOIN stores as s ON o.store_id = s.id
JOIN users as u ON o.owner_id = u.id
WHERE u.external_id = :userExternalId
AND s.id = :storeId
""", nativeQuery = true)
    List<Publisher> findMostPopularUserProductPublishers(UUID storeId, UUID userExternalId);
}
