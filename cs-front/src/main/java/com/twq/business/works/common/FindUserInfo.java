package com.twq.business.works.common;

import com.twq.business.platform.SimpleWorksUnit;
import com.twq.dao.model.Users;
import com.twq.exception.CustomException;
import com.twq.util.Constants;
import com.twq.util.Nodes;
import com.twq.worksflow.WorksDoData;

public class FindUserInfo extends SimpleWorksUnit {
    @Override
    public void myProcess(WorksDoData doData) throws CustomException {
        String userId = "0";
        if (doData.hasParameter(Nodes.userId)) {
            userId = doData.getParameter(Nodes.userId, Integer.class);
        }
        Users user = userService.getUserById(userId);
        if (user == null) {
            throw new CustomException(Constants.RET_CODE_LOGIN_USER_NOT_EXIST);
        }
        doData.setParameter(Nodes.USER, user);
    }
}
