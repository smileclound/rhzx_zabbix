package com.rmyh.report.controller;

import java.util.regex.Pattern;

public class General {
	
	public static void main(String[] args) {
//		System.out.println(isInteger("9"));
	}

	public static boolean isInteger(String str) {
		
		if (str == null || str == "" || str.trim()=="") {
			return false;
		} else {
			Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
			return pattern.matcher(str).matches();
		}
	}

}
