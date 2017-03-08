
package com.jishang.bimeng.activity.wode;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.jishang.bimeng.R;
import com.jishang.bimeng.activity.base.BaseActivity;
import com.jishang.bimeng.activity.wode.tixian.TixianActivity;
import com.jishang.bimeng.utils.SharUtil;

/**
 * "我的钱包"UI
 * 
 * @author wangliang Sep 6, 2016
 */
public class ActivityWallet extends BaseActivity {
    private TextView mTv_shuru;

    private Intent intent;

    private String shouru;

    private TextView mTv_name;

    private SharUtil mShare;

    @Override
    public int initResource() {
        return R.layout.activity_wallet;
    }

    @Override
    public void initView() {
        intent = getIntent();
        mShare = new SharUtil(this);
        shouru = (String)intent.getSerializableExtra("shouru");
        mTv_shuru = (TextView)findViewById(R.id.activity_shuru_money);
        mTv_name = (TextView)findViewById(R.id.activity_shuru_name);
        mTv_name.setText("我的钱包");
        ((TextView)findViewById(R.id.tv_back_to_activity)).setText(R.string.my);
    }

    @Override
    public void initData() {
        mTv_shuru.setText(shouru);
        String num = mShare.getLingqian();
        mShare.setLingqian(shouru);
    }

    @Override
    public void addListener() {

    }

    public void back(View v) {
        finish();
    }

    /**
     * 充值
     * 
     * @param v
     */
    public void chongzhi(View v) {
        Intent intent = new Intent(this, ActivityChongzhi.class);
        startActivity(intent);
    }

    /**
     * 提现
     * 
     * @param v
     */
    public void tixian(View v) {
        Intent intent = new Intent(this, TixianActivity.class);
        intent.putExtra("shouru", shouru);
        startActivity(intent);
    }

}
