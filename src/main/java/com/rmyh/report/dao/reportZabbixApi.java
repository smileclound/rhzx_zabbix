package com.rmyh.report.dao;

import java.io.IOException;
//import java.util.Properties;

import com.rmyh.report.controller.General;
import com.zabbix4j.ZabbixApi;
import com.zabbix4j.ZabbixApiException;

public class reportZabbixApi {

	// 这里配置zabbix的url，帐号和密码
	// public static final String ZBX_URL =
	// "http://127.0.0.1/zabbix/api_jsonrpc.php";
	// public static final String USERNAME = "Admin";
	// public static final String PASSWORD = "abc123";
	// Properties prop = General.initProp("prop.properties");
	public static final String ZBX_URL = "url";
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";

//	public static ZabbixApi zabbixApi = new ZabbixApi(ZBX_URL);

	public static ZabbixApi getApi() throws IOException {
		String ZBX_URL = General.initProp("prop.properties").getProperty("zabbixUrl");
		return new ZabbixApi(ZBX_URL);
	}

	public static void login() throws ZabbixApiException, IOException {
		String USERNAME = General.initProp("prop.properties").getProperty("zabbixUsername");
		String PASSWORD = General.initProp("prop.properties").getProperty("zabbixPassword");
		getApi().login(USERNAME, PASSWORD);
	}

}
