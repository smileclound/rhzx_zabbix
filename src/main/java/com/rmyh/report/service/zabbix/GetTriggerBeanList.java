package com.rmyh.report.service.zabbix;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.rmyh.report.bean.ItemBean;
import com.rmyh.report.bean.TriggerBean;
import com.zabbix4j.ZabbixApiException;
import com.zabbix4j.trigger.TriggerGetResponse;
import com.zabbix4j.trigger.TriggerObject;

public class GetTriggerBeanList {

	// public static int itemId;

	public static void main(String[] args) throws ZabbixApiException {
		getBean();
	}

	public static List<TriggerBean> getBean() throws ZabbixApiException {
		TriggerGetResponse triggerObj = new GetTriggerResponse().getTrigger();
		// TriggerPrototypeGetResponse triggerObj = new
		// GetTriggerResponse().getProtoTrigger();
		List<TriggerBean> beans = new ArrayList<TriggerBean>();
		List<ItemBean> allItemBean = new GetItems().getAllItems();
		for (int i = 0; i < triggerObj.getResult().size(); i++) {
			TriggerBean bean = new TriggerBean();
			TriggerObject sigobj = triggerObj.getResult().get(i);
			// TriggerPrototypeObject sigobj = triggerObj.getResult().get(i);
			bean.setTriggerText(transExpression(allItemBean, sigobj.getExpression()));
			// bean.setTriggerText(sigobj.getExpression());
			bean.setTriggerId(sigobj.getTriggerid());
			beans.add(bean);
		}
//		System.out.println(beans);

		return beans;

	}

	public static String transExpression(List<ItemBean> allItemBean, String string)
			throws NumberFormatException, ZabbixApiException {
		// String destinStr = string;
		Pattern ptn = Pattern.compile("(?:\\{\\d*})");
		Matcher m = ptn.matcher(string);
		StringBuffer sb = new StringBuffer();
		// List <ItemBean> allItemBean= new GetItems().getAllItems();
		while (m.find()) {
			String Replace = transItemId2Name(allItemBean,
					Integer.parseInt(m.group().substring(m.group().indexOf("{") + 1, m.group().indexOf("}"))));
			m.appendReplacement(sb, Replace);

		}
		m.appendTail(sb);
		return sb.toString();
		// return string;
	}

	public static String transItemId2Name(List<ItemBean> allItemBean, int id) throws ZabbixApiException {
		// String destinStr = new String();

		for (int i = 0; i < allItemBean.size(); i++) {
			if (allItemBean.get(i).getItemId() == id) {
				return "{" + allItemBean.get(i).getItemName() + "}";
			}
		}
		return "" + id;
	}

	public static String transItemId2Name(String string) {
		return string;
	}

}
