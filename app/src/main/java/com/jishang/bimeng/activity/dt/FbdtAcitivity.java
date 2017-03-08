package com.jishang.bimeng.activity.dt;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.jishang.bimeng.R;
import com.jishang.bimeng.activity.base.BaseActivity;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.Internet.MyHttpRequest;
import com.jishang.bimeng.utils.url.UrlUtils;

public class FbdtAcitivity extends BaseActivity implements OnClickListener {
	private EditText mEdt_content;
	private Button mBt_confirm;
	private List<BasicNameValuePair> params;

	@Override
	public int initResource() {
		return R.layout.activity_fbdt;
	}

	@Override
	public void initView() {
		mEdt_content = (EditText) findViewById(R.id.activity_fbdt_edt_content);
		mBt_confirm = (Button) findViewById(R.id.activity_fbdt_bt_confirm);
		params = new ArrayList<BasicNameValuePair>();
	}

	@Override
	public void initData() {

	}

	@Override
	public void addListener() {
		mBt_confirm.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.activity_fbdt_bt_confirm:
			putMsg();
			break;

		}
	}

	public void putMsg(){
		String content=mEdt_content.getText().toString().trim();
		params.add(new BasicNameValuePair("content", content));
		final String token=new SharUtil(FbdtAcitivity.this).getAccess_token();
		new Thread(){
			public void run() {
				String url=UrlUtils.BASEURL+"v1/user_content/release.json";
				String result=MyHttpRequest.getHttpPostBasic(url, params, token);
				if(result!=null){
					Log.e("result", result.toString());
				}
			};
		}.start();
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:

				break;

			}
		};
	};
	public void back(View view) {
		finish();
	}

}
