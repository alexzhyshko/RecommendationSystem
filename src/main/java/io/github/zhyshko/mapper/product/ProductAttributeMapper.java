package io.github.zhyshko.mapper.product;

import io.github.zhyshko.dao.product.ProductAttributeDao;
import io.github.zhyshko.dto.product.CategoryData;
import io.github.zhyshko.dto.product.ProductAttributeData;
import io.github.zhyshko.mapper.StoreMapper;
import io.github.zhyshko.model.product.Category;
import io.github.zhyshko.model.product.ProductAttribute;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring", uses = {StoreMapper.class})
public abstract class ProductAttributeMapper {

    @Autowired
    protected StoreMapper storeMapper;
    @Autowired
    protected ProductAttributeDao productAttributeDao;

    public ProductAttribute toModel(ProductAttributeData orderData) {
        if ( orderData == null ) {
            return null;
        }

        return productAttributeDao.findByExternalIdAndStoreId(orderData.getExternalId(), orderData.getStore().getExternalId())
                .orElseGet(() -> productAttributeDao.save(createProductAttribute(orderData)));
    }
    public abstract ProductAttributeData toDto(ProductAttribute order);

    public List<ProductAttributeData> toDtolist(List<ProductAttribute> attributeList) {
        return attributeList
                .stream()
                .map(this::toDto)
                .toList();
    }

    private ProductAttribute createProductAttribute(ProductAttributeData productAttributeData) {
        ProductAttribute.ProductAttributeBuilder<?, ?> productAttribute = ProductAttribute.builder();

        productAttribute.id( productAttributeData.getId() );
        productAttribute.externalId( productAttributeData.getExternalId() );
        productAttribute.store( storeMapper.toModel( productAttributeData.getStore() ) );

        return productAttribute.build();
    }
}
