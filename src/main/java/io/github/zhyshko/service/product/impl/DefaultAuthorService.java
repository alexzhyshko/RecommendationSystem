package io.github.zhyshko.service.product.impl;

import io.github.zhyshko.dao.product.AuthorDao;
import io.github.zhyshko.model.product.Author;
import io.github.zhyshko.service.DefaultService;
import io.github.zhyshko.service.product.AuthorService;
import org.springframework.stereotype.Service;

@Service
public class DefaultAuthorService extends DefaultService<Author> implements AuthorService {

    public DefaultAuthorService(AuthorDao authorDao) {
        super(authorDao);
    }

}
