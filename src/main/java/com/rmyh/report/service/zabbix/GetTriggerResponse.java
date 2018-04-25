package com.rmyh.report.service.zabbix;

import java.io.IOException;
import java.util.ArrayList;

import com.rmyh.report.dao.reportZabbixApi;
import com.zabbix4j.ZabbixApiException;
import com.zabbix4j.trigger.TriggerGetRequest;
import com.zabbix4j.trigger.TriggerGetResponse;
import com.zabbix4j.triggerprototype.TriggerPrototypeGetRequest;
import com.zabbix4j.triggerprototype.TriggerPrototypeGetResponse;

public class GetTriggerResponse {
	
	public TriggerGetResponse getTrigger(int hostid) throws ZabbixApiException, IOException {  
//		reportZabbixApi zabbixApi = new reportZabbixApi();
    	ArrayList<Integer> hostids = new ArrayList<Integer>();
    	hostids.add(hostid);
    	reportZabbixApi.login();  
	    TriggerGetRequest request = new TriggerGetRequest();  
	    TriggerGetRequest.Params params = request.getParams();  
	  
//	    ArrayList<Integer> hostIds = new ArrayList<Integer>();  
	    // params.setHostids(hostIds);  
	    // 这里可以设指定的id值，也可以不设值。设值的话，取指定的内容，不设的话，获取全部的host  
	    params.setHostids(hostids);  

	    TriggerGetResponse response = reportZabbixApi.getApi().trigger().get(request);  
	  
	    return response;  
	    }  

	public TriggerGetResponse getTrigger() throws ZabbixApiException, IOException {  
//		reportZabbixApi zabbixApi = new reportZabbixApi();
		reportZabbixApi.login();  
	    TriggerGetRequest request = new TriggerGetRequest();  
	    TriggerGetRequest.Params params = request.getParams();  
	  
//	    ArrayList<Integer> hostIds = new ArrayList<Integer>();  
	    // params.setHostids(hostIds);  
	    // 这里可以设指定的id值，也可以不设值。设值的话，取指定的内容，不设的话，获取全部的host  
//	    params.setHostids(null);  
	    params.setSelectFunctions("extend");

	    TriggerGetResponse response = reportZabbixApi.getApi().trigger().get(request);  
	  
	    return response;  
	    } 
	
	
	public TriggerPrototypeGetResponse getProtoTrigger() throws ZabbixApiException, IOException {  
//		reportZabbixApi zabbixApi = new reportZabbixApi();
		reportZabbixApi.login();  
	    TriggerPrototypeGetRequest request = new TriggerPrototypeGetRequest();  
//	    TriggerPrototypeGetRequest.Params params = request.getParams();  
	  
//	    ArrayList<Integer> hostIds = new ArrayList<Integer>();  
	    // params.setHostids(hostIds);  
	    // 这里可以设指定的id值，也可以不设值。设值的话，取指定的内容，不设的话，获取全部的host  
//	    params.setHostids(null);  
//	    params.set

	    TriggerPrototypeGetResponse response = reportZabbixApi.getApi().triggerPrototype().get(request);  
	  
	    return response;  
	    } 
	
	
}
