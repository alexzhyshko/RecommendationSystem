package io.github.zhyshko.facade;

import io.github.zhyshko.dto.product.ProductData;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface RecommendationsFacade {

    Map<Long, ProductData> getForUser(UUID userExternalId);

}
