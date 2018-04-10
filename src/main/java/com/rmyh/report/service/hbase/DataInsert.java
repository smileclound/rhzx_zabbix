package com.rmyh.report.service.hbase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.rmyh.report.bean.ItemBean;
import com.rmyh.report.bean.XNDataBean;
import com.rmyh.report.dao.HbaseConnectionFactory;
import com.rmyh.report.service.hbase.HbaseTools;
import com.rmyh.report.service.zabbix.GetItems;
import com.rmyh.report.service.zabbix.GetHistoryBeanList;
import com.zabbix4j.ZabbixApiException;

public class DataInsert  implements Job{

	public static final String XNTableName = "zabbix_data";
	public static final String ItemsTableName = "zabbix_items";
	public static final String EveentsTableName = "zabbix_event";
	public static final String url = "localhost";
//	public static final String dateprourl = "";

	public static void dataInsert(long datePre,long dateNex) throws ZabbixApiException {

		GetHistoryBeanList historyBean = new GetHistoryBeanList();
		List<ItemBean> itemBeans = new GetItems().getAllItems_App();
		// List<ItemBean> itemBeans = new GetItems().getAllItems();
		// clock for Items saved in hbase with nowtime
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm"); 
		String insertItemClock = format.format(new Date());
				List dataBeans = new ArrayList();
		for (int j = 0; j < itemBeans.size(); j++) {

			int itemId = itemBeans.get(j).getItemId();
			System.out.println(datePre+""+dateNex);
			List<XNDataBean> itemDataBeans = historyBean.getBean(itemBeans, itemId, datePre, dateNex);
			// System.out.println(dataBeans);
			dataBeans.addAll(itemDataBeans);
		}
			HbaseConnectionFactory.init(url);
			HbaseTools hbtdatains = new HbaseTools();
			hbtdatains.putsDataBean(dataBeans, XNTableName, datePre, dateNex);
			HbaseTools hbtitemins = new HbaseTools();
			hbtitemins.putsDataBean(itemBeans, ItemsTableName, insertItemClock);
			// logg4j. getTime +""+data insert hbase success
			HbaseConnectionFactory.closeConnections("");

		
	}

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
//		try {
//			dataInsert();
//		} catch (ZabbixApiException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		long dateNow = new Date().getTime();
		long datePre;
		try {
			datePre = Long.parseLong( initProp().getProperty("datePre"));
			long dateNex = dateNow<(datePre+2592000000l)?dateNow:(datePre+2592000000l);
			dataInsert(datePre,dateNex);
//			move to HbaseTools , makesure wtirelog after insertdate successfully;
//			writeDateLog(datePre,dateNex);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ZabbixApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		
	}
	
	public Properties initProp() throws IOException {
		Properties prop = new Properties();
		String property = System.getProperty("user.dir");
		File file = new File(property+"/datePre.properties");
		InputStream input = new FileInputStream(file);
		//    InputStreamReader inputR = new InputStreamReader(input,"utf-8");
		prop.load(input);
		return prop;
	}
	
	public void writeDateLog(long datePre, long dateNex) {
		FileWriter fwriterlog = null;
		FileWriter fwriterdate = null;
		String property = System.getProperty("user.dir");
		File filelog = new File(property+"/dateFns.log");
		File filedate = new File(property+"/datePre.properties");
		try {
			fwriterlog = new FileWriter(filelog,true);
			fwriterdate = new FileWriter(filedate);
			String contentlog = "\n"+"|finished insert between _"+datePre+" and _"+dateNex;
			String contentdate = "datePre="+String.valueOf(dateNex);
			fwriterlog.write(contentlog);
			fwriterdate.write(contentdate);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				fwriterlog.flush();fwriterdate.flush();
				fwriterlog.close();fwriterdate.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}
