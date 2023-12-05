package io.github.zhyshko.service.product.impl;

import io.github.zhyshko.dao.product.CategoryDao;
import io.github.zhyshko.model.product.Category;
import io.github.zhyshko.service.DefaultService;
import io.github.zhyshko.service.product.CategoryService;
import org.springframework.stereotype.Service;

@Service
public class DefaultCategoryService extends DefaultService<Category> implements CategoryService {

    public DefaultCategoryService(CategoryDao categoryDao) {
        super(categoryDao);
    }

}
