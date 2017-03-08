package com.jishang.bimeng.activity.yuewan;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.http.message.BasicNameValuePair;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jishang.bimeng.R;
import com.jishang.bimeng.activity.base.BaseActivity;
import com.jishang.bimeng.activity.pickphoto.view.PublishActivity;
import com.jishang.bimeng.entity.yuewan.zlsh.ZlshEntity;
import com.jishang.bimeng.entity.yuewan.zlsh.Zlsh_dataEntity;
import com.jishang.bimeng.entity.yuezhan.cyzf.Cyzf_wtEntity;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.ToastUtil;
import com.jishang.bimeng.utils.Internet.MyHttpRequest;
import com.jishang.bimeng.utils.dialog.DialogUtils;
import com.jishang.bimeng.utils.url.UrlUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class Yw_zlshActivity extends BaseActivity implements OnClickListener {
	private EditText mEdt_name, mEdt_age;
	private ImageView mImgv_idcardzm, mImgv_idcardfm, mImgv_zp;
	private Context mContext;
	private Uri photoUri;
	private String filename_zm, filename_fm, filename_zp;
	private File file;
	private List<BasicNameValuePair> params;
	private String token;
	private Gson mGson;
	private Spinner mSp_xf;
	private List<Zlsh_dataEntity> data=new ArrayList<Zlsh_dataEntity>();
	private MySpAdapter adapter;
	private TextView mTv_confirm;
	private Button mBt_age;
	private String xf;
	private HttpUtils httpUtils;

	@Override
	public int initResource() {
		return R.layout.activity_yw_zlsh;
	}

	@Override
	public void initView() {
		mContext = this;
		httpUtils = new HttpUtils(10000);
		mImgv_idcardzm = (ImageView) findViewById(R.id.activity_yw_imgv_idcardzm);
		mImgv_idcardfm = (ImageView) findViewById(R.id.activity_yw_imgv_idcardfm);
		mImgv_zp = (ImageView) findViewById(R.id.activity_yw_imgv_zp);
		params = new ArrayList<BasicNameValuePair>();
		token = new SharUtil(mContext).getAccess_token();
		mSp_xf=(Spinner) findViewById(R.id.activity_yw_spiner_xf);
		mTv_confirm=(TextView) findViewById(R.id.activity_yw_confirm);
		mBt_age=(Button) findViewById(R.id.activity_yw_age);
		mEdt_name=(EditText) findViewById(R.id.activity_yw_name);
		mGson = new Gson();
		adapter=new MySpAdapter(data);
		mSp_xf.setAdapter(adapter);
	}

	@Override
	public void initData() {
		getXf();

	}
	public void getXf(){
		DialogUtils.getInstance().createNotifier(mContext, "正在加载中");
		params.add(new BasicNameValuePair("1", "1") );
		new Thread(){
			public void run() {
				String url=UrlUtils.BASEURL+"v1/tip/list.json";
				String result=MyHttpRequest.getHttpPostBasic(url, params, token);
				if(result!=null){
					try {
						ZlshEntity entity=mGson.fromJson(result, ZlshEntity.class);
//					Log.e("entity", entity.toString());
						data=entity.getData();
						if(entity.getStatus()!=0){
						  handler.sendEmptyMessage(1);
						}else{
							handler.sendEmptyMessage(0);
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
		public void handleMessage(android.os.Message msg) {
			DialogUtils.getInstance().cancel();
			switch (msg.what) {
			case 0:
				ToastUtil.Toast(mContext, "出错了");
				break;
			case 1:
				adapter.refreshAdapter(data);
				xf=data.get(0).getTip_select();
				break;

			}
		};
	};

	@Override
	public void addListener() {
		mImgv_idcardzm.setOnClickListener(this);
		mImgv_idcardfm.setOnClickListener(this);
		mImgv_zp.setOnClickListener(this);
		mTv_confirm.setOnClickListener(this);
		mBt_age.setOnClickListener(this);
		mSp_xf.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				Zlsh_dataEntity entity=data.get(position);
				xf=entity.getTip_select();
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
			}
		});
	}

	public void back(View v) {
		finish();
	}

	public void takePhoto_cardzm() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 调用android自带的照相机
		photoUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
		startActivityForResult(intent, 1);
	}

	public void takePhoto_cardfm() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 调用android自带的照相机
		photoUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
		startActivityForResult(intent, 2);
	}

	public void takePhoto_zp() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 调用android自带的照相机
		photoUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
		startActivityForResult(intent, 3);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.activity_yw_imgv_idcardzm:
			takePhoto_cardzm();
			break;
		case R.id.activity_yw_imgv_idcardfm:
			takePhoto_cardfm();
			break;
		case R.id.activity_yw_imgv_zp:
			takePhoto_zp();
			break;
		case R.id.activity_yw_confirm:
			putMsg();
			
			break;
		case R.id.activity_yw_age:
			showdata();
			
			break;

		}

	}
	public void putMsg(){
		File file_zm=new File(filename_zm);
		File file_fm=new File(filename_fm);
		File file_zp=new File(filename_zp);
		final String URL = UrlUtils.BASEURL+"v1/user_details/update.json";
		RequestParams params = new RequestParams();
		String name=mEdt_name.getText().toString().trim();
		String birthday=mBt_age.getText().toString().trim();
		params.addBodyParameter("ud_full_name", name);
		params.addBodyParameter("birthday", birthday);
		params.addBodyParameter("z_identification", file_zm);
		params.addBodyParameter("f_identification", file_fm);
		params.addBodyParameter("photo", file_zp);
		params.addBodyParameter("w_tip", xf);
		params.addHeader("Authorization", "Bearer" + " " + token);// 认证token
		params.addHeader("User-Agent", "imgfornote");
		Log.e("----", name+""+birthday+"  "+filename_zm+"   "+filename_fm+"  "+filename_zp+"  "+xf+"  ");
		httpUtils.send(HttpMethod.POST, URL, params,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException e, String msg) {
						Toast.makeText(mContext,
								"上传失败，检查一下服务器地址是否正确", Toast.LENGTH_SHORT)
								.show();
						Log.i("MainActivity", e.getExceptionCode() + "====="
								+ msg);
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						Toast.makeText(mContext, "上传成功，马上去服务器看看吧！",
								Toast.LENGTH_SHORT).show();
						Log.e("MainActivity", "====upload_error====="
								+ responseInfo.result);
						finish();
					}
				});
		
	}
	// 选择生日
	public void showdata() {
		final Calendar objTime = Calendar.getInstance();
		int iYear = objTime.get(Calendar.YEAR);
		int iMonth = objTime.get(Calendar.MONTH);
		int iDay = objTime.get(Calendar.DAY_OF_MONTH);
		DatePickerDialog dpd = new DatePickerDialog(this,
				new OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						mBt_age.setText(new StringBuilder()
								.append(year)
								.append("-")
								.append((monthOfYear + 1) < 10 ? "0"
										+ (monthOfYear + 1) : (monthOfYear + 1))
								.append("-")
								.append((dayOfMonth < 10) ? "0" + dayOfMonth
										: dayOfMonth));
					}
				}, iYear, iMonth, iDay);
		dpd.show();
	}

	@SuppressLint("SdCardPath")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case 1:
			switch (resultCode) {
			case Activity.RESULT_OK:// 照相完成点击确定
				String sdStatus = Environment.getExternalStorageState();
				if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
					Log.v("TestFile",
							"SD card is not avaiable/writeable right now.");
					return;
				}
				Bundle bundle = data.getExtras();
				Bitmap bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式
				FileOutputStream b = null;
				file = new File("/sdcard/pk4fun/");
				file.mkdirs();// 创建文件夹，名称为pk4fun //
									// 照片的命名，目标文件夹下，以当前时间数字串为名称，即可确保每张照片名称不相同。网上流传的其他Demo这里的照片名称都写死了，则会发生无论拍照多少张，后一张总会把前一张照片覆盖。细心的同学还可以设置这个字符串，比如加上“ＩＭＧ”字样等；然后就会发现sd卡中myimage这个文件夹下，会保存刚刚调用相机拍出来的照片，照片名称不会重复。
				String str = null;
				str =  DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA))+".jpg";
				filename_zm = file.getPath() + str;
				try {
					b = new FileOutputStream(filename_zm);
					bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} finally {
					try {
						b.flush();
						b.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				mImgv_idcardzm.setImageBitmap(bitmap);
				break;

			case Activity.RESULT_CANCELED:// 取消
				break;
			}
			break;
		case 2:
			switch (resultCode) {
			case Activity.RESULT_OK:// 照相完成点击确定
				String sdStatus = Environment.getExternalStorageState();
				if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
					Log.v("TestFile",
							"SD card is not avaiable/writeable right now.");
					return;
				}
				Bundle bundle = data.getExtras();
				Bitmap bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式
				FileOutputStream b = null;
				file = new File("/sdcard/pk4fun/");
				file.mkdirs();// 创建文件夹，名称为pk4fun //
									// 照片的命名，目标文件夹下，以当前时间数字串为名称，即可确保每张照片名称不相同。网上流传的其他Demo这里的照片名称都写死了，则会发生无论拍照多少张，后一张总会把前一张照片覆盖。细心的同学还可以设置这个字符串，比如加上“ＩＭＧ”字样等；然后就会发现sd卡中myimage这个文件夹下，会保存刚刚调用相机拍出来的照片，照片名称不会重复。
				String str = null;
				str =  DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA))+".jpg";
				filename_fm = file.getPath() + str;
				try {
					b = new FileOutputStream(filename_fm);
					bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} finally {
					try {
						b.flush();
						b.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				mImgv_idcardfm.setImageBitmap(bitmap);
				break;

			case Activity.RESULT_CANCELED:// 取消
				break;
			}
			break;
		case 3:
			switch (resultCode) {
			case Activity.RESULT_OK:// 照相完成点击确定
				String sdStatus = Environment.getExternalStorageState();
				if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
					Log.v("TestFile",
							"SD card is not avaiable/writeable right now.");
					return;
				}
				Bundle bundle = data.getExtras();
				Bitmap bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式
				FileOutputStream b = null;
				file = new File("/sdcard/pk4fun/");
				file.mkdirs();// 创建文件夹，名称为pk4fun //
									// 照片的命名，目标文件夹下，以当前时间数字串为名称，即可确保每张照片名称不相同。网上流传的其他Demo这里的照片名称都写死了，则会发生无论拍照多少张，后一张总会把前一张照片覆盖。细心的同学还可以设置这个字符串，比如加上“ＩＭＧ”字样等；然后就会发现sd卡中myimage这个文件夹下，会保存刚刚调用相机拍出来的照片，照片名称不会重复。
				String str = null;
				str =  DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA))+".jpg";
				filename_zp = file.getPath() + str;
				try {
					b = new FileOutputStream(filename_zp);
					bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} finally {
					try {
						b.flush();
						b.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				mImgv_zp.setImageBitmap(bitmap);
				break;

			case Activity.RESULT_CANCELED:// 取消
				break;
			}
			break;
			

		}
	}
	public class MySpAdapter extends BaseAdapter {
		protected List<Zlsh_dataEntity> mDatas;

		public MySpAdapter(List<Zlsh_dataEntity> mDatas) {
			this.mDatas = mDatas;
		}

		@Override
		public int getCount() {
			if (mDatas.size() != 0) {
				return mDatas.size();
			}
			return 0;
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		// 向list中添加数据
		public void refreshAdapter(List<Zlsh_dataEntity> arrayList) {
			mDatas.addAll(arrayList);
			notifyDataSetChanged();
		}

		// 清空list集合
		public void clear() {
			mDatas.clear();
			notifyDataSetChanged();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder5 vh1 = null;
			if (convertView == null) {
				vh1 = new ViewHolder5();
				convertView = View.inflate(mContext,
						R.layout.yuezhan_sp_item, null);
				vh1.name = (TextView) convertView
						.findViewById(R.id.yuezhan_sp_item_name);
				convertView.setTag(vh1);
			} else {
				vh1 = (ViewHolder5) convertView.getTag();

			}
			vh1.name.setText(mDatas.get(position).getTip_select());

			return convertView;
		}

	}

	class ViewHolder5 {
		TextView name;
	}

}
