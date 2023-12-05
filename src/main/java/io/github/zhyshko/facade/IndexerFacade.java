package io.github.zhyshko.facade;

import io.github.zhyshko.dto.order.OrderData;
import io.github.zhyshko.dto.review.ReviewData;
import org.springframework.core.convert.converter.Converter;

public interface IndexerFacade {

    void indexOrder(OrderData orderData);

    void indexReview(ReviewData reviewData);
}
