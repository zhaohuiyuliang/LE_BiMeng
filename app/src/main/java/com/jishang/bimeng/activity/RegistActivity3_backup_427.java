package com.jishang.bimeng.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.jishang.bimeng.R;
import com.jishang.bimeng.activity.base.BaseActivity;
import com.jishang.bimeng.activity.mendian.DiquAcitivity;
import com.jishang.bimeng.activity.mendian.MendianAcitivity;
import com.jishang.bimeng.entity.regist.RegistEntity;
import com.jishang.bimeng.entity.regist.ShengEntity;
import com.jishang.bimeng.parse.ParseUtil;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.ToastUtil;
import com.jishang.bimeng.utils.Internet.MyHttpRequest;
import com.jishang.bimeng.utils.url.UrlUtils;

/**
 * @author kangming 注册的第三个界面
 */
public class RegistActivity3_backup_427 extends BaseActivity implements OnClickListener {
	private EditText mEdt_diqu, mEdt_mendian;
	private TextView mTv_diqu, mTv_mendian, mTv_confim;
	private List<BasicNameValuePair> params;
	private ShengEntity entity = new ShengEntity();
	private String wangba; 
	private ProgressDialog dialog;

	@Override
	public int initResource() {
		return R.layout.citys1_back;
	}
	
	@Override
	public void initView() {
		params = new ArrayList<BasicNameValuePair>();
		mEdt_diqu = (EditText) findViewById(R.id.activity_regist3_edt_diqu);
		mEdt_mendian = (EditText) findViewById(R.id.activity_regist3_edt_mendian);
		mTv_diqu = (TextView) findViewById(R.id.activity_regist3_bt_diqu);
		mTv_mendian = (TextView) findViewById(R.id.activity_regist3_bt_mendian);
		mTv_confim = (TextView) findViewById(R.id.activity_regist3_bt_confirm);

	}

	@Override
	public void initData() {
	}

	@Override
	public void addListener() {
		mTv_confim.setOnClickListener(this);
		mTv_diqu.setOnClickListener(this);
		mTv_mendian.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.activity_regist3_bt_diqu:
			intent = new Intent(RegistActivity3_backup_427.this, DiquAcitivity.class);
			startActivityForResult(intent, 10);

			break;
		case R.id.activity_regist3_bt_mendian:
			intent = new Intent(RegistActivity3_backup_427.this, MendianAcitivity.class);
			startActivityForResult(intent, 10);

			break;
		case R.id.activity_regist3_bt_confirm:
//			Log.e("点击有效", "111");
			setMsg();

			break;

		}

	}

	/**
	 * 
	 * 向服务提交数据，修改用户信息
	 * 
	 * 
	 */
	public void setMsg() {
		Setprogrese();//启动进度条
		new Thread() {

			public void run() {
				Log.e("entity.getSheng()", (entity.getSheng()==null)+"");
				if (entity.getSheng() != null) {
					
					String province = entity.getSheng();
					String city = entity.getShi();
					String uid=new SharUtil(RegistActivity3_backup_427.this).getUid();
					String business=mEdt_mendian.getText().toString().trim();
					params.add(new BasicNameValuePair("province", province));
					params.add(new BasicNameValuePair("business", business));
					params.add(new BasicNameValuePair("city", city));
//					String url = "http://apicms.gbimoo.com/v1/business/select.json?"+"uid="+uid;
					String url = UrlUtils.REGISTE_SELECT+"uid="+uid;
					String result = MyHttpRequest.getHttpPost(url, params);
					if(result!=null){
//						Log.e("result", result.toString());//275120
						RegistEntity entity=ParseUtil.getBanner_regist(result);
						if(entity.getStatus()==0){
							Message msg=new Message();
							msg.obj=entity.getErrors();
							msg.what=0;
							handler.sendMessage(msg);
						}else{
							Message msg=new Message();
							msg.obj=result;
							msg.what=1;
							handler.sendMessage(msg);
						}
					}
				}

			};
		}.start();

	}
	Handler handler=new Handler(){
		public void handleMessage(Message msg) {
			dialog.cancel();
			String result=(String) msg.obj;
			switch (msg.what) {
			case 0:
				ToastUtil.Toast(RegistActivity3_backup_427.this, result);
				break;
			case 1:
				ToastUtil.Toast(RegistActivity3_backup_427.this, getString(R.string.register_success));
				finish();
				break;

			}
		};
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Log.e("111", "111");
		switch (resultCode) {
		case 100:
			Log.e("111", "222");
			entity = new ShengEntity();
			entity = (ShengEntity) data.getSerializableExtra("entity");
			// Log.e("entity", entity.toString());
			mEdt_diqu.setText(entity.getSheng() + " " + entity.getShi()
					+ entity.getXian());
			break;
		case 101:
			String wangba = (String) data.getSerializableExtra("wangba");
			Log.e("wangba", wangba);
			mEdt_mendian.setText(wangba);

			break;

		}

	}
	/**
	 * 
	 * 初始化进度条，显示进度条
	 * 
	 * 
	 */
	public void Setprogrese(){
		dialog = new ProgressDialog(RegistActivity3_backup_427.this);
		dialog.setMessage(getString(R.string.register_loading));
		dialog.show();
	}

}
