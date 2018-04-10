package com.rmyh.report.service.zabbix;

import java.util.ArrayList;

import com.rmyh.report.dao.reportZabbixApi;
import com.zabbix4j.ZabbixApiException;
import com.zabbix4j.event.EventGetRequest;
import com.zabbix4j.event.EventGetResponse;

public class GetEventResponse {
	
	public EventGetResponse getEvent(int hostid) throws ZabbixApiException {  
		reportZabbixApi zabbixApi = new reportZabbixApi();
    	ArrayList<Integer> hostids = new ArrayList<Integer>();
    	hostids.add(hostid);
		zabbixApi.login();  
	    EventGetRequest request = new EventGetRequest();  
	    EventGetRequest.Params params = request.getParams();  
	  
	    ArrayList<Integer> hostIds = new ArrayList<Integer>();  
	    // params.setHostids(hostIds);  
	    // 这里可以设指定的id值，也可以不设值。设值的话，取指定的内容，不设的话，获取全部的host  
	    params.setHostids(hostids);  

	    EventGetResponse response = zabbixApi.getApi().event().get(request);  
	  
	    return response;  
	    }  

	public EventGetResponse getEvent() throws ZabbixApiException {  
		reportZabbixApi zabbixApi = new reportZabbixApi();
		zabbixApi.login();  
	    EventGetRequest request = new EventGetRequest();  
	    EventGetRequest.Params params = request.getParams();  
	  
//	    ArrayList<Integer> hostIds = new ArrayList<Integer>();  
	    // params.setHostids(hostIds);  
	    // 这里可以设指定的id值，也可以不设值。设值的话，取指定的内容，不设的话，获取全部的host  
//	    params.setHostids(null);  
//	    params.set

	    EventGetResponse response = zabbixApi.getApi().event().get(request);  
	  
	    return response;  
	    } 
}
