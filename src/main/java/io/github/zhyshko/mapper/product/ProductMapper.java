package io.github.zhyshko.mapper.product;

import io.github.zhyshko.dao.product.ProductDao;
import io.github.zhyshko.dto.order.OrderData;
import io.github.zhyshko.dto.product.*;
import io.github.zhyshko.mapper.StoreMapper;
import io.github.zhyshko.model.order.Order;
import io.github.zhyshko.model.product.*;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {ProductAttributeMapper.class, AuthorMapper.class, PublisherMapper.class, CategoryMapper.class, StoreMapper.class})
public abstract class ProductMapper {

    @Autowired
    protected ProductAttributeMapper productAttributeMapper;
    @Autowired
    protected AuthorMapper authorMapper;
    @Autowired
    protected PublisherMapper publisherMapper;
    @Autowired
    protected CategoryMapper categoryMapper;
    @Autowired
    protected StoreMapper storeMapper;
    @Autowired
    protected ProductDao productDao;
    
    public Product toModel(ProductData orderData) {
        if ( orderData == null ) {
            return null;
        }

        return productDao.findByExternalIdAndStoreId(orderData.getExternalId(), orderData.getStore().getExternalId())
                .orElseGet(() -> productDao.save(createProduct(orderData)));

    }
    public abstract ProductData toDto(Product order);

    public List<ProductData> toDtoList(List<Product> products) {
        return products.stream().map(this::toDto).toList();
    }

    private Product createProduct(ProductData productData) {
        Product.ProductBuilder<?, ?> product = Product.builder();

        product.id( productData.getId() );
        product.externalId( productData.getExternalId() );
        product.store( storeMapper.toModel( productData.getStore() ) );
        product.productAttributes( productAttributeDataListToProductAttributeList( productData.getProductAttributes() ) );
        product.authors( authorDataListToAuthorList( productData.getAuthors() ) );
        product.publishers( publisherDataListToPublisherList( productData.getPublishers() ) );
        product.categories( categoryDataListToCategoryList( productData.getCategories() ) );

        return product.build();
    }

    protected List<ProductAttribute> productAttributeDataListToProductAttributeList(List<ProductAttributeData> list) {
        if ( list == null ) {
            return null;
        }

        List<ProductAttribute> list1 = new ArrayList<ProductAttribute>( list.size() );
        for ( ProductAttributeData productAttributeData : list ) {
            list1.add( productAttributeMapper.toModel( productAttributeData ) );
        }

        return list1;
    }

    protected List<Author> authorDataListToAuthorList(List<AuthorData> list) {
        if ( list == null ) {
            return null;
        }

        List<Author> list1 = new ArrayList<Author>( list.size() );
        for ( AuthorData authorData : list ) {
            list1.add( authorMapper.toModel( authorData ) );
        }

        return list1;
    }

    protected List<Publisher> publisherDataListToPublisherList(List<PublisherData> list) {
        if ( list == null ) {
            return null;
        }

        List<Publisher> list1 = new ArrayList<Publisher>( list.size() );
        for ( PublisherData publisherData : list ) {
            list1.add( publisherMapper.toModel( publisherData ) );
        }

        return list1;
    }

    protected List<Category> categoryDataListToCategoryList(List<CategoryData> list) {
        if ( list == null ) {
            return null;
        }

        List<Category> list1 = new ArrayList<Category>( list.size() );
        for ( CategoryData categoryData : list ) {
            list1.add( categoryMapper.toModel( categoryData ) );
        }

        return list1;
    }

}
