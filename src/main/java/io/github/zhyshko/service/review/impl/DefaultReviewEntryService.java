package io.github.zhyshko.service.review.impl;

import io.github.zhyshko.dao.review.ReviewEntryDao;
import io.github.zhyshko.model.review.ReviewEntry;
import io.github.zhyshko.service.review.ReviewEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DefaultReviewEntryService implements ReviewEntryService {

    @Autowired
    private ReviewEntryDao reviewEntryDao;

    @Override
    public ReviewEntry getReviewEntry(UUID entryId, UUID storeId) {
        return this.reviewEntryDao.findByExternalIdAndStoreId(entryId, storeId)
                .orElseThrow(() -> new RuntimeException("review entry not found"));
    }

    @Override
    public void save(ReviewEntry existing) {
        reviewEntryDao.save(existing);
    }
}
