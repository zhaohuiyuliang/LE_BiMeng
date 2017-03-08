package com.jishang.bimeng.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.jishang.bimeng.R;
import com.jishang.bimeng.activity.base.BaseActivity;
import com.jishang.bimeng.utils.ToastUtil;
import com.jishang.bimeng.utils.Internet.MyHttpRequest;
import com.jishang.bimeng.utils.url.UrlUtils;

/**
 * @author kangming
 *
 */
public class LogActivity1 extends BaseActivity implements OnClickListener {
	private EditText mEdt_userName;
	private EditText mEdt_userPwd;
	private TextView mTv_login;
	private TextView mTv_reg;
	private String phone = "18233644940";
	private String username = "wagn1";
	private int sex = 1;
	private String password = "123456";
	private List<BasicNameValuePair> params;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_login);
		super.onCreate(savedInstanceState);

	}

	@Override
	public void initView() {
		mEdt_userName = (EditText) findViewById(R.id.activity_log_edt_phone);
		mEdt_userPwd = (EditText) findViewById(R.id.activity_log_edt_pwd);
		mTv_login = (TextView) findViewById(R.id.activity_log_bt_login);
		mTv_reg = (TextView) findViewById(R.id.activity_log_tv_register);
		mTv_reg.setOnClickListener(this);
		mTv_login.setOnClickListener(this);
		params = new ArrayList<BasicNameValuePair>();

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.activity_log_tv_register:

			Intent intent = new Intent(LogActivity1.this, RegistActivity.class);
			startActivity(intent);
			break;
		case R.id.activity_log_bt_login:
			ToastUtil.Toast(LogActivity1.this, "点击有效");
			params.add(new BasicNameValuePair("phone", phone));
			params.add(new BasicNameValuePair("username", username));
			params.add(new BasicNameValuePair("password", password));
			params.add(new BasicNameValuePair("sex", sex + ""));
			new Thread() {
				public void run() {
					
						String url = UrlUtils.BASEURL+"v1/site/signup.json";
						String result=MyHttpRequest.getHttpPost(url, params);
						if(result!=null){
							Log.e("result", result);
						}
						

					

				};
			}.start();
			// getDate();
			break;

		}

	}

	@Override
	public void initData() {

	}


	public void getDate() {
		new Thread() {
			public void run() {
				String url = UrlUtils.BASEURL+"v1/user/index.json";
				String result = MyHttpRequest.getHttpGet(url, null);
				Log.e("111", "11111");
				if (result != null) {
					Log.e("result", result.toString());
				}

			};
		}.start();
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {

			switch (msg.what) {
			case 1:

				break;

			}
		};
	};

	@Override
	public int initResource() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void addListener() {
		// TODO Auto-generated method stub
		
	}

}
