package com.info.competition.service;

import com.info.competition.model.dto.TeamDto;

import java.util.List;

public interface TeamService {
    /**
     * 创建团队
     */
    Integer buildTeam(TeamDto teamDto);

    /**
     * 获取所有团队信息
     */
    List<TeamDto> getAllTeam();

    /**
     * 根据id删除团队
     */
    Integer deleteTeam(Integer id);
}
