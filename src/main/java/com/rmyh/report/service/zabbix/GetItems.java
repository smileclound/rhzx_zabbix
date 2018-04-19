package com.rmyh.report.service.zabbix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.rmyh.report.bean.ItemBean;
import com.rmyh.report.dao.reportZabbixApi;
import com.zabbix4j.ZabbixApiException;
import com.zabbix4j.application.ApplicationGetRequest;
import com.zabbix4j.application.ApplicationGetResponse;
import com.zabbix4j.application.ApplicationObject;
import com.zabbix4j.item.ItemGetRequest;
import com.zabbix4j.item.ItemGetResponse;
import com.zabbix4j.item.ItemObject;

public class GetItems {

	public static void main(String[] args) throws ZabbixApiException {
		// getAllItems_App();
//		System.out.println(transKeyofName("cpu $1 time","fsjhfjk[nice,bad]"));
	}

	public static List<ItemBean> getAllItems() throws ZabbixApiException {
		List<ItemBean> itemBeans = new ArrayList();
		List<HashMap> hostGroupList = new GetHostGroups().getHostGroupsObjList();
		for (int i = 0; i < hostGroupList.size(); i++) {
			int groupId = (Integer) hostGroupList.get(i).get("groupId");
			String groupName = (String) hostGroupList.get(i).get("groupName");
			List<HashMap> hostList = new GetHosts().gethostsObjList(groupId);
			for (int j = 0; j < hostList.size(); j++) {
				int hostId = (Integer) hostList.get(j).get("hostId");
				String hostName = (String) hostList.get(j).get("hostName");
				List<ItemObject> itemList = getItemsObjList(hostId);
				for (int k = 0; k < itemList.size(); k++) {
					int itemId = (Integer) itemList.get(k).getItemid();
					String itemName = itemList.get(k).getName();
					ItemBean itemBean = new ItemBean();
					itemBean.setGroupId(groupId);
					itemBean.setGroupName(groupName);
					itemBean.setHostId(hostId);
					itemBean.setHostName(hostName);
					itemBean.setItemId(itemId);
					itemBean.setItemName(itemName);
					itemBeans.add(itemBean);
				}

			}
		}
		System.out.println(itemBeans);
		return itemBeans;

	}

	public static List<ItemBean> getAllItems_App() throws ZabbixApiException {
		List<ItemBean> itemBeans = new ArrayList();
		List<HashMap> hostGroupList = new GetHostGroups().getHostGroupsObjList();
		for (int i = 0; i < hostGroupList.size(); i++) {
			int groupId = (Integer) hostGroupList.get(i).get("groupId");
			String groupName = (String) hostGroupList.get(i).get("groupName");
			List<HashMap> hostList = new GetHosts().gethostsObjList(groupId);
			for (int j = 0; j < hostList.size(); j++) {
				int hostId = (Integer) hostList.get(j).get("hostId");
				String hostName = (String) hostList.get(j).get("hostName");
				String hostIp = (String) hostList.get(j).get("hostIp");
				List<HashMap> applicationList = getApplicationObjList(hostId);
				for (int l = 0; l < applicationList.size(); l++) {
					int applicationId = (Integer) applicationList.get(l).get("applicationId");
					String applicationName = (String) applicationList.get(l).get("applicationName");
					List<ItemObject> itemList = getItemsObjList_ByApp(applicationId);
					for (int k = 0; k < itemList.size(); k++) {
						int itemId = (Integer) itemList.get(k).getItemid();
						String itemTmpName = itemList.get(k).getName();
						String itemKey = itemList.get(k).getKey_();
						String itemName = transKeyofName(itemTmpName, itemKey);
						ItemBean itemBean = new ItemBean();
						itemBean.setGroupId(groupId);
						itemBean.setGroupName(groupName);
						itemBean.setHostId(hostId);
						itemBean.setHostName(hostName);
						itemBean.setItemId(itemId);
						itemBean.setItemName(itemName);
						itemBean.setItemKey(itemKey);
						itemBean.setApplicationId(applicationId);
						itemBean.setApplicationName(applicationName);
						itemBean.setHostIp(hostIp);
						itemBeans.add(itemBean);
					}

				}
			}
		}
		// System.out.println(itemBeans);
		return itemBeans;

	}

