package com.info.competition.dao;

import com.info.competition.model.dto.UserDto;
import com.info.competition.model.User;
import com.info.competition.model.UserQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserDao {

    /**
     * 根据主键查找唯一用户
     */
    User selectUser(UserQuery query);

    /**
     * 根据Id查找用户
     */
    User selectUserById(Integer id);

    /**
     * 根据学号或工号查询用户
     */
    User selectUserByNumber(String number);

    /**
     * 查询所有用户
     */
    List<User> selectAll();

    /**
     * 增加用户
     */
    Integer insertUser(User user);

    /**
     * 用户更新
     */
    Integer updateUser(User user);

    /**
     * 获取学生列表
     */
    List<UserDto> getStudentList();

    /**
     * 查找所有老师
     */
    List<User> selectTeacher();

}
