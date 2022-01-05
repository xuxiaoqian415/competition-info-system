package com.info.competition.dao;

import com.info.competition.model.dto.CompetitionDto;
import com.info.competition.model.Competition;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface CompetitionDao {
    /**
     * 获取全部竞赛信息
     */
    List<Competition> getCompetitionList();

    /**
     *获取当前时间内可报名的竞赛信息
     */
    List<Competition> getCompetitionByApply(Date currentTime);

    //竞赛发布
    int addCompetition(Competition competition);

    /**
     * 根据Id获取竞赛详情
     */
    Competition getCompetitionDetail(@Param("id") Integer id);

}
