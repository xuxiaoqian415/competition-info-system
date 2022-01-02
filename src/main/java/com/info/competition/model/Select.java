package com.info.competition.model;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("Select")
public class Select {

    private Integer id;

    private Integer teamId;

    private Integer teacherId;

    private Integer selectType;//反选

    private Integer flag;//字段有效
}
