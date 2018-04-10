package com.rmyh.report.zabbix;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.rmyh.report.bean.ItemBean;
import com.rmyh.report.dao.reportZabbixApi;
import com.rmyh.report.service.zabbix.GetHostGroups;
import com.rmyh.report.service.zabbix.GetHosts;
import com.zabbix4j.ZabbixApiException;
import com.zabbix4j.item.ItemGetRequest;
import com.zabbix4j.item.ItemGetResponse;
import com.zabbix4j.item.ItemObject;

public class GetItemstest {

	public static void main(String[] args) throws ZabbixApiException {
		ArrayList hostidarr = new ArrayList();
		hostidarr.add(10084);
		ItemGetResponse response = getItems(hostidarr);
		List itemsList = new ArrayList();

		for (int i = 0; i < response.getResult().size(); i++) {
			ItemObject myItemObject = response.getResult().get(i);
			itemsList.add(myItemObject);
			System.out.println("descri:"+myItemObject.getDescription()+
					"delay:"+myItemObject.getDelay_flex()+
					"error:"+myItemObject.getError()+
					"ipmisensor:"+myItemObject.getIpmi_sensor()+
					"key:"+myItemObject.getKey_()+
					"lastvalue:"+myItemObject.getLastvalue()+
					"longtimefmt:"+myItemObject.getLogtimefmt()+
					"name:"+myItemObject.getName()+
					"params:"+myItemObject.getParams()+
					"password:"+myItemObject.getPassword()+
					"port:"+myItemObject.getPort()+
					"prevvvalue:"+myItemObject.getPrevvalue()+
					"preorgvalue:"+myItemObject.getPrevorgvalue()+
					"prikey:"+myItemObject.getPrivatekey()+
					"publickey:"+myItemObject.getPublickey()+
					"community:"+myItemObject.getSnmp_community()+
					"snmpoid:"+myItemObject.getSnmp_oid()+
					"authpassphraase:"+myItemObject.getSnmpv3_authpassphrase()+
					"privpassphrase:"+myItemObject.getSnmpv3_privpassphrase()+
					"tmpid:"+myItemObject.getTemplateid()+
					"securitylevel:"+myItemObject.getSnmpv3_securitylevel()+
					"securityname:"+myItemObject.getSnmpv3_securityname()+
					"templateid:"+myItemObject.getTemplateid()+
					"trapperhosts:"+myItemObject.getTrapper_hosts()+
					"units:"+myItemObject.getUnits()+
					"username:"+myItemObject.getUsername()+
					"valuemapid:"+myItemObject.getValuemapid()+
					"authtype:"+myItemObject.getAuthtype()+
					"datatype:"+myItemObject.getData_type()+
					"delay:"+myItemObject.getDelay()+
					"delta:"+myItemObject.getDelta()+
					"flags:"+myItemObject.getFlags()+
					"history:"+myItemObject.getHistory()+
					"formula:"+myItemObject.getFormula()+
					"histid:"+myItemObject.getHostid()+
					"interfaceid:"+myItemObject.getInterfaceid()+
					"inventory:"+myItemObject.getInventory_link()+
					"itemid"+myItemObject.getItemid()+
					"lastclock"+myItemObject.getLastclock()+
					"lastns"+myItemObject.getLastns()+
					"lifetime"+myItemObject.getLifetime()+
					"mtime"+myItemObject.getMtime()+
					"multiplier"+myItemObject.getMultiplier()+
					"status"+myItemObject.getStatus()+
					"trends:"+myItemObject.getTrends()+
					"type"+myItemObject.getType()+
					"valuetype"+myItemObject.getValue_type());		}
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

}
