package com.twq.business.works.usersinfo;

import com.twq.business.platform.SimpleWorksUnit;
import com.twq.dao.model.Users;
import com.twq.exception.CustomException;
import com.twq.util.Nodes;
import com.twq.worksflow.WorksDoData;

import java.util.HashMap;
import java.util.Map;

public class OutUnit extends SimpleWorksUnit {

    @Override
    public void myProcess(WorksDoData data) throws CustomException {
        Map<String, String> body = new HashMap<String, String>();
        Users user = null;
        if (data.hasParameter(Nodes.USER)) {
            user = data.getParameter(Nodes.USER, Users.class);
            body.put(Nodes.userId, user.getId());
            body.put(Nodes.token, user.getSessionId());
            body.put(Nodes.accountType, user.getAccountType().toString());
            data.setOutParameter(Nodes.BODY, body);
        }
    }
}
