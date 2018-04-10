package com.rmyh.report.zabbix;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PtnTest {
	public static void main(String[] args) {
		String string = "sh{12222245}fhj{120045}>12{12345}3456;";
		Pattern ptn = Pattern.compile("(?:\\{\\d*})"); // 
		System.out.println(ptn);
		
		Matcher m = ptn.matcher(string);
		StringBuffer sb = new StringBuffer();
			while(m.find()) {
				System.out.println(m.group());
				String REPLACE = "0";  //TransId2Name()
				m.appendReplacement(sb,REPLACE);
			}
			
		m.appendTail(sb);
	
		System.out.println(sb.toString());
		
	}

}
