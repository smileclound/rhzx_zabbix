package com.rmyh.report;

import java.io.IOException;
import java.util.Properties;

import com.rmyh.report.controller.General;

public class proptest {
	public static void main(String[] args) throws IOException {
		Properties prop = General.initProp("datePre.properties");
		System.out.println(prop.getProperty("datePre"));
		prop.setProperty("datePre", "123456");
		System.out.println(prop.getProperty("datePre"));

	}

}
