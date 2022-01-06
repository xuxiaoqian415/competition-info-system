package com.info.competition.service;

import com.info.competition.model.dto.SelectDto;
import com.info.competition.model.dto.TeamDto;

import java.util.List;

public interface SelectService {

    /**
     * 增加一条Select记录
     */
    Integer insertSelect(SelectDto slectDto);

    /**
     * 根据老师id查找申请指导记录
     */
    List<TeamDto> noSelectTeams(Integer id);

    /**
     * 根据id更新选择记录
     */
    Integer updateSelect(Integer id);

    /**
     * 获取已反选团队
     */
    List<TeamDto> selectTeams(Integer id);


}
