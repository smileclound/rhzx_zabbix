package com.rmyh.report.service.zabbix;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.rmyh.report.dao.reportZabbixApi;
import com.zabbix4j.ZabbixApiException;
import com.zabbix4j.host.HostGetRequest;
import com.zabbix4j.host.HostGetResponse;
import com.zabbix4j.host.HostObject;
import com.zabbix4j.hostinteface.HostInterfaceGetRequest;
import com.zabbix4j.hostinteface.HostInterfaceGetResponse;
import com.zabbix4j.hostinteface.HostInterfaceObject;

public class GetHosts {

	 public static void main(String[] args) throws ZabbixApiException {
	 gethostsObjList();
	 }

	public static HostGetResponse getHost(ArrayList<Integer> groupids) throws ZabbixApiException {
		reportZabbixApi zabbixApi = new reportZabbixApi();
		zabbixApi.login();

		HostGetRequest request = new HostGetRequest();
		HostGetRequest.Params params = request.getParams();

		params.setGroupids(groupids);
		HostGetResponse response = zabbixApi.getApi().host().get(request);
		return response;
	}
	public static HostInterfaceGetResponse getHostInterface(int hostId) throws ZabbixApiException {
		reportZabbixApi zabbixApi = new reportZabbixApi();
		zabbixApi.login();

		HostInterfaceGetRequest request = new HostInterfaceGetRequest();
		HostInterfaceGetRequest.Params params = request.getParams();
		List<Integer> hostIdsList = new ArrayList<Integer>();
		hostIdsList.add(hostId);
		params.setHostids(hostIdsList);

		HostInterfaceGetResponse response = zabbixApi.getApi().hostInterface().get(request);
		return response;
	}

	public List<List<Integer>> gethostsIdList() throws ZabbixApiException {
		List<Integer> hostGroupsList = new GetHostGroups().getHostGroupdIdList();

		List<List<Integer>> hostsIdList = new ArrayList<List<Integer>>();
		for (int k = 0; k < hostGroupsList.size(); k++) {
			ArrayList<Integer> groupid = new ArrayList<Integer>();
			groupid.add(hostGroupsList.get(k));
			HostGetResponse response = getHost(groupid);
			ArrayList<Integer> hostIdList = new ArrayList<Integer>();

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
//			System.out.println("groupid_" + hostGroupsList.get(k) + "\'s hostIdList:" + hostIdList);

		}
//		System.out.println("allGroupshostsIdList:" + hostsIdList);
		return hostsIdList;
	}

	public ArrayList<Integer> gethostsIdList(int groupid) throws ZabbixApiException {

		ArrayList<Integer> groupidarr = new ArrayList<Integer>();
		groupidarr.add(groupid);
		HostGetResponse response = getHost(groupidarr);
		ArrayList<Integer> hostIdList = new ArrayList<Integer>();

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
//		System.out.println("groupid_" + groupid + "'s hostIdList:" + hostIdList);
		return hostIdList;

	}

	public static ArrayList<ArrayList<HashMap<String, String>>> gethostsObjList() throws ZabbixApiException {
		List<Integer> hostGroupsList = new GetHostGroups().getHostGroupdIdList();
		ArrayList<ArrayList<HashMap<String, String>>> hostsObjList = new ArrayList<ArrayList<HashMap<String, String>>>();
		for (int k = 0; k < hostGroupsList.size(); k++) {
			ArrayList<Integer> groupid = new ArrayList<Integer>();
			groupid.add(hostGroupsList.get(k));
			HostGetResponse response = getHost(groupid);
			ArrayList<HashMap<String, String>> hostObjList = new ArrayList<HashMap<String, String>>();

			for (int i = 0; i < response.getResult().size(); i++) {
				HostObject myHostObject = response.getResult().get(i);
				// 打印hostgroup信息
				if (null == myHostObject) {
					System.out.println("Get Host null");
				} else {
					HashMap<String, String> hostobj = new HashMap<String, String>();
					// hostObjList.add(myHostObject.getHost());
					hostobj.put("name", myHostObject.getName());
					hostobj.put("id", String.valueOf(myHostObject.getHostid()));
					hostObjList.add(hostobj);
					// System.out.println("-----------------------");
					// System.out.println("id:"+myHostGroupObject.getGroupid()+";name:"+myHostGroupObject.getName());
				}

			}
			hostsObjList.add(hostObjList);
//			System.out.println("groupid_" + hostGroupsList.get(k) + "\'s hostIdList:" + hostObjList);

		}
//		System.out.println("allGroupshostsIdList:" + hostsObjList);
		return hostsObjList;
	}

	public List<HashMap<String, String>> gethostsObjList(int groupid) throws ZabbixApiException {

		ArrayList<Integer> groupidarr = new ArrayList<Integer>();
		groupidarr.add(groupid);
		HostGetResponse response = getHost(groupidarr);
		ArrayList<HashMap<String, String>> hostsObjList = new ArrayList<HashMap<String, String>>();
//		ProxyGetResponse proxyresponse = getProxy();

		

		for (int i = 0; i < response.getResult().size(); i++) {
			HostObject myHostObject = response.getResult().get(i);
			Set<String> hostsIpList = new HashSet<String>();
			// 打印hostgroup信息
			if (null == myHostObject) {
				System.out.println("Get data null");
			} else {
				HashMap<String, String> hostobj = new HashMap<String, String>();
				// hostObjList.add(myHostObject.getHost());
				hostobj.put("hostName", myHostObject.getName());
				hostobj.put("hostId", String.valueOf(myHostObject.getHostid()));
				HostInterfaceGetResponse HostInterfaceresponse = getHostInterface(myHostObject.getHostid());
				for(int j = 0; j< HostInterfaceresponse.getResult().size(); j++) {
					for(HostInterfaceObject result : HostInterfaceresponse.getResult()) {
							hostsIpList.add(result.getIp());
					}
				}  
				hostobj.put("hostIp", hostsIpList.toString());
				hostsObjList.add(hostobj);
			}

		}
//		System.out.println("groupid_" + groupid + "'s hostsObjList:" + hostsObjList);
		return hostsObjList;

	}

}
