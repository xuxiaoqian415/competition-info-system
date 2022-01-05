package com.info.competition.model.dto;

import lombok.Data;

@Data
public class TeamDto {
    private Integer id;
    private Integer cpId;
    private String teamName;
    private Integer leaderId;
    private String teamIntro;
    private String member;
    private Integer member1Id;
    private Integer member2Id;
    private Integer member3Id;
    private Integer member4Id;
}
