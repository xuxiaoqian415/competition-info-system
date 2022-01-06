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

    @Override
    public Integer updateTeam(TeamDto teamDto) {
        Team team = new Team();
        team.setCpId(teamDto.getCpId());
        team.setTeamName(teamDto.getTeamName());
        team.setLeaderId(teamDto.getLeaderId());
        team.setTeamIntro(teamDto.getTeamIntro());
        StringBuffer memberBuffer = new StringBuffer();
        if(teamDto.getMember1Id() != null){
            memberBuffer.append(teamDto.getMember1Id() + ";");
        }
        if(teamDto.getMember2Id() != null){
            memberBuffer.append(teamDto.getMember2Id() + ";");
        }
        if(teamDto.getMember3Id() != null){
            memberBuffer.append(teamDto.getMember3Id() + ";");
        }
        if(teamDto.getMember4Id() != null){
            memberBuffer.append(teamDto.getMember4Id() + ";");
        }
        String member = memberBuffer.toString();
        teamDto.setMember(member);
        return teamDao.updateTeam(team);
    }

    @Override
    public TeamDto getTeamById(Integer id) {
        Team team = teamDao.selectTeamById(id);
        if(team != null){
            TeamDto teamDto = new TeamDto();
            teamDto.setId(team.getId());
            teamDto.setCpId(team.getCpId());
            teamDto.setTeamName(team.getTeamName());
            teamDto.setLeaderId(team.getLeaderId());
            teamDto.setTeamIntro(team.getTeamIntro());
            String[] members = team.getMember().split(";");
            teamDto.setMember1Id(Integer.parseInt(members[0]));
            teamDto.setMember2Id(Integer.parseInt(members[1]));
            teamDto.setMember3Id(Integer.parseInt(members[2]));
            teamDto.setMember4Id(Integer.parseInt(members[3]));
            return teamDto;
        }
        return null;
    }
}
