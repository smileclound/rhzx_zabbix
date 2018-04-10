package com.rmyh.report.bean;

import java.io.Serializable;

public interface XNDataBeanInterface extends Serializable{
	
	String getItemName();
	void setItemName(String string);
	
	String getHostName();
	void setHostName(String string);
	
	String getGroupName();
	void setGroupName(String string);
	
	int getItemId();
	void setItemId(int num);
	
	int getHostId();
	void setHostId(int num);
	
	int getGroupId();
	void setGroupId(int num);
	
	String getClock();
	void setClock(String string);
	
}
