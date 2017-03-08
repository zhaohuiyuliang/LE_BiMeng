
package com.jishang.bimeng.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.jishang.bimeng.R;
import com.jishang.bimeng.activity.addfd.SearchFriendActivity;
import com.jishang.bimeng.activity.addfd.fdlist.FdListActivity;
import com.jishang.bimeng.activity.base.BaseActivity;
import com.jishang.bimeng.activity.dt.fourway.Dt_Activity;
import com.jishang.bimeng.activity.hxchat.ActivityChat;
import com.jishang.bimeng.activity.hxchat.ActivityChatHistory;
import com.jishang.bimeng.activity.pickphoto.view.PublishActivity;
import com.jishang.bimeng.activity.wode.MyActivity;

public class TestActivity extends BaseActivity implements OnClickListener {
    private TextView mTv_login, mTv_city, mTv_chat, mTv_addfd, mTv_dt, mTv_tupian, mTv_htlist,
            mTv_wode, mTv_history;

    private Context mContext;

    @Override
    public int initResource() {
        return R.layout.activity_test;
    }

    @Override
    public void initView() {
        mTv_login = (TextView)findViewById(R.id.fragment_yuezhan_login);
        mTv_city = (TextView)findViewById(R.id.fragment_yuezhan_check);
        mTv_chat = (TextView)findViewById(R.id.fragment_yuezhan_chat);
        mTv_addfd = (TextView)findViewById(R.id.fragment_yuezhan_addfd);
        mTv_dt = (TextView)findViewById(R.id.fragment_yuezhan_dt);
        mTv_tupian = (TextView)findViewById(R.id.fragment_yuezhan_tupian);
        mTv_htlist = (TextView)findViewById(R.id.fragment_yuezhan_hylist);
        mTv_wode = (TextView)findViewById(R.id.fragment_yuezhan_my);
        mTv_history = (TextView)findViewById(R.id.fragment_yuezhan_history);

        mContext = this;
    }

    @Override
    public void initData() {

    }

    @Override
    public void addListener() {

        mTv_login.setOnClickListener(this);
        mTv_city.setOnClickListener(this);
        mTv_chat.setOnClickListener(this);
        mTv_addfd.setOnClickListener(this);
        mTv_dt.setOnClickListener(this);
        mTv_tupian.setOnClickListener(this);
        mTv_htlist.setOnClickListener(this);
        mTv_wode.setOnClickListener(this);
        mTv_history.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.fragment_yuezhan_login:
                Intent intent = new Intent(mContext, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.fragment_yuezhan_check:
                Intent intent2 = new Intent(mContext, RegistActivity3.class);
                startActivity(intent2);
                break;
            case R.id.fragment_yuezhan_chat:
                Intent intent3 = new Intent(mContext, ActivityChat.class);
                startActivity(intent3);
                break;
            case R.id.fragment_yuezhan_addfd:
                Intent intent4 = new Intent(mContext, SearchFriendActivity.class);
                startActivity(intent4);
                break;
            case R.id.fragment_yuezhan_dt:
                Intent intent5 = new Intent(mContext, Dt_Activity.class);
                startActivity(intent5);
                break;
            case R.id.fragment_yuezhan_tupian:
                Intent intent6 = new Intent(mContext, PublishActivity.class);
                startActivity(intent6);
                break;
            case R.id.fragment_yuezhan_hylist:
                Intent intent7 = new Intent(mContext, FdListActivity.class);
                startActivity(intent7);
                break;
            case R.id.fragment_yuezhan_my:
                Intent intent8 = new Intent(mContext, MyActivity.class);
                startActivity(intent8);
                break;
            case R.id.fragment_yuezhan_history:

                Intent intent9 = new Intent(mContext, ActivityChatHistory.class);
                startActivity(intent9);
                break;

        }

    }

    public void back(View v) {
        finish();
    }

}
