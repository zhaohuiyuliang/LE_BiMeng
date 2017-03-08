package com.jishang.bimeng.utils;

public class CheckNulll {
	public static boolean check(String str){
		if("".equals(str)||null==str){
			return false;
		}
		return true;
		
	}

}
