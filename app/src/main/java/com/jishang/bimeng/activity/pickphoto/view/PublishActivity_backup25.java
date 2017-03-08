package com.jishang.bimeng.activity.pickphoto.view;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.jishang.bimeng.R;
import com.jishang.bimeng.activity.base.BaseActivity;
import com.jishang.bimeng.activity.pickphoto.adapter.ImagePublishAdapter;
import com.jishang.bimeng.activity.pickphoto.model.ImageItem;
import com.jishang.bimeng.activity.pickphoto.util.CustomConstants;
import com.jishang.bimeng.activity.pickphoto.util.IntentConstants;
import com.jishang.bimeng.utils.url.UrlUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class PublishActivity_backup25 extends BaseActivity implements OnClickListener{
	private GridView mGridView;
	private ImagePublishAdapter mAdapter;
	private TextView sendTv;
	public static List<ImageItem> mDataList = new ArrayList<ImageItem>();
	private static final int TAKE_PICTURE = 0x000000;
	private String path = "";
	private EditText mEdt_content;
	private HttpUtils httpUtils;
	private File tempFile = new File(Environment.getExternalStorageDirectory(),
			getPhotoFileName());
	// 浣跨敤绯荤粺褰撳墠鏃ユ湡鍔犱互璋冩暣浣滀负鐓х墖鐨勫悕绉�
		private String getPhotoFileName() {
			Date date = new Date(System.currentTimeMillis());
			SimpleDateFormat sdf = new SimpleDateFormat("'PNG'_yyyyMMdd_HHmmss");
			return sdf.format(date) + ".png";
		}


	@Override
	public int initResource() {
		return R.layout.act_publish;
	}

	public void initView() {
		httpUtils=new HttpUtils(10000);
		getMsg();
		mEdt_content=(EditText) findViewById(R.id.act_publish_content);
		TextView titleTv = (TextView) findViewById(R.id.title);
		titleTv.setText("");
		mGridView = (GridView) findViewById(R.id.gridview);
		mGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		mAdapter = new ImagePublishAdapter(this, mDataList);
		mGridView.setAdapter(mAdapter);
		mGridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (position == getDataSize()) {
					new PopupWindows(PublishActivity_backup25.this, mGridView);
				} else {
					Intent intent = new Intent(PublishActivity_backup25.this,
							ImageZoomActivity.class);
					intent.putExtra(IntentConstants.EXTRA_IMAGE_LIST,
							(Serializable) mDataList);
					intent.putExtra(IntentConstants.EXTRA_CURRENT_IMG_POSITION,
							position);
					startActivity(intent);
				}
			}
		});
		sendTv = (TextView) findViewById(R.id.action);
		sendTv.setText("鍙戦��");
		sendTv.setOnClickListener(this);

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		saveTempToPref();
	}

	/**
	 * 鎶婄収鐗囧偍瀛樺湪鍏变韩鍙傛暟閲岄潰
	 * 
	 */
	private void saveTempToPref() {
		SharedPreferences sp = getSharedPreferences(
				CustomConstants.APPLICATION_NAME, MODE_PRIVATE);
		String prefStr = JSON.toJSONString(mDataList);
		Log.e("prefStr", prefStr);
		sp.edit().putString(CustomConstants.PREF_TEMP_IMAGES, prefStr).commit();

	}

	/**
	 * 鎶婄収鐗囦粠鍏变韩鍙傛暟閲岄潰鍙栧嚭鏉�
	 */
	private void getTempFromPref() {
		SharedPreferences sp = getSharedPreferences(
				CustomConstants.APPLICATION_NAME, MODE_PRIVATE);
		String prefStr = sp.getString(CustomConstants.PREF_TEMP_IMAGES, null);
		if (!TextUtils.isEmpty(prefStr)) {
			List<ImageItem> tempImages = JSON.parseArray(prefStr,
					ImageItem.class);
			mDataList = tempImages;
		}
	}

	/**
	 * 涓婁紶鎴愬姛涔嬪悗鎶婄収鐗囩Щ闄�
	 */
	private void removeTempFromPref() {
		SharedPreferences sp = getSharedPreferences(
				CustomConstants.APPLICATION_NAME, MODE_PRIVATE);
		sp.edit().remove(CustomConstants.PREF_TEMP_IMAGES).commit();
	}

	@SuppressWarnings("unchecked")
	public void getMsg() {
		getTempFromPref();
		List<ImageItem> incomingDataList = (List<ImageItem>) getIntent()
				.getSerializableExtra(IntentConstants.EXTRA_IMAGE_LIST);
		if (incomingDataList != null) {
			mDataList.addAll(incomingDataList);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		notifyDataChanged(); // 褰撳湪ImageZoomActivity涓垹闄ゅ浘鐗囨椂锛岃繑鍥炶繖閲岄渶瑕佸埛鏂�
	}

	private int getDataSize() {
		return mDataList == null ? 0 : mDataList.size();
	}

	private int getAvailableSize() {
		int availSize = CustomConstants.MAX_IMAGE_SIZE - mDataList.size();
		if (availSize >= 0) {
			return availSize;
		}
		return 0;
	}

	/*public String getString(String s) {
		String path = null;
		if (s == null)
			return "";
		for (int i = s.length() - 1; i > 0; i++) {
			s.charAt(i);
		}
		return path;
	}*/

	public class PopupWindows extends PopupWindow {

		public PopupWindows(Context mContext, View parent) {

			View view = View.inflate(mContext, R.layout.item_popupwindow, null);
			view.startAnimation(AnimationUtils.loadAnimation(mContext,
					R.anim.fade_ins));
			LinearLayout ll_popup = (LinearLayout) view
					.findViewById(R.id.ll_popup);
			ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
					R.anim.push_bottom_in_2));

			setWidth(LayoutParams.MATCH_PARENT);
			setHeight(450);// 杩欎釜鍦版柟鍘诲鏋滆缃负match_parent浼氶攢姣� 涓嶄簡
			ColorDrawable cd = new ColorDrawable(-0000);
			setBackgroundDrawable(cd);
			setFocusable(true);
			setOutsideTouchable(true);

			setContentView(view);
			showAtLocation(parent, Gravity.BOTTOM, 0, 0);
			// update();

			Button bt1 = (Button) view
					.findViewById(R.id.item_popupwindows_camera);
			Button bt2 = (Button) view
					.findViewById(R.id.item_popupwindows_Photo);
			Button bt3 = (Button) view
					.findViewById(R.id.item_popupwindows_cancel);
			bt1.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					takePhoto();
					dismiss();
				}
			});
			bt2.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent intent = new Intent(PublishActivity_backup25.this,
							ImageBucketChooseActivity.class);
					intent.putExtra(IntentConstants.EXTRA_CAN_ADD_IMAGE_SIZE,
							getAvailableSize());
					startActivity(intent);
					dismiss();
				}
			});
			bt3.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					dismiss();
				}
			});

		}
	}

	public void takePhoto() {
		Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		openCameraIntent.putExtra("camerasensortype", 2); // 璋冪敤鍓嶇疆鎽勫儚澶�
		openCameraIntent.putExtra("autofocus", true); // 鑷姩瀵圭劍
		openCameraIntent.putExtra("fullScreen", false); // 鍏ㄥ睆
		openCameraIntent.putExtra("showActionIcons", false);
		// 鎸囧畾璋冪敤鐩告満鎷嶇収鍚庣収鐗囩殑瀛樺偍璺緞
		openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
		path = tempFile.getPath();
		Log.e("path", path);
		startActivityForResult(openCameraIntent, TAKE_PICTURE);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case TAKE_PICTURE:
			if (mDataList.size() < CustomConstants.MAX_IMAGE_SIZE
					&& resultCode == -1 && !TextUtils.isEmpty(path)) {
				ImageItem item = new ImageItem();
				item.sourcePath = path;
				mDataList.add(item);
			}
			break;
		}
	}

	private void notifyDataChanged() {
		mAdapter.notifyDataSetChanged();
	}

	protected void onPause() {
		super.onPause();
		saveTempToPref();
	}

	


	@Override
	public void addListener() {
		
	}

	@Override
	public void initData() {
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.action:
			SendMsg();
			break;

		}
	}
	/*public String Getimg(){
		 
		
		return imgv;*/
		
		
		
