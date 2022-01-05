package com.info.competition.service.impl;


import com.info.competition.dao.SelectDao;
import com.info.competition.model.dto.SelectDto;
import com.info.competition.model.Select;
import com.info.competition.service.SelectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SelectServiceImpl implements SelectService {

    @Autowired
    SelectDao selectDao;

    @Override
    public Integer insertSelect(SelectDto selectDto) {
        Select select = new Select();
        select.setSelectType(0);
        select.setFlag(0);
        select.setTeamId(selectDto.getTeamId());
        Integer i;
        select.setTeacherId(selectDto.getTeacher1Id());
        selectDao.insertSelect(select);
//        try {
//            i = selectDao.insertSelect(select);
//        } catch (Exception e) {
//            i = -1;
//        }
        select.setTeacherId(selectDto.getTeacher2Id());
        selectDao.insertSelect(select);
//        try {
//            i = selectDao.insertSelect(select);
//        } catch (Exception e) {
//            i = -1;
//        }
        select.setTeacherId(selectDto.getTeacher3Id());
//        try {
//            i = selectDao.insertSelect(select);
//        } catch (Exception e) {
//            i = -1;
//        }
        return selectDao.insertSelect(select);
    }
}
