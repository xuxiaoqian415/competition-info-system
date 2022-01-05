package com.info.competition.service.impl;

import com.info.competition.dao.UserDao;
import com.info.competition.dto.LoginDto;
import com.info.competition.dto.UserDto;
import com.info.competition.model.User;
import com.info.competition.model.UserQuery;
import com.info.competition.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

//    @Override
//    public Integer login(LoginDto user) {
//        UserQuery query = new UserQuery();
//        query.setNumber(user.getUsername());
//        query.setPassword(user.getPassword());
//        query.setType(user.getType());
//        User thisUser = userDao.selectUser(query);
//        if (thisUser == null){
//            return -1;
//        }
//        else {
//            return thisUser.getId();
//        }
//    }
//
//    @Override
//    public User getUserById(Integer userId) {
//        UserQuery query = new UserQuery();
//        query.setId(userId);
//        User u = userDao.selectUser(query);
//        return u;
//    }

    @Override
    public Integer login(LoginDto loginDto){
        User user = userDao.selectUserByNumber(loginDto.getNumber());
        if(user != null){
            if(loginDto.getPassword().equals(user.getPassword())){
                if(loginDto.getType().equals(user.getType())){
                    return user.getId();    //登录验证成功
                }
                else{
                    return -1;  //类型不匹配
                }

            }
            else{
                return -2;  //密码错误
            }
        }
        else{
            return -3;  //无该学号/工号的用户
        }
    }

    @Override
    public UserDto getUserById(Integer userId) {
        User user = userDao.selectUserById(userId);
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setNumber(user.getNumber());
        userDto.setName(user.getName());
        userDto.setMobile(user.getMobile());
        userDto.setEmail(user.getEmail());
        userDto.setIntro(user.getIntro());
        userDto.setType(user.getType());
        return userDto;
    }
}
