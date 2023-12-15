package io.github.zhyshko.service.product;

import io.github.zhyshko.model.product.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryService {

    List<Category> getMostPopularUserCategories(UUID storeId, UUID userExternalId);

}
