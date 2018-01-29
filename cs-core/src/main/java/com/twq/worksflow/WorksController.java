package com.twq.worksflow;

import java.util.Map;

public interface WorksController {
    WorksRetData doFlow(Map<String,String> reqMsg) throws Exception;
}