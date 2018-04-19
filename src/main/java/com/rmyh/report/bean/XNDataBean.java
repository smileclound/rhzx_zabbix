package com.rmyh.report.bean;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.rmyh.report.bean.XNDataBeanInterface;
import com.rmyh.report.service.zabbix.GetItems;

public class XNDataBean implements XNDataBeanInterface {

	// public final static String Family1 = "basic_info";
	public final static String Family2 = "value_info";

	public static Map<String, String> map = new HashMap<String, String>();

	static {
		// map.put("groupId", Family1);
		// map.put("hostId", Family1);
		// map.put("itemId", Family1);
		// map.put("applicationId", Family1);
		// map.put("groupName", Family1);
		// map.put("hostName", Family1);
		// map.put("itemName", Family1);
		// map.put("monitorobj", Family1);
		// map.put("applicationName", Family1);
		//

		// map.put("clock", Family2);
		map.put("value", Family2);
		// map.put("value_max", Family2);
		// map.put("value_min", Family2);
		// map.put("value_avg", Family2);

	}

	public int groupId = -1;//
	public int hostId = -1;//
	public int itemId = -1;//
	public int applicationId = -1; //
	public String groupName = "";//
	public String hostName = "";//
	public String hostIp = "";//
	public String itemName = "";//
	public String clock = "";//
	public String value = "";// different type.. how to do!
	public String value_max = "";
	public String value_min = "";
	public String value_avg = "";
	public String applicationName = "";
//	public String monitorobj = "";

	public static Map<String, String> getMap() {
		return map;
	}

	public static void setMap(Map<String, String> map) {
		XNDataBean.map = map;
	}

	// public static String getFamily1() {
	// return Family1;
	// }

	public static String getFamily2() {
		return Family2;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
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

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int id) {
		this.itemId = id;
	}

	public int getHostId() {
		return hostId;
	}

	public void setHostId(int id) {
		this.hostId = id;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int id) {
		this.groupId = id;
	}

	public int getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(int id) {
		this.applicationId = id;
	}

	public String getClock() {
		return clock;
	}

	public void setClock(String clock) {
		this.clock = clock;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue_max() {
		return value_max;
	}

	public void setValue_max(String value) {
		this.value_max = value;
	}

	public String getValue_min() {
		return value_min;
	}

	public void setValue_min(String value) {
		this.value_min = value;
	}

	public String getValue_avg() {
		return value_avg;
	}

	public void setValue_avg(String value) {
		this.value_avg = value;
	}

	public String getHostIp() {
		return hostIp;
	}

	public void setHostIp(String ip) {
		this.hostIp = ip;
	}
	
//	public String getMonitorobj() {
//		return monitorobj;
//	}
//
//	public void setMonitorobj(String str) {
//		this.monitorobj = str;
//	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String str) {
		this.applicationName = str;
	}

	public String getKey() {

		StringBuilder stringBuilder = new StringBuilder();
//		stringBuilder.append("G");
//		stringBuilder.append(groupId);
//		stringBuilder.append("H");
//		stringBuilder.append(hostId);
		stringBuilder.append("I");
		stringBuilder.append(itemId);
		stringBuilder.append("T");
		stringBuilder.append(clock);

		return stringBuilder.toString();
	}

	public String getJsonValue() {

		JSONObject json = new JSONObject();
		json.put("groupId", groupId);
		json.put("hostId", hostId);
		json.put("itemId", itemId);
		json.put("applicationId", applicationId);
		json.put("groupName", groupName);
		json.put("hostName", hostName);
		json.put("itemName", itemName);
		json.put("applicationName", applicationName);
		json.put("clock", clock);
		json.put("value", value);

		return json.toJSONString();
	}

	public String toString() {

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("{groupId:").append(groupId).append(",");
		stringBuilder.append("hostId:").append(hostId).append(",");
		stringBuilder.append("itemId:").append(itemId).append(",");
		stringBuilder.append("applicationId:").append(applicationId).append(",");
		stringBuilder.append("groupName:").append(groupName).append(",");
		stringBuilder.append("hostName:").append(hostName).append(",");
		stringBuilder.append("itemName:").append(itemName).append(",");
		stringBuilder.append("applicationName:").append(applicationName).append(",");
		stringBuilder.append("clock:").append(clock).append(",");
		stringBuilder.append("value_min:").append(value_min).append(",");
		stringBuilder.append("value_max:").append(value_max).append(",");
		stringBuilder.append("value_avg:").append(value_avg).append(",");
		stringBuilder.append("value:").append(value).append("}");

		return stringBuilder.toString();
	}

}
