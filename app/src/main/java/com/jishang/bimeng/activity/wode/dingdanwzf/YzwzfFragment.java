package com.jishang.bimeng.activity.wode.dingdanwzf;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jishang.bimeng.R;
import com.jishang.bimeng.activity.yuezhan.wzf.WzfddActivity.ItemListAdapter;
import com.jishang.bimeng.activity.yuezhan.wzf.WzfddActivity.ViewHolder;
import com.jishang.bimeng.activity.zhifu.ZfListActivity;
import com.jishang.bimeng.entity.yuezhan.confirm.C_DataEntity;
import com.jishang.bimeng.entity.yuezhan.wzf.CancelEntity;
import com.jishang.bimeng.entity.yuezhan.wzf.WzfEntity;
import com.jishang.bimeng.entity.yuezhan.wzf.Wzf_dataEntity;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.ToastUtil;
import com.jishang.bimeng.utils.Internet.MyHttpRequest;
import com.jishang.bimeng.utils.dialog.DialogUtils;
import com.jishang.bimeng.utils.url.UrlUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class YzwzfFragment extends Fragment {
	private View v;
	private Gson mGson;
	private Context mContext;
	private List<BasicNameValuePair> params;
	private String token;
	protected ImageLoader imageLoader_head;
	private DisplayImageOptions options_head; // 设置图片显示相关参数
	private List<Wzf_dataEntity> entities;
	private ItemListAdapter adtapter;
	private ListView mList;
	C_DataEntity entity1 = null;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.activity_wzfdd, null);
		mContext = getActivity();
		initView(v);
		getMsg();
		return v;
	}
	public void initView(View v) {
		params = new ArrayList<BasicNameValuePair>();
		adtapter = new ItemListAdapter();
		mGson = new Gson();
		token = new SharUtil(mContext).getAccess_token();
		mList = (ListView)v.findViewById(R.id.activity_wzfdd_list);
		imageLoader_head = ImageLoader.getInstance();
		options_head = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_stub) // 设置图片下载期间显示的图片
				.showImageForEmptyUri(R.drawable.ic_empty) // 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(R.drawable.ic_error) // 设置图片加载或解码过程中发生错误显示的图片
				.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
				.cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
				.displayer(new RoundedBitmapDisplayer(10)) // 设置成圆角图片
				.build(); // 构建完成

		mList.setAdapter(adtapter);

	}

	public void getMsg() {
		DialogUtils.getInstance().createNotifier(mContext, "正在加载中");
		params.add(new BasicNameValuePair("1", "1"));
		new Thread() {
			public void run() {
				String url = UrlUtils.BASEURL+"v1/yz/my_yz_pay_no.json";
				String resusult = MyHttpRequest.getHttpPostBasic(url, params,
						token);
				// Log.e("result", resusult.toString());
				if (resusult != null) {
					Log.e("result", resusult.toString());
					try {
						WzfEntity entity = mGson.fromJson(resusult,
								WzfEntity.class);
						entities = entity.getData();
						handler.sendEmptyMessage(0);
						// Log.e("entity", entity.toString());
					} catch (JsonSyntaxException e) {
						e.printStackTrace();
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
				adtapter.notifyDataSetChanged();
				break;
			case 1:
				ToastUtil.Toast(mContext, "取消成功");
				break;

			}
		};
	};

	public class ItemListAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			if (entities == null) {
				return 0;
			}
			return entities.size();
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
						.inflate(mContext, R.layout.yzwzflist_item, null);
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
				viewHolder.mTv_location = (TextView) convertView
						.findViewById(R.id.yzlist_item_tv_location);
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
			Wzf_dataEntity entity = entities.get(position);
			if ((entity.getGame().getPay_get()).equals("0")) {
				viewHolder.mTv_money_name.setText("人民币:  ");
				viewHolder.mTv_peple_money.setText("  -"
						+ entity.getGame().getPay_peple_money());
				viewHolder.mTv_peple_money.setTextColor(getResources()
						.getColor(R.color.green));
			} else {
				viewHolder.mTv_money_name.setText("人民币:  ");
				viewHolder.mTv_peple_money.setText("+"
						+ entity.getGame().getPay_peple_money());
				viewHolder.mTv_peple_money.setTextColor(getResources()
						.getColor(R.color.red));
			}

			viewHolder.mTv_yz_title.setText("游戏名:  "+entity.getGame().getYz_title());
			viewHolder.mTv_need_persons.setText("玩家数:  "+entity.getGame().getNeed_peple_item()
					+ "/" + entity.getNeed_persons());
			viewHolder.mTv_server.setText("服务器:  "+entity.getGame().getYz_server());

			viewHolder.mTv_yz_name.setText(new SharUtil(mContext).getUserName());
			viewHolder.mTv_start_time.setText("开始时:  "+times(entity.getGame().getYz_created_at()));
			imageLoader_head.displayImage(entity.getGame().getUser().getHead_img(), viewHolder.image,
					options_head);
			setEntity(entity);
			viewHolder.mTv_begin.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(mContext, ZfListActivity.class);
					intent.putExtra("entity1", entity1);
					startActivity(intent);

				}
			});
			viewHolder.mTv_cancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					cancel();

				}
			});

			return convertView;
		}

	}

	public void cancel(){
		params.add(new BasicNameValuePair("p_id", entity1.getP_id()));
		params.add(new BasicNameValuePair("uid", entity1.getF_uid()));
		params.add(new BasicNameValuePair("order_notice_sn", entity1.getOrder_notice_sn()));
		Log.e("--", entity1.getP_id()+" "+entity1.getF_uid()+"  "+entity1.getOrder_notice_sn());
		new Thread(){
			public void run() {
				String url=UrlUtils.BASEURL+"v1/yz/off_payment_notice.json";
				String result=MyHttpRequest.getHttpPostBasic(url, params, token);
				if(result!=null){
//					Log.e("result", result.toString());
					CancelEntity entity;
					try {
						entity = mGson.fromJson(result, CancelEntity.class);
						if(entity.getStatus()==1){
							handler.sendEmptyMessage(1);
						}
					} catch (JsonSyntaxException e) {
						e.printStackTrace();
					}
					
				}
			};
		}.start();
	}

	/**
	 * @param data
	 *            初始化订单信息
	 */
	public void setEntity(Wzf_dataEntity entity) {
		entity1 = new C_DataEntity();
		entity1.setNeed_persons(entity.getNeed_persons());
		entity1.setApp_money(entity.getApp_money());
		entity1.setTotal_money(entity.getTotal_money());
		entity1.setOne_peple_money(entity.getOne_peple_money());
		entity1.setP_id(entity.getP_id());
		entity1.setF_uid(entity.getUser_uid());
		entity1.setOrder_notice_sn(entity.getOrder_notice_sn());
	}

	public class ViewHolder {
		public TextView mTv_yz_title, mTv_need_persons, mTv_server,
				mTv_peple_money, mTv_money_name, mTv_yz_name, mTv_start_time,
				mTv_location, mTv_begin, mTv_cancel;
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
