package io.github.zhyshko.dao.product;

import io.github.zhyshko.model.product.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AuthorDao extends JpaRepository<Author, Long> {

    Optional<Author> findByExternalIdAndStoreId(UUID externalId, UUID storeId);

    @Query(value = """
SELECT a.* FROM authors as a 
JOIN product_authors as pa ON pa.author_id = a.id
JOIN products as p ON pa.product_id=p.id
JOIN order_entries as oe ON oe.product_id = p.id
JOIN orders as o ON oe.order_id=o.id
JOIN stores as s ON o.store_id = s.id
JOIN users as u ON o.owner_id = u.id
WHERE u.external_id = :userExternalId
AND s.id = :storeId
""", nativeQuery = true)
    List<Author> findMostPopularUserProductAuthors(UUID storeId, UUID userExternalId);
}
