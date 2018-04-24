package com.rmyh.report.service.zabbix;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.rmyh.report.dao.reportZabbixApi;
import com.zabbix4j.ZabbixApiException;
import com.zabbix4j.hostgroup.*;

public class GetHostGroups {
	public static List<Integer> hostGroupdIdList;
	public static List<?> hostGroupObjList;

	// public static void main(String[] args) throws ZabbixApiException {
	//
	// getHostGroupsObjList();
	// }

	public HostgroupGetResponse getHostGroup() throws ZabbixApiException {
		reportZabbixApi zabbixApi = new reportZabbixApi();
		zabbixApi.login();

		HostgroupGetRequest request = new HostgroupGetRequest();
		// HostgroupGetRequest.Params params = request.getParams();
		// params.setSelectConditions(null);
		// params.setSelectDiscoveryRule(null);
		// params.setSelectGroupDiscovery(null);
		// params.setSelectHosts(null);
		// params.setSelectOperations(null);
		// params.setSelectTemplates(null);
		// params.setFilter(null);

		HostgroupGetResponse response = zabbixApi.getApi().hostgroup().get(request);
		return response;
	}

	public List<Integer> getHostGroupdIdList() throws ZabbixApiException {
		GetHostGroups myGetHostGroups = new GetHostGroups();
		HostgroupGetResponse response = myGetHostGroups.getHostGroup();
		List<Integer> hostGroupdIdList = new ArrayList<Integer>();
		for (int i = 0; i < response.getResult().size(); i++) {
			HostgroupObject myHostGroupObject = response.getResult().get(i);

			// 打印hostgroup信息
			if (null == myHostGroupObject) {
				System.out.println("Get hostgroup null");
			} else {
				hostGroupdIdList.add(myHostGroupObject.getGroupid());
				// System.out.println("-----------------------");
				// System.out.println("id:"+myHostGroupObject.getGroupid()+";name:"+myHostGroupObject.getName());
			}
		}
//		System.out.println("hostGroupdIdList:" + hostGroupdIdList);
		return hostGroupdIdList;
	}

	public List<HashMap<String, String>> getHostGroupsObjList() throws ZabbixApiException {
		GetHostGroups myGetHostGroups = new GetHostGroups();
		HostgroupGetResponse response = myGetHostGroups.getHostGroup();
		List<HashMap<String, String>> hostGroupObjList = new ArrayList<HashMap<String, String>>();
		for (int i = 0; i < response.getResult().size(); i++) {
			HostgroupObject myHostGroupObject = response.getResult().get(i);

			// 打印hostgroup信息
			if (null == myHostGroupObject) {
				System.out.println("Get hostgroup null");
			} else {
				HashMap<String, String> groupobj = new HashMap<String, String>();
				groupobj.put("groupName", myHostGroupObject.getName());
				groupobj.put("groupId", String.valueOf(myHostGroupObject.getGroupid()));
				hostGroupObjList.add(groupobj);

			}
		}
		// System.out.println("hostGroupObjList:" + hostGroupObjList);
		return hostGroupObjList;
	}
}
