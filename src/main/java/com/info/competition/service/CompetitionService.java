package com.info.competition.service;

import com.info.competition.model.Competition;

import java.util.List;

public interface CompetitionService {

    /**
     * 管理员获取竞赛列表
     */
    List<Competition> getCompetitionList();

    /**
     * 管理员发布竞赛
     */
    int addCompetition(Competition competition);
}
