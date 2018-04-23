package com.rmyh.report.service.hbase;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.rmyh.report.bean.AlertBean;
import com.rmyh.report.bean.ItemBean;
import com.rmyh.report.bean.TriggerBean;
import com.rmyh.report.bean.XNDataBean;
import com.rmyh.report.controller.General;
import com.rmyh.report.dao.HbaseConnectionFactory;
import com.rmyh.report.service.hbase.HbaseTools;
import com.rmyh.report.service.zabbix.GetItems;
import com.rmyh.report.service.zabbix.GetTriggers;
import com.rmyh.report.service.zabbix.GetAlerts;
import com.rmyh.report.service.zabbix.GetHistoryBeanList;
import com.zabbix4j.ZabbixApiException;

public class DataInsert implements Job {

	//performance data table name 
	public static final String XNTableName = "zabbix_data";
	public static final String ItemsTableName = "zabbix_items";
	public static final String AlertsTableName = "zabbix_alert";
	public static final String TriggerTableName = "zabbix_trigger";
	
	// zookeeper url and port
	public static final String zoourl = "localhost";
	public static final String zooport = "2181";

	//log4j
	Logger logger = Logger.getLogger(DataInsert.class);
	
	// Insert date main function
	public static void dataInsert(long datePre, long dateNex) throws ZabbixApiException {

		// Indentifined performance history data
		GetHistoryBeanList historyBean = new GetHistoryBeanList();
		// get latest item data
		List<ItemBean> itemBeans = new GetItems().getAllItems_App();
		// List<ItemBean> itemBeans = new GetItems().getAllItems();
		// clock for Items saved in hbase with nowtime
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
		String insertClock = format.format(new Date());
		
		// get performance databean (contain host and group information etc.)
		List<XNDataBean> dataBeans = new ArrayList<XNDataBean>();
		for (int j = 0; j < itemBeans.size(); j++) {
			int itemId = itemBeans.get(j).getItemId();
			List<XNDataBean> itemDataBeans = historyBean.getBean(itemBeans, itemId, datePre, dateNex);
			dataBeans.addAll(itemDataBeans);
		}

		// get latest trigger data
		List<TriggerBean> triggerBeans = new GetTriggers().getAllTriggers_App();
		// get latest alert data
		List<AlertBean> alertBeans = new GetAlerts().getBean();
		
		HbaseConnectionFactory.init(zoourl,zooport);
		
		// Insert performace data between datepre and datenex
		HbaseTools hbtdatains = new HbaseTools();
		hbtdatains.putsXNDataBean(dataBeans, XNTableName, datePre, dateNex);
		
		//Insert items at insertclock
		HbaseTools hbtitemins = new HbaseTools();
		hbtitemins.putsItemDataBean(itemBeans, ItemsTableName, insertClock);
//		event data
//		HbaseTools hbteventins = new HbaseTools();
//		hbtitemins.putsDataBean(eventBeans, EventsTableName, insertItemClock);
		
		// Insert trigger at insertclock
		HbaseTools hbttriggerins = new HbaseTools();
		hbttriggerins.putsTriggerDataBean(triggerBeans, TriggerTableName, insertClock);
		
		// Insert alert data between datepre and datenex
		HbaseTools hbtalertins = new HbaseTools();
		hbtalertins.putsAlertDataBean(alertBeans, AlertsTableName, datePre, dateNex);
		
		// logg4j. getTime +""+data insert hbase success
		
		// close hbase connection
		HbaseConnectionFactory.closeConnections("");

	}

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		long dateNow = new Date().getTime();
		long datePre;
		try {
			datePre = Long.parseLong(new General().initProp("datePre.properties").getProperty("datePre"));
			long dateNex = dateNow < (datePre + 2592000000l) ? dateNow : (datePre + 2592000000l);
			dataInsert(datePre, dateNex);
			// logger succ
			logger.info(new Date()+"succ! insert data between "+datePre+" and "+dateNex+"");
		} catch (NumberFormatException e) {
			e.printStackTrace();
			logger.error(e);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e);
		} catch (ZabbixApiException e) {
			e.printStackTrace();
			logger.error(e);
		}

	}



}
