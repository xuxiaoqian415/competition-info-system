package com.info.competition.service.impl;


import com.info.competition.dao.SelectDao;
import com.info.competition.dao.UserDao;
import com.info.competition.model.dto.SelectDto;
import com.info.competition.model.Select;
import com.info.competition.model.dto.TeamDto;
import com.info.competition.model.dto.UserDto;
import com.info.competition.service.SelectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SelectServiceImpl implements SelectService {

    @Autowired
    SelectDao selectDao;
    @Autowired
    UserDao userDao;

    @Override
    public Integer insertSelect(SelectDto selectDto) {
        Select select = new Select();
        select.setSelectType(0);
        select.setFlag(0);
        select.setTeamId(selectDto.getTeamId());
        Integer i;
        select.setTeacherId(selectDto.getTeacher1Id());
        try {
            i = selectDao.insertSelect(select);
        } catch (Exception e) {
            i = -1;
        }
        select.setTeacherId(selectDto.getTeacher2Id());
        try {
            i = selectDao.insertSelect(select);
        } catch (Exception e) {
            i = -1;
        }
        select.setTeacherId(selectDto.getTeacher3Id());
        try {
            i = selectDao.insertSelect(select);
        } catch (Exception e) {
            i = -1;
        }
        return i;
    }

    @Override
    public List<TeamDto> noSelectTeams(Integer id) {
        List<TeamDto> list = selectDao.noSelectTeams(id);
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
    public Integer updateSelect(Integer id) {
        int count=0;
        int i=selectDao.updateSelectType(id);
        int j=selectDao.updateSelectFlag(selectDao.selectById(id).getTeamId());
        if(i!=1||j!=1) count=1;
        return count;
    }

    @Override
    public List<TeamDto> selectTeams(Integer id) {
        List<TeamDto> list = selectDao.selectTeams(id);
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
