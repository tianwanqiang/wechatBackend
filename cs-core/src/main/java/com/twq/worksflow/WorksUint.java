package com.twq.worksflow;


import com.twq.exception.CustomException;

/**
 * Created by Leo on 2016/11/30.
 */
public interface WorksUint {
    void process(WorksDoData doData) throws CustomException;

    void setDesc(String desc);
}
