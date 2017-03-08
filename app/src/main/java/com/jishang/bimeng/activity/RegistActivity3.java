package com.jishang.bimeng.activity;

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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jishang.bimeng.R;
import com.jishang.bimeng.activity.base.BaseActivity;
import com.jishang.bimeng.activity.yuezhan.ActivityYuezhan;
import com.jishang.bimeng.entity.regist.regist3.Re_wangbaEntity;
import com.jishang.bimeng.entity.tonyong.TYEntity;
import com.jishang.bimeng.entity.yuezhan.zhdianqiehuan.ZhudianqhEntity;
import com.jishang.bimeng.entity.yuezhan.zhdianqiehuan.Zhudianqh_BsEntity;
import com.jishang.bimeng.entity.yuezhan.zhdianqiehuan.Zhudianqh_CityEntity;
import com.jishang.bimeng.entity.yuezhan.zhdianqiehuan.Zhudianqh_ProvinceEntity;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.ToastUtil;
import com.jishang.bimeng.utils.Internet.MyHttpRequest;
import com.jishang.bimeng.utils.dialog.DialogUtils;
import com.jishang.bimeng.utils.url.UrlUtils;

/**
 * @author kangming 注册的第三个界面
 */
public class RegistActivity3 extends BaseActivity implements OnClickListener {
	private TextView mTv_name, mTv_confirm;
	private Spinner mSp_province, mSp_city, mSp_zhudian;
	private Gson mGson;
	private Context mContext;
	private List<Zhudianqh_ProvinceEntity> provinces = new ArrayList<Zhudianqh_ProvinceEntity>();
	private List<Zhudianqh_CityEntity> citys = new ArrayList<Zhudianqh_CityEntity>();
	private List<Zhudianqh_BsEntity> businesss = new ArrayList<Zhudianqh_BsEntity>();
	private List<Zhudianqh_BsEntity> businesss_ = new ArrayList<Zhudianqh_BsEntity>();
	private MySpAdapter_business adapter_bus;
	private MySpAdapter_province adapter_pc;
	private MySpAdapter_city adapter_ct;
	private List<BasicNameValuePair> params;
	private String province, city, business;
	private String business_name=null;

	@Override
	public int initResource() {
		return R.layout.citys1;
	}

	@Override
	public void initView() {
		params = new ArrayList<BasicNameValuePair>();
		mSp_province = (Spinner) findViewById(R.id.activity_qiehuan_city);
		mSp_city = (Spinner) findViewById(R.id.activity_qiehuan_location);
		mSp_zhudian = (Spinner) findViewById(R.id.activity_qiehuan_zhudian);
		mTv_confirm = (TextView) findViewById(R.id.activity_regist3_bt_confirm);
		mGson = new Gson();
		mContext = this;

		adapter_bus = new MySpAdapter_business(businesss);
		adapter_ct = new MySpAdapter_city(citys);
		adapter_pc = new MySpAdapter_province(provinces);
		mSp_city.setAdapter(adapter_ct);
		mSp_province.setAdapter(adapter_pc);
		mSp_zhudian.setAdapter(adapter_bus);
	}

	@Override
	public void initData() {
		getMsg();
	}

	@Override
	public void addListener() {
		mTv_confirm.setOnClickListener(this);
		mSp_province.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				province=provinces.get(position).getId();

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
		mSp_city.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				city=citys.get(position).getId();
				
				adapter_bus.clear();
				businesss_.clear();
				// for (int i = 0; i < citys.size(); i++) {
				// if(citys.get(position).getId().equals(businesss.get(i).getId())){
				// businesss_.add(businesss.get(i));
				// }
				// }

				for (int i = 0; i < businesss.size(); i++) {
					Zhudianqh_BsEntity entity = new Zhudianqh_BsEntity();
					// Log.e("city", city);
					String str = businesss.get(i).getW_citid();
					Log.e("str", str);
					if (str.equals(city)) {
						entity = businesss.get(i);

					}
					if (entity.getId() != null) {
						businesss_.add(entity);
					}

				}
				mSp_zhudian.setAdapter(adapter_bus);
				adapter_bus.refreshAdapter(businesss_);
//				Log.e("businesss_", businesss_.toString());
				if (businesss_ == null || businesss_.isEmpty()) {
					business = null;
					business_name = null;
				}
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
		mSp_zhudian.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				Log.e("businesss_1", businesss_.toString());
				if (businesss_ == null || businesss_.isEmpty()) {
					business = null;
					business_name = null;
				}else{
					business = businesss_.get(position).getId();
					business_name = businesss_.get(position).getW_name();
					
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
	}

	public void getMsg() {
		DialogUtils.getInstance().createNotifier(mContext, "正在加载中");
		params.add(new BasicNameValuePair("province", province));
		params.add(new BasicNameValuePair("business", business));
		params.add(new BasicNameValuePair("city", city));
		new Thread() {
			public void run() {
				String url = UrlUtils.BASEURL+"v1/province/get.json";
				String result = MyHttpRequest.getHttpGet(url, null);
//				Log.e("result", result.toString());
				if (result != null) {
					Message msg = new Message();
					msg.obj = result;
					msg.what = 1;
					handler.sendMessage(msg);
				}
			};
		}.start();
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			DialogUtils.getInstance().cancel();
			String result = (String) msg.obj;
			switch (msg.what) {
			case 0:
				
				break;
			case 1:
				try {
					ZhudianqhEntity entity = mGson.fromJson(result,
							ZhudianqhEntity.class);
					provinces = entity.getProvince();
					citys = entity.getCity();
					businesss = entity.getBusiness();
					adapter_pc.refreshAdapter(provinces);
					adapter_ct.refreshAdapter(citys);
					adapter_bus.refreshAdapter(businesss);
//					province=provinces.get(0).getId();
//					city=citys.get(0).getId();
//					business=businesss.get(0).getId();
//					business_name=businesss.get(0).getW_name();
				} catch (JsonSyntaxException e) {
					e.printStackTrace();
				}

				break;
			case 2:
				ToastUtil.Toast(mContext, "注册成功");
				finish();
				break;

			}
		};
	};

