package com.rmyh.report.bean;


public class TriggerBean{
	
	public String clock;// 
	public int hostId;// 
	public int groupId;
	public int applicationId;
	public String hostIp;
	public String hostName;// 
	public String groupName;
	public String applicationName;
	public String triggerText;//
	public int triggerId;
	public int triggerValue;
	public String triggerDescri;


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
	
	public int  getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	
	public int  getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(int applicationId) {
		this.applicationId = applicationId;
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
	
	public String  getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	public String  getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}
	
	public String getTriggerText() {
		return triggerText;
	}

	public void setTriggerText(String triggerText) {
		this.triggerText = triggerText;
	}
	
	public String getTriggerDescri() {
		return triggerDescri;
	}

	public void setTriggerDescri(String triggerDescri) {
		this.triggerDescri = triggerDescri;
	}
	
	public int getTriggerId() {
		return triggerId;
	}
	public void setTriggerId(int triggerId) {
		this.triggerId = triggerId;
	}
	
	public int getTriggerValue() {
		return triggerValue;
	}
	public void setTriggerValue(int triggerValue) {
		this.triggerValue = triggerValue;
	}
	
	

	public String toString() {

		StringBuilder stringBuilder = new StringBuilder();
		
		stringBuilder.append("{triggerId:").append(triggerId).append(",");
		stringBuilder.append("triggerText:").append(triggerText).append(",");
		stringBuilder.append("triggerDescri:").append(triggerDescri).append(",");
		stringBuilder.append("hostId:").append(hostId).append(",");
		stringBuilder.append("groupId:").append(groupId).append(",");
		stringBuilder.append("applicationId:").append(applicationId).append(",");
		stringBuilder.append("hostIp:").append(hostIp).append(",");
		stringBuilder.append("hostName:").append(hostName).append(",");
		stringBuilder.append("groupName:").append(groupName).append(",");
		stringBuilder.append("applicationName:").append(applicationName).append(",");
		stringBuilder.append("triggerValue:").append(triggerValue).append("}");


		return stringBuilder.toString();
	}
}
