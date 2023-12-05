package io.github.zhyshko.dao.product;

import io.github.zhyshko.model.product.ProductAttribute;
import io.github.zhyshko.model.product.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PublisherDao extends JpaRepository<Publisher, Long> {

    Optional<Publisher> findByExternalIdAndStoreId(UUID externalId, UUID storeId);

}
