package io.github.zhyshko.dao.product;

import io.github.zhyshko.model.product.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryDao extends JpaRepository<Category, Long> {

    Optional<Category> findByExternalIdAndStoreId(UUID externalId, UUID storeId);

}
