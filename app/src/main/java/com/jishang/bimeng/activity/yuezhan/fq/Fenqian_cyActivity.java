package com.jishang.bimeng.activity.yuezhan.fq;

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
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jishang.bimeng.R;
import com.jishang.bimeng.activity.base.BaseActivity;
import com.jishang.bimeng.entity.yuezhan.fq.wfq.fenqian.Fenqian_confimEntity;
import com.jishang.bimeng.entity.yuezhan.fq.wfq.fenqian_cy.Fenqian_cyDataEntity;
import com.jishang.bimeng.entity.yuezhan.fq.wfq.fenqian_cy.Fenqian_cyEntity;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.ToastUtil;
import com.jishang.bimeng.utils.Internet.MyHttpRequest;
import com.jishang.bimeng.utils.dialog.DialogUtils;
import com.jishang.bimeng.utils.url.UrlUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class Fenqian_cyActivity extends BaseActivity implements OnClickListener {
	private Gson mGson;
	private Context mContext;
	private List<BasicNameValuePair> params;
	private String token;
	private Intent mItent;
	private Fenqian_cyDataEntity data = new Fenqian_cyDataEntity();
	protected ImageLoader imageLoader_head;
	private DisplayImageOptions options_head; // 设置图片显示相关参数
	private TextView mTv_name, mTv_username, mTv_id, mTv_confirm;
	private ImageView mImg_head;

	@Override
	public int initResource() {
		return R.layout.activity_fenqian_cy;
	}

	@Override
	public void initView() {
		mTv_name = (TextView) findViewById(R.id.activity_fenqian_cy_name_);
		mTv_username = (TextView) findViewById(R.id.activity_fenqian_cy_name);
		mTv_id = (TextView) findViewById(R.id.activity_fenqian_cy_id);
		mTv_confirm = (TextView) findViewById(R.id.activity_fenqian_cy_confirm);
		mImg_head=(ImageView) findViewById(R.id.activity_fenqian_cy_imgv_headimg);
		params = new ArrayList<BasicNameValuePair>();
		mContext = this;
		mGson = new Gson();
		mItent = getIntent();
		token = new SharUtil(mContext).getAccess_token();
		Fenqian_cyEntity entity = (Fenqian_cyEntity) mItent
				.getSerializableExtra("entity");
		data = entity.getData();
		imageLoader_head = ImageLoader.getInstance();
		options_head = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_stub) // 设置图片下载期间显示的图片
				.showImageForEmptyUri(R.drawable.ic_empty) // 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(R.drawable.ic_error) // 设置图片加载或解码过程中发生错误显示的图片
				.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
				.cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
				.displayer(new RoundedBitmapDisplayer(10)) // 设置成圆角图片
				.build(); // 构建完成
		Log.e("entity-----", entity.toString());
		mTv_name.setText("确认支付");
	}

	@Override
	public void initData() {
		mTv_username.setText(data.getJoin_user().getUsername());
		mTv_id.setText(data.getJoin_user().getUid());
		imageLoader_head.displayImage(data.getJoin_user().getHead_img(), mImg_head,options_head);
		
		

	}

	public void confirm(String promoter_uid, String join_peple_uid,
			String yuezhan_id) {
		DialogUtils.getInstance().createNotifier(mContext, "正在加载中");
		params.add(new BasicNameValuePair("promoter_uid", promoter_uid));
		params.add(new BasicNameValuePair("join_peple_uid", join_peple_uid));
		params.add(new BasicNameValuePair("yuezhan_id", yuezhan_id));
		new Thread() {
			public void run() {
				String url = UrlUtils.BASEURL+"v1/yz/start_game_confirm.json";
				String result = MyHttpRequest.getHttpPostBasic(url, params,
						token);
				if (result != null) {

					try {
						Fenqian_confimEntity entity = mGson.fromJson(result,
								Fenqian_confimEntity.class);
						if (entity.getStatus() == 0) {
							Message msg = new Message();
							msg.what = 0;
							msg.obj = entity.getErrors();
							handler.sendMessage(msg);
						} else {
							handler.sendEmptyMessage(1);
						}
					} catch (JsonSyntaxException e) {
						e.printStackTrace();
					}

				}
			};
		}.start();
	}

	@Override
	public void addListener() {
		mTv_confirm.setOnClickListener(this);

	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			DialogUtils.getInstance().cancel();
			switch (msg.what) {
			case 0:
				String erro = (String) msg.obj;
				ToastUtil.Toast(mContext, erro);

				break;
			case 1:
				ToastUtil.Toast(mContext, "支付成功！");
				break;

			}
		};
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.activity_fenqian_cy_confirm:
			confirm(data.getPromoter_uid(), data.getJoin_peple_uid(),
					data.getYuezhan_id());
			break;

		}

	}

	public void back(View v) {
		finish();
	}

}
