package com.info.competition.dto;

import lombok.Data;

@Data
public class LoginDto {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 0管理员 1教师 2学生
     */
    private Integer type;

}
