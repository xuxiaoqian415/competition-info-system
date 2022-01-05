package com.info.competition.dto;

import lombok.Data;

@Data
public class SelectDto {
    private Integer id;

    private Integer teamId;

    private Integer teacherId;

    private Integer selectType;//反选

    private Integer flag;//字段有效
}
