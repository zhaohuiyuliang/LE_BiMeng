package com.jishang.bimeng.activity.addfd;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.easemob.chat.EMContactManager;
import com.jishang.bimeng.R;
import com.jishang.bimeng.activity.addfd.widget.PullScrollView;
import com.jishang.bimeng.activity.base.BaseActivity;
import com.jishang.bimeng.entity.chat.UserDetailEntity;
import com.jishang.bimeng.utils.ToastUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class AddFriendActivity_b extends BaseActivity implements
		PullScrollView.OnTurnListener, OnClickListener {
	private PullScrollView mScrollView;
	private ImageView mHeadImg, mBackImg;
	private Intent intent;
	UserDetailEntity entity;
	private TextView mTv_user_name;
	private TextView mTv_user_post;
	private TextView mTv_user_book;
	protected ImageLoader imageLoader_head;
	private DisplayImageOptions options_head; // 设置图片显示相关参数
	private List<BasicNameValuePair> params;
	private Button mBt_add;
	private String release="0";
	private ProgressDialog progressDialog;
	private String toAddUsername="";

	@Override
	public int initResource() {
		return R.layout.activity_addfd;
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub

		params = new ArrayList<BasicNameValuePair>();
		imageLoader_head = ImageLoader.getInstance();
		options_head = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_stub) // 设置图片下载期间显示的图片
				.showImageForEmptyUri(R.drawable.ic_empty) // 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(R.drawable.ic_error) // 设置图片加载或解码过程中发生错误显示的图片
				.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
				.cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
				.displayer(new RoundedBitmapDisplayer(10)) // 设置成圆角图片
				.build(); // 构建完成
		intent = getIntent();
		entity = (UserDetailEntity) intent.getSerializableExtra("entity");
		toAddUsername=entity.getData().getH_username();
		mScrollView = (PullScrollView) findViewById(R.id.scroll_view);
		mBackImg = (ImageView) findViewById(R.id.background_img);
		mHeadImg = (ImageView) findViewById(R.id.user_avatar);
		mTv_user_name = (TextView) findViewById(R.id.user_name);
		mTv_user_post = (TextView) findViewById(R.id.user_post);
		mTv_user_book = (TextView) findViewById(R.id.user_book);
		mBt_add = (Button) findViewById(R.id.activity_addfd_bt_add);
		if(entity.getStatus()==1){
			mBt_add.setVisibility(View.VISIBLE);
		}else{
			mBt_add.setVisibility(View.GONE);
			
		}
		mScrollView.setHeader(mBackImg);
		mScrollView.setOnTurnListener(this);
	}

	@Override
	public void initData() {
		mTv_user_name.setText(entity.getData().getUsername());
		mTv_user_post.setText("网吧名字：" + entity.getData().getBusiness());
		mTv_user_book.setText("城市:" + entity.getData().getProvince() + entity.getData().getCity());
		imageLoader_head.displayImage(entity.getData().getHead_img(), mHeadImg,
				options_head);
	}

	@Override
	public void addListener() {
		mBt_add.setOnClickListener(this);

	}

	@Override
	public void onTurn() {

	}

	public void back(View view) {
		finish();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.activity_addfd_bt_add:
			AddFd();
			break;

		}

	}

	public void AddFd() {

		
		
		progressDialog = new ProgressDialog(this);
		String stri = "正在发送请求";
		progressDialog.setMessage(stri);
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.show();
		
		new Thread(new Runnable() {
			public void run() {
				
				try {
					//demo写死了个reason，实际应该让用户手动填入
					
					String s ="加个好友呗";
					EMContactManager.getInstance().addContact(toAddUsername, s);
					Log.e("toAddUsername", toAddUsername);
					runOnUiThread(new Runnable() {
						public void run() {
							progressDialog.dismiss();
							String s1 = getResources().getString(R.string.send_successful);
							Toast.makeText(getApplicationContext(), s1, 1).show();
							finish();
						}
					});
				} catch (final Exception e) {
					runOnUiThread(new Runnable() {
						public void run() {
							progressDialog.dismiss();
							String s2 = "添加好友失败";
							Toast.makeText(getApplicationContext(), s2 + e.getMessage(), 1).show();
						}
					});
				}
			}
		}).start();
	}
	Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				ToastUtil.Toast(AddFriendActivity_b.this, "添加好友错误！");
				break;
			case 1:
				ToastUtil.Toast(AddFriendActivity_b.this, "添加成功");
				finish();
				break;
			case 2:
				
				break;

			}
		};
	};

}
