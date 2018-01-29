package com.twq.worksflow.msg;


import com.twq.util.Constants;
import com.twq.util.PropertiesUtils;

import java.io.Serializable;

public class ResHeader implements Serializable {
	private String retCode;
	private String retMsg;
	public ResHeader() {
		super();
		this.retCode = Constants.RET_CODE_SUCCESSFUL;
		this.retMsg  = PropertiesUtils.getRetMapperInstans().getProperty(this.retCode);
	}

	public ResHeader(String retCode) {
		this.retCode = retCode;
		this.retMsg  = PropertiesUtils.getRetMapperInstans().getProperty(this.retCode);
	}
	public ResHeader(String retCode,String retMsg) {
		this.retCode = retCode;
		this.retMsg = retMsg;
	}



	public String getRetCode() {
		return retCode;
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

}