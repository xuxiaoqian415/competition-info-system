package com.info.competition.service.impl;

import com.info.competition.dao.TeamDao;
import com.info.competition.model.Query;
import com.info.competition.model.dto.TeamDto;
import com.info.competition.model.Team;
import com.info.competition.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        if(1 == teamDao.insertTeam(team)){
//            return teamDao.selectTeamId(team.getCpId(),team.getLeaderId());
            return team.getId();
        }
        return -1;
    }

    @Override
    public List<TeamDto> getAllTeam() {
        Query query = new Query();
        return teamDao.selectAll(query);
    }

    @Override
    public Integer deleteTeam(Integer id) {
        Integer i;
        try {
            i = teamDao.deleteTeam(id);
        } catch (Exception e) {
            i = -1;
        }
        return i;
    }
}
