package com.info.competition.service.impl;

import com.info.competition.dao.CompetitionDao;
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
        return competitionDao.getCompetitionList();
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
}
