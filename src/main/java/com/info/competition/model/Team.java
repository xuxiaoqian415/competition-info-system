package com.info.competition.model;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("Team")
public class Team {
    private Integer id;

    private Integer cpId;

    private String teamName;

    private Integer leaderId;

    private String teamIntro;

    private String member;
}
