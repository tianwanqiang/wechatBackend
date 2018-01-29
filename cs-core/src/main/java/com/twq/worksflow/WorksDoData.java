package com.twq.worksflow;

import com.chushou.platform.exception.CustomException;
import com.chushou.platform.utils.CSLog;
import com.chushou.platform.utils.Constants;
import com.chushou.platform.utils.Node;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Leo on 2016/11/30.
 */
public class WorksDoData  {
    protected static Logger LOGGER = CSLog.get();
    private String  rpid ;
    private int  index ;
    private Map<String,String> inData ;
    private Map<String,Object> outData ;
    private Map<String,Object> globalData ;

    public WorksDoData(Map<String,String> inData) {
        this.rpid = inData.get(Node.rpid);
        this.inData = inData;
        setGlobalData(inData) ;
    }
    public <T> T getParameter(String key,Class cl) throws CustomException {
        if(!hasParameter(key)){
            CSLog.debugRPID_M(LOGGER,rpid,"&&&获取不到【{}】!!",key);
            return null ;
        }else if(cl.isInstance(globalData.get(key))){
            return (T)globalData.get(key);
        }else if(cl.isInstance(new String())  && (Integer.class.isInstance(globalData.get(key)) || Long.class.isInstance(globalData.get(key)) || Float.class.isInstance(globalData.get(key)))){
            CSLog.debugRPID_M(LOGGER,rpid,"&&&字段【{}】强制转换【{}】==>【{}】",key,globalData.get(key).getClass().getSimpleName(),cl.getSimpleName());
            return (T)String.valueOf(globalData.get(key));
        }else if(cl.isInstance(new Integer(0)) && String.class.isInstance(globalData.get(key)) && !((String)globalData.get(key)).contains(".")&& !((String)globalData.get(key)).isEmpty()){
            CSLog.debugRPID_M(LOGGER,rpid,"&&&字段【{}】强制转换【{}】==>【{}】",key,globalData.get(key).getClass().getSimpleName(),cl.getSimpleName());
            return (T)Integer.valueOf((String) globalData.get(key));
        }else {
            CSLog.debugRPID_M(LOGGER,rpid,"&&&字段【{}】类型有误!!!所需类型【{}】<==>实际类型【{}】",key,cl.getSimpleName(),globalData.get(key).getClass().getSimpleName());
            throw new CustomException(Constants.RET_CODE_FIELD_CONVERT);
        }
    }
/*    public String getStringParameter(String key) {
        if(!hasParameter(key)){
            CSLog.debugRPID_M(LOGGER,rpid,"&&&&&&&&&&获取不到【{}】&&&&&&&&&&",key);
            return null ;
        }
        return String.valueOf(globalData.get(key));
    }
    public Integer getIntParameter(String key) {
        if(!hasParameter(key)){
            System.out.println("&&&&&&&&&&【"+key+"】获取不到&&&&&&&&&&");
            return null ;
        }else if(String.valueOf(globalData.get(key)).isEmpty()){
            return null ;
        }else if(String.valueOf(globalData.get(key)).contains(".")){
            return null ;
        }

        if(globalData.get(key) instanceof Integer)
            return (Integer) globalData.get(key);

        return Integer.valueOf((String) globalData.get(key));
    }*/



    public <T> void setParameter(String key,T value) {
        globalData.put(key,value);
    }
    public boolean hasParameter(String key) {
        return globalData.get(key)==null?false:true;
    }
    public Map<String, String> getInData() {
        return inData;
    }

    public void setInData(Map<String, String> inData) {
        this.inData = inData;
    }

    public Map<String, Object> getOutData() {
        return outData;
    }

    public <T> T getOutParameter(String key) {
        return (T)outData.get(key);
    }
    public <T> void setOutParameter(String key,T value) {
        if(outData == null){
            outData = new HashMap<String,Object>();
        }
        outData.put(key,value);
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getRpid() {
        return rpid;
    }

    public void setRpid(String rpid) {
        this.rpid = rpid;
    }

    public void setGlobalData(Map<String, String> data) {
        if(globalData == null){
            globalData = new HashMap<String,Object>();
        }
        for (Map.Entry<String, String> entry: data.entrySet()) {
            globalData.put(entry.getKey(),entry.getValue());
        }
    }
}
