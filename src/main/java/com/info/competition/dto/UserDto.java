package com.info.competition.dto;

import lombok.Data;

@Data
public class UserDto {
    private Integer id;
    private String number;
    private String name;
    private String mobile;
    private String email;
    private String intro;
    /**
     * 0管理员 1教师 2学生
     */
    private Integer type;
}
