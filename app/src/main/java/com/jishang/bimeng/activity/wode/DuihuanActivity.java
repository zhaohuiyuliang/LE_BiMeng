
package com.jishang.bimeng.activity.wode;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.jishang.bimeng.R;
import com.jishang.bimeng.activity.wode.duihuan.WeiduihuanFragment;
import com.jishang.bimeng.activity.wode.duihuan.YiDuihuanFragment;

/**
 * 兑换码UI
 * 
 * @author wangliang Jul 16, 2016
 */
public class DuihuanActivity extends FragmentActivity implements OnCheckedChangeListener,
        OnClickListener {
    private YiDuihuanFragment yi_fragment;

    private WeiduihuanFragment wei_fragment;

    private RadioGroup mRg;

    private TextView mTv_name;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_duihuan);
        initView();
        setDefaultFragment();
    }

    public void initView() {
        mRg = (RadioGroup)findViewById(R.id.checkquote_fragment_rg);
        mTv_name = (TextView)findViewById(R.id.activity_duihuan_name);
        mRg.setOnCheckedChangeListener(this);
        mTv_name.setText("兑换码");
        Intent intent = getIntent();
        if (intent != null) {
            String back = intent.getStringExtra("back");
            ((TextView)findViewById(R.id.tv_back_to_activity)).setText(back);
        } else {
            ((TextView)findViewById(R.id.tv_back_to_activity)).setText(R.string.my);
        }

    }

    public void setDefaultFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        wei_fragment = new WeiduihuanFragment();
        transaction.replace(R.id.checkquote_fragment_fl, wei_fragment);
        transaction.commit();

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        switch (checkedId) {
            case R.id.activity_duihuan_weiduihuan:
                if (wei_fragment == null) {
                    wei_fragment = new WeiduihuanFragment();
                }
                transaction.replace(R.id.checkquote_fragment_fl, wei_fragment);// 经常把这个东西弄错啊checkquote_fragment_fl
                break;
            case R.id.activity_duihuan_yiduihuan:
                if (yi_fragment == null) {
                    yi_fragment = new YiDuihuanFragment();
                }
                transaction.replace(R.id.checkquote_fragment_fl, yi_fragment);
                break;
            default:
                break;

        }
        transaction.commit();
    }

    public void back(View v) {
        finish();
    }

}
