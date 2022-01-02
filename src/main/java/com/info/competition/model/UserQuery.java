package com.info.competition.model;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("UserQuery")
public class UserQuery {

    private String number;

    private String password;

    private Integer type;

    private Integer id;

//    private List<Integer> ids;

}
