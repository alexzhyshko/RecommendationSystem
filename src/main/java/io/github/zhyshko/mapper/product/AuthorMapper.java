package io.github.zhyshko.mapper.product;

import io.github.zhyshko.dao.product.AuthorDao;
import io.github.zhyshko.dto.product.AuthorData;
import io.github.zhyshko.mapper.StoreMapper;
import io.github.zhyshko.model.product.Author;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {StoreMapper.class})
public abstract class AuthorMapper {

    @Autowired
    protected StoreMapper storeMapper;
    @Autowired
    protected AuthorDao authorDao;

    public Author toModel(AuthorData orderData) {
        if ( orderData == null ) {
            return null;
        }

        return authorDao.findByExternalIdAndStoreId(orderData.getExternalId(), orderData.getStore().getExternalId())
                .orElseGet(() -> authorDao.save(createAuthor(orderData)));
    }
    public abstract AuthorData toDto(Author order);

    private Author createAuthor(AuthorData authorData) {
        Author.AuthorBuilder<?, ?> author = Author.builder();

        author.id( authorData.getId() );
        author.externalId( authorData.getExternalId() );
        author.store( storeMapper.toModel( authorData.getStore() ) );

        return author.build();
    }

}
