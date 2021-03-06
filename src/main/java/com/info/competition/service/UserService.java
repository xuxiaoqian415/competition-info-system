package com.info.competition.service;

import com.info.competition.model.Query;
import com.info.competition.model.dto.LoginDto;
import com.info.competition.model.dto.TeamDto;
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

    /**
     * 获取所有老师信息
     */
    List<UserDto> getTeacherList();

    /**
     * 更新用户
     */
    Integer updateUser(UserDto userDto);

    /**
     * 修改密码
     */
    Integer updatePsw(UserDto userDto);

    /**
     * 根据学号/工号删除用户
     */
    Integer deleteUser(Integer id);

    /**
     * 获取所有用户
     */
    List<UserDto> getAllUser();

    /**
     * 添加用户
     */
    Integer addUser(UserDto userDto);

    /**
     * 根据条件查询用户列表
     */
    List<UserDto> searchUser(Query query);

}
