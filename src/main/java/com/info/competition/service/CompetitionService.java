package com.info.competition.service;

import com.info.competition.model.dto.CompetitionDto;
import com.info.competition.model.Competition;

import java.util.List;

public interface CompetitionService {

    /**
     * 管理员获取竞赛列表
     */
    List<Competition> getCompetitionList();

    /**
     * 获取当前时间可报名竞赛
     */
    List<Competition> getCompetitionByApply();

    /**
     * 管理员发布竞赛
     */
    int addCompetition(Competition competition);

    /**
     * 根据Id获取竞赛详情
     */
    Competition getCompetitionDetail(Integer id);

    /**
     * 删除竞赛
     */
    Integer deleteCompetition(Integer id);
    /**
     * 竞赛更新
     */
    Integer updateCompetition(CompetitionDto competitionDto);
}