	/**
	 * @author kangming
	 *
	 * @param <T>所需费用适配器
	 */
	public class MySpAdapter_province extends BaseAdapter {
		protected List<Zhudianqh_ProvinceEntity> mDatas;

		public MySpAdapter_province(List<Zhudianqh_ProvinceEntity> mDatas) {
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
		public void refreshAdapter(List<Zhudianqh_ProvinceEntity> arrayList) {
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
			ViewHolder1 vh1 = null;
			if (convertView == null) {
				vh1 = new ViewHolder1();
				convertView = View.inflate(mContext, R.layout.yuezhan_sp_item,
						null);
				vh1.name = (TextView) convertView
						.findViewById(R.id.yuezhan_sp_item_name);
				convertView.setTag(vh1);
			} else {
				vh1 = (ViewHolder1) convertView.getTag();

			}
			vh1.name.setText(mDatas.get(position).getS_provname());

			return convertView;
		}

	}

	class ViewHolder1 {
		TextView name;
	}

	/**
	 * @author kangming
	 *
	 * @param <T>所需费用适配器
	 */
	public class MySpAdapter_city extends BaseAdapter {
		protected List<Zhudianqh_CityEntity> mDatas;

		public MySpAdapter_city(List<Zhudianqh_CityEntity> mDatas) {
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
		public void refreshAdapter(List<Zhudianqh_CityEntity> arrayList) {
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
			ViewHolder1 vh1 = null;
			if (convertView == null) {
				vh1 = new ViewHolder1();
				convertView = View.inflate(mContext, R.layout.yuezhan_sp_item,
						null);
				vh1.name = (TextView) convertView
						.findViewById(R.id.yuezhan_sp_item_name);
				convertView.setTag(vh1);
			} else {
				vh1 = (ViewHolder1) convertView.getTag();

			}
			vh1.name.setText(mDatas.get(position).getS_cityname());

			return convertView;
		}

	}

	/**
	 * @author kangming
	 *
	 * @param <T>所需费用适配器
	 */
	public class MySpAdapter_business extends BaseAdapter {
		protected List<Zhudianqh_BsEntity> mDatas;

		public MySpAdapter_business(List<Zhudianqh_BsEntity> mDatas) {
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
		public void refreshAdapter(List<Zhudianqh_BsEntity> arrayList) {
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
			ViewHolder1 vh1 = null;
			if (convertView == null) {
				vh1 = new ViewHolder1();
				convertView = View.inflate(mContext, R.layout.yuezhan_sp_item,
						null);
				vh1.name = (TextView) convertView
						.findViewById(R.id.yuezhan_sp_item_name);
				convertView.setTag(vh1);
			} else {
				vh1 = (ViewHolder1) convertView.getTag();

			}
			vh1.name.setText(mDatas.get(position).getW_name());

			return convertView;
		}

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.activity_regist3_bt_confirm:
//			Log.e("provice", province+"  "+city+"   "+business+"  ");
			Intent intent = new Intent(RegistActivity3.this,
					RegistActivity1.class);
			Re_wangbaEntity entity=new Re_wangbaEntity();
			entity.setProvince(province);
			entity.setCity(city);
			entity.setBusiness(business);
			entity.setBusiness_name(business_name);
			Log.e("entity", entity.toString());
			intent.putExtra("entity", entity);
			setResult(100, intent);
			finish();
//			putMsg();
			break;

		}
	}

	public void putMsg() {
		DialogUtils.getInstance().createNotifier(mContext, "正在加载中");
		final String uid=new SharUtil(mContext).getUid();
		
		/*清空数据，防止数据上次数据堆积*/
		params.clear();
		params.add(new BasicNameValuePair("province", province));
		params.add(new BasicNameValuePair("business", business));
		params.add(new BasicNameValuePair("city", city));
		new Thread(){
			public void run() {
				String url=UrlUtils.BASEURL+"v1/business/select.json?"+"uid="+uid;
				String result=MyHttpRequest.getHttpPost(url, params);
				if(result!=null){
//					Log.e("result", result.toString());
					try {
						TYEntity entity=mGson.fromJson(result, TYEntity.class);
						if(entity.getStatus()==0){
							
						}else if(entity.getStatus()==1){
							handler.sendEmptyMessage(2);
						}
					} catch (JsonSyntaxException e) {
						e.printStackTrace();
					}
					
				}
				
			};
		}.start();

	}

}
