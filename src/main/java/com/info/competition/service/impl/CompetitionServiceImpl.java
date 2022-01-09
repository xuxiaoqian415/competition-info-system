package com.info.competition.service.impl;

import com.info.competition.dao.CompetitionDao;
import com.info.competition.dao.SelectDao;
import com.info.competition.dao.TeamDao;
import com.info.competition.model.Query;
import com.info.competition.model.StuComp;
import com.info.competition.model.dto.CompetitionDto;
import com.info.competition.model.Competition;
import com.info.competition.model.dto.SelectDto;
import com.info.competition.model.dto.TeamDto;
import com.info.competition.service.CompetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CompetitionServiceImpl implements CompetitionService {

    @Autowired
    CompetitionDao competitionDao;
    @Autowired
    TeamDao teamDao;
    @Autowired
    SelectDao selectDao;

    @Override
    public List<Competition> getCompetitionList() {
        Query query = new Query();
        return competitionDao.getCompetitionList(query);
    }

    @Override
    public List<Competition> searchCompetition(Query query) {
        return competitionDao.getCompetitionList(query);
    }

    @Override
    public int addCompetition(Competition competition) { return competitionDao.addCompetition(competition); }

    @Override
    public List<Competition> getCompetitionByApply() {
        return competitionDao.getCompetitionByApply(new Date());
    }

    @Override
    public CompetitionDto getCompetitionDetail(Integer id) {
        return competitionDao.getCompetitionDetail(id);
    }

    @Override
    public Integer deleteCompetition(Integer id) {
        Integer i;
        try {
            i = competitionDao.deleteCompetition(id);
            teamDao.deleteStuCompByCompId(id);
            Query query = new Query();
            query.setCpId(id);
            List<TeamDto> teamList = teamDao.selectTeamList(query);
            if (teamList != null) {
                for (TeamDto t : teamList) {
                    selectDao.deleteByTeamId(t.getId());
                }
                teamDao.deleteTeamByCpiD(id);
            }
        } catch (Exception e) {
            i = -1;
        }
        return i;
    }

    @Override
    public Integer updateCompetition(CompetitionDto competitionDto) {
        Competition competition = new Competition();
        competition.setId(competitionDto.getId());
        competition.setCpName(competitionDto.getCpName());
        competition.setCpContent(competitionDto.getCpContent());
        competition.setApplyStart(competitionDto.getApplyStart());
        competition.setApplyEnd(competitionDto.getApplyEnd());
        competition.setCpStart(competitionDto.getCpStart());
        competition.setApplyEnd(competitionDto.getApplyEnd());
        return competitionDao.updateCompetition(competition);
    }

    @Override
    public List<CompetitionDto> getApplyList(Integer id) {
        List<CompetitionDto> list = competitionDao.getApplyList(id);
        for (CompetitionDto c : list) {
            Integer teamId = c.getTeamId();
            List<SelectDto> teachers = selectDao.getTeacherByTeamId(teamId);
            if (teachers!=null && teachers.size()!=0) {
                c.setHaveChoose(1);
                c.setTeacherName(teachers.get(0).getTeacherName());
            }
            else {
                c.setHaveChoose(0);
            }
        }
        return list;
    }

    @Override
    public Integer ifHaveApply(StuComp stuComp) {
        if (competitionDao.ifHaveApply(stuComp) == null || competitionDao.ifHaveApply(stuComp).size() == 0)
            return 0;
        else
            return 1;
    }
}