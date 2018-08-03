package com.twq.dao.mapper;

import java.util.List;
import java.util.Map;

public interface CustomMapper {

    /**
     * 早起之星
     *
     * @return
     */
    public List<Map<String, String>> getMostUser();

    public Map getLuckUser();
}
