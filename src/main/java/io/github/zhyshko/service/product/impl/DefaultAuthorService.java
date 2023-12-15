package io.github.zhyshko.service.product.impl;

import io.github.zhyshko.dao.product.AuthorDao;
import io.github.zhyshko.model.product.Author;
import io.github.zhyshko.service.product.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class DefaultAuthorService implements AuthorService {

    @Autowired
    private AuthorDao authorDao;

    @Override
    public List<Author> getMostPopularUserAuthors(UUID storeId, UUID userExternalId) {
        return authorDao.findMostPopularUserProductAuthors(storeId, userExternalId);
    }
}
