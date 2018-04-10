package com.rmyh.report.zabbix;

import java.util.ArrayList;
import java.util.List;

import com.rmyh.report.dao.reportZabbixApi;
import com.rmyh.report.service.zabbix.GetHostGroups;
import com.zabbix4j.ZabbixApiException;
import com.zabbix4j.host.HostGetRequest;
import com.zabbix4j.host.HostGetRequest.Params;
import com.zabbix4j.host.HostGetResponse;
import com.zabbix4j.host.HostObject;

public class GetHostIpTest {
	public static void main(String[] args) throws ZabbixApiException {
		reportZabbixApi api = new reportZabbixApi();
		api.login();
		HostGetResponse response = new HostGetResponse();
		HostGetRequest req = new HostGetRequest();
		Params param = req.getParams();
		List hostids = new ArrayList();
		hostids.add(10084);
		param.setHostids(hostids);
		response = api.getApi().host().get(req);
		System.out.println(response.getResult().get(0).getName());

	}
}
