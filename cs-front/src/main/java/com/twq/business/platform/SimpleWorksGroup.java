package com.twq.business.platform;


import com.twq.exception.CustomException;
import com.twq.util.CSLog;
import com.twq.worksflow.WorksDoData;
import com.twq.worksflow.WorksGroup;
import com.twq.worksflow.WorksUint;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by twq on 2018/1/29.
 */
public class SimpleWorksGroup implements WorksGroup {
    private static Logger LOGGER = CSLog.get();
    protected List<WorksUint> workList ;
    protected String desc;
    public SimpleWorksGroup(){
        super();
    }
    public void setWorkList(ArrayList<WorksUint> workList) {
        this.workList = workList;
    }

    @Override
    public WorksDoData startingWorks(Map<String, String> gc) throws CustomException {
        WorksDoData doData = new WorksDoData(gc);
        CSLog.debugRPID_In(LOGGER,doData.getRpid(),"【{}】启动,单元个数【{}】",desc,workList.size());
        return running(doData);
    }

    protected WorksDoData running(WorksDoData doData) throws CustomException {
        WorksUint work ;
        for (int i=0; i < workList.size();i++) {
            work = workList.get(i);
            doData.setIndex(i+1);
            work.process(doData);
            if(doData.getOutData()!=null && i!=workList.size()-1){
                CSLog.debugRPID_End(LOGGER,doData.getRpid(),"【{}】中途完成",desc);
                break;
            }
        }
        CSLog.debugRPID_End(LOGGER,doData.getRpid(),"【{}】完成",desc);
        return doData ;
    }


    @Override
    public void setDesc(String desc) {
      this.desc = desc ;
    }

    @Override
    public void setWorkList(List<WorksUint> workList) {
       this.workList = workList ;
    }
}