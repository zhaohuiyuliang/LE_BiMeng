package com.jishang.bimeng.activity.zhifu;

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
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jishang.bimeng.R;
import com.jishang.bimeng.activity.base.BaseActivity;
import com.jishang.bimeng.entity.yuezhan.confirm.C_DataEntity;
import com.jishang.bimeng.entity.yuezhan.zf.Zf_list_DataEntity;
import com.jishang.bimeng.entity.yuezhan.zf.Zf_llistEntity;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.ToastUtil;
import com.jishang.bimeng.utils.Internet.MyHttpRequest;
import com.jishang.bimeng.utils.dialog.DialogUtils;
import com.jishang.bimeng.utils.url.UrlUtils;

public class Yw_ZfListActivity extends BaseActivity implements OnClickListener {
	private Intent intent;
	private TextView mTv_xf, mTv_sc,mTv_zfy, mTv_confirm;
	private C_DataEntity entity;
	private String token;
	private Context mContext;
	private List<BasicNameValuePair> params;
	private Gson mGson;
	public static Yw_ZfListActivity instance = null;

	@Override
	public int initResource() {
		return R.layout.activity_ywzflist;
	}

	@Override
	public void initView() {
		instance=this;
		params = new ArrayList<BasicNameValuePair>();
		mGson=new Gson();
		mContext = this;
		intent = getIntent();
		token = new SharUtil(mContext).getAccess_token();
		entity = (C_DataEntity) intent.getSerializableExtra("entity1");
//		Log.e("----", entity.toString());
		mTv_confirm = (TextView) findViewById(R.id.activity_zflist_confirm);
		mTv_xf = (TextView) findViewById(R.id.activity_zflist_xf);
		mTv_sc = (TextView) findViewById(R.id.activity_zflist_sc);
		mTv_zfy = (TextView) findViewById(R.id.activity_zflist_zfy);

	}

	@Override
	public void initData() {
		mTv_xf.setText(entity.getOne_peple_money());
		mTv_sc.setText(entity.getCreate_time());
		mTv_zfy.setText(entity.getTotal_money());

	}

	@Override
	public void addListener() {
		mTv_confirm.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.activity_zflist_confirm:
			putMsg();
			break;

		}

	}

	public void putMsg() {
		DialogUtils.getInstance().createNotifier(mContext, "正在加载中");
		final String p_id = entity.getP_id();
		final String uid = entity.getF_uid();
		final String order_notice_sn = entity.getOrder_notice_sn();
		
		params.add(new BasicNameValuePair("ypn_id", p_id));
		params.add(new BasicNameValuePair("pnyw_uid", uid));
		params.add(new BasicNameValuePair("yw_order_notice", order_notice_sn));

		new Thread() {
			public void run() {
				String url = UrlUtils.BASEURL+"v1/yw/yw_pay_confirm.json";
				String result = MyHttpRequest.getHttpPostBasic(url, params,
						token);
				if (result != null) {
					Log.e("resutl", result.toString());
					try {
						Zf_llistEntity entity=mGson.fromJson(result, Zf_llistEntity.class);
						Zf_list_DataEntity entity1=entity.getData();
						if(entity.getStatus()==0){
							handler.sendEmptyMessage(0);
						}else{
							Message msg=new Message();
							msg.what=1;
							msg.obj=entity1;
							handler.sendMessage(msg);
						}
					} catch (JsonSyntaxException e) {
						e.printStackTrace();
						handler.sendEmptyMessage(0);
					}
					
				}

			};
		}.start();

	}
	private Handler handler=new Handler(){
		public void handleMessage(Message msg) {
			DialogUtils.getInstance().cancel();
			switch (msg.what) {
			case 0:
				ToastUtil.Toast(mContext, "网络出错");
				break;
			case 1:
				Zf_list_DataEntity entity=(Zf_list_DataEntity) msg.obj;
				Log.e("---", entity.toString());
				Intent intent=new Intent(Yw_ZfListActivity.this, Yw_ZfTwoActivity.class);
				intent.putExtra("entity", entity);
				startActivity(intent);
				
				break;

			}
		};
	};

}
