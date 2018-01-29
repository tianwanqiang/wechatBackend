package com.twq.worksflow;

import com.twq.util.Constants;
import com.twq.util.PropertiesUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 工作流返回数据结构
 */
public class WorksRetData {

    private String retCode;

    public String getRetCode() {
        return retCode;
    }

    private String retMsg;
    private Map<String, Object> retData;

    public WorksRetData() {
        this.retCode = Constants.RET_CODE_SUCCESSFUL;
    }

    public void setRetCode(String retCode,String ... errMsg) {
        this.retCode = retCode;
        String msgTemplet  = PropertiesUtils.getRetMapperInstans().getProperty(retCode) ;
        for (String err: errMsg) {
            msgTemplet = msgTemplet.replaceFirst("\\$",err);
        }
        this.retMsg =  msgTemplet ;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public Map<String, Object> getRetData() {
        return retData;
    }
    public void setRetData(Map<String, Object> retData) {
        this.retData = retData;
    }
    public <T> T getRetParameter(String key) {
        return (T)retData.get(key);
    }
    public <T> void setRetParameter(String key,T value) {
        if(retData == null){
            retData = new HashMap<String,Object>();
        }
        retData.put(key,value);
    }
}
