package com.rmyh.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.rmyh.report.dao.reportZabbixApi;
import com.zabbix4j.ZabbixApiException;
import com.zabbix4j.application.ApplicationGetRequest;
import com.zabbix4j.application.ApplicationGetResponse;
import com.zabbix4j.history.HistoryGetRequest;
import com.zabbix4j.history.HistoryGetResponse;

public class GetApplicationtest {

	public static void main(String[] args) throws ZabbixApiException {
		getApplication(23294);
	}
	
	public static List<HashMap> getAllItem_Appli(){
		return null;
	}

	public static String getApplication(int itemid) throws ZabbixApiException {
		reportZabbixApi zabbixApi = new reportZabbixApi();
		ArrayList<Integer> itemids = new ArrayList<Integer>();
		zabbixApi.login();
		ApplicationGetRequest request = new ApplicationGetRequest();
		ApplicationGetRequest.Params params = request.getParams();

		ArrayList<Integer> hostIds = new ArrayList<Integer>();
		// hostIds.add(10084);
		// params.setHostids(hostIds);
		// 这里可以设指定的id值，也可以不设值。设值的话，取指定的内容，不设的话，获取全部的host
		// params.setHostids(hostIds);

		itemids.add(itemid);
		params.setItemids(itemids);
		ApplicationGetResponse response = zabbixApi.getApi().application().get(request);

		List list = new ArrayList();
		for (int i = 0; i < response.getResult().size(); i++) {
			list.add(response.getResult().get(i).getName());
		}
		System.out.println(list.toString());
		return list.toString();
	}
}
