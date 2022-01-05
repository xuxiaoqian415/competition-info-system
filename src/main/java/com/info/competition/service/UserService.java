package com.info.competition.service;

import com.info.competition.dto.LoginDto;
import com.info.competition.dto.UserDto;

public interface UserService {

    /**
     * 用户登录
     */
    Integer login(LoginDto loginDto);

    /**
     * 根据Id获取用户信息
     */
    UserDto getUserById(Integer userId);


}
