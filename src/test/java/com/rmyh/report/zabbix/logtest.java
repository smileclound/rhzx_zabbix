package com.rmyh.report.zabbix;

import org.apache.log4j.Logger;

public class logtest {
	public static void main(String[] args) {
		Logger log = Logger.getLogger(logtest.class);
		System.out.println(log);
		System.out.println(new logtest().getClass()+""+logtest.class);
	}

}
