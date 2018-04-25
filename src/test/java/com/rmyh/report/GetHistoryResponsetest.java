package com.rmyh.report;

import java.util.ArrayList;
import java.util.HashMap;

import com.rmyh.report.dao.reportZabbixApi;
import com.zabbix4j.ZabbixApiException;
import com.zabbix4j.history.HistoryGetRequest;
import com.zabbix4j.history.HistoryGetResponse;
import com.zabbix4j.history.HistoryObject;

public class GetHistoryResponsetest {
	
    public static void main(String[] args) throws ZabbixApiException {  
    	getHistory(23294,1400000000000l,1600000000000l);
    } 
    
    
	public HistoryGetResponse getHistory(ArrayList itemids) throws ZabbixApiException {  
		reportZabbixApi zabbixApi = new reportZabbixApi();

		zabbixApi.login();  
	    HistoryGetRequest request = new HistoryGetRequest();  
	    HistoryGetRequest.Params params = request.getParams();  
	  
	    ArrayList<Integer> hostIds = new ArrayList<Integer>();  
	    // params.setHostids(hostIds);  
	    // 这里可以设指定的id值，也可以不设值。设值的话，取指定的内容，不设的话，获取全部的host  
	    params.setHostids(null);  

	    params.setItemids(itemids);
	    params.setTime_from(1400000000l);
	    params.setTime_till(1600000000l);
	    HistoryGetResponse response = zabbixApi.getApi().history().get(request);  
	  
	    return response;  
	    }  
	public static HistoryGetResponse getHistory(int itemid,long datePre , long dateNex) throws ZabbixApiException {  
		reportZabbixApi zabbixApi = new reportZabbixApi();
    	ArrayList<Integer> itemids = new ArrayList<Integer>();
	    itemids.add(itemid);
		zabbixApi.login();  
	    HistoryGetRequest request = new HistoryGetRequest();  
	    HistoryGetRequest.Params params = request.getParams();  
	  
	    ArrayList<Integer> hostIds = new ArrayList<Integer>();  
	    // params.setHostids(hostIds);  
	    // 这里可以设指定的id值，也可以不设值。设值的话，取指定的内容，不设的话，获取全部的host  
	    params.setHostids(null);  

	    params.setItemids(itemids);
	    params.setTime_from(datePre/1000);
	    params.setTime_till(dateNex/1000);
	    HistoryGetResponse response = zabbixApi.getApi().history().get(request);  
	  System.out.println(response.getResult().size());
	    return response;  
	    }  
	      
	    public static HashMap getHistoryArrByItem(int itemid) throws ZabbixApiException { 
	    	HashMap historyOfItem = new HashMap();
	    	ArrayList historyArr = new ArrayList();
	    	ArrayList<Integer> itemids = new ArrayList<Integer>();
		    itemids.add(itemid);
	    	HistoryGetResponse response = new GetHistoryResponsetest().getHistory(itemids);  
	        for (int i = 0; i < response.getResult().size(); i++) {  
	        //response 返回的信息非常大，可以赋值给HostObject，也可以其他对象  
	        HistoryObject myHistoryObject = response.getResult().get(i);  
	        //response 返回的信息非常大，可以赋值给ItemObject，也可以其他对象  
	        HashMap historySigObj = new HashMap();
	  
	        //打印host信息  
	        if (null == myHistoryObject)  
	            System.out.println("Get data null");  
	        else {  
	        	historySigObj.put("value",myHistoryObject.getValue());
	        	historySigObj.put("clock",myHistoryObject.getClock());
	            historyArr.add(historySigObj);	  
	        }  
	        }
	        
	        historyOfItem.put("value", historyArr);
	        historyOfItem.put("itemId", itemid);
	        System.out.println(historyOfItem);
			return historyOfItem;  

	    }  

}
