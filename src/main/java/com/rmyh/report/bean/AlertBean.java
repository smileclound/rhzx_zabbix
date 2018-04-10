package com.rmyh.report.bean;


public class AlertBean{
	
	public String clock;// 
	public int hostId;//  
	public String hostIp;
	public String hostName;// 
	public String alertLevel;//
	public int alertTimes;//
	public String alertText;//
	public String status;//
	public String confirmstatus;//


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

	public void setAlertLevel(String alertLevel) {
		this.alertLevel = alertLevel;
	}
	public int getAlertTimes() {
		return alertTimes;
	}

	public void setAlertTimes(int alertTimes) {
		this.alertTimes = alertTimes;
	}
	
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

	public void setConfirmstatus(String confirmstatus) {
		this.confirmstatus = confirmstatus;
	}

	public String toString() {

		StringBuilder stringBuilder = new StringBuilder();
		
		stringBuilder.append("Clock:").append(clock);
		stringBuilder.append("alertText:").append(alertText);


		return stringBuilder.toString();
	}
}
