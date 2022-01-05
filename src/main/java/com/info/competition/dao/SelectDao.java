package com.info.competition.dao;

import com.info.competition.model.Select;

public interface SelectDao {
    /**
     * 添加一条互选记录
     */
    Integer insertSelect(Select select);
}
