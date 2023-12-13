package io.github.zhyshko.dao.order;

import io.github.zhyshko.model.order.OrderEntry;
import io.github.zhyshko.model.review.ReviewEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderEntryDao extends JpaRepository<OrderEntry, Long> {

    Optional<OrderEntry> findByExternalIdAndStoreId(UUID externalId, UUID storeId);

    @Query(value = """
SELECT oe.* FROM orders as o 
JOIN order_entries as oe ON oe.order_id=o.id 
WHERE o.id IN (
    SELECT o.id FROM orders as o 
    JOIN order_entries as oe ON oe.order_id=o.id
    JOIN products as p ON oe.product_id = p.id
    WHERE p.external_id = :productExternalId
)
""", nativeQuery = true)
    List<OrderEntry> findAllOrderEntriesOfOrdersWithThisProduct(UUID productExternalId);

}
