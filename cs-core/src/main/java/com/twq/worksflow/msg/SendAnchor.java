package com.twq.worksflow.msg;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SendAnchor {

	public Map<Integer,Date> map;//用户ID，最后一次推送时间
	public Date lastSendDate;
	
	public SendAnchor(Integer userId, Date cur) {
		Map<Integer, Date> tmap=new HashMap<Integer, Date>();
		tmap.put(userId, cur);
		lastSendDate=cur;
		this.setMap(tmap);;
	}

	public SendAnchor() {
		// TODO Auto-generated constructor stub
		
	}

	public boolean getSendAble(Integer userId,Date cur,long userInterval){
		if(userInterval==0){
			userInterval=60;
		}
		boolean flag=false;
		if(getMap()==null){
			setMap(new HashMap<Integer, Date>());;
			flag= true;
		}
		if(getMap().containsKey(userId)){
			Date lastDate = getMap().get(userId);
			long between=cur.getTime()-lastDate.getTime();
			System.out.println("用户"+userId+"上次呼叫时间："+lastDate.toLocaleString()+" 间隔时间："+between/1000+"s");
			if(between>1000*userInterval){//一个用户一分钟只能推送一次
				
				flag=true;
			}
		}else{
			flag=true;
		}
		if(flag){
			getMap().put(userId, cur);
		}
		
		return flag;
	}
	public boolean getSendAble(Integer userId,Date cur,long userInterval,long anchorInterval){
		if(anchorInterval==0){
			anchorInterval=10;
		}
		//用户的推送 间隔
		boolean flag =this.getSendAble(userId, cur, userInterval);
		if(flag){
			long between=cur.getTime()-lastSendDate.getTime();
			System.out.println("主播："+userId+"上次推送时间："+lastSendDate.toLocaleString()+" 间隔时间："+between/1000+"s");
			if(between>1000*anchorInterval){//2次推送间隔不鞥低于10秒
				 flag=true;
			}
		}
		if(flag){
			lastSendDate=cur;
		}
		
		return flag;
	}

	public Map<Integer, Date> getMap() {
		return map;
	}

	public void setMap(Map<Integer, Date> map) {
		this.map = map;
	}

	public Date getLastSendDate() {
		return lastSendDate;
	}

	public void setLastSendDate(Date lastSendDate) {
		this.lastSendDate = lastSendDate;
	}
	
	
	
}
