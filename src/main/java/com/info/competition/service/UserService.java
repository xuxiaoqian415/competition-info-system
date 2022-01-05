package com.info.competition.service;

import com.info.competition.model.dto.LoginDto;
import com.info.competition.model.dto.UserDto;

import java.util.List;

public interface UserService {

    /**
     * 用户登录
     */
    Integer login(LoginDto loginDto);

    /**
     * 根据Id获取用户信息
     */
    UserDto getUserById(Integer userId);

    /**
     * 获取学生列表
     */
    List<UserDto> getStudentList();


}
