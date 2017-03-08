package com.jishang.bimeng.activity.hongbao;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jishang.bimeng.R;
import com.jishang.bimeng.entity.hongbao.chai.ChaiEntity;
import com.jishang.bimeng.entity.hongbao.dian.Dian_dataEntity;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.ToastUtil;
import com.jishang.bimeng.utils.Internet.MyHttpRequest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class GetMoneyActivity extends Activity implements OnClickListener{

	private TextView mTv_money, mTv_confirm;
	private Gson mGson;
	private Context mContext;
	private List<BasicNameValuePair> params;
	private String token;
	private Intent intent;
	private String money;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_getmoney);
		initView();
		initData();
		addListener();
	}

	public void initView() {
		intent = getIntent();
		mTv_confirm = (TextView) findViewById(R.id.activity_chaihb_bt_confrim);
		money = (String) intent.getSerializableExtra("money");
		mTv_money = (TextView) findViewById(R.id.activity_chaihb_money);
		params = new ArrayList<BasicNameValuePair>();
		mContext = this;
		mGson = new Gson();
		token = new SharUtil(mContext).getAccess_token();
		mTv_money.setText(money+"元");

	}

	public void initData() {

	}

	public void addListener() {
		mTv_confirm.setOnClickListener(this);
	}

	public void getHb() {

	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				break;
			case 1:
				break;
			case 2:
				break;

			}
		};
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.activity_chaihb_bt_confrim:
			finish();

			break;

		}
	}

}
