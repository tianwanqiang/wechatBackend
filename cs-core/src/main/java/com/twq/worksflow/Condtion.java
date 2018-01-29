package com.twq.worksflow;

import com.chushou.platform.utils.CSLog;
import org.slf4j.Logger;

/**
 * Created by admin on 2016/12/7.
 */
public class Condtion {
    private String nodeKey = "";
    private String nodeValue = "";
    private String cond="";
    private String condValue="";
    private static Logger LOGGER = CSLog.get();
    public String getNodeKey() {
        return nodeKey;
    }

    public void setNodeKey(String nodeKey) {
        this.nodeKey = nodeKey;
    }

    public String getCond() {
        return cond;
    }

    public void setCond(String cond) {
        this.cond = cond;
    }

    public String getCondValue() {
        return condValue;
    }

    public void setCondValue(String condValue) {
        this.condValue = condValue;
    }

    public String getNodeValue() {
        return nodeValue;
    }

    public void setNodeValue(String nodeValue) {
        this.nodeValue = nodeValue;
    }
    public boolean cond(){

        if("EQ".equalsIgnoreCase(cond)){
            if("null".equals(condValue)){
                if(nodeValue == null || nodeValue.isEmpty())
                    return true ;
                else
                    return false;
            }else if(nodeValue == null){
                return false;
            }else
                return (String.valueOf(nodeValue).equals(String.valueOf(condValue))) ;
        }else if(">".equals(cond)){
            if(nodeValue == null){
                return false;
            }
            if(Integer.parseInt(nodeValue)>Integer.parseInt(condValue)){
                return true ;
            }else {
                return false;
            }
        }else if("<".equals(cond)){
            if(nodeValue == null){
                return false;
            }
            if(Integer.parseInt(nodeValue)<Integer.parseInt(condValue)){
                return true ;
            }else {
                return false;
            }
        }else{
            if(nodeValue == null){
                return false;
            }
            if(Integer.parseInt(nodeValue)==Integer.parseInt(condValue)){
                return true ;
            }else {
                return false;
            }
        }
    }
}