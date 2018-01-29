package com.twq.business.platform;


import com.twq.customxml.CondtionGroup;
import com.twq.exception.CustomException;
import com.twq.util.CSLog;
import com.twq.worksflow.Condtion;
import com.twq.worksflow.WorksDoData;
import org.slf4j.Logger;

/**
 * Created by admin on 2016/12/7.
 */
public class SimpleCondtionGroup extends SimpleWorksGroup implements CondtionGroup {
    private static Logger LOGGER = CSLog.get();
    private Condtion condtion;

    public void setCond(String cond) {
        this.condtion = new Condtion();
        int index = cond.indexOf("=");
        if (index == -1) {
            index = cond.indexOf(">");
            if (index == -1) {
                index = cond.indexOf("<");
                if (index == -1) {
                    index = cond.indexOf("$");
                    if (index > -1) {
                        this.condtion.setCond("EQ");
                    } else return;
                } else {
                    this.condtion.setCond("<");
                }
            } else {
                this.condtion.setCond(">");
            }
        } else {
            this.condtion.setCond("=");
        }
        this.condtion.setNodeKey(cond.substring(0, index).trim());
        this.condtion.setCondValue(cond.substring(index + 1).trim());

    }


    public void process(WorksDoData doData) throws CustomException {
        //CSLog.debugRPID_In(LOGGER,doData.getRpid(),"《条件子流程》名称：【{}】 单元个数：【{}】",desc,workList.size());

        String nodeValue = doData.getParameter(this.condtion.getNodeKey(), String.class);
        this.condtion.setNodeValue(nodeValue);
        if (!condtion.cond()) {
            //CSLog.debugRPID(LOGGER,doData.getRpid(),"[流程]启动      >>>>>>>>>>>>>>>>>>>>条件名称：《{}》",desc);
            CSLog.debugRPID_In(LOGGER, doData.getRpid(), "/////////////【{}】({} {} {}) 不成立,跳过__________!!!", desc, this.condtion.getNodeValue(), this.condtion.getCond(), this.condtion.getCondValue());
            return;
        }
        CSLog.debugRPID_In(LOGGER, doData.getRpid(), "/////////////【{}】({} {} {}) 成立,进入__________>>>", desc, this.condtion.getNodeValue(), this.condtion.getCond(), this.condtion.getCondValue());
        running(doData);
    }
}