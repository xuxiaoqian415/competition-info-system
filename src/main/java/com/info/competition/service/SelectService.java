package com.info.competition.service;

import com.info.competition.model.dto.SelectDto;

public interface SelectService {

    /**
     * 增加一条Select记录
     */
    Integer insertSelect(SelectDto slectDto);

}
