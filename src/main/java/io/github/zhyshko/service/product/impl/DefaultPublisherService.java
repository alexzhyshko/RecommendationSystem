package io.github.zhyshko.service.product.impl;

import io.github.zhyshko.dao.product.PublisherDao;
import io.github.zhyshko.model.product.Publisher;
import io.github.zhyshko.service.DefaultService;
import io.github.zhyshko.service.product.PublisherService;
import org.springframework.stereotype.Service;

@Service
public class DefaultPublisherService extends DefaultService<Publisher> implements PublisherService {

    public DefaultPublisherService(PublisherDao publisherDao) {
        super(publisherDao);
    }

}
