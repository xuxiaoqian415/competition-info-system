package com.info.competition.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CompetitionDto {

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
