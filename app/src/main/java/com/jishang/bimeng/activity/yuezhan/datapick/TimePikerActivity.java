package com.jishang.bimeng.activity.yuezhan.datapick;

import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

import com.jishang.bimeng.R;
import com.jishang.bimeng.activity.base.BaseActivity;
import com.jishang.bimeng.utils.ToastUtil;

public class TimePikerActivity extends BaseActivity {
	private TimePicker mTime_time;

	@Override
	public int initResource() {
		return R.layout.activity_timepicker;
	}

	@Override
	public void initView() {
		mTime_time = (TimePicker) findViewById(R.id.acitivity_datapiker_time);

	}

	@Override
	public void initData() {
		mTime_time.setOnTimeChangedListener(new OnTimeChangedListener() {

			@Override
			public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
				
				ToastUtil.Toast(TimePikerActivity.this, "您选择的时间是：" + hourOfDay
						+ "时" + minute + "分。");
			}
		});

	}

	@Override
	public void addListener() {

	}

}
