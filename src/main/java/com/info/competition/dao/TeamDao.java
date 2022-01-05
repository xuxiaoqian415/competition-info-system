package com.info.competition.dao;

import com.info.competition.model.Team;

public interface TeamDao {
    /**
     * 增加团队
     */
    Integer insertTeam(Team team);

    /**
     * 根据竞赛id与队长id查询团队id
     */
    Integer selectTeamId(Integer cpId,Integer leaderId);
}