//	}
	public void SendMsg(){
		String imgv=mDataList.get(0).sourcePath;
		final String URL=UrlUtils.BASEURL+"v1/user_content/release.json";
		String content=mEdt_content.getText().toString().trim();
		RequestParams params=new RequestParams();
		params.addBodyParameter("content",content);
		params.addBodyParameter("thumb_img", tempFile);
		String token="PHPA0g5MeQiWaP37u37ItkRCw_Y2-0vI";
		/*娣诲姞璇锋眰澶�*/
		params.addHeader("Authorization", "Bearer"+" "+token);//璁よ瘉token
		params.addHeader("User-Agent", "imgfornote");
		
		Log.e("imgv", imgv);
//		Log.e("mDataList", mDataList.toString());
		httpUtils.send(HttpMethod.POST,URL, params,new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException e, String msg) {
				Toast.makeText(PublishActivity_backup25.this, "涓婁紶澶辫触锛屾鏌ヤ竴涓嬫湇鍔″櫒鍦板潃鏄惁姝ｇ‘", Toast.LENGTH_SHORT).show();
				Log.i("MainActivity", e.getExceptionCode() + "====="
						+ msg);
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				Toast.makeText(PublishActivity_backup25.this, "涓婁紶鎴愬姛锛岄┈涓婂幓鏈嶅姟鍣ㄧ湅鐪嬪惂锛�", Toast.LENGTH_SHORT).show();
				Log.i("MainActivity", "====upload_error====="
						+ responseInfo.result);
			}
		});
		
	}

}