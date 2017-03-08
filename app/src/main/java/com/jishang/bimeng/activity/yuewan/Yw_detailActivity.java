package com.jishang.bimeng.activity.yuewan;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jishang.bimeng.R;
import com.jishang.bimeng.activity.base.BaseActivity;
import com.jishang.bimeng.activity.yuewan.YwListActivity.ViewHolder;
import com.jishang.bimeng.entity.yuewan.list.YwList_dataEntity;
import com.jishang.bimeng.entity.yuewan.list.Ywlist_data_ugEntity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class Yw_detailActivity extends BaseActivity implements OnClickListener {
	private Intent mItent;
	private ListView mList;
	private String token;
	private List<BasicNameValuePair> params;
	private Gson mGson;
	private Context mContext;
	protected ImageLoader imageLoader_head;
	private DisplayImageOptions options_head; // 设置图片显示相关参数
	List<Ywlist_data_ugEntity> usergames = new ArrayList<Ywlist_data_ugEntity>();
	private MyList adapter;
	private YwList_dataEntity entity;
	private ImageView mImg_headimg;
	private TextView mTv_name, mTv_id,mTv_confrim;

	@Override
	public int initResource() {
		return R.layout.activity_yw_detail;
	}

	@Override
	public void initView() {
		mContext = this;
		mItent = getIntent();
		entity = (YwList_dataEntity) mItent.getSerializableExtra("entity");
		usergames = entity.getUsergames();
		mList = (ListView) findViewById(R.id.activity_detail_list);
		mImg_headimg = (ImageView) findViewById(R.id.activity_ywdetail_imgv_headimg);
		mTv_name = (TextView) findViewById(R.id.activity_ywdetail_name);
		mTv_id = (TextView) findViewById(R.id.activity_ywdetail_id);
		mTv_confrim=(TextView) findViewById(R.id.activity_ywdetail_begin);
		options_head = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_stub) // 设置图片下载期间显示的图片
				.showImageForEmptyUri(R.drawable.ic_empty) // 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(R.drawable.ic_error) // 设置图片加载或解码过程中发生错误显示的图片
				.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
				.cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
				.displayer(new RoundedBitmapDisplayer(10)) // 设置成圆角图片
				.build(); // 构建完成
		imageLoader_head = ImageLoader.getInstance();
		// Log.e("entity-------", entity.toString());
		adapter = new MyList();
		mList.setAdapter(adapter);
		if (adapter != null) {
			adapter.notifyDataSetChanged();
		}

	}

	@Override
	public void initData() {

		imageLoader_head.displayImage(entity.getHead_img(), mImg_headimg,
				options_head);
		mTv_id.setText(entity.getUid());
		mTv_name.setText(entity.getUsername());
	}

	@Override
	public void addListener() {
		mTv_confrim.setOnClickListener(this);
	}

	public void back(View v) {
		finish();
	}

	class MyList extends BaseAdapter {

		@Override
		public int getCount() {
			if (usergames.size() == 0) {
				return 0;
			}
			return usergames.size();
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
			ViewHolder vh = null;
			if (convertView == null) {
				vh = new ViewHolder();
				convertView = View.inflate(mContext,
						R.layout.activity_ywdetail_item, null);
				vh.img_gameimg = (ImageView) convertView
						.findViewById(R.id.activity_ywdetail_item_gameimg);
				vh.mTv_gamename = (TextView) convertView
						.findViewById(R.id.activity_ywdetail_item_gamename);
				vh.mTv_fwq = (TextView) convertView
						.findViewById(R.id.activity_ywdetail_item_fwq);
				vh.mTv_position = (TextView) convertView
						.findViewById(R.id.activity_ywdetail_item_position);
				vh.mTv_dj = (TextView) convertView
						.findViewById(R.id.activity_ywdetail_item_dj);
				convertView.setTag(vh);
			} else {
				vh = (ViewHolder) convertView.getTag();
			}
			Ywlist_data_ugEntity entity = usergames.get(position);
			vh.mTv_gamename.setText(entity.getYw_title());
			vh.mTv_dj.setText(entity.getYw_pattern());
			vh.mTv_fwq.setText(entity.getYw_server());
			vh.mTv_position.setText(entity.getYw_position());
			imageLoader_head.displayImage(entity.getYw_img(), vh.img_gameimg,
					options_head);
			return convertView;
		}

	}

	public class ViewHolder {
		TextView mTv_gamename, mTv_fwq, mTv_dj, mTv_position;
		ImageView img_gameimg;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.activity_ywdetail_begin:
			putMsg();
			break;

		}

	}

	public void putMsg() {
		Intent intent=new Intent(mContext, Yw_listConfrimActivity.class);
		intent.putExtra("entity", entity);
		startActivity(intent);
	}

}
