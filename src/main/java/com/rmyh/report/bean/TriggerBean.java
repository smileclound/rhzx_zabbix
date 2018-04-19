package com.rmyh.report.bean;

import java.util.HashMap;
import java.util.Map;

public class TriggerBean implements Comparable<TriggerBean> {

	public final static String Family = "basic_info";

	public static Map<String, String> map = new HashMap<String, String>();

	static {
		map.put("hostId", Family);
		map.put("hostName", Family);
		map.put("groupId", Family);
		map.put("groupName", Family);
		map.put("triggerId", Family);
		map.put("triggerValue", Family);
		map.put("applicationId", Family);
		map.put("applicationName", Family);
		map.put("triggerDescri", Family);
		map.put("hostIp", Family);
		map.put("triggerText", Family);
		map.put("status", Family);
		map.put("priority", Family);
	}
	
//	public String clock = "";//
	public int hostId = -1;//
	public int groupId = -1;
	public int applicationId = -1;
	public String hostIp = "";
	public String hostName = "";//
	public String groupName = "";
	public String applicationName = "";
	public String triggerText = "";//
	public int triggerId = -1;
	public int triggerValue = -1;
	public String triggerDescri = "";
	public String status = "";
	public String priority = "";

//	public String getClock() {
//		return clock;
//	}
//
//	public void setClock(String clock) {
//		this.clock = clock;
//	}

	public int getHostId() {
		return hostId;
	}

	public void setHostId(int hostId) {
		this.hostId = hostId;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public int getApplicationId() {
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

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getApplicationName() {
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
	
	
	public String getStatus() {
		return status;
	}

	public void setStatus(int i) {
		switch(i) {
			case 0:
				this.status = "(default) enabled";
				break;
			case 1:
				this.status = "diasbled";
				break;
			default:
				this.status = "";
				break;
		}
	}
	
	public String getPriority() {
		return priority;
	}

	public void setPriority(int i) {
//		Possible values are: 
//			0 - (default) not classified; 
//			1 - information; 
//			2 - warning; 
//			3 - average; 
//			4 - high; 
//			5 - disaster.
		switch(i) {
			case 0:
				this.priority = "(default) not classified";
				break;
			case 1:
				this.priority = "information";
				break;
			case 2:
				this.priority = "warning";
				break;
			case 3:
				this.priority = "average";
				break;
			case 4:
				this.priority = "high";
				break;
			case 5:
				this.priority = "disaster";
				break;
			default:
				this.priority = "";
				break;
		}
	}


	public String getKey() {

		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append("TR");
		stringBuilder.append(triggerId);


		return stringBuilder.toString();
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

	public int compareTo(TriggerBean bean) {
		if (this.getGroupId() != bean.getGroupId()) {
			return this.getGroupId() - bean.getGroupId();
		} else if (this.getHostId() != bean.getHostId()) {
			return this.getHostId() - bean.getHostId();
		} else if (this.getApplicationId() != bean.getApplicationId()) {
			return this.getApplicationId() - bean.getApplicationId();
		} else {
			return this.getTriggerId() - bean.getTriggerId();
		}
	}
}
