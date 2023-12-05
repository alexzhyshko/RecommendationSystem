package io.github.zhyshko.service.user.impl;

import io.github.zhyshko.dao.user.UserDao;
import io.github.zhyshko.model.user.User;
import io.github.zhyshko.service.DefaultService;
import io.github.zhyshko.service.user.UserService;
import org.springframework.stereotype.Service;

@Service
public class DefaultUserService extends DefaultService<User> implements UserService {

    public DefaultUserService(UserDao userDao) {
        super(userDao);
    }

}
