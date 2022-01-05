package com.info.competition.service.impl;

import com.info.competition.dao.UserDao;
import com.info.competition.model.dto.LoginDto;
import com.info.competition.model.dto.UserDto;
import com.info.competition.model.User;
import com.info.competition.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

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
        return userDao.selectUserById(userId);
    }

    @Override
    public List<UserDto> getStudentList() {
        return userDao.getStudentList();
    }

    @Override
    public List<UserDto> getAllTeacher(){
        return userDao.getTeacherList();
    }
}
