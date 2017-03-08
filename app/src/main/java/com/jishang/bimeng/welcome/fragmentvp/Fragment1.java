package com.jishang.bimeng.welcome.fragmentvp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jishang.bimeng.R;

/**
 * @author kangming
 * 
 * 引导层的第一个页面
 *
 */
public class Fragment1 extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_1, container, false);
		return view;
	}

}
