package com.info.competition.service.impl;

import com.info.competition.dao.CompetitionDao;
import com.info.competition.dao.SelectDao;
import com.info.competition.dao.TeamDao;
import com.info.competition.dao.UserDao;
import com.info.competition.model.Query;
import com.info.competition.model.StuComp;
import com.info.competition.model.dto.TeamDto;
import com.info.competition.model.Team;
import com.info.competition.model.dto.UserDto;
import com.info.competition.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {
    @Autowired
    TeamDao teamDao;
    @Autowired
    UserDao userDao;
    @Autowired
    CompetitionDao competitionDao;
    @Autowired
    SelectDao selectDao;

    @Override
    public Integer buildTeam(TeamDto teamDto) {
        Team team = new Team();
        team.setCpId(teamDto.getCpId());
        team.setTeamName(teamDto.getTeamName());
        team.setLeaderId(teamDto.getLeaderId());
        team.setTeamIntro(teamDto.getTeamIntro());
        List<Integer> memberList = teamDto.getMemberList();
        StringBuffer memberBuffer = new StringBuffer();
        for (Integer mId : memberList) {
            memberBuffer.append(mId + ";");
        }
        String member = memberBuffer.toString();
        team.setMember(member);
        if(1 == teamDao.insertTeam(team)){
            Integer teamId = team.getId();
            StuComp stuComp = new StuComp();
            stuComp.setCompetitionId(teamDto.getCpId());
            stuComp.setTeamId(teamId);
            for (Integer mId : memberList) {
                stuComp.setStudentId(mId);
                competitionDao.insertStuComp(stuComp);
            }
            stuComp.setStudentId(teamDto.getLeaderId());
            competitionDao.insertStuComp(stuComp);
            return teamId;
        }
        return -1;
    }

    @Override
    public List<TeamDto> getAllTeam() {
        Query query = new Query();
        List<TeamDto> list = teamDao.selectTeamList(query);
        StringBuffer memberNames = new StringBuffer();
        for (TeamDto t : list) {
            String[] members = t.getMember().split(";");
            for (String m : members) {
                UserDto u = userDao.selectUserById(Integer.parseInt(m));
                if (u != null) {
                    memberNames.append(u.getName() + ",");
                }
            }
            t.setMemberNames(memberNames.toString());
        }
        return list;
    }

    @Override
    public List<TeamDto> searchTeam(Query query) {
        List<TeamDto> list = teamDao.selectTeamList(query);
        StringBuffer memberNames = new StringBuffer();
        for (TeamDto t : list) {
            String[] members = t.getMember().split(";");
            for (String m : members) {
                UserDto u = userDao.selectUserById(Integer.parseInt(m));
                if (u != null) {
                    memberNames.append(u.getName() + ",");
                }
            }
            t.setMemberNames(memberNames.toString());
        }
        return list;
    }

    @Override
    public Integer deleteTeam(Integer id) {
        Integer i;
        try {
            i = teamDao.deleteTeam(id);
            teamDao.deleteStuCompByTeamId(id);
            selectDao.deleteByTeamId(id);
        } catch (Exception e) {
            i = -1;
        }
        return i;
    }

    @Override
    public Integer updateTeam(TeamDto teamDto) {
        Team team = new Team();
        team.setId(teamDto.getId());
        team.setTeamName(teamDto.getTeamName());
        team.setTeamIntro(teamDto.getTeamIntro());
        ArrayList<Integer> memberList = new ArrayList<>();
        StringBuffer memberBuffer = new StringBuffer();
        if(teamDto.getMember1Id() != null){
            memberList.add(teamDto.getMember1Id());
            memberBuffer.append(teamDto.getMember1Id() + ";");
        }
        if(teamDto.getMember2Id() != null){
            memberList.add(teamDto.getMember2Id());
            memberBuffer.append(teamDto.getMember2Id() + ";");
        }
        if(teamDto.getMember3Id() != null){
            memberList.add(teamDto.getMember3Id());
            memberBuffer.append(teamDto.getMember3Id() + ";");
        }
        if(teamDto.getMember4Id() != null){
            memberList.add(teamDto.getMember4Id());
            memberBuffer.append(teamDto.getMember4Id() + ";");
        }
        if (memberList.size() != 0) {
            team.setMember(memberBuffer.toString());
            teamDao.deleteStuCompByTeamId(teamDto.getId());
            StuComp stuComp = new StuComp();
            stuComp.setCompetitionId(teamDto.getCpId());
            stuComp.setTeamId(teamDto.getId());
            for (Integer mId : memberList) {
                stuComp.setStudentId(mId);
                competitionDao.insertStuComp(stuComp);
            }
            stuComp.setStudentId(teamDto.getLeaderId());
            competitionDao.insertStuComp(stuComp);
        }
        return teamDao.updateTeam(team);
    }

    @Override
    public TeamDto getTeamById(Integer id) {
        TeamDto team = teamDao.selectTeamById(id);
        if(team.getMember() != null){
            String[] members = team.getMember().split(";");
            ArrayList<Integer> memberList = new ArrayList<>();
            for (String m : members) {
                memberList.add(Integer.parseInt(m));
            }
            team.setMemberList(memberList);
        }
        return team;
    }

    @Override
    public List<TeamDto> getOwnTeam(Integer id) {
        Query query= new Query();
        query.setLeaderId(id);
        List<TeamDto> list = teamDao.selectTeamList(query);
        StringBuffer memberNames = new StringBuffer();
        for (TeamDto t : list) {
            String[] members = t.getMember().split(";");
            for (String m : members) {
                UserDto u = userDao.selectUserById(Integer.parseInt(m));
                if (u != null) {
                    memberNames.append(u.getName() + ",");
                }
            }
            t.setMemberNames(memberNames.toString());
        }
        return list;
    }
}
