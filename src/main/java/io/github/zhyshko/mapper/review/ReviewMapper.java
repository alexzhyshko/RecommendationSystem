package io.github.zhyshko.mapper.review;

import io.github.zhyshko.dao.review.ReviewDao;
import io.github.zhyshko.dao.user.UserDao;
import io.github.zhyshko.dto.review.ReviewData;
import io.github.zhyshko.dto.review.ReviewEntryData;
import io.github.zhyshko.dto.user.UserData;
import io.github.zhyshko.mapper.StoreMapper;
import io.github.zhyshko.mapper.product.ProductMapper;
import io.github.zhyshko.model.review.Review;
import io.github.zhyshko.model.review.ReviewEntry;
import io.github.zhyshko.model.user.User;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {ReviewEntryMapper.class, StoreMapper.class, ProductMapper.class})
public abstract class ReviewMapper {

    @Autowired
    protected ReviewDao reviewDao;
    @Autowired
    protected StoreMapper storeMapper;
    @Autowired
    protected ReviewEntryMapper reviewEntryMapper;

    public Review toModel(ReviewData orderData) {
        if ( orderData == null ) {
            return null;
        }

        return findAndPopulateIfExists(orderData)
                .orElseGet(() -> reviewDao.save(createReview(orderData)));
    }
    public abstract ReviewData toDto(Review order);

    private Optional<Review> findAndPopulateIfExists(ReviewData reviewData) {
        return reviewDao.findByExternalIdAndStoreId(reviewData.getExternalId(), reviewData.getStore().getExternalId())
                .map(r -> {
                    r.setReviewEntries(reviewEntryDataListToReviewEntryList( reviewData.getReviewEntries() ));
                    return reviewDao.save(r);
                });
    }

    private Review createReview(ReviewData reviewData) {
        Review.ReviewBuilder<?, ?> review = Review.builder();

        review.id( reviewData.getId() );
        review.externalId( reviewData.getExternalId() );
        review.store( storeMapper.toModel( reviewData.getStore() ) );
        review.reviewEntries( reviewEntryDataListToReviewEntryList( reviewData.getReviewEntries() ) );

        return review.build();
    }

    protected List<ReviewEntry> reviewEntryDataListToReviewEntryList(List<ReviewEntryData> list) {
        if ( list == null ) {
            return null;
        }

        List<ReviewEntry> list1 = new ArrayList<ReviewEntry>( list.size() );
        for ( ReviewEntryData reviewEntryData : list ) {
            list1.add( reviewEntryMapper.toModel( reviewEntryData ) );
        }

        return list1;
    }

}
