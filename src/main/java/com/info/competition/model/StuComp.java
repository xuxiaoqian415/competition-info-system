package com.info.competition.model;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("StuComp")
public class StuComp {

    private Integer id;

    private Integer studentId;

    private Integer competitionId;

    private Integer teamId;
}
