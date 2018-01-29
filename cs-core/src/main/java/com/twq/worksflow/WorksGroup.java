package com.twq.worksflow;


import com.twq.exception.CustomException;

import java.util.List;
import java.util.Map;

/**
 * Created by Leo on 2016/11/30.
 */
public interface WorksGroup {
    WorksDoData startingWorks(Map<String, String> gc) throws CustomException;

    void setDesc(String desc);

    void setWorkList(List<WorksUint> workList);
}
