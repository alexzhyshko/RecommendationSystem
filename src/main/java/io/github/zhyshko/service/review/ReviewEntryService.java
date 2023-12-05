package io.github.zhyshko.service.review;

import io.github.zhyshko.model.review.ReviewEntry;
import io.github.zhyshko.service.Service;

import java.util.List;
import java.util.UUID;

public interface ReviewEntryService extends Service<ReviewEntry> {

    List<ReviewEntry> getAllRangedByReviewRatingAndPopularity(UUID productId);

}
