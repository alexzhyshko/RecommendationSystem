package io.github.zhyshko.mapper.product;

import io.github.zhyshko.dao.product.CategoryDao;
import io.github.zhyshko.dto.product.CategoryData;
import io.github.zhyshko.mapper.StoreMapper;
import io.github.zhyshko.model.product.Category;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", uses = {StoreMapper.class})
public abstract class CategoryMapper {

    @Autowired
    protected StoreMapper storeMapper;
    @Autowired
    protected CategoryDao categoryDao;

    public Category toModel(CategoryData orderData) {
        if ( orderData == null ) {
            return null;
        }

        return categoryDao.findByExternalIdAndStoreId(orderData.getExternalId(), orderData.getStore().getExternalId())
                .orElseGet(() -> categoryDao.save(createCategory(orderData)));
    }
    public abstract CategoryData toDto(Category order);

    private Category createCategory(CategoryData categoryData) {
        Category.CategoryBuilder<?, ?> category = Category.builder();

        category.id( categoryData.getId() );
        category.externalId( categoryData.getExternalId() );
        category.store( storeMapper.toModel( categoryData.getStore() ) );
        category.subcategories( categoryDataListToCategoryList( categoryData.getSubcategories() ) );

        return category.build();
    }

    protected List<Category> categoryDataListToCategoryList(List<CategoryData> list) {
        if ( list == null ) {
            return null;
        }

        List<Category> list1 = new ArrayList<Category>( list.size() );
        for ( CategoryData categoryData : list ) {
            list1.add( toModel( categoryData ) );
        }

        return list1;
    }
}
