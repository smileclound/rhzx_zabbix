package com.rmyh.report.service.zabbix;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.rmyh.report.dao.reportZabbixApi;
import com.zabbix4j.ZabbixApiException;
import com.zabbix4j.application.ApplicationGetRequest;
import com.zabbix4j.application.ApplicationGetResponse;
import com.zabbix4j.host.HostGetRequest;
import com.zabbix4j.host.HostGetResponse;
import com.zabbix4j.hostgroup.HostgroupGetRequest;
import com.zabbix4j.hostgroup.HostgroupGetResponse;
import com.zabbix4j.item.ItemGetRequest;
import com.zabbix4j.item.ItemGetResponse;

public class GetAllResponse {
	
	
	public HostgroupGetResponse getHostGroup() throws ZabbixApiException, IOException {
//		reportZabbixApi zabbixApi = new reportZabbixApi();
//		reportZabbixApi.login();
		HostgroupGetRequest hostGroupRequest = new HostgroupGetRequest();
//		HostgroupGetRequest.Params params = hostGroupRequest.getParams();
		HostgroupGetResponse hostGroupResponse = reportZabbixApi.getApi().hostgroup().get(hostGroupRequest);
		return hostGroupResponse;
	}
	public HostGetResponse getHostByGroupId(int groupId) throws ZabbixApiException, IOException {
//		reportZabbixApi zabbixApi = new reportZabbixApi();
//		reportZabbixApi.login();
		HostGetRequest hostRequest = new HostGetRequest();
		HostGetRequest.Params params = hostRequest.getParams();
		List<Integer> groupIdList = new ArrayList<Integer>();
		groupIdList.add(groupId);
		params.setGroupids(groupIdList);
		HostGetResponse hostResponse = reportZabbixApi.getApi().host().get(hostRequest);
		return hostResponse;
	}
	public ApplicationGetResponse getApplicationByHostId(int hostId) throws ZabbixApiException, IOException {
//		reportZabbixApi zabbixApi = new reportZabbixApi();
//		reportZabbixApi.login();
		ApplicationGetRequest applicationRequest = new ApplicationGetRequest();
		ApplicationGetRequest.Params params = applicationRequest.getParams();
		List<Integer> hostIdList = new ArrayList<Integer>();
		hostIdList.add(hostId);
		params.setHostids(hostIdList);
		ApplicationGetResponse applicationResponse = reportZabbixApi.getApi().application().get(applicationRequest);
		return applicationResponse;
	}
	public ItemGetResponse getItemByApplicationId(int applicationId) throws ZabbixApiException, IOException {
//		reportZabbixApi zabbixApi = new reportZabbixApi();
//		reportZabbixApi.login();
		ItemGetRequest itemRequest = new ItemGetRequest();
		ItemGetRequest.Params params = itemRequest.getParams();
		List<Integer> applicationIdList = new ArrayList<Integer>();
		applicationIdList.add(applicationId);
		params.setApplicationids(applicationIdList);
		ItemGetResponse itemResponse = reportZabbixApi.getApi().item().get(itemRequest);
		return itemResponse;
	}
}
