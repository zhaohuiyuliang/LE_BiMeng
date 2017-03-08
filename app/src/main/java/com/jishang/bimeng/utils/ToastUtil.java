package com.jishang.bimeng.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
	
     private static boolean isToast=true;	
	public ToastUtil() {
		
	}
	public static void Toast(Context context, String st){
		if(isToast){
		Toast.makeText(context, st, 0).show();
		}
	}

}
