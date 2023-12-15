package io.github.zhyshko.service.product.impl;

import io.github.zhyshko.dao.product.AuthorDao;
import io.github.zhyshko.dao.product.PublisherDao;
import io.github.zhyshko.model.product.Author;
import io.github.zhyshko.model.product.Publisher;
import io.github.zhyshko.service.product.AuthorService;
import io.github.zhyshko.service.product.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class DefaultPublisherService implements PublisherService {

    @Autowired
    private PublisherDao publisherDao;

    @Override
    public List<Publisher> getMostPopularUserPublishers(UUID storeId, UUID userExternalId) {
        return publisherDao.findMostPopularUserProductPublishers(storeId, userExternalId);
    }
}
