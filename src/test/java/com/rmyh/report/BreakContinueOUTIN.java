package com.rmyh.report;

public class BreakContinueOUTIN {
	public static void main(String[] args) {
		OUT:for(int i = 0; i<5; i++) {
			IN:for (int j=0; j<5; j++) {
				if(i==2&&j==2) {
					continue OUT;
				}
				System.out.println("i="+i+","+"j="+j);
			}
		}
	}

}
