package com.twq.api.msg;

import java.io.Serializable;

/**
 * 接口返回消息
 */
public class ResMsg implements Serializable {

    //@JSONField(serialize=false)
    private ResHeader result;

    public ResMsg() {
        super();
        this.result = new ResHeader();
    }

    public ResMsg(String retCode) {
        super();
        this.result = new ResHeader(retCode);
    }

    public ResMsg(String retCode, String retMsg) {
        super();
        this.result = new ResHeader(retCode, retMsg);
    }

    public ResMsg(ResHeader result) {
        super();
        this.result = result;
    }

    public ResHeader getResult() {
        return result;
    }

    //public void setResult(ResHeader result) {
    //	this.result = result;
    //}

    public void putRetCode(String retCode) {
        this.result.setRetCode(retCode);
    }
}