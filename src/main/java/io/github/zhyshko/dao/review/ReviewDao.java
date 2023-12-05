package io.github.zhyshko.dao.review;

import io.github.zhyshko.model.review.Review;
import io.github.zhyshko.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReviewDao extends JpaRepository<Review, Long> {

    Optional<Review> findByExternalIdAndStoreId(UUID externalId, UUID storeId);

}
