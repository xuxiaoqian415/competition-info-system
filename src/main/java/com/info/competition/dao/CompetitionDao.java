package com.info.competition.dao;

import com.info.competition.model.Query;
import com.info.competition.model.StuComp;
import com.info.competition.model.dto.CompetitionDto;
import com.info.competition.model.Competition;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface CompetitionDao {
    /**
     * 根据条件查询全部竞赛信息
     */
    List<Competition> getCompetitionList(Query query);

    /**
     *获取当前时间内可报名的竞赛信息
     */
    List<Competition> getCompetitionByApply(Date currentTime);

    //竞赛发布
    int addCompetition(Competition competition);

    /**
     * 根据Id获取竞赛详情
     */
    CompetitionDto getCompetitionDetail(@Param("id") Integer id);

    /**
     * 删除竞赛
     */
    Integer deleteCompetition(@Param("id") Integer id);

    /**
     * 竞赛更新
     */
    Integer updateCompetition(Competition competition);

    /**
     * 根据学生id获取已报名竞赛
     */
    List<CompetitionDto> getApplyList(@Param("id") Integer id);

    /**
     * 增加一条student-competition记录
     */
    void insertStuComp(StuComp stuComp);

    /**
     * 查看学生是否已报名某竞赛
     */
    StuComp ifHaveApply(StuComp stuComp);

}
