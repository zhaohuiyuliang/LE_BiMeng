package com.jishang.bimeng.activity.wode;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jishang.bimeng.R;
import com.jishang.bimeng.activity.base.BaseActivity;
import com.jishang.bimeng.entity.tonyong.TYEntity;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.ToastUtil;
import com.jishang.bimeng.utils.Internet.MyHttpRequest;
import com.jishang.bimeng.utils.url.UrlUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class XiugaincActivity extends BaseActivity{
	private TextView mTv_name,mEdt_content;
	
	private List<BasicNameValuePair> params;
	private Gson mGson;
	private Context mContext;
	private SharUtil mShareUtil;
	private String token;
	private String username;

	@Override
	public int initResource() {
		return R.layout.activity_xiugainc;
	}

	@Override
	public void initView() {
		params=new ArrayList<BasicNameValuePair>();
		mGson=new Gson();
		mContext=this;
		mShareUtil=new SharUtil(mContext);
		token=mShareUtil.getAccess_token();
		mTv_name=(TextView) findViewById(R.id.activity_xiugainc_name);
		mEdt_content=(TextView) findViewById(R.id.activity_xiugainc_edt_nicheng);
		mTv_name.setText("修改昵称");
		
		
	}

	@Override
	public void initData() {
		
	}

	@Override
	public void addListener() {
		
	}
	public void save(View v){
		 getMsg();
		
		
		
	}
	public void back(View v){
		finish();
	}
	public void getMsg(){
		username=mEdt_content.getText().toString().trim();
		 params.add(new BasicNameValuePair("username", username));
		new Thread(){
			public void run() {
				String url=UrlUtils.BASEURL+"v1/user/update_personal.json";
				String result=MyHttpRequest.getHttpPostBasic(url, params, token);
				if(result!=null){
//					Log.e("result", result.toString());
					TYEntity entity=mGson.fromJson(result, TYEntity.class);
					if(entity.getStatus()==1){
						handler.sendEmptyMessage(1);
					}else if(entity.getStatus()==0){
						Message msg=new Message();
						msg.obj=entity.getErrors();
						msg.what=0;
						handler.sendMessage(msg);
					}
					
				}
			};
		}.start();
	}
	private Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				String erros=(String) msg.obj;
				ToastUtil.Toast(mContext, erros);
				break;
			case 1:
				Intent intent=new Intent(XiugaincActivity.this, GerenSzActivity.class);
				intent.putExtra("content", username);
				setResult(200, intent);
				ToastUtil.Toast(mContext, "修改成功");
				mShareUtil.setUserName(username);
				finish();
				break;

			}
		};
	};

}
