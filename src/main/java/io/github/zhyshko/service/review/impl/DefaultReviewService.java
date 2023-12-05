package io.github.zhyshko.service.review.impl;

import io.github.zhyshko.dao.review.ReviewDao;
import io.github.zhyshko.model.review.Review;
import io.github.zhyshko.service.DefaultService;
import io.github.zhyshko.service.review.ReviewService;
import org.springframework.stereotype.Service;

@Service
public class DefaultReviewService extends DefaultService<Review> implements ReviewService {

    public DefaultReviewService(ReviewDao reviewDao) {
        super(reviewDao);
    }

}
