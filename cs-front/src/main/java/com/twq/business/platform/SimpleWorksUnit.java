package com.twq.business.platform;

import com.twq.exception.CustomException;
import com.twq.service.ConfigService;
import com.twq.service.UsersService;
import com.twq.util.CSLog;
import com.twq.worksflow.WorksDoData;
import com.twq.worksflow.WorksUint;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by twq on 2018/1/29.
 */

@Service
public abstract class SimpleWorksUnit implements WorksUint {
    protected static Logger LOGGER = CSLog.get();
    @Autowired
    public UsersService userService;
    @Autowired
    public ConfigService managerService;



    private String desc;

    public String getDesc() {
        return desc;
    }

    @Override
    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void process(WorksDoData doData) throws CustomException {
        //CSLog.debugRPID_In(LOGGER,doData.getRpid(),"@{}【{}】启动.",doData.getIndex(),desc);
        myProcess(doData);
        CSLog.debugRPID_End(LOGGER, doData.getRpid(), "@{}【{}】完成.", doData.getIndex(), desc);
    }

    public abstract void myProcess(WorksDoData data) throws CustomException;
}
