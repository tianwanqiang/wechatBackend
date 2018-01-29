package com.twq.business.works.common;

import com.twq.business.platform.SimpleWorksUnit;
import com.twq.exception.CustomException;
import com.twq.util.Constants;
import com.twq.util.Nodes;
import com.twq.worksflow.WorksDoData;

public class OutUnit extends SimpleWorksUnit {

    @Override
    public void myProcess(WorksDoData data) throws CustomException {
        data.setOutParameter(Nodes.retCode, Constants.RET_CODE_SUCCESSFUL);
    }
}
