package com.rmyh.report.service.zabbix;

import java.io.IOException;
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
import com.zabbix4j.item.ItemGetRequest;
import com.zabbix4j.item.ItemGetResponse;
import com.zabbix4j.item.ItemObject;

public class GetItems {

//	public static void main(String[] args) throws ZabbixApiException {
//		// getAllItems_App();
////		System.out.println(transKeyofName("cpu $1 time","fsjhfjk[nice,bad]"));
//	}

	public List<ItemBean> getAllItems() throws ZabbixApiException, IOException {
		List<ItemBean> itemBeans = new ArrayList<ItemBean>();
		List<HashMap<String, String>> hostGroupList = new GetHostGroups().getHostGroupsObjList();
		for (int i = 0; i < hostGroupList.size(); i++) {
			int groupId = Integer.parseInt(hostGroupList.get(i).get("groupId"));
			String groupName = (String) hostGroupList.get(i).get("groupName");
			List<HashMap<String, String>> hostList = GetHosts.gethostsObjList(groupId);
			for (int j = 0; j < hostList.size(); j++) {
				int hostId = Integer.parseInt(hostList.get(j).get("hostId"));
				String hostName = (String) hostList.get(j).get("hostName");
				List<ItemObject> itemList = new GetItems().getItemsObjList(hostId);
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
//		System.out.println(itemBeans);
		return itemBeans;

	}

	public List<ItemBean> getAllItems_App() throws ZabbixApiException, IOException {
		List<ItemBean> itemBeans = new ArrayList<ItemBean>();
		List<HashMap<String, String>> hostGroupList = new GetHostGroups().getHostGroupsObjList();
		for (int i = 0; i < hostGroupList.size(); i++) {
			int groupId = Integer.parseInt(hostGroupList.get(i).get("groupId"));
			String groupName = (String) hostGroupList.get(i).get("groupName");
			List<HashMap<String, String>> hostList = GetHosts.gethostsObjList(groupId);
			for (int j = 0; j < hostList.size(); j++) {
				int hostId = Integer.parseInt(hostList.get(j).get("hostId"));
				String hostName = (String) hostList.get(j).get("hostName");
				String hostIp = (String) hostList.get(j).get("hostIp");
				List<HashMap<String, String>> applicationList = getApplicationObjList(hostId);
				for (int l = 0; l < applicationList.size(); l++) {
					int applicationId = Integer.parseInt(applicationList.get(l).get("applicationId"));
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

	public static List<HashMap<String, String>> getApplicationObjList(int hostId) throws ZabbixApiException, IOException {
//		reportZabbixApi.login();
		ApplicationGetRequest request = new ApplicationGetRequest();
		ApplicationGetRequest.Params params = request.getParams();

		ArrayList<Integer> hostIds = new ArrayList<Integer>();
		hostIds.add(hostId);
		params.setHostids(hostIds);
		// 这里可以设指定的id值，也可以不设值。设值的话，取指定的内容，不设的话，获取全部的host
		// params.setHostids(hostIds);

		ApplicationGetResponse response = reportZabbixApi.getApi().application().get(request);

		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		for (int i = 0; i < response.getResult().size(); i++) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("applicationId", String.valueOf(response.getResult().get(i).getApplicationid()));
			map.put("applicationName", response.getResult().get(i).getName());
			list.add(map);
			// System.out.println(map);
		}
		return list;
	}

	public static List<ItemObject> getItemsObjList_ByApp(int appliId) throws ZabbixApiException, IOException {
//		reportZabbixApi.login();

		ItemGetRequest request = new ItemGetRequest();
		ItemGetRequest.Params params = request.getParams();
		ArrayList<Integer> appliIdArr = new ArrayList<Integer>();
		appliIdArr.add(appliId);
		params.setApplicationids(appliIdArr);
		ItemGetResponse response = reportZabbixApi.getApi().item().get(request);

		ArrayList<ItemObject> itemsList = new ArrayList<ItemObject>();

		for (int i = 0; i < response.getResult().size(); i++) {
			ItemObject myItemObject = response.getResult().get(i);
			itemsList.add(myItemObject);
		}
		// System.out.println(hostid + "'s items: " + itemsList);
		return itemsList;

	}

	public ItemGetResponse getItems(int hostid) throws ZabbixApiException, IOException {
//		reportZabbixApi.login();

		ItemGetRequest request = new ItemGetRequest();
		ItemGetRequest.Params params = request.getParams();
		ArrayList<Integer> hostids = new ArrayList<Integer>();
		hostids.add(hostid);
		params.setHostids(hostids);

		ItemGetResponse response = reportZabbixApi.getApi().item().get(request);
		return response;
	}

	public ItemGetResponse getItems(List<Integer> hostsidarr) throws ZabbixApiException, IOException {
//		reportZabbixApi.login();

		ItemGetRequest request = new ItemGetRequest();
		ItemGetRequest.Params params = request.getParams();
		params.setHostids(hostsidarr);

		ItemGetResponse response = reportZabbixApi.getApi().item().get(request);
		return response;
	}

	public List<ItemObject> getItemsObjList(int hostid) throws ZabbixApiException, IOException {

		ArrayList<Integer> hostidarr = new ArrayList<Integer>();
		hostidarr.add(hostid);
		ItemGetResponse response = getItems(hostidarr);
		List<ItemObject> itemsList = new ArrayList<ItemObject>();

		for (int i = 0; i < response.getResult().size(); i++) {
			ItemObject myItemObject = response.getResult().get(i);
			itemsList.add(myItemObject);
		}
		// System.out.println(hostid + "'s items: " + itemsList);
		return itemsList;

	}

	public static ArrayList<ArrayList<Integer>> getItemsList() throws ZabbixApiException, IOException {
		// // group by host
		// ArrayList hostGroups = new reportGetHostGroups().getHostGroupdIdList();
		// ArrayList itemsList = new ArrayList();
		// for (int k=0;k<hostGroups.size(); k++) {
		// ArrayList hostidarr = (ArrayList) reportGetHosts.gethostsIdList((int)
		// hostGroups.get(k)) ;
		// ItemGetResponse response = getItems(hostidarr);
		// ArrayList itemsList_pg = new ArrayList();

		List<List<Integer>> hosts = new GetHosts().gethostsIdList();
		ArrayList<ArrayList<Integer>> itemsList = new ArrayList<ArrayList<Integer>>();
		for (int k = 0; k < hosts.size(); k++) {
			List<Integer> hostidarr = (ArrayList<Integer>) hosts.get(k);
			ItemGetResponse response = new GetItems().getItems(hostidarr);
			ArrayList<Integer> itemsList_pg = new ArrayList<Integer>();

			for (int i = 0; i < response.getResult().size(); i++) {
				ItemObject myItemObject = response.getResult().get(i);
				itemsList_pg.add(myItemObject.getItemid());
			}
//			System.out.println("hostid:" + hosts.get(k) + "itemsid:" + itemsList_pg);
			itemsList.add(itemsList_pg);

		}
//		System.out.println(itemsList);
		return itemsList;
	}

	public static ArrayList<ArrayList<ArrayList<Integer>>> getItemsListByGroup() throws ZabbixApiException, IOException {
		// group by host
		List<?> hostGroupsList = new GetHostGroups().getHostGroupdIdList();
		ArrayList<ArrayList<ArrayList<Integer>>> itemsList = new ArrayList<ArrayList<ArrayList<Integer>>>();
		for (int m = 0; m < hostGroupsList.size(); m++) {
			ArrayList<Integer> hostsList = new GetHosts().gethostsIdList((Integer) hostGroupsList.get(m));
			ArrayList<ArrayList<Integer>> itemsList_pg = new ArrayList<ArrayList<Integer>>();
			for (int k = 0; k < hostsList.size(); k++) {

				ArrayList<Integer> hostidarr = new ArrayList<Integer>();
				hostidarr.add(hostsList.get(k));
				ItemGetResponse response = new GetItems().getItems(hostidarr);
				ArrayList<Integer> itemsList_ph = new ArrayList<Integer>();

				for (int i = 0; i < response.getResult().size(); i++) {
					ItemObject myItemObject = response.getResult().get(i);
					itemsList_ph.add(myItemObject.getItemid());
				}
//				System.out.println("hostid:" + hostsList.get(k) + "itemsid:" + itemsList_ph);
				itemsList_pg.add(itemsList_ph);

			}
			// System.out.println(itemsList_pg);
			itemsList.add(itemsList_pg);
		}
		return itemsList;
	}

//	public static ArrayList<HashMap<String, ArrayList<HashMap<String, ArrayList<?>>>>> getItemsListObjByGroup() throws ZabbixApiException {
//		// group by host
//		List<Integer> hostGroupsList = new GetHostGroups().getHostGroupdIdList();
//		ArrayList<HashMap<String, ArrayList<HashMap<String, ArrayList<?>>>>> itemsListObjByGroup = new ArrayList<HashMap<String, ArrayList<HashMap<String, ArrayList<?>>>>>();
//
//		for (int m = 0; m < hostGroupsList.size(); m++) {
//			ArrayList<Integer> hostsList = GetHosts.gethostsIdList((Integer) hostGroupsList.get(m));
//			ArrayList<HashMap<String, ArrayList<?>>> itemsList_pg = new ArrayList<HashMap<String, ArrayList<?>>>();
//
//			HashMap<String, String> itemsListObj = new HashMap<String, String>();
//			for (int k = 0; k < hostsList.size(); k++) {
//				HashMap<String, ArrayList<?>> itemsObj_pg = new HashMap<String, ArrayList<?>>();
//				ArrayList<Integer> hostidarr = new ArrayList<Integer>();
//				hostidarr.add(hostsList.get(k));
//				ItemGetResponse response = new GetItems().getItems(hostidarr);
//				ArrayList<HashMap<String, String>> itemsList_ph = new ArrayList<HashMap<String, String>>();
//
//				for (int i = 0; i < response.getResult().size(); i++) {
//					ItemObject myItemObject = response.getResult().get(i);
//					HashMap<String, String> itemobj = new HashMap<String, String>();
//					itemobj.put("itemname", myItemObject.getName());
//					itemobj.put("itemid", String.valueOf(myItemObject.getItemid()));
//					itemsList_ph.add(itemobj);
//				}
//				// System.out.println("hostid:" + hostsList.get(k) + " ; items:" +
//				// itemsList_ph);
//				itemsObj_pg.put("itemlist", itemsList_ph);
//				itemsObj_pg.put("hostid", hostidarr);
//				itemsList_pg.add(itemsObj_pg);
//			}
//			// System.out.println(itemsList_pg);
//			// itemsListObj.add(itemsList_pg);
//			itemsListObj.put("groupid", String.valueOf(hostGroupsList.get(m)));
//			itemsListObj.put("itemslistbyhost", itemsList_pg);
//
//			// System.out.println(itemsListObj);
//			itemsListObjByGroup.add(itemsListObj);
//		}
//		System.out.println(itemsListObjByGroup);
//		return itemsListObjByGroup;
//	}

	public static String transKeyofName(String itemName, String key) {
//		String destinStr = "";
		Pattern ptn = Pattern.compile("(?:\\$\\d*)");
		Matcher m = ptn.matcher(itemName);
		StringBuffer sb = new StringBuffer();
		// List <ItemBean> allItemBean= new GetItems().getAllItems();
		while (m.find()) {
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
		List<String> keyarray = new ArrayList<String>();
		keyarray = Arrays.asList(keyarray_s.split(","));
		for (int i = 0; i < keyarray.size(); i++) {
			if (Integer.parseInt(keyId)-1 == i)
				keyName = (String) keyarray.get(i);
		}
		return keyName;
	}
}
