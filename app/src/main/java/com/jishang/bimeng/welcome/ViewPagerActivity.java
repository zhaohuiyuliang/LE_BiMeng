package com.jishang.bimeng.welcome;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.jishang.bimeng.R;
import com.jishang.bimeng.welcome.fragmentvp.Fragment1;
import com.jishang.bimeng.welcome.fragmentvp.Fragment2;
import com.jishang.bimeng.welcome.fragmentvp.Fragment3;

/**
 * ViewPager 寮曞 com.zhangyx.MyLauncherGuide.activity.viewPage.ViewPagerActivity
 * 
 * @author kangming
 *
 * 
 */
public class ViewPagerActivity extends FragmentActivity {

	private ViewPager mVPActivity;
	private Fragment1 mFragment1;
	private Fragment2 mFragment2;
	private Fragment3 mFragment3;
	private List<Fragment> mListFragment = new ArrayList<Fragment>();
	private PagerAdapter mPgAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_viewpager);
		initView();
	}

	private void initView() {
		mVPActivity = (ViewPager) findViewById(R.id.vp_activity);
		mFragment1 = new Fragment1();
		mFragment2 = new Fragment2();
		mFragment3 = new Fragment3();
		mListFragment.add(mFragment1);
		mListFragment.add(mFragment2);
		mListFragment.add(mFragment3);
		mPgAdapter = new ViewPagerAdapter(getSupportFragmentManager(),
				mListFragment);
		mVPActivity.setAdapter(mPgAdapter);
	}
}
