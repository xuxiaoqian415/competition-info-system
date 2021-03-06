package com.info.competition.dao;

import com.info.competition.model.dto.UserDto;
import com.info.competition.model.User;
import com.info.competition.model.Query;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserDao {

    /**
     * 根据主键查找唯一用户
     */
    User selectUser(Query query);

    /**
     * 根据Id查找用户
     */
    UserDto selectUserById(Integer id);

    /**
     * 根据学号或工号查询用户
     */
    User selectUserByNumber(String number);

    /**
     * 根据条件查询所有用户
     */
    List<UserDto> selectUsers(Query query);

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
     * 获取老师列表
     */
    List<UserDto> getTeacherList();

    /**
     * 根据id删除用户
     */
    Integer deleteUser(@Param("id") Integer id);

}
