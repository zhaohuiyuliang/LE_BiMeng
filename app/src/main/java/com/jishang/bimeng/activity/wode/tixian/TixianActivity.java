package com.jishang.bimeng.activity.wode.tixian;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jishang.bimeng.R;
import com.jishang.bimeng.activity.base.BaseActivity;
import com.jishang.bimeng.activity.wode.PwdSettingActivity;
import com.jishang.bimeng.activity.zhifu.Tx_pwdActivity;
import com.jishang.bimeng.activity.zhifu.Yw_ZfTwoActivity;
import com.jishang.bimeng.entity.wode.tixian.TixianEntity;
import com.jishang.bimeng.utils.CheckNulll;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.ToastUtil;
import com.jishang.bimeng.utils.Internet.MyHttpRequest;
import com.jishang.bimeng.utils.dialog.DialogUtils;
import com.jishang.bimeng.utils.url.UrlUtils;

public class TixianActivity extends BaseActivity implements OnClickListener {
	private TextView mTv_name, mTv_money, mTv_confrim;
	private EditText mEdt_zfbname, mEdt_name, mEdt_jine;
	private String token;
	private Context mContext;
	private List<BasicNameValuePair> params;
	private Gson mGson;
	private TixianEntity entity;
	public static TixianActivity instance = null;
	private Intent intent;
	private String shouru;

	@Override
	public int initResource() {
		return R.layout.activity_tixian;
	}

	@Override
	public void initView() {
		mContext = this;
		instance = this;
		intent = getIntent();
		shouru = (String) intent.getSerializableExtra("shouru");
		token = new SharUtil(mContext).getAccess_token();
		params = new ArrayList<BasicNameValuePair>();
		mGson = new Gson();
		mTv_name = (TextView) findViewById(R.id.activity_tixian_name);
		mEdt_zfbname = (EditText) findViewById(R.id.activity_tixian_edt_zfbname);
		mEdt_name = (EditText) findViewById(R.id.activity_tixian_edt_name);
		mEdt_jine = (EditText) findViewById(R.id.activity_tixian_edt_jine);
		mTv_confrim = (TextView) findViewById(R.id.activity_tixian_confrim);
		mTv_money = (TextView) findViewById(R.id.activity_tixian_tv_money);
		mTv_money.setText(shouru);
		mTv_name.setText("提现");

	}

	@Override
	public void initData() {

	}

	@Override
	public void addListener() {
		mTv_confrim.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.activity_tixian_confrim:
			PutMsg();
			break;

		}
	}

	public void PutMsg() {
		DialogUtils.getInstance().createNotifier(mContext, "正在加载中");
		String alipay_code = mEdt_zfbname.getText().toString().trim();
		String full_name = mEdt_name.getText().toString().trim();
		String e_money = mEdt_jine.getText().toString().trim();
		if (!CheckNulll.check(alipay_code) || !CheckNulll.check(full_name)
				|| !CheckNulll.check(e_money)) {
			ToastUtil.Toast(mContext, "您的信息没有填完整，不能提交");
			return;

		}
		params.add(new BasicNameValuePair("alipay_code", alipay_code));
		params.add(new BasicNameValuePair("full_name", full_name));
		params.add(new BasicNameValuePair("e_money", e_money));
		// DialogUtils.getInstance().createNotifier(mContext, "正在加载中");
		new Thread() {
			public void run() {
				String url = UrlUtils.BASEURL+"v1/extract/apply_money.json";
				String result = MyHttpRequest.getHttpPostBasic(url, params,
						token);
				if (result != null) {
					Log.e("result", result.toString());
					try {
						entity = mGson.fromJson(result, TixianEntity.class);
						if (entity.getStatus() == 0) {
							Message msg = new Message();
							msg.what = 0;
							msg.obj = entity.getErrors();
							handler.sendMessage(msg);
						} else if (entity.getStatus() == 1) {

							handler.sendEmptyMessage(1);
						} else if (entity.getStatus() == 2) {
							handler.sendEmptyMessage(2);
						}
					} catch (JsonSyntaxException e) {
						e.printStackTrace();
						handler.sendEmptyMessage(3);
					}
					
				}
			};
		}.start();
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			DialogUtils.getInstance().cancel();
			switch (msg.what) {
			case 0:
				String erros = (String) msg.obj;
				ToastUtil.Toast(mContext, erros);
				break;
			case 1:
				Intent intent = new Intent(mContext, Tx_pwdActivity.class);
				intent.putExtra("entity", entity);
				startActivity(intent);
				break;
			case 2:
				Intent intent2=new Intent(mContext, PwdSettingActivity.class);
				startActivity(intent2);
				break;
			case 3:
				ToastUtil.Toast(mContext, "网络错误");
				break;

			}
		};
	};

	public void back(View v) {
		finish();
	}

}
