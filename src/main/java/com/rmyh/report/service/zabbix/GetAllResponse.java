package com.rmyh.report.service.zabbix;

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
	
	
	public HostgroupGetResponse getHostGroup() throws ZabbixApiException {
		reportZabbixApi zabbixApi = new reportZabbixApi();
		zabbixApi.login();
		HostgroupGetRequest hostGroupRequest = new HostgroupGetRequest();
		HostgroupGetRequest.Params params = hostGroupRequest.getParams();
		HostgroupGetResponse hostGroupResponse = zabbixApi.getApi().hostgroup().get(hostGroupRequest);
		return hostGroupResponse;
	}
	public HostGetResponse getHostByGroupId(int groupId) throws ZabbixApiException {
		reportZabbixApi zabbixApi = new reportZabbixApi();
		zabbixApi.login();
		HostGetRequest hostRequest = new HostGetRequest();
		HostGetRequest.Params params = hostRequest.getParams();
		List<Integer> groupIdList = new ArrayList();
		groupIdList.add(groupId);
		params.setGroupids(groupIdList);
		HostGetResponse hostResponse = zabbixApi.getApi().host().get(hostRequest);
		return hostResponse;
	}
	public ApplicationGetResponse getApplicationByHostId(int hostId) throws ZabbixApiException {
		reportZabbixApi zabbixApi = new reportZabbixApi();
		zabbixApi.login();
		ApplicationGetRequest applicationRequest = new ApplicationGetRequest();
		ApplicationGetRequest.Params params = applicationRequest.getParams();
		List<Integer> hostIdList = new ArrayList();
		hostIdList.add(hostId);
		params.setHostids(hostIdList);
		ApplicationGetResponse applicationResponse = zabbixApi.getApi().application().get(applicationRequest);
		return applicationResponse;
	}
	public ItemGetResponse getItemByApplicationId(int applicationId) throws ZabbixApiException {
		reportZabbixApi zabbixApi = new reportZabbixApi();
		zabbixApi.login();
		ItemGetRequest itemRequest = new ItemGetRequest();
		ItemGetRequest.Params params = itemRequest.getParams();
		List<Integer> applicationIdList = new ArrayList();
		applicationIdList.add(applicationId);
		params.setApplicationids(applicationIdList);
		ItemGetResponse itemResponse = zabbixApi.getApi().item().get(itemRequest);
		return itemResponse;
	}
}