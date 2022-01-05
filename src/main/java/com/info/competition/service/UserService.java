package com.info.competition.service;

import com.info.competition.dto.LoginDto;
import com.info.competition.model.User;

public interface UserService {

    Integer login(LoginDto loginDto);

    /**
     * 根据Id获取用户信息
     */
    User getUserById(Integer userId);

}
