package io.github.zhyshko.dao.review;

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

}
