package com.rmyh.report.dao;

import com.zabbix4j.ZabbixApi;
import com.zabbix4j.ZabbixApiException;

public class reportZabbixApi{  

    // 这里配置zabbix的url，帐号和密码  
    public static final String ZBX_URL = "http://127.0.0.1/zabbix/api_jsonrpc.php";  
    public static final String USERNAME = "Admin";  
    public static final String PASSWORD = "abc123"; 
    public static ZabbixApi zabbixApi = new ZabbixApi(ZBX_URL); 
    

    
    public ZabbixApi getApi() {
    	return zabbixApi;
    }
    public void login() throws ZabbixApiException {
    	zabbixApi.login(USERNAME,PASSWORD); 
    }
 
}
  
