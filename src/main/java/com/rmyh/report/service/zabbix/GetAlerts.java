package com.rmyh.report.service.zabbix;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import com.rmyh.report.bean.AlertBean;
import com.zabbix4j.ZabbixApiException;
import com.zabbix4j.alert.AlertGetResponse;
import com.zabbix4j.alert.AlertObject;

public class GetAlerts {

	// public static int itemId;

//	public static void main(String[] args) throws ZabbixApiException {
//		getCountBean();
//	}

	// for zabbixweb
	public static List<AlertBean> getCountBean() throws ZabbixApiException {
		List<AlertBean> alertBeans = new GetAlerts().getBean();
		List<AlertBean> alertCountBeans = new ArrayList<AlertBean>();
		OUT: for (AlertBean alertbean : alertBeans) {
			for (AlertBean countbean : alertCountBeans) {
				if (alertbean.getHostId() == countbean.getHostId()
						&& alertbean.getAlertTitle().equals(countbean.getAlertTitle())) {
					countbean.setAlertTimes((Integer) (countbean.getALertTimes() + 1));
					if (Long.parseLong(alertbean.getClock()) > Long.parseLong(countbean.getClock())) {
						countbean.setConfirmstatus(alertbean.getConfirmstatus());
						countbean.setClock(alertbean.getClock());
					}
					continue OUT;
				}
			}
			alertCountBeans.add(alertbean);
		}
		System.out.println(alertCountBeans);
		return alertCountBeans;
	}

	public List<AlertBean> getBean() throws ZabbixApiException {
		List<AlertBean> alertBeans = new ArrayList<AlertBean>();
		List<HashMap<String, String>> hostGroupList = new GetHostGroups().getHostGroupsObjList();
		for (int i = 0; i < hostGroupList.size(); i++) {
			int groupId = Integer.parseInt(hostGroupList.get(i).get("groupId"));
//			String groupName = (String) hostGroupList.get(i).get("groupName");
			List<HashMap<String, String>> hostList = new GetHosts().gethostsObjList(groupId);
			for (int j = 0; j < hostList.size(); j++) {
				int hostId = Integer.parseInt(hostList.get(j).get("hostId"));
				String hostName = (String) hostList.get(j).get("hostName");
				String hostIp = (String) hostList.get(j).get("hostIp");

				AlertGetResponse AlertObj = new GetAlertResponse().getAlert(hostId);
				List<AlertBean> beans = new ArrayList<AlertBean>();

				for (int k = 0; k < AlertObj.getResult().size(); k++) {
					AlertBean bean = new AlertBean();
					AlertObject sigobj = AlertObj.getResult().get(k);
					bean.setClock(sigobj.getClock().toString());
					bean.setAlertLevel(sigobj.getAlerttype());
					bean.setAlertTitle(sigobj.getSubject());
					bean.setAlertText(sigobj.getMessage());
					bean.setAlertId(sigobj.getAlertid());
					bean.setHostId(hostId);
					bean.setHostIp(hostIp);
					bean.setHostName(hostName);
					bean.setActionId(sigobj.getActionid());
					bean.setConfirmstatus(sigobj.getStatus());
					bean.setSendto(sigobj.getSendto());
					// bean.setRetries(sigobj.getRetries());
					// String message = sigobj.getMessage();
					// String trigger = "", triggerstatus = "", triggerseverity = "";
					// Matcher m1 = Pattern.compile("(.*)(Trigger:)(.*)").matcher(message);
					// Matcher m2 = Pattern.compile("(.*)(Trigger status:)(.*)").matcher(message);
					// Matcher m3 = Pattern.compile("(.*)(Trigger severity:)(.*)").matcher(message);
					// if (m1.find()) {
					// trigger = m1.group(3);
					// }
					// if (m2.find()) {
					// triggerstatus = m2.group(3);
					// }
					// if (m3.find()) {
					// triggerseverity = m3.group(3);
					// }
					// bean.setStatus(triggerstatus);
					// bean.setConfirmstatus(sigobj.getStatus());
					// bean.setTriggerName(trigger);

					beans.add(bean);
				}
				alertBeans.addAll(beans);
			}
		}
		// System.out.println(alertBeans);

		return alertBeans;

	}
}
