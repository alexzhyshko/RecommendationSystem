package io.github.zhyshko.dao.order;

import io.github.zhyshko.model.order.OrderEntry;
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
    JOIN stores as s ON o.store_id = s.id
    WHERE p.external_id = :productExternalId
    AND s.id = :storeId
)
""", nativeQuery = true)
    List<OrderEntry> findAllOrderEntriesOfOrdersWithThisProductWithStoreId(UUID storeId, UUID productExternalId);

    @Query(value = """
SELECT DISTINCT oe.*, o.time_created as order_created FROM order_entries as oe 
JOIN orders as o ON oe.order_id=o.id
JOIN users as u ON o.owner_id=u.id
WHERE u.external_id=:externalId
ORDER BY order_created DESC
""", nativeQuery = true)
    List<OrderEntry> findAllUserOrderEntries(UUID externalId);

}
