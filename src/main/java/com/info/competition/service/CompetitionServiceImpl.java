package com.info.competition.service;

import com.info.competition.dao.CompetitionDao;
import com.info.competition.model.Competition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompetitionServiceImpl implements CompetitionService {

    @Autowired
    CompetitionDao competitionDao;

    @Override
    public List<Competition> getCompetitionList() {
        return competitionDao.getCompetitionList();
    }
}
