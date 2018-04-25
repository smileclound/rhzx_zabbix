package com.rmyh.report.service.zabbix;

import java.io.IOException;
import java.util.ArrayList;
import com.rmyh.report.dao.reportZabbixApi;
import com.zabbix4j.ZabbixApiException;
import com.zabbix4j.history.HistoryGetRequest;
import com.zabbix4j.history.HistoryGetResponse;

public class GetHistoryResponse {
	
//    public static void main(String[] args) throws ZabbixApiException {  
//    	getHistoryArrByItem(23720);
//    } 
    
    
	public HistoryGetResponse getHistory(ArrayList<Integer> itemids) throws ZabbixApiException, IOException {  
//		reportZabbixApi zabbixApi = new reportZabbixApi();
//		reportZabbixApi.login();  
	    HistoryGetRequest request = new HistoryGetRequest();  
	    HistoryGetRequest.Params params = request.getParams();  
	  
//	    ArrayList<Integer> hostIds = new ArrayList<Integer>();  
	    // params.setHostids(hostIds);  
	    // 这里可以设指定的id值，也可以不设值。设值的话，取指定的内容，不设的话，获取全部的host  
	    params.setHostids(null);  

	    params.setItemids(itemids);
	    HistoryGetResponse response = reportZabbixApi.getApi().history().get(request);  
	  
	    return response;  
	    }  
	public HistoryGetResponse getHistory(int itemid, long datePre, long dateNex) throws ZabbixApiException, IOException {  
//		reportZabbixApi zabbixApi = new reportZabbixApi();
    	ArrayList<Integer> itemids = new ArrayList<Integer>();
	    itemids.add(itemid);
//	    reportZabbixApi.login();  
	    HistoryGetRequest request = new HistoryGetRequest();  
	    HistoryGetRequest.Params params = request.getParams();  
	  
//	    ArrayList<Integer> hostIds = new ArrayList<Integer>();  
	    // params.setHostids(hostIds);  
	    // 这里可以设指定的id值，也可以不设值。设值的话，取指定的内容，不设的话，获取全部的host  
	    params.setHostids(null);  

	    params.setItemids(itemids);
	    
	    params.setTime_from(datePre/1000);
	    params.setTime_till(dateNex/1000);
	 
	    HistoryGetResponse response = reportZabbixApi.getApi().history().get(request);  
	    return response;  
	    }  
	      
//	    public static HashMap<String, V> getHistoryArrByItem(int itemid) throws ZabbixApiException { 
//	    	HashMap<String, ?> historyOfItem = new HashMap<String, Object>();
//	    	ArrayList<HashMap<String, String>> historyArr = new ArrayList<HashMap<String, String>>();
//	    	ArrayList<Integer> itemids = new ArrayList<Integer>();
//		    itemids.add(itemid);
//	    	HistoryGetResponse response = new GetHistoryResponse().getHistory(itemids);  
//	        for (int i = 0; i < response.getResult().size(); i++) {  
//	        //response 返回的信息非常大，可以赋值给HostObject，也可以其他对象  
//	        HistoryObject myHistoryObject = response.getResult().get(i);  
//	        //response 返回的信息非常大，可以赋值给ItemObject，也可以其他对象  
//	        HashMap<String, String> historySigObj = new HashMap<String, String>();
//	  
//	        //打印host信息  
//	        if (null == myHistoryObject)  
//	            System.out.println("Get data null");  
//	        else {  
//	        	historySigObj.put("value",myHistoryObject.getValue());
//	        	historySigObj.put("clock",String.valueOf(myHistoryObject.getClock()));
//	            historyArr.add(historySigObj);	  
//	        }  
//	        }
//	        
//	        historyOfItem.put("value", historyArr);
//	        historyOfItem.put("itemId", itemid);
////	        System.out.println(historyOfItem);
//			return historyOfItem;  
//
//	    }  

}
