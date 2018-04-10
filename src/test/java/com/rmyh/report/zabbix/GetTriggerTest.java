package com.rmyh.report.zabbix;

import java.util.ArrayList;
import java.util.List;

import com.rmyh.report.dao.reportZabbixApi;
import com.zabbix4j.ZabbixApiException;
import com.zabbix4j.trigger.TriggerGetRequest;
import com.zabbix4j.trigger.TriggerGetResponse;
import com.zabbix4j.trigger.TriggerGetResponse.Result;

public class GetTriggerTest {
	public static void main(String[] args) throws ZabbixApiException {
		getTriggersObjList_ByApp();
		
	}
	public static List<Result> getTriggersObjList_ByApp() throws ZabbixApiException {
		reportZabbixApi zabbixApi = new reportZabbixApi();
		zabbixApi.login();

		TriggerGetRequest request = new TriggerGetRequest();
		TriggerGetRequest.Params params = request.getParams();
		params.setSelectFunctions("extend");
		params.setOutput("extend");
		ArrayList<Integer> triggeridssss = new ArrayList();
		triggeridssss.add(13570);
		params.setTriggerids(triggeridssss);
		TriggerGetResponse response = zabbixApi.getApi().trigger().get(request);
		
		List<Result> triggersList = new ArrayList();

		for (int i = 0; i < response.getResult().size(); i++) {
			Result myTriggerObject = response.getResult().get(i);
			System.out.println(myTriggerObject.getFunctions().get(0).getFunctionid());
			triggersList.add(myTriggerObject);
		}
		// System.out.println(hostid + "'s triggers: " + triggersList);
		return triggersList;

	}

}
