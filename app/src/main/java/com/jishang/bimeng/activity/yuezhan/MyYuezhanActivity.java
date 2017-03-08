
package com.jishang.bimeng.activity.yuezhan;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.jishang.bimeng.R;
import com.jishang.bimeng.activity.yuezhan.cj.Fragment_cy;
import com.jishang.bimeng.activity.yuezhan.fq.Fragment_fq;

public class MyYuezhanActivity extends FragmentActivity implements OnPageChangeListener {
    private ViewPager pager = null;

    private PagerTabStrip tabStrip = null;

    private ArrayList<String> titleContainer = new ArrayList<String>();

    public String TAG = "tag";

    private TextView mTv_name;

    private Intent intent;

    // 加入/参与我的开黑人数
    private String ballGamesNum = "";

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_myyuezhan);
        intent = getIntent();
        ballGamesNum = intent.getStringExtra("getYz_fq_num");
        initView();
        initData();
        addListener();

    }

    public void initView() {
        pager = (ViewPager)this.findViewById(R.id.viewpager);
        tabStrip = (PagerTabStrip)this.findViewById(R.id.tabstrip);
        mTv_name = (TextView)findViewById(R.id.activity_myyuezhan_name);
        mTv_name.setText("我的开黑");
        // 取消tab下面的长横线
        tabStrip.setDrawFullUnderline(false);
        // 设置tab的背景色
        tabStrip.setBackgroundColor(getResources().getColor(R.color.bg_gray));
        // 设置当前tab页签的下划线颜色
        tabStrip.setTabIndicatorColor(getResources().getColor(R.color.bar_color));
        tabStrip.setTextSpacing(200);
        pager.setAdapter(new MyFragmentadater(getSupportFragmentManager()));
        // viewPager开始添加view
        // 页签项
        titleContainer.add("我参加");
        titleContainer.add("我发起");

        pager.setOnPageChangeListener(this);
    }

    public void initData() {
        if (!TextUtils.isEmpty(ballGamesNum)) {// 有我为查看的参与我发起的开黑人数
            pager.setCurrentItem(1, false);
        }
    }

    public void addListener() {

    }

    public class MyFragmentadater extends FragmentPagerAdapter {

        public MyFragmentadater(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int arg0) {
            switch (arg0) {
                case 0:// 我参与的开黑
                    return new Fragment_cy();

                case 1:// 我发起的开黑
                    return new Fragment_fq(ballGamesNum);
                default:
                    break;

            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titleContainer.get(position);
        }

    }

    public void back(View v) {
        finish();
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int arg0) {

    }

}
