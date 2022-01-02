package com.info.competition.model;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Date;

@Data
@Alias("Competition")
public class Competition {

    private Integer id;

    private String cpName;

    private String cpContent;

    //报名起止时间
    private Date applyStart;

    private Date applyEnd;

    //竞赛起止时间
    private Date cpStart;

    private Date cpEnd;
}
