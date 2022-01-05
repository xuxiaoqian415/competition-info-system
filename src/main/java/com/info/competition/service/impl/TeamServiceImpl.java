package com.info.competition.service.impl;

import com.info.competition.dao.TeamDao;
import com.info.competition.dto.TeamDto;
import com.info.competition.model.Team;
import com.info.competition.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamServiceImpl implements TeamService {
    @Autowired
    TeamDao teamDao;

    @Override
    public Integer buildTeam(TeamDto teamDto) {
        Team team = new Team();
        team.setCpId(teamDto.getCpId());
        team.setTeamName(teamDto.getTeamName());
        team.setLeaderId(teamDto.getLeaderId());
        team.setTeamIntro(teamDto.getTeamIntro());
        team.setMember(teamDto.getMember());
        return teamDao.insertTeam(team);
    }
}
