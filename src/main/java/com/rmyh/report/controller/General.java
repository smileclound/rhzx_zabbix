package com.rmyh.report.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

//import org.apache.log4j.Logger;

public class General {

	// public static void main(String[] args) {
	//// System.out.println(isInteger("9"));
	// }

	public static Properties initProp(String proname) throws IOException {
		Properties prop = new Properties();
		String property = System.getProperty("user.dir");
		File file = new File(property + "/" + proname);
		InputStream input = new FileInputStream(file);
		// InputStreamReader inputR = new InputStreamReader(input,"utf-8");
		prop.load(input);
		return prop;
	}
	
	public static String getProperties(String profilename, String key) throws IOException {
		return initProp(profilename).getProperty(key);
	}

	public static boolean isNum(String str) {

		if (str == null || str == "" || str.trim() == "") {
			return false;
		} else {
			Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
			return pattern.matcher(str).matches();
		}
	}

	public static Logger logger(Object obj) {
		org.apache.log4j.LogManager.resetConfiguration();
		String property = System.getProperty("user.dir");
		org.apache.log4j.PropertyConfigurator.configure(property+"/log4j.properties");
		return Logger.getLogger(obj.getClass());
	};

}
