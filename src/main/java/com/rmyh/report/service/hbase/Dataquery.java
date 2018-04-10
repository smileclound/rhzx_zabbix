package com.rmyh.report.service.hbase;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.rmyh.report.bean.ItemBean;
import com.rmyh.report.bean.XNDataBean;
import com.rmyh.report.dao.HbaseConnectionFactory;
import com.rmyh.report.service.zabbix.GetItems;
import com.rmyh.report.service.zabbix.GetHistoryBeanList;
import com.zabbix4j.ZabbixApiException;

public class Dataquery {
	public static final String hbaseTableName = "zabbix";
	public static final String url = "127.0.0.1";

	public static void main(String[] args) throws ZabbixApiException, ParseException {
		List<Integer> list = new ArrayList();
		list.add(23720);list.add(23700);
		getByKeyWTime(0,9531119873l,list);
	}

	public static XNDataBean getByKey(String key) throws ZabbixApiException {
			GetHistoryBeanList historyBean = new GetHistoryBeanList();
			List<ItemBean> itemBeans = new GetItems().getAllItems();
			HbaseConnectionFactory.init(url);
			HbaseTools hbtquery = new HbaseTools();
			XNDataBean dataqueryresult = hbtquery.getValueByTransDetails(hbaseTableName, key);
			System.out.println(dataqueryresult);
			return dataqueryresult;
			// logg4j. getTime +""+data insert hbase success
		}
	
	public static List<XNDataBean> getByKeyWTime(long startTime, long stopTime, List<Integer> itemIdList) throws ZabbixApiException, ParseException {
		GetHistoryBeanList historyBean = new GetHistoryBeanList();
		List<ItemBean> itemBeans = new GetItems().getAllItems_App();
		HbaseConnectionFactory.init(url);
		HbaseTools hbtquery = new HbaseTools();
		List<XNDataBean> result = new ArrayList();
		for(int itemId:itemIdList) {
		XNDataBean dataqueryresult = hbtquery.getValueByTime(hbaseTableName,startTime, stopTime, itemId);
		result.add(dataqueryresult); }
		System.out.println(result);
		return result;
		// logg4j. getTime +""+data insert hbase success
	}
	

}
