package com.info.competition.service.impl;

import com.info.competition.dao.CompetitionDao;
import com.info.competition.model.Query;
import com.info.competition.model.dto.CompetitionDto;
import com.info.competition.model.Competition;
import com.info.competition.service.CompetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CompetitionServiceImpl implements CompetitionService {

    @Autowired
    CompetitionDao competitionDao;

    @Override
    public List<Competition> getCompetitionList() {
        Query query = new Query();
        return competitionDao.getCompetitionList(query);
    }

    @Override
    public int addCompetition(Competition competition) { return competitionDao.addCompetition(competition); }

    @Override
    public List<Competition> getCompetitionByApply() {
        return competitionDao.getCompetitionByApply(new Date());
    }

    @Override
    public Competition getCompetitionDetail(Integer id) {
        return competitionDao.getCompetitionDetail(id);
    }

    @Override
    public Integer deleteCompetition(Integer id) {
        return competitionDao.deleteCompetition(id);
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
}
