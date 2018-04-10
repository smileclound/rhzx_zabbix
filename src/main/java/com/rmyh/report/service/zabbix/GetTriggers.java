package com.rmyh.report.service.zabbix;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.rmyh.report.bean.ItemBean;
import com.rmyh.report.bean.TriggerBean;
import com.rmyh.report.dao.reportZabbixApi;
import com.zabbix4j.ZabbixApiException;
import com.zabbix4j.application.ApplicationGetRequest;
import com.zabbix4j.application.ApplicationGetResponse;
import com.zabbix4j.application.ApplicationObject;
import com.zabbix4j.trigger.TriggerGetRequest;
import com.zabbix4j.trigger.TriggerGetResponse;
import com.zabbix4j.trigger.TriggerGetResponse.Result;
import com.zabbix4j.trigger.TriggerObject;
import com.zabbix4j.trigger.FunctionObject;

public class GetTriggers {

//	public static void main(String[] args) throws ZabbixApiException {
//		getAllTriggers_App();
//	}

	public static List<TriggerBean> getAllTriggers() throws ZabbixApiException {
		List<TriggerBean> triggerBeans = new ArrayList();
		List<HashMap> hostGroupList = new GetHostGroups().getHostGroupsObjList();
		for (int i = 0; i < hostGroupList.size(); i++) {
			int groupId = (Integer) hostGroupList.get(i).get("groupId");
			String groupName = (String) hostGroupList.get(i).get("groupName");
			List<HashMap> hostList = new GetHosts().gethostsObjList(groupId);
			for (int j = 0; j < hostList.size(); j++) {
				int hostId = (Integer) hostList.get(j).get("hostId");
				String hostName = (String) hostList.get(j).get("hostName");
				List<TriggerObject> triggerList = getTriggersObjList(hostId);

				for (int k = 0; k < triggerList.size(); k++) {

					int triggerId = (Integer) triggerList.get(k).getTriggerid();
					int triggerValue = triggerList.get(k).getValue();
					TriggerBean triggerBean = new TriggerBean();
					triggerBean.setGroupId(groupId);
					triggerBean.setGroupName(groupName);
					triggerBean.setHostId(hostId);
					triggerBean.setHostName(hostName);
					triggerBean.setTriggerId(triggerId);
					triggerBean.setTriggerValue(triggerValue);
					triggerBeans.add(triggerBean);
				}

			}
		}
		System.out.println(triggerBeans);
		return triggerBeans;

	}

	public static List<TriggerBean> getAllTriggers_App() throws ZabbixApiException {
		List <ItemBean> allItemBean= new GetItems().getAllItems_App(); 
		List<TriggerBean> triggerBeans = new ArrayList();
		List<HashMap> hostGroupList = new GetHostGroups().getHostGroupsObjList();
		for (int i = 0; i < hostGroupList.size(); i++) {
			int groupId = (Integer) hostGroupList.get(i).get("groupId");
			String groupName = (String) hostGroupList.get(i).get("groupName");
			List<HashMap> hostList = new GetHosts().gethostsObjList(groupId);
			for (int j = 0; j < hostList.size(); j++) {
				int hostId = (Integer) hostList.get(j).get("hostId");
				String hostName = (String) hostList.get(j).get("hostName");
				List<HashMap> applicationList = getApplicationObjList(hostId);
				for (int l = 0; l < applicationList.size(); l++) {
					int applicationId = (Integer) applicationList.get(l).get("applicationId");
					String applicationName = (String) applicationList.get(l).get("applicationName");
					List<Result> triggerList = getTriggersObjList_ByApp(applicationId);

					for (int k = 0; k < triggerList.size(); k++) {
						TriggerBean triggerBean = new TriggerBean();
						triggerBean.setGroupId(groupId);
						triggerBean.setGroupName(groupName);
						triggerBean.setHostId(hostId);
						triggerBean.setHostName(hostName);
						triggerBean.setTriggerId((Integer) triggerList.get(k).getTriggerid());
						triggerBean.setTriggerValue(triggerList.get(k).getValue());
						List<FunctionObject> functionObject = new ArrayList();
						functionObject = triggerList.get(k).getFunctions();
						for (FunctionObject funcobj : functionObject) {
								triggerBean.setTriggerText(transExpression(allItemBean,funcobj,triggerList.get(k).getExpression()));
						}
						triggerBean.setTriggerDescri(triggerList.get(k).getDescription());
						triggerBean.setApplicationId(applicationId);
						triggerBean.setApplicationName(applicationName);
						triggerBeans.add(triggerBean);
					}
				}
			}
		}
		System.out.println(triggerBeans);
		return triggerBeans;

	}

