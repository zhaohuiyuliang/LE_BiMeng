package com.jishang.bimeng.welcome;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;

import com.jishang.bimeng.R;
import com.jishang.bimeng.activity.LoginActivity;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//		new Thread(){
//			public void run() {
//				
//				SystemClock.sleep(20000);
//				handler.sendEmptyMessage(1);
//				
//			};
//		}.start();
//	}
//    Handler handler=new Handler(){
//    	public void handleMessage(android.os.Message msg) {
//    		
//    		switch (msg.what) {
//			case 1:
//				Intent intent=new Intent(MainActivity.this, LogActivity.class);
//				startActivity(intent);
//				
//				break;
//
//			}
//    	};
//    };
	}
}
