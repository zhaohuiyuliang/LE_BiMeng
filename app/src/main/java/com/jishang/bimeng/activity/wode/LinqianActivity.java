
package com.jishang.bimeng.activity.wode;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.jishang.bimeng.R;
import com.jishang.bimeng.activity.base.BaseActivity;
import com.jishang.bimeng.utils.SharUtil;

/**
 * @author wangliang Jul 14, 2016
 */
public class LinqianActivity extends BaseActivity {
    private TextView mTv_money;

    private Intent intent;

    private String linqian;

    private TextView mTv_name;

    private SharUtil mShare;

    @Override
    public int initResource() {
        return R.layout.activity_linqian;
    }

    @Override
    public void initView() {
        intent = getIntent();
        linqian = (String)intent.getSerializableExtra("linqian");
        mShare = new SharUtil(this);
        mTv_money = (TextView)findViewById(R.id.activity_linqian_money);
        mTv_name = (TextView)findViewById(R.id.activity_linqian_name);
        mTv_name.setText("我的红包");
        ((TextView)findViewById(R.id.tv_back_to_activity)).setText(R.string.my);
    }

    @Override
    public void initData() {
        mTv_money.setText(linqian);

    }

    @Override
    public void addListener() {

    }

    public void back(View v) {
        finish();
    }

}
