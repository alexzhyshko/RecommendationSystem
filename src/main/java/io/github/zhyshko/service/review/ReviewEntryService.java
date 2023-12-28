package io.github.zhyshko.service.review;

import io.github.zhyshko.model.review.ReviewEntry;

import java.util.UUID;

public interface ReviewEntryService {

    ReviewEntry getReviewEntry(UUID entryId, UUID storeId);

    void save(ReviewEntry existing);
}
