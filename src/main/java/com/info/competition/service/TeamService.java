package com.info.competition.service;

import com.info.competition.dto.TeamDto;

public interface TeamService {
    /**
     * 创建团队
     */
    Integer buildTeam(TeamDto teamDto);
}
