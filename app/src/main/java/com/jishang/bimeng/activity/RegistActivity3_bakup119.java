package com.jishang.bimeng.activity;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.jishang.bimeng.R;
import com.jishang.bimeng.activity.base.BaseActivity;
import com.jishang.bimeng.entity.regist.BarEntity;
import com.jishang.bimeng.parse.ParseUtil;
import com.jishang.bimeng.utils.Internet.MyHttpRequest;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * @author kangming 注册的第三个界面
 */
public class RegistActivity3_bakup119 extends BaseActivity implements
		OnWheelChangedListener {
	private JSONObject mJsonObj;
	private WheelView mProvince;
	private WheelView mCity;
	private WheelView mArea;
	private String[] mProvinceDatas;
	private Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
	private Map<String, String[]> mAreaDatasMap = new HashMap<String, String[]>();
	private String mCurrentProviceName;
	private String mCurrentCityName;
	private String mCurrentAreaName = "";

	private GridView mGv;
	private String[] imageUrls; // 图片路径
	private DisplayImageOptions options; // 设置图片显示相关参数
	protected ImageLoader imageLoader;
	private ProgressDialog dialog;

	@Override
	public int initResource() {
		return R.layout.citys;
	}

	public void initView() {
		mGv = (GridView) findViewById(R.id.activity_regist_gv);
		// imageUrls=Constants.images;
		imageLoader = ImageLoader.getInstance();
		mProvince = (WheelView) findViewById(R.id.id_province);
		mCity = (WheelView) findViewById(R.id.id_city);
		mArea = (WheelView) findViewById(R.id.id_area);

		// 使用DisplayImageOptions.Builder()创建DisplayImageOptions
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_stub) // 设置图片下载期间显示的图片
				.showImageForEmptyUri(R.drawable.ic_empty) // 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(R.drawable.ic_error) // 设置图片加载或解码过程中发生错误显示的图片
				.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
				.cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
				.displayer(new RoundedBitmapDisplayer(10)) // 设置成圆角图片
				.build(); // 构建完成

	}

	@Override
	public void initData() {
		Setprogrese();
		initPhoto();
		initJsonData();
		initDatas();
		updateCities();
		updateAreas();
	}

	public void initPhoto() {
		new Thread() {
			public void run() {

				String url = "http://apicms.gbimoo.com/v1/province/get.json";
				String result = MyHttpRequest.getHttpGet(url, null);
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
			dialog.cancel();
			String result = (String) msg.obj;
			switch (msg.what) {
			case 1:
				List<BarEntity> entities = ParseUtil.getBanner_photo(result);
				imageUrls = new String[entities.size()];
				for (int i = 0; i < entities.size(); i++) {
					imageUrls[i] = entities.get(i).getW_img();
				}
				mGv.setAdapter(new ItemListAdapter(entities));
				break;

			}
		};
	};

	@Override
	public void addListener() {
		mProvince.setViewAdapter(new ArrayWheelAdapter<String>(this,
				mProvinceDatas));
		// 添加change事件
		mProvince.addChangingListener(this);
		// 添加change事件
		mCity.addChangingListener(this);
		// 添加change事件
		mArea.addChangingListener(this);

		mProvince.setVisibleItems(5);// 设置高度
		mCity.setVisibleItems(5);
		mArea.setVisibleItems(5);
	}

	/**
	 * 根据当前的市，更新区WheelView的信息
	 */
	private void updateAreas() {
		int pCurrent = mCity.getCurrentItem();
		mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
		String[] areas = mAreaDatasMap.get(mCurrentCityName);

		if (areas == null) {
			areas = new String[] { "" };
		}
		mArea.setViewAdapter(new ArrayWheelAdapter<String>(this, areas));
		mArea.setCurrentItem(0);
	}

	/**
	 * 根据当前的省，更新市WheelView的信息
	 */
	private void updateCities() {
		int pCurrent = mProvince.getCurrentItem();
		mCurrentProviceName = mProvinceDatas[pCurrent];
		String[] cities = mCitisDatasMap.get(mCurrentProviceName);
		if (cities == null) {
			cities = new String[] { "" };
		}
		mCity.setViewAdapter(new ArrayWheelAdapter<String>(this, cities));
		mCity.setCurrentItem(0);
		updateAreas();
	}

	/**
	 * 解析整个Json对象，完成后释放Json对象的内存
	 */
	private void initDatas() {
		try {
			JSONArray jsonArray = mJsonObj.getJSONArray("citylist");
			mProvinceDatas = new String[jsonArray.length()];
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonP = jsonArray.getJSONObject(i);// 每个省的json对象
				String province = jsonP.getString("p");// 省名字

				mProvinceDatas[i] = province;

				JSONArray jsonCs = null;
				try {
					/**
					 * Throws JSONException if the mapping doesn't exist or is
					 * not a JSONArray.
					 */
					jsonCs = jsonP.getJSONArray("c");
				} catch (Exception e1) {
					continue;
				}
				String[] mCitiesDatas = new String[jsonCs.length()];
				for (int j = 0; j < jsonCs.length(); j++) {
					JSONObject jsonCity = jsonCs.getJSONObject(j);
					String city = jsonCity.getString("n");// 市名字
					mCitiesDatas[j] = city;
					JSONArray jsonAreas = null;
					try {
						/**
						 * Throws JSONException if the mapping doesn't exist or
						 * is not a JSONArray.
						 */
						jsonAreas = jsonCity.getJSONArray("a");
					} catch (Exception e) {
						continue;
					}

					String[] mAreasDatas = new String[jsonAreas.length()];// 当前市的所有区
					for (int k = 0; k < jsonAreas.length(); k++) {
						String area = jsonAreas.getJSONObject(k).getString("s");// 区域的名称
						mAreasDatas[k] = area;
					}
					mAreaDatasMap.put(city, mAreasDatas);

				}

				mCitisDatasMap.put(province, mCitiesDatas);
				Log.e("mCitisDatasMap", mCitisDatasMap.toString());
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		mJsonObj = null;
	}

	/**
	 * 从assert文件夹中读取省市区的json文件，然后转化为json对象
	 */
	private void initJsonData() {
		try {
			StringBuffer sb = new StringBuffer();
			InputStream is = getAssets().open("city.json");
			int len = -1;
			byte[] buf = new byte[1024];
			while ((len = is.read(buf)) != -1) {
				sb.append(new String(buf, 0, len, "gbk"));
			}
			is.close();
			mJsonObj = new JSONObject(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * change事件的处理
	 */
	@Override
	public void onChanged(WheelView wheel, int oldValue, int newValue) {
		if (wheel == mProvince) {
			updateCities();
		} else if (wheel == mCity) {
			updateAreas();
		} else if (wheel == mArea) {
			mCurrentAreaName = mAreaDatasMap.get(mCurrentCityName)[newValue];

		}
	}

	public void showChoose(View view) {
		Toast.makeText(this,
				mCurrentProviceName + mCurrentCityName + mCurrentAreaName, 1)
				.show();
	}

	public class ItemListAdapter extends BaseAdapter {
		List<BarEntity> entities;

		public ItemListAdapter(List<BarEntity> entities) {
			this.entities = entities;
		}

		@Override
		public int getCount() {
			return imageUrls.length;
		}

		@Override
		public Object getItem(int position) {
			return imageUrls[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder = null;
			if (convertView == null) {
				convertView = getLayoutInflater().inflate(R.layout.item_list_regist,
						null);
				viewHolder = new ViewHolder();
				viewHolder.image = (ImageView) convertView
						.findViewById(R.id.iv_image);
				viewHolder.text = (TextView) convertView
						.findViewById(R.id.tv_introduce);
				viewHolder.bt=(Button) convertView.findViewById(R.id.choosedbt);
//				viewHolder.tg=(ToggleButton) findViewById(R.id.toggle_button);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}

			/**
			 * imageUrl 图片的Url地址 imageView 承载图片的ImageView控件 options
			 * DisplayImageOptions配置文件
			 */
			imageLoader.displayImage(imageUrls[position], viewHolder.image,
					options);
			

			// viewHolder.text.setText("Item " + (position + 1)); //
			// TextView设置文本
			BarEntity entity = entities.get(position);
			viewHolder.text.setText(entity.getW_name());
			return convertView;
		}

	}

	public class ViewHolder {
		public ImageView image;
		public TextView text;
		public Button bt;
		public ToggleButton tg;
	}

	public void Setprogrese() {
		dialog = new ProgressDialog(RegistActivity3_bakup119.this);
		dialog.setMessage("正在更新数据，请稍候。。。");
		dialog.show();
	}

}
