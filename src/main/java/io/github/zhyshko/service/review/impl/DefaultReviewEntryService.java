package io.github.zhyshko.service.review.impl;

import io.github.zhyshko.dao.review.ReviewEntryDao;
import io.github.zhyshko.model.review.ReviewEntry;
import io.github.zhyshko.service.review.ReviewEntryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DefaultReviewEntryService implements ReviewEntryService {

    private ReviewEntryDao reviewEntryDao;

    public DefaultReviewEntryService(ReviewEntryDao reviewEntryDao) {
        this.reviewEntryDao = reviewEntryDao;
    }

    @Override
    public List<ReviewEntry> getAllRangedByReviewRatingAndPopularity(UUID productId) {
        return reviewEntryDao.findAllReviewEntriesOfReviewsWithThisProduct(productId);
    }

}
