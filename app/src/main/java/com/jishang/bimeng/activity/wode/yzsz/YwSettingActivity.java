package com.jishang.bimeng.activity.wode.yzsz;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jishang.bimeng.R;
import com.jishang.bimeng.activity.base.BaseActivity;
import com.jishang.bimeng.utils.SharUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class YwSettingActivity extends BaseActivity implements OnClickListener {
	private ImageView mImgv_head;
	private TextView mTv_name, mTv_barname, mTv_id;
	protected ImageLoader imageLoader_head;
	private DisplayImageOptions options_head; // 设置图片显示相关参数
	private Context mContext;
	private SharUtil mShareUtil;
	private RelativeLayout mRl_xfsetting, mRl_gameSetting;

	@Override
	public int initResource() {
		return R.layout.activity_ywsetting;
	}

	@Override
	public void initView() {
		mContext = this;
		mShareUtil = new SharUtil(mContext);
		mImgv_head = (ImageView) findViewById(R.id.activity_ywsetting_imgv_headimg);
		mTv_name = (TextView) findViewById(R.id.activity_ywsetting_name);
		mTv_id = (TextView) findViewById(R.id.activity_ywsetting_id);
		mTv_barname = (TextView) findViewById(R.id.activity_ywsetting_bar_name);
		mRl_xfsetting = (RelativeLayout) findViewById(R.id.activity_ywsetting_xf_setting);
		mRl_gameSetting = (RelativeLayout) findViewById(R.id.activity_ywsetting_game_setting);

		imageLoader_head = ImageLoader.getInstance();
		options_head = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_stub) // 设置图片下载期间显示的图片
				.showImageForEmptyUri(R.drawable.ic_empty) // 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(R.drawable.ic_error) // 设置图片加载或解码过程中发生错误显示的图片
				.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
				.cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
				.displayer(new RoundedBitmapDisplayer(10)) // 设置成圆角图片
				.build(); // 构建完成
		// Log.e("entity-----", entity.toString());
		mTv_barname.setText("约玩设置");
	}

	@Override
	public void initData() {
		String imghead = mShareUtil.getHead_img();
		imageLoader_head.displayImage(imghead, mImgv_head, options_head);
		mTv_id.setText(mShareUtil.getUid());
		mTv_name.setText(mShareUtil.getUserName());

	}

	@Override
	public void addListener() {
		mRl_gameSetting.setOnClickListener(this);
		mRl_xfsetting.setOnClickListener(this);
	}

	public void back(View v) {
		finish();
	}

	@Override
	public void onClick(View v) {
		Intent intent=null;
		switch (v.getId()) {
		case R.id.activity_ywsetting_xf_setting:

			break;
		case R.id.activity_ywsetting_game_setting:
			intent=new Intent(mContext, YwgameSetting.class);
			startActivity(intent);

			break;

		}

	}

}
