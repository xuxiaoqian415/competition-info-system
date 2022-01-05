package com.info.competition.dao;

import com.info.competition.model.Competition;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface CompetitionDao {
    /**
     * 获取全部竞赛信息
     */
    List<Competition> getCompetitionList();

    /**
     * 添加竞赛信息
     */
    Integer insertCompetition(Competition competition);

    /**
     *获取当前时间内可报名的竞赛信息
     */
    List<Competition> getCompetitionByApply(Date currentTime);

}