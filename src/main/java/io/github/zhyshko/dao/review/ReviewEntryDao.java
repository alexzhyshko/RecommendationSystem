package io.github.zhyshko.dao.review;

import io.github.zhyshko.model.product.Product;
import io.github.zhyshko.model.review.Review;
import io.github.zhyshko.model.review.ReviewEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReviewEntryDao extends JpaRepository<ReviewEntry, Long> {

    Optional<ReviewEntry> findByExternalIdAndStoreId(UUID externalId, UUID storeId);

    @Query(value = """
SELECT re.* FROM reviews as r 
JOIN review_entries as re ON re.review_id=r.id 
WHERE r.id IN (
    SELECT r.id FROM reviews as r 
    JOIN review_entries as re ON re.review_id=r.id
    JOIN products as p ON re.product_id = p.id
    WHERE p.external_id = :productExternalId
)
""", nativeQuery = true)
    List<ReviewEntry> findAllReviewEntriesOfReviewsWithThisProduct(UUID productExternalId);

}