	public static List getApplicationObjList(int hostId) throws ZabbixApiException {
		reportZabbixApi zabbixApi = new reportZabbixApi();
		ArrayList<Integer> itemids = new ArrayList<Integer>();
		zabbixApi.login();
		ApplicationGetRequest request = new ApplicationGetRequest();
		ApplicationGetRequest.Params params = request.getParams();

		ArrayList<Integer> hostIds = new ArrayList<Integer>();
		hostIds.add(hostId);
		params.setHostids(hostIds);
		// 这里可以设指定的id值，也可以不设值。设值的话，取指定的内容，不设的话，获取全部的host
		// params.setHostids(hostIds);

		ApplicationGetResponse response = zabbixApi.getApi().application().get(request);

		List list = new ArrayList();
		for (int i = 0; i < response.getResult().size(); i++) {
			HashMap map = new HashMap();
			map.put("applicationId", response.getResult().get(i).getApplicationid());
			map.put("applicationName", response.getResult().get(i).getName());
			list.add(map);
			// System.out.println(map);
		}
		return list;
	}

	public static List getItemsObjList_ByApp(int appliId) throws ZabbixApiException {
		reportZabbixApi zabbixApi = new reportZabbixApi();
		zabbixApi.login();

		ItemGetRequest request = new ItemGetRequest();
		ItemGetRequest.Params params = request.getParams();
		ArrayList appliIdArr = new ArrayList();
		appliIdArr.add(appliId);
		params.setApplicationids(appliIdArr);
		ItemGetResponse response = zabbixApi.getApi().item().get(request);

		ArrayList itemsList = new ArrayList();

		for (int i = 0; i < response.getResult().size(); i++) {
			ItemObject myItemObject = response.getResult().get(i);
			itemsList.add(myItemObject);
		}
		// System.out.println(hostid + "'s items: " + itemsList);
		return itemsList;

	}

	public ItemGetResponse getItems(int hostid) throws ZabbixApiException {
		reportZabbixApi zabbixApi = new reportZabbixApi();
		zabbixApi.login();

		ItemGetRequest request = new ItemGetRequest();
		ItemGetRequest.Params params = request.getParams();
		ArrayList hostids = new ArrayList();
		hostids.add(hostid);
		params.setHostids(hostids);

		ItemGetResponse response = zabbixApi.getApi().item().get(request);
		return response;
	}

	public static ItemGetResponse getItems(ArrayList hostsidarr) throws ZabbixApiException {
		reportZabbixApi zabbixApi = new reportZabbixApi();
		zabbixApi.login();

		ItemGetRequest request = new ItemGetRequest();
		ItemGetRequest.Params params = request.getParams();
		params.setHostids(hostsidarr);

		ItemGetResponse response = zabbixApi.getApi().item().get(request);
		return response;
	}

	public static List getItemsObjList(int hostid) throws ZabbixApiException {

		ArrayList hostidarr = new ArrayList();
		hostidarr.add(hostid);
		ItemGetResponse response = getItems(hostidarr);
		ArrayList itemsList = new ArrayList();

		for (int i = 0; i < response.getResult().size(); i++) {
			ItemObject myItemObject = response.getResult().get(i);
			itemsList.add(myItemObject);
		}
		// System.out.println(hostid + "'s items: " + itemsList);
		return itemsList;

	}

	public static ArrayList getItemsList() throws ZabbixApiException {
		// // group by host
		// ArrayList hostGroups = new reportGetHostGroups().getHostGroupdIdList();
		// ArrayList itemsList = new ArrayList();
		// for (int k=0;k<hostGroups.size(); k++) {
		// ArrayList hostidarr = (ArrayList) reportGetHosts.gethostsIdList((int)
		// hostGroups.get(k)) ;
		// ItemGetResponse response = getItems(hostidarr);
		// ArrayList itemsList_pg = new ArrayList();

		List hosts = new GetHosts().gethostsIdList();
		ArrayList itemsList = new ArrayList();
		for (int k = 0; k < hosts.size(); k++) {
			ArrayList hostidarr = (ArrayList) hosts.get(k);
			ItemGetResponse response = getItems(hostidarr);
			ArrayList itemsList_pg = new ArrayList();

			for (int i = 0; i < response.getResult().size(); i++) {
				ItemObject myItemObject = response.getResult().get(i);
				itemsList_pg.add(myItemObject.getItemid());
			}
			System.out.println("hostid:" + hosts.get(k) + "itemsid:" + itemsList_pg);
			itemsList.add(itemsList_pg);

		}
		System.out.println(itemsList);
		return itemsList;
	}

