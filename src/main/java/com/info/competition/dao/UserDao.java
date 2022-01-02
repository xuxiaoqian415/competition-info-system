package com.info.competition.dao;

import com.info.competition.model.User;
import com.info.competition.model.UserQuery;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {

    /**
     * 根据主键查找唯一用户
     */
    User selectUser(UserQuery query);

}
