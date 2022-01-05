package com.info.competition.dto;

import lombok.Data;

@Data
public class TeamDto {
    private Integer id;
    private Integer cpId;
    private String teamName;
    private Integer leaderId;
    private String teamIntro;
    private String member;
}
