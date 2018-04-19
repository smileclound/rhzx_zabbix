package com.rmyh.report.bean;

import java.util.HashMap;
import java.util.Map;

public class AlertBean{
	
	
	public final static String Family = "basic_info";

	public static Map<String, String> map = new HashMap<String, String>();

	static {
		map.put("hostId", Family);
		map.put("hostName", Family);
		map.put("hostIp", Family);
//		map.put("groupId", Family);
//		map.put("groupName", Family);
		map.put("alertId", Family);
		map.put("alertLevel", Family);
		map.put("alertText", Family);
		map.put("status", Family);
		map.put("confirmstatus", Family);
		map.put("triggerName", Family);
		map.put("clock", Family);
	}
	
	
	public int alertId;
	public String clock;// 
	public int hostId;//  
	public String hostIp;
	public String hostName;// 
	public String alertLevel;//
	public String alertText;//
	public String status;//
	public String confirmstatus;//
	public String triggerName;


	public String getClock() {
		return clock;
	}

	public void setClock(String clock) {
		this.clock = clock;
	}
	public int  getHostId() {
		return hostId;
	}

	public void setHostId(int hostId) {
		this.hostId = hostId;
	}
	
	public int  getAlertId() {
		return alertId;
	}

	public void setAlertId(int id) {
		this.alertId = id;
	}
	
	public String getHostIp() {
		return hostIp;
	}

	public void setHostIp(String hostIp) {
		this.hostIp = hostIp;
	}
	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	
	public String getAlertLevel() {
		return alertLevel;
	}
	

	public void setAlertLevel(int alertLevel) {
		switch (alertLevel) {
			case 0: 
				this.alertLevel = "0";
				break;
			case 1: 
				this.alertLevel = "1";
				break;
			case 2: 
				this.alertLevel = "2";
				break;
			default: 
				this.alertLevel = "...";
				break;
		}
	}
//	public int getAlertTimes() {
//		return alertTimes;
//	}
//
//	public void setAlertTimes(int alertTimes) {
//		this.alertTimes = alertTimes;
//	}
	
	public String getAlertText() {
		return alertText;
	}

	public void setAlertText(String alertText) {
		this.alertText = alertText;
	}
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	public String getConfirmstatus() {
		return confirmstatus;
	}

	public void setConfirmstatus(int confirmstatus) {
		switch (confirmstatus) {
		case 0: 
			this.confirmstatus = "0";
			break;
		case 1: 
			this.confirmstatus = "1";
			break;
		case 2: 
			this.confirmstatus = "2";
			break;
		default: 
			this.confirmstatus = "...";
			break;
	}
	}
	
	public String getTriggerName() {
		return triggerName;
	}

	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}
	
	public String getKey() {

		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append("A");
		stringBuilder.append(alertId);
		stringBuilder.append("T");
		stringBuilder.append(clock);


		return stringBuilder.toString();
	}

	public String toString() {

		StringBuilder stringBuilder = new StringBuilder();
		
		stringBuilder.append("{Clock:").append(clock).append(",");
		stringBuilder.append("hostId:").append(hostId).append(",");
		stringBuilder.append("hostName:").append(hostName).append(",");
		stringBuilder.append("hostIp:").append(hostIp).append(",");

		stringBuilder.append("triggerName:").append(triggerName).append(",");
		stringBuilder.append("status:").append(status).append(",");
		stringBuilder.append("confirmstatus:").append(confirmstatus).append(",");
		stringBuilder.append("alertText:").append(alertText).append("}");


		return stringBuilder.toString();
	}
}
