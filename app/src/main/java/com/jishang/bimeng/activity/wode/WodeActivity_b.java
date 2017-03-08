package com.jishang.bimeng.activity.wode;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jishang.bimeng.R;
import com.jishang.bimeng.activity.base.BaseActivity;
import com.jishang.bimeng.utils.ToastUtil;

public class WodeActivity_b extends BaseActivity implements OnClickListener {
	private TextView mTv_name;
	private RelativeLayout mRl_lingqianbao, mRl_shurubao, mRl_huoyuedu,
			mRl_yaoyiyao, mRl_qiehuanzd, mRl_gerensz, mRl_shenqingpw,
			mRl_yuewansz, mRl_quxiaosh, mRl_qiehuanzh, mRl_tuichucx;

	@Override
	public int initResource() {
		return R.layout.activity_wode;
	}

	@Override
	public void initView() {
		mTv_name = (TextView) findViewById(R.id.activity_wode_name);
		mRl_lingqianbao = (RelativeLayout) findViewById(R.id.activity_wode_rl_lingqianbao);
		mRl_shurubao = (RelativeLayout) findViewById(R.id.activity_wode_rl_shurubao);
		mRl_huoyuedu = (RelativeLayout) findViewById(R.id.activity_wode_rl_huoyuedu);
		mRl_yaoyiyao = (RelativeLayout) findViewById(R.id.activity_wode_rl_yaoyiyao);
		mRl_qiehuanzd = (RelativeLayout) findViewById(R.id.activity_wode_rl_qiehuanzd);
		mRl_gerensz = (RelativeLayout) findViewById(R.id.activity_wode_rl_gerensz);
		mRl_shenqingpw = (RelativeLayout) findViewById(R.id.activity_wode_rl_shenqingpw);
		mRl_yuewansz = (RelativeLayout) findViewById(R.id.activity_wode_rl_yuewansz);
		mRl_quxiaosh = (RelativeLayout) findViewById(R.id.activity_wode_rl_quxiaosh);
		mRl_qiehuanzh = (RelativeLayout) findViewById(R.id.activity_wode_rl_qiehuanzh);
		mRl_tuichucx = (RelativeLayout) findViewById(R.id.activity_wode_rl_tuichucx);

		mTv_name.setText("我的");
	}

	@Override
	public void initData() {

	}

	@Override
	public void addListener() {
		mRl_lingqianbao.setOnClickListener(this);
		mRl_shurubao.setOnClickListener(this);
		mRl_huoyuedu.setOnClickListener(this);
		mRl_yaoyiyao.setOnClickListener(this);
		mRl_qiehuanzd.setOnClickListener(this);
		mRl_gerensz.setOnClickListener(this);
		mRl_shenqingpw.setOnClickListener(this);
		mRl_yuewansz.setOnClickListener(this);
		mRl_quxiaosh.setOnClickListener(this);
		mRl_qiehuanzh.setOnClickListener(this);
		mRl_tuichucx.setOnClickListener(this);

	}

	public void lingqian(View v) {
		ToastUtil.Toast(WodeActivity_b.this, "我是零钱");

	}

	@Override
	public void onClick(View v) {
		Intent intent=new Intent();
		switch (v.getId()) {
		
		case R.id.activity_wode_rl_lingqianbao:
			break;
		case R.id.activity_wode_rl_shurubao:
			break;
		case R.id.activity_wode_rl_huoyuedu:
			break;
		case R.id.activity_wode_rl_yaoyiyao:
			break;
		case R.id.activity_wode_rl_qiehuanzd:
			break;
		case R.id.activity_wode_rl_gerensz:
			intent.setClass(WodeActivity_b.this, GerenSzActivity.class);
			startActivity(intent);
			break;
		case R.id.activity_wode_rl_shenqingpw:
			break;
		case R.id.activity_wode_rl_yuewansz:
			break;
		case R.id.activity_wode_rl_quxiaosh:
			break;
		case R.id.activity_wode_rl_qiehuanzh:
			break;
		case R.id.activity_wode_rl_tuichucx:
			break;

		}
		

	}
	public void back(View v){
		finish();
		
	}

}
