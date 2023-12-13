package io.github.zhyshko.service.review;

import io.github.zhyshko.model.review.ReviewEntry;

import java.util.List;
import java.util.UUID;

public interface ReviewEntryService {

    List<ReviewEntry> getAllRangedByReviewRatingAndPopularity(UUID productId);

}
