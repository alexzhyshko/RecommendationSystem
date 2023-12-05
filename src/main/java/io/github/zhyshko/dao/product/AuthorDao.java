package io.github.zhyshko.dao.product;

import io.github.zhyshko.model.product.Author;
import io.github.zhyshko.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AuthorDao extends JpaRepository<Author, Long> {

    Optional<Author> findByExternalIdAndStoreId(UUID externalId, UUID storeId);

}
