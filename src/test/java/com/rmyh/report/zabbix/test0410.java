package com.rmyh.report.zabbix;

import java.util.ArrayList;
import java.util.List;

public class test0410 {
	public static void main(String[] args) {
		printSize(null);
	}
	public static int printSize(List list) {
//		list = new ArrayList();
		System.out.println(list.size());
		return list.size();
	}

}
