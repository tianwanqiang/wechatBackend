package com.twq.securiy;


import com.twq.worksflow.msg.ResHeader;

import java.util.Map;

/**
 * Created by twq on 2018/1/29.
 */
public interface Check {
    ResHeader check(String rpid, Map<String, String> reqMsg);
}
