package com.info.competition.service;

import com.info.competition.dao.UserDao;
import com.info.competition.model.LoginDto;
import com.info.competition.model.User;
import com.info.competition.model.UserQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public Integer login(LoginDto user) {
        UserQuery query = new UserQuery();
        query.setNumber(user.getUsername());
        query.setPassword(user.getPassword());
        query.setType(user.getType());
        User thisUser = userDao.selectUser(query);
        if (thisUser == null){
            return -1;
        }
        else {
            return thisUser.getId();
        }
    }

    @Override
    public User getUserById(Integer userId) {
        UserQuery query = new UserQuery();
        query.setId(userId);
        User u = userDao.selectUser(query);
        return u;
    }
}