	public static ArrayList getItemsListByGroup() throws ZabbixApiException {
		// group by host
		List hostGroupsList = new GetHostGroups().getHostGroupdIdList();
		ArrayList itemsList = new ArrayList();
		for (int m = 0; m < hostGroupsList.size(); m++) {
			ArrayList hostsList = GetHosts.gethostsIdList((Integer) hostGroupsList.get(m));
			ArrayList itemsList_pg = new ArrayList();
			for (int k = 0; k < hostsList.size(); k++) {

				ArrayList hostidarr = new ArrayList();
				hostidarr.add(hostsList.get(k));
				ItemGetResponse response = getItems(hostidarr);
				ArrayList itemsList_ph = new ArrayList();

				for (int i = 0; i < response.getResult().size(); i++) {
					ItemObject myItemObject = response.getResult().get(i);
					itemsList_ph.add(myItemObject.getItemid());
				}
				System.out.println("hostid:" + hostsList.get(k) + "itemsid:" + itemsList_ph);
				itemsList_pg.add(itemsList_ph);

			}
			// System.out.println(itemsList_pg);
			itemsList.add(itemsList_pg);
		}
		return itemsList;
	}

	public static ArrayList getItemsListObjByGroup() throws ZabbixApiException {
		// group by host
		List hostGroupsList = new GetHostGroups().getHostGroupdIdList();
		ArrayList itemsListObjByGroup = new ArrayList();

		for (int m = 0; m < hostGroupsList.size(); m++) {
			ArrayList hostsList = GetHosts.gethostsIdList((Integer) hostGroupsList.get(m));
			ArrayList itemsList_pg = new ArrayList();

			HashMap itemsListObj = new HashMap();
			for (int k = 0; k < hostsList.size(); k++) {
				HashMap itemsObj_pg = new HashMap();
				ArrayList hostidarr = new ArrayList();
				hostidarr.add(hostsList.get(k));
				ItemGetResponse response = getItems(hostidarr);
				ArrayList itemsList_ph = new ArrayList();

				for (int i = 0; i < response.getResult().size(); i++) {
					ItemObject myItemObject = response.getResult().get(i);
					HashMap itemobj = new HashMap();
					itemobj.put("itemname", myItemObject.getName());
					itemobj.put("itemid", myItemObject.getItemid());
					itemsList_ph.add(itemobj);
				}
				// System.out.println("hostid:" + hostsList.get(k) + " ; items:" +
				// itemsList_ph);
				itemsObj_pg.put("itemlist", itemsList_ph);
				itemsObj_pg.put("hostid", hostidarr);
				itemsList_pg.add(itemsObj_pg);
			}
			// System.out.println(itemsList_pg);
			// itemsListObj.add(itemsList_pg);
			itemsListObj.put("groupid", hostGroupsList.get(m));
			itemsListObj.put("itemslistbyhost", itemsList_pg);

			// System.out.println(itemsListObj);
			itemsListObjByGroup.add(itemsListObj);
		}
		System.out.println(itemsListObjByGroup);
		return itemsListObjByGroup;
	}

	public static String transKeyofName(String itemName, String key) {
		String destinStr = "";
		Pattern ptn = Pattern.compile("(?:\\$\\d*)");
		Matcher m = ptn.matcher(itemName);
		StringBuffer sb = new StringBuffer();
		// List <ItemBean> allItemBean= new GetItems().getAllItems();
		for (int i = 0; m.find(); i++) {
			String Replace = transKeyId2Name(key,
					m.group().substring(m.group().indexOf("$") + 1, m.group().indexOf("$") + 2));
			m.appendReplacement(sb, Replace);
		}
		m.appendTail(sb);
		return sb.toString();
	}

	public static String transKeyId2Name(String key, String keyId) {
		String keyName = null;
		String keyarray_s = key.substring(key.indexOf("[")+1,key.lastIndexOf("]"));
		List keyarray = new ArrayList();
		keyarray = Arrays.asList(keyarray_s.split(","));
		for (int i = 0; i < keyarray.size(); i++) {
			if (Integer.parseInt(keyId)-1 == i)
				keyName = (String) keyarray.get(i);
		}
		return keyName;
	}
}
