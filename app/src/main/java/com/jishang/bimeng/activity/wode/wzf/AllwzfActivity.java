
package com.jishang.bimeng.activity.wode.wzf;

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
import com.jishang.bimeng.activity.wode.dingdanwzf.ScwzfFragment;
import com.jishang.bimeng.activity.wode.dingdanwzf.YzwzfFragment;

public class AllwzfActivity extends FragmentActivity implements OnCheckedChangeListener,
        OnClickListener {
    private RadioGroup mRg;

    private TextView mTv_name;

    private ScwzfFragment sc_fragment;

    private YzwzfFragment yz_fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allwzf);
        initView();
        setDefaultFragment();
    }

    public void initView() {
        mRg = (RadioGroup)findViewById(R.id.checkquote_fragment_rg);
        mTv_name = (TextView)findViewById(R.id.activity_duihuan_name);
        mRg.setOnCheckedChangeListener(this);
        mTv_name.setText("未支付订单");
        ((TextView)findViewById(R.id.tv_back_to_activity)).setText(R.string.my);

    }

    public void setDefaultFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        yz_fragment = new YzwzfFragment();
        transaction.replace(R.id.checkquote_fragment_fl, yz_fragment);
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
            case R.id.activity_allwzf_yzwzf:
                if (yz_fragment == null) {
                    yz_fragment = new YzwzfFragment();
                }
                transaction.replace(R.id.checkquote_fragment_fl, yz_fragment);
                break;
            case R.id.activity_allwzf_scwzf:
                if (sc_fragment == null) {
                    sc_fragment = new ScwzfFragment();
                }
                transaction.replace(R.id.checkquote_fragment_fl, sc_fragment);// 经常把这个东西弄错啊checkquote_fragment_fl
                break;

        }
        transaction.commit();
    }

    public void back(View v) {
        finish();
    }

}
