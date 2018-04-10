package com.rmyh.report.service.zabbix;

import java.util.ArrayList;
import java.util.List;

import com.rmyh.report.bean.AlertBean;
import com.zabbix4j.ZabbixApiException;
import com.zabbix4j.event.EventGetResponse;
import com.zabbix4j.event.EventObject;

public class GetEventBeanList {

	// public static int itemId;

	public static void main(String[] args) throws ZabbixApiException {
		getBean();
	}

	public static List<AlertBean> getBean() throws ZabbixApiException {
		EventGetResponse EventObj = new GetEventResponse().getEvent();
		List beans = new ArrayList();

		for (int i = 0; i < EventObj.getResult().size(); i++) {
			AlertBean bean = new AlertBean();
			EventObject sigobj = EventObj.getResult().get(i);
			bean.setClock(sigobj.getClockDate().toString());
//			bean.setAlertText(sigobj.);
//			bean.setAlertLevel(sigobj.getAlerttype().toString());

			beans.add(bean);
		}
		System.out.println(beans);

		return beans;

	}

}
