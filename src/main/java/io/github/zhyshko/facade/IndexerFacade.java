package io.github.zhyshko.facade;

import io.github.zhyshko.dto.order.OrderData;

public interface IndexerFacade {

    void indexOrder(OrderData orderData);

    void indexReview(OrderData reviewData);
}