	public static List getApplicationObjList(int hostId) throws ZabbixApiException {
		reportZabbixApi zabbixApi = new reportZabbixApi();
		ArrayList<Integer> triggerids = new ArrayList<Integer>();
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

	public static List<Result> getTriggersObjList_ByApp(int appliId) throws ZabbixApiException {
		reportZabbixApi zabbixApi = new reportZabbixApi();
		zabbixApi.login();

		TriggerGetRequest request = new TriggerGetRequest();
		TriggerGetRequest.Params params = request.getParams();
		ArrayList appliIdArr = new ArrayList();
		appliIdArr.add(appliId);
		params.setApplicationids(appliIdArr);
		params.setOutput("extend");
		params.setSelectFunctions("extend");

		request.setJsonrpc("2.0");
		request.setMethod("trigger.get");
		request.setParams(params);

		// ArrayList<Integer> triggeridssss = new ArrayList();
		// triggeridssss.add(13570);
		// params.setTriggerids(triggeridssss);
		TriggerGetResponse response = zabbixApi.getApi().trigger().get(request);

		List<Result> triggersList = new ArrayList();

		for (int i = 0; i < response.getResult().size(); i++) {
			Result myTriggerObject = response.getResult().get(i);
			triggersList.add(myTriggerObject);
		}
		// System.out.println(hostid + "'s triggers: " + triggersList);
		return triggersList;

	}

	public TriggerGetResponse getTriggers(int hostid) throws ZabbixApiException {
		reportZabbixApi zabbixApi = new reportZabbixApi();
		zabbixApi.login();

		TriggerGetRequest request = new TriggerGetRequest();
		TriggerGetRequest.Params params = request.getParams();
		ArrayList hostids = new ArrayList();
		hostids.add(hostid);
		params.setHostids(hostids);

		TriggerGetResponse response = zabbixApi.getApi().trigger().get(request);
		return response;
	}

	public static TriggerGetResponse getTriggers(ArrayList hostsidarr) throws ZabbixApiException {
		reportZabbixApi zabbixApi = new reportZabbixApi();
		zabbixApi.login();

		TriggerGetRequest request = new TriggerGetRequest();
		TriggerGetRequest.Params params = request.getParams();
		params.setHostids(hostsidarr);

		TriggerGetResponse response = zabbixApi.getApi().trigger().get(request);
		return response;
	}

	public static List getTriggersObjList(int hostid) throws ZabbixApiException {

		ArrayList hostidarr = new ArrayList();
		hostidarr.add(hostid);
		TriggerGetResponse response = getTriggers(hostidarr);
		ArrayList triggersList = new ArrayList();

		for (int i = 0; i < response.getResult().size(); i++) {
			TriggerObject myTriggerObject = response.getResult().get(i);
			triggersList.add(myTriggerObject);
		}
		// System.out.println(hostid + "'s triggers: " + triggersList);
		return triggersList;

	}

	public static ArrayList getTriggersList() throws ZabbixApiException {
		// // group by host
		// ArrayList hostGroups = new reportGetHostGroups().getHostGroupdIdList();
		// ArrayList triggersList = new ArrayList();
		// for (int k=0;k<hostGroups.size(); k++) {
		// ArrayList hostidarr = (ArrayList) reportGetHosts.gethostsIdList((int)
		// hostGroups.get(k)) ;
		// TriggerGetResponse response = getTriggers(hostidarr);
		// ArrayList triggersList_pg = new ArrayList();

		List hosts = new GetHosts().gethostsIdList();
		ArrayList triggersList = new ArrayList();
		for (int k = 0; k < hosts.size(); k++) {
			ArrayList hostidarr = (ArrayList) hosts.get(k);
			TriggerGetResponse response = getTriggers(hostidarr);
			ArrayList triggersList_pg = new ArrayList();

			for (int i = 0; i < response.getResult().size(); i++) {
				TriggerObject myTriggerObject = response.getResult().get(i);
				triggersList_pg.add(myTriggerObject.getTriggerid());
			}
			System.out.println("hostid:" + hosts.get(k) + "triggersid:" + triggersList_pg);
			triggersList.add(triggersList_pg);

		}
		System.out.println(triggersList);
		return triggersList;
	}

	public static ArrayList getTriggersListByGroup() throws ZabbixApiException {
		// group by host
		List hostGroupsList = new GetHostGroups().getHostGroupdIdList();
		ArrayList triggersList = new ArrayList();
		for (int m = 0; m < hostGroupsList.size(); m++) {
			ArrayList hostsList = GetHosts.gethostsIdList((Integer) hostGroupsList.get(m));
			ArrayList triggersList_pg = new ArrayList();
			for (int k = 0; k < hostsList.size(); k++) {

				ArrayList hostidarr = new ArrayList();
				hostidarr.add(hostsList.get(k));
				TriggerGetResponse response = getTriggers(hostidarr);
				ArrayList triggersList_ph = new ArrayList();

				for (int i = 0; i < response.getResult().size(); i++) {
					TriggerObject myTriggerObject = response.getResult().get(i);
					triggersList_ph.add(myTriggerObject.getTriggerid());
				}
				System.out.println("hostid:" + hostsList.get(k) + "triggersid:" + triggersList_ph);
				triggersList_pg.add(triggersList_ph);

			}
			// System.out.println(triggersList_pg);
			triggersList.add(triggersList_pg);
		}
		return triggersList;
	}

	public static ArrayList getTriggersListObjByGroup() throws ZabbixApiException {
		// group by host
		List hostGroupsList = new GetHostGroups().getHostGroupdIdList();
		ArrayList triggersListObjByGroup = new ArrayList();

		for (int m = 0; m < hostGroupsList.size(); m++) {
			ArrayList hostsList = GetHosts.gethostsIdList((Integer) hostGroupsList.get(m));
			ArrayList triggersList_pg = new ArrayList();

			HashMap triggersListObj = new HashMap();
			for (int k = 0; k < hostsList.size(); k++) {
				HashMap triggersObj_pg = new HashMap();
				ArrayList hostidarr = new ArrayList();
				hostidarr.add(hostsList.get(k));
				TriggerGetResponse response = getTriggers(hostidarr);
				ArrayList triggersList_ph = new ArrayList();

				for (int i = 0; i < response.getResult().size(); i++) {
					TriggerObject myTriggerObject = response.getResult().get(i);
					HashMap triggerobj = new HashMap();
					// triggerobj.put("triggername", myTriggerObject.getName());
					triggerobj.put("triggerid", myTriggerObject.getTriggerid());
					triggersList_ph.add(triggerobj);
				}
				// System.out.println("hostid:" + hostsList.get(k) + " ; triggers:" +
				// triggersList_ph);
				triggersObj_pg.put("triggerlist", triggersList_ph);
				triggersObj_pg.put("hostid", hostidarr);
				triggersList_pg.add(triggersObj_pg);
			}
			// System.out.println(triggersList_pg);
			// triggersListObj.add(triggersList_pg);
			triggersListObj.put("groupid", hostGroupsList.get(m));
			triggersListObj.put("triggerslistbyhost", triggersList_pg);

			// System.out.println(triggersListObj);
			triggersListObjByGroup.add(triggersListObj);
		}
		System.out.println(triggersListObjByGroup);
		return triggersListObjByGroup;
	}

	public static String transExpression(List <ItemBean> allItemBean,FunctionObject funcobj, String string)
			throws NumberFormatException, ZabbixApiException {
		String destinStr = string;
		Pattern ptn = Pattern.compile("(?:\\{\\d*})");
		Matcher m = ptn.matcher(string);
		StringBuffer sb = new StringBuffer();
		// List <ItemBean> allItemBean= new GetItems().getAllItems();
		for (int i = 0; m.find(); i++) {
			if (true) {
				String Replace = transFunctionId2Name(allItemBean, funcobj,
						Integer.parseInt(m.group().substring(m.group().indexOf("{") + 1, m.group().indexOf("}"))));
				m.appendReplacement(sb, Replace);
			} else {
				continue;
			}

		}
		m.appendTail(sb);
		return sb.toString();
		// return string;
	}

	public static String transFunctionId2Name(List <ItemBean> allItemBean, FunctionObject funcobj , int id) throws ZabbixApiException {
		String destinStr = new String();
		String hostName = "", itemName = "", itemKey = "";
		for(ItemBean bean:allItemBean) {
			if(bean.getItemId() == funcobj.getItemid()) {
				hostName = bean.getHostName(); itemName = bean.getItemName(); itemKey = bean.getItemKey();
			}
		}

			if (funcobj.getFunctionid() == id) {
				return "{" + hostName+":"+itemKey+"."+funcobj.getFunction()+"("+funcobj.getParameter()+")" + "}";
			}
		return "" + id;
	}

	public static String transFunctionId2Name(String string) {
		return string;
	}

}