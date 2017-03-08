package com.jishang.bimeng.activity.yuezhan.fq;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jishang.bimeng.R;
import com.jishang.bimeng.entity.yuezhan.fq.wfq.WfqEntity;
import com.jishang.bimeng.entity.yuezhan.fq.wfq.Wfq_DataEntity;
import com.jishang.bimeng.entity.yuezhan.fq.wfq.fenqian.FenqianEntity;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.Internet.MyHttpRequest;
import com.jishang.bimeng.utils.dialog.DialogUtils;
import com.jishang.bimeng.utils.url.UrlUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class Fragment_fq_b extends Fragment {

	private ListView mList_wfq, mList_wcjfq;
	private Gson mGson;
	private Context mContext;
	private List<BasicNameValuePair> params;
	private String token;
	protected ImageLoader imageLoader_head;
	private DisplayImageOptions options_head; // 设置图片显示相关参数
	private List<Wfq_DataEntity> entities_wfq = new ArrayList<Wfq_DataEntity>();
	private List<Wfq_DataEntity> entities_wcjfq = new ArrayList<Wfq_DataEntity>();
	private ItemListAdapter adtapter;
	private ItemListAdapter_wcj adapter_wcj;
	private TextView mTv_name;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_fq_yuezhan, null);
		mContext = getActivity();
		initView(view);
		initData();
		addListener();
		return view;

	}

	public void initView(View view) {
		params = new ArrayList<BasicNameValuePair>();
		mGson = new Gson();
		mList_wfq = (ListView) view.findViewById(R.id.activity_fq_list_wfq);
		mList_wcjfq = (ListView) view.findViewById(R.id.activity_fq_list_wcjfq);
		token = new SharUtil(mContext).getAccess_token();
		imageLoader_head = ImageLoader.getInstance();
		options_head = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_stub) // 设置图片下载期间显示的图片
				.showImageForEmptyUri(R.drawable.ic_empty) // 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(R.drawable.ic_error) // 设置图片加载或解码过程中发生错误显示的图片
				.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
				.cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
				.displayer(new RoundedBitmapDisplayer(10)) // 设置成圆角图片
				.build(); // 构建完成
		adtapter = new ItemListAdapter();
		adapter_wcj = new ItemListAdapter_wcj();
		mList_wfq.setAdapter(adtapter);
		mList_wcjfq.setAdapter(adapter_wcj);
	}

	public void initData() {
		getWfq();
		getWcjfq();
	}
	public void getWfq() {
		DialogUtils.getInstance().createNotifier(mContext, "正在加载中");
		params.add(new BasicNameValuePair("1", "1"));
		new Thread() {
			public void run() {
				String url = UrlUtils.BASEURL+"v1/yz/my_creat_yz_ing.json";
				String resusult = MyHttpRequest.getHttpPostBasic(url, params,
						token);
				if (resusult != null) {
					Log.e("result_发起", resusult.toString());
					try {
						WfqEntity entity = mGson.fromJson(resusult,
								WfqEntity.class);
						// Log.e("entity", entity.toString());\
						entities_wfq = entity.getData();
						handler.sendEmptyMessage(0);
					} catch (JsonSyntaxException e) {
						e.printStackTrace();
					}

				}
			};
		}.start();
	}

	public void getWcjfq() {
		params.add(new BasicNameValuePair("1", "1"));
		new Thread() {
			public void run() {
				String url = UrlUtils.BASEURL+"v1/yz/my_creat_yz_past.json";
				String resusult = MyHttpRequest.getHttpPostBasic(url, params,
						token);
				if (resusult != null) {
					Log.e("result--发起cj", resusult.toString());
					try {
						WfqEntity entity = mGson.fromJson(resusult,
								WfqEntity.class);
						// Log.e("entity", entity.toString());\
						entities_wcjfq = entity.getData();
						handler.sendEmptyMessage(1);
					} catch (JsonSyntaxException e) {
						e.printStackTrace();
					}

				}
			};
		}.start();
	}

	public void addListener() {

	}
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			DialogUtils.getInstance().cancel();
			switch (msg.what) {
			case 0:
				adtapter.notifyDataSetChanged();
				break;
			case 1:
				adapter_wcj.notifyDataSetChanged();
				break;
			case 2:
				FenqianEntity entity=(FenqianEntity) msg.obj;
				Intent intent=new Intent(mContext, FenqianActivity.class);
				intent.putExtra("entity", entity);
				startActivity(intent);
				break;

			}
		};
	};


	public class ItemListAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			if (entities_wfq == null) {
				return 0;
			}
			return entities_wfq.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder = null;
			if (convertView == null) {
				convertView = View
						.inflate(mContext, R.layout.fqlist_item, null);
				viewHolder = new ViewHolder();

				viewHolder.mTv_yz_title = (TextView) convertView
						.findViewById(R.id.yzlist_item_tv_yz_title);
				viewHolder.mTv_need_persons = (TextView) convertView
						.findViewById(R.id.yzlist_item_tv_need_persons);
				viewHolder.mTv_server = (TextView) convertView
						.findViewById(R.id.yzlist_item_tv_yz_server);
				viewHolder.mTv_peple_money = (TextView) convertView
						.findViewById(R.id.yzlist_item_tv_pay_peple_money);
				viewHolder.mTv_yz_name = (TextView) convertView
						.findViewById(R.id.yzlist_item_tv_yz_name);
				viewHolder.mTv_start_time = (TextView) convertView
						.findViewById(R.id.yzlist_item_tv_start_time);
				viewHolder.image = (ImageView) convertView
						.findViewById(R.id.img_launch_ball_games_head);
				viewHolder.mTv_money_name = (TextView) convertView
						.findViewById(R.id.yzlist_item_tv_pay_peple_money_name);
				viewHolder.mTv_begin = (TextView) convertView
						.findViewById(R.id.yzlist_item_begin);
				viewHolder.mTv_cancel = (TextView) convertView
						.findViewById(R.id.yzlist_item_cancel);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			final Wfq_DataEntity entity = entities_wfq.get(position);
			if ((entity.getPay_get()).equals("0")) {
				viewHolder.mTv_money_name.setText("支付:   ");
				viewHolder.mTv_peple_money.setText("  -"
						+ entity.getPay_peple_money());
				viewHolder.mTv_peple_money.setTextColor(getResources().getColor(R.color.green));
			} else {
				viewHolder.mTv_money_name.setText("收入:    ");
				viewHolder.mTv_peple_money.setText("+"
						+ entity.getPay_peple_money());
				viewHolder.mTv_peple_money.setTextColor(getResources().getColor(R.color.red));
			}

			viewHolder.mTv_yz_title.setText("游戏:    "+entity.getYz_title());
			viewHolder.mTv_need_persons.setText("人数:    "+entity.getNeed_peple_item()
					+ "/" + entity.getNeed_persons());
			viewHolder.mTv_server.setText("服区:    "+entity.getYz_server());

			viewHolder.mTv_yz_name.setText("发起:    "+entity.getUser().getUsername());
			viewHolder.mTv_start_time.setText("时间:    "+times(entity.getStart_time()));

			imageLoader_head.displayImage(entity.getYz_img(), viewHolder.image,
					options_head);
			viewHolder.mTv_begin.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					startGame(entity.getYz_id());

				}
			});
			return convertView;
		}

	}
	public void startGame(String yz_id){
		params.add(new BasicNameValuePair("yz_id", yz_id));
		new Thread(){
			public void run() {
				String url=UrlUtils.BASEURL+"v1/yz/start_game.json";
				String result=MyHttpRequest.getHttpPostBasic(url, params, token);
				if(result!=null){
//					Log.e("result_发起", result.toString());
					try {
						FenqianEntity entity=mGson.fromJson(result, FenqianEntity.class);
//					Log.e("entity", entity.toString());
						Message msg=new Message();
						msg.what=2;
						msg.obj=entity;
						handler.sendMessage(msg);
					} catch (JsonSyntaxException e) {
						e.printStackTrace();
					}
				}
			};
		}.start();
	}

	public class ViewHolder {
		public TextView mTv_yz_title, mTv_need_persons, mTv_server,
				mTv_peple_money, mTv_money_name, mTv_yz_name, mTv_start_time,
				mTv_location, mTv_begin,mTv_cancel;
		private ImageView image;
	}

	public class ItemListAdapter_wcj extends BaseAdapter {

		@Override
		public int getCount() {
			if (entities_wfq == null) {
				return 0;
			}
			return entities_wfq.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder1 viewHolder = null;
			if (convertView == null) {
				convertView = View
						.inflate(mContext, R.layout.fqlist_item, null);
				viewHolder = new ViewHolder1();

				viewHolder.mTv_yz_title = (TextView) convertView
						.findViewById(R.id.yzlist_item_tv_yz_title);
				viewHolder.mTv_need_persons = (TextView) convertView
						.findViewById(R.id.yzlist_item_tv_need_persons);
				viewHolder.mTv_server = (TextView) convertView
						.findViewById(R.id.yzlist_item_tv_yz_server);
				viewHolder.mTv_peple_money = (TextView) convertView
						.findViewById(R.id.yzlist_item_tv_pay_peple_money);
				viewHolder.mTv_yz_name = (TextView) convertView
						.findViewById(R.id.yzlist_item_tv_yz_name);
				viewHolder.mTv_start_time = (TextView) convertView
						.findViewById(R.id.yzlist_item_tv_start_time);
				viewHolder.image = (ImageView) convertView
						.findViewById(R.id.img_launch_ball_games_head);
				viewHolder.mTv_money_name = (TextView) convertView
						.findViewById(R.id.yzlist_item_tv_pay_peple_money_name);

				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder1) convertView.getTag();
			}
			Wfq_DataEntity entity = entities_wcjfq.get(position);
			if ((entity.getPay_get()).equals("0")) {
				viewHolder.mTv_money_name.setText("支付:   ");
				viewHolder.mTv_peple_money.setText("  -"
						+ entity.getPay_peple_money());
				viewHolder.mTv_peple_money.setTextColor(getResources().getColor(R.color.green));
			} else {
				viewHolder.mTv_money_name.setText("收入:    ");
				viewHolder.mTv_peple_money.setText("+"
						+ entity.getPay_peple_money());
				viewHolder.mTv_peple_money.setTextColor(getResources().getColor(R.color.red));
			}

			viewHolder.mTv_yz_title.setText("游戏:    "+entity.getYz_title());
			viewHolder.mTv_need_persons.setText("人数:    "+entity.getNeed_peple_item()
					+ "/" + entity.getNeed_persons());
			viewHolder.mTv_server.setText("服区:    "+entity.getYz_server());

			viewHolder.mTv_yz_name.setText("发起:    "+entity.getUser().getUsername());
			viewHolder.mTv_start_time.setText("时间:    "+times(entity.getStart_time()));
			imageLoader_head.displayImage(entity.getYz_img(), viewHolder.image,
					options_head);

			return convertView;
		}

	}

	public class ViewHolder1 {
		public TextView mTv_yz_title, mTv_need_persons, mTv_server,
				mTv_peple_money, mTv_money_name, mTv_yz_name, mTv_start_time,
				mTv_location, mTv_begin;
		private ImageView image;
	}

	public String times(String time) {
		SimpleDateFormat sdr = new SimpleDateFormat("MM-dd-HH:mm");
		@SuppressWarnings("unused")
		// long lcc = Long.valueOf(time);
		int i = Integer.parseInt(time);
		String times = sdr.format(new Date(i * 1000L));
		return times;

	}
}
