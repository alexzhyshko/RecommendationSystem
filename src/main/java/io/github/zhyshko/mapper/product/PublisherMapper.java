package io.github.zhyshko.mapper.product;

import io.github.zhyshko.dao.product.PublisherDao;
import io.github.zhyshko.dto.product.PublisherData;
import io.github.zhyshko.mapper.StoreMapper;
import io.github.zhyshko.model.product.Publisher;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {StoreMapper.class})
public abstract class PublisherMapper {

    @Autowired
    protected StoreMapper storeMapper;
    @Autowired
    protected PublisherDao publisherDao;

    public Publisher toModel(PublisherData orderData) {
        if ( orderData == null ) {
            return null;
        }

        return publisherDao.findByExternalIdAndStoreId(orderData.getExternalId(), orderData.getStore().getExternalId())
                .orElseGet(() -> publisherDao.save(createPublisher(orderData)));
    }
    public abstract PublisherData toDto(Publisher order);

    private Publisher createPublisher(PublisherData publisherData) {
        Publisher.PublisherBuilder<?, ?> publisher = Publisher.builder();

        publisher.id( publisherData.getId() );
        publisher.externalId( publisherData.getExternalId() );
        publisher.store( storeMapper.toModel( publisherData.getStore() ) );

        return publisher.build();
    }

}
