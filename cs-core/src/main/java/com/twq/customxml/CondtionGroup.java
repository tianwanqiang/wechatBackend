package com.twq.customxml;


import com.twq.worksflow.WorksUint;

import java.util.List;

/**
 * Created by admin on 2016/12/7.
 */
public interface CondtionGroup extends WorksUint {
    void setCond(String cond)  ;
    void setWorkList(List<WorksUint> workList);
}