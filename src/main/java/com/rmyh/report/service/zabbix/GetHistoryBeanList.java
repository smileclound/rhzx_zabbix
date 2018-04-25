package com.rmyh.report.service.zabbix;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.rmyh.report.bean.ItemBean;
import com.rmyh.report.bean.XNDataBean;
//import com.rmyh.report.bean.XNDataBeanInterface;
import com.zabbix4j.ZabbixApiException;
import com.zabbix4j.history.HistoryGetResponse;
import com.zabbix4j.history.HistoryObject;

public class GetHistoryBeanList extends ItemBean {

//	public static int itemId;
	
//	public static void main(String[] args) throws ZabbixApiException {
//	List<ItemBean> itemBeans = new GetItems().getAllItems_App();
//		getBean(itemBeans,23294,1407776000000l,1530368000000l);
//	}
//	
	public List<XNDataBean> getBean(List<ItemBean> itemBeans, int itemId, long datePre,long dateNex) throws ZabbixApiException, IOException {
		HistoryGetResponse historyObj = new GetHistoryResponse().getHistory(itemId, datePre, dateNex);
		List<XNDataBean> beans = new ArrayList<XNDataBean>();
//		List<ItemBean> itemBeans = new GetItems().getAllItems();
		ItemBean itemBean = new ItemBean();
		for(int j=0;j<itemBeans.size();j++) {
			if(itemBeans.get(j).getItemId() == itemId) {
				itemBean = itemBeans.get(j);
				break;
			}
		}
		
		for(int i=0;i<historyObj.getResult().size();i++) {
		XNDataBean bean = new XNDataBean();
		HistoryObject sigobj = historyObj.getResult().get(i); 
		bean.setClock(sigobj.getClock().toString());
		bean.setValue(sigobj.getValue());
		bean.setGroupId(itemBean.getGroupId());
		bean.setGroupName(itemBean.getGroupName());
		bean.setHostId(itemBean.getHostId());
		bean.setHostName(itemBean.getHostName());
		bean.setItemId(itemBean.getItemId());
		bean.setItemName(itemBean.getItemName());
//		bean.setMonitorobj(sigobj.getText());
		bean.setApplicationName(itemBean.getApplicationName());
		bean.setApplicationId(itemBean.getApplicationId());
		beans.add(bean);
		}
//		System.out.println(beans);

		return beans;
		
	}

}
