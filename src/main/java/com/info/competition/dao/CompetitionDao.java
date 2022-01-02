package com.info.competition.dao;

import com.info.competition.model.Competition;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CompetitionDao {

    List<Competition> getCompetitionList();

}
