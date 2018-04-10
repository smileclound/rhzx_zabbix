package com.rmyh.report.service.zabbix;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.rmyh.report.dao.reportZabbixApi;
import com.zabbix4j.ZabbixApiException;
import com.zabbix4j.host.HostGetRequest;
import com.zabbix4j.host.HostGetResponse;
import com.zabbix4j.host.HostObject;

public class GetHosts {
	public static List hostsIdList = new ArrayList();
	public static List hostsObjList = new ArrayList();

	 public static void main(String[] args) throws ZabbixApiException {
	 gethostsObjList();
	 }

	public static HostGetResponse getHost(ArrayList groupids) throws ZabbixApiException {
		reportZabbixApi zabbixApi = new reportZabbixApi();
		zabbixApi.login();

		HostGetRequest request = new HostGetRequest();
		HostGetRequest.Params params = request.getParams();

		params.setGroupids(groupids);
		HostGetResponse response = zabbixApi.getApi().host().get(request);
		return response;
	}

	public static List gethostsIdList() throws ZabbixApiException {
		List hostGroupsList = new GetHostGroups().getHostGroupdIdList();

		for (int k = 0; k < hostGroupsList.size(); k++) {
			ArrayList groupid = new ArrayList();
			groupid.add(hostGroupsList.get(k));
			HostGetResponse response = getHost(groupid);
			ArrayList hostIdList = new ArrayList();

			for (int i = 0; i < response.getResult().size(); i++) {
				HostObject myHostObject = response.getResult().get(i);
				// 打印hostgroup信息
				if (null == myHostObject) {
					System.out.println("Get data null");
				} else {
					hostIdList.add(myHostObject.getHostid());
					// System.out.println("-----------------------");
					// System.out.println("id:"+myHostGroupObject.getGroupid()+";name:"+myHostGroupObject.getName());
				}

			}
			hostsIdList.add(hostIdList);
			System.out.println("groupid_" + hostGroupsList.get(k) + "\'s hostIdList:" + hostIdList);

		}
		System.out.println("allGroupshostsIdList:" + hostsIdList);
		return hostsIdList;
	}

	public static ArrayList gethostsIdList(int groupid) throws ZabbixApiException {

		ArrayList groupidarr = new ArrayList();
		groupidarr.add(groupid);
		HostGetResponse response = getHost(groupidarr);
		ArrayList hostIdList = new ArrayList();

		for (int i = 0; i < response.getResult().size(); i++) {
			HostObject myHostObject = response.getResult().get(i);
			// 打印hostgroup信息
			if (null == myHostObject) {
				System.out.println("Get data null");
			} else {
				hostIdList.add(myHostObject.getHostid());
				// System.out.println("-----------------------");
				// System.out.println("id:"+myHostGroupObject.getGroupid()+";name:"+myHostGroupObject.getName());
			}

		}
		System.out.println("groupid_" + groupid + "'s hostIdList:" + hostIdList);
		return hostIdList;

	}

	public static ArrayList gethostsObjList() throws ZabbixApiException {
		List hostGroupsList = new GetHostGroups().getHostGroupdIdList();
		ArrayList hostsObjList = new ArrayList();
		for (int k = 0; k < hostGroupsList.size(); k++) {
			ArrayList groupid = new ArrayList();
			groupid.add(hostGroupsList.get(k));
			HostGetResponse response = getHost(groupid);
			ArrayList hostObjList = new ArrayList();

			for (int i = 0; i < response.getResult().size(); i++) {
				HostObject myHostObject = response.getResult().get(i);
				// 打印hostgroup信息
				if (null == myHostObject) {
					System.out.println("Get Host null");
				} else {
					HashMap hostobj = new HashMap();
					// hostObjList.add(myHostObject.getHost());
					hostobj.put("name", myHostObject.getName());
					hostobj.put("id", myHostObject.getHostid());
					hostObjList.add(hostobj);
					// System.out.println("-----------------------");
					// System.out.println("id:"+myHostGroupObject.getGroupid()+";name:"+myHostGroupObject.getName());
				}

			}
			hostsObjList.add(hostObjList);
			System.out.println("groupid_" + hostGroupsList.get(k) + "\'s hostIdList:" + hostObjList);

		}
		System.out.println("allGroupshostsIdList:" + hostsObjList);
		return hostsObjList;
	}

	public static List gethostsObjList(int groupid) throws ZabbixApiException {

		ArrayList groupidarr = new ArrayList();
		groupidarr.add(groupid);
		HostGetResponse response = getHost(groupidarr);
		ArrayList hostsObjList = new ArrayList();

		for (int i = 0; i < response.getResult().size(); i++) {
			HostObject myHostObject = response.getResult().get(i);
			// 打印hostgroup信息
			if (null == myHostObject) {
				System.out.println("Get data null");
			} else {
				HashMap hostobj = new HashMap();
				// hostObjList.add(myHostObject.getHost());
				hostobj.put("hostName", myHostObject.getName());
				hostobj.put("hostId", myHostObject.getHostid());
				hostsObjList.add(hostobj);
			}

		}
//		System.out.println("groupid_" + groupid + "'s hostsObjList:" + hostsObjList);
		return hostsObjList;

	}

}
