package com.rmyh.report.service.zabbix;

import java.util.ArrayList;

import com.rmyh.report.dao.reportZabbixApi;
import com.zabbix4j.ZabbixApiException;
import com.zabbix4j.alert.AlertGetRequest;
import com.zabbix4j.alert.AlertGetResponse;

public class GetAlertResponse {
	
	public static AlertGetResponse getAlert(int hostid) throws ZabbixApiException {  
		reportZabbixApi zabbixApi = new reportZabbixApi();
    	ArrayList<Integer> hostids = new ArrayList<Integer>();
    	hostids.add(hostid);
		zabbixApi.login();  
	    AlertGetRequest request = new AlertGetRequest();  
	    AlertGetRequest.Params params = request.getParams();  
	  
//	    ArrayList<Integer> hostIds = new ArrayList<Integer>();  
	    // params.setHostids(hostIds);  
	    // 这里可以设指定的id值，也可以不设值。设值的话，取指定的内容，不设的话，获取全部的host  
	    params.setHostids(hostids);  

	    AlertGetResponse response = zabbixApi.getApi().alert().get(request);  
	  
	    return response;  
	    }  

	public AlertGetResponse getAlert() throws ZabbixApiException {  
		reportZabbixApi zabbixApi = new reportZabbixApi();
		zabbixApi.login();  
	    AlertGetRequest request = new AlertGetRequest();  
//	    AlertGetRequest.Params params = request.getParams();  
	  
//	    ArrayList<Integer> hostIds = new ArrayList<Integer>();  
	    // params.setHostids(hostIds);  
	    // 这里可以设指定的id值，也可以不设值。设值的话，取指定的内容，不设的话，获取全部的host  
//	    params.setHostids(null);  
//	    params.set

	    AlertGetResponse response = zabbixApi.getApi().alert().get(request);  
	  
	    return response;  
	    } 
}
