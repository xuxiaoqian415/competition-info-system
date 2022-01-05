package com.info.competition.service.impl;


import com.info.competition.dao.SelectDao;
import com.info.competition.dto.SelectDto;
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
        select.setTeamId(selectDto.getTeamId());
        select.setTeacherId(selectDto.getTeacherId());
        select.setSelectType(0);
        select.setFlag(1);
        return selectDao.insertSelect(select);
    }
}
