package com.twq.api.msg;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class ResArray extends ResMsg implements Serializable {    
	 
	private List<Object> items ; 
	public List<Object> getItems() {
		return items;
	}
	public void setItems(List<Object> items) {
		this.items = items;
	} 
	public void addItem(Object obj) {
		if(items==null)
			items = new ArrayList<Object>();
		items.add(obj);
	}

	private Object body ;
	public Object getBody() {
		return body;
	}
	public void setBody(Object body) {
		this.body = body;
	}

}