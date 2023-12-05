package io.github.zhyshko.dao.order;

import io.github.zhyshko.model.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderDao extends JpaRepository<Order, Long> {

    Optional<Order> findByExternalIdAndStoreId(UUID externalId, UUID storeId);

}
