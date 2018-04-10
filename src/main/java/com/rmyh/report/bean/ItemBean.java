package com.rmyh.report.bean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ItemBean{
	
	
	public final static String Family = "basic_info";

	public static Map<String, String> map = new HashMap<String, String>();

	static {
		map.put("hostId", Family);
		map.put("hostName", Family);
		map.put("groupId", Family);
		map.put("groupName", Family);
		map.put("itemId", Family);
		map.put("itemName", Family);
		map.put("applicationId", Family);
		map.put("applicationName", Family);
		

	}
	
	public int groupId;//  
	public int hostId;//  
	public int itemId;//  
	public int applicationId;
	public String groupName;// 
	public String hostName;//  
	public String itemName;// 
	public String itemKey;
	public String applicationName;



	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	public String getItemKey() {
		return itemKey;
	}

	public void setItemKey(String itemKey) {
		this.itemKey = itemKey;
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
	
	public String getKey() {

		StringBuilder stringBuilder = new StringBuilder();
//		stringBuilder.append("G");
//		stringBuilder.append(groupId);
//		stringBuilder.append("H");
//		stringBuilder.append(hostId);
		
//		change to insert time(insure all items have same time )
//		stringBuilder.append("T");
//		stringBuilder.append(clock);

		stringBuilder.append("I");
		stringBuilder.append(itemId);


		return stringBuilder.toString();
	}
	
	public String toString() {

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("{groupId:").append(groupId).append(",");
		stringBuilder.append("hostId:").append(hostId).append(",");
		stringBuilder.append("itemId:").append(itemId).append(",");
		stringBuilder.append("groupName:").append(groupName).append(",");
		stringBuilder.append("hostName:").append(hostName).append(",");
		stringBuilder.append("itemName:").append(itemName).append(",");
		stringBuilder.append("itemKey:").append(itemKey).append(",");
		stringBuilder.append("applicationName:").append(applicationName).append(",");
		stringBuilder.append("applicationId:").append(applicationId).append("}");


		return stringBuilder.toString();
	}
}
