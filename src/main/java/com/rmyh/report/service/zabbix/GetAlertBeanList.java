package com.rmyh.report.service.zabbix;


import java.util.ArrayList;
import java.util.List;

import com.rmyh.report.bean.AlertBean;
import com.zabbix4j.ZabbixApiException;
import com.zabbix4j.alert.AlertGetResponse;
import com.zabbix4j.alert.AlertObject;

public class GetAlertBeanList {

//	public static int itemId;
	
	public static void main(String[] args) throws ZabbixApiException {
		getBean();
	}
	
	public static List<AlertBean> getBean() throws ZabbixApiException {
		AlertGetResponse AlertObj = new GetAlertResponse().getAlert();
		List beans = new ArrayList();

		
		for(int i=0;i<AlertObj.getResult().size();i++) {
		AlertBean bean = new AlertBean();
		AlertObject sigobj = AlertObj.getResult().get(i); 
		bean.setClock(sigobj.getClock().toString());
		bean.setAlertText(sigobj.getMessage());
		bean.setAlertLevel(sigobj.getAlerttype().toString());
		

		beans.add(bean);
		}
		System.out.println(beans);

		return beans;
		
	}

}

