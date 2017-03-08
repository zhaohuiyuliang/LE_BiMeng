
package com.jishang.bimeng.activity.yuezhan.datapick;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.jishang.bimeng.R;
import com.jishang.bimeng.activity.base.BaseActivity;
import com.jishang.bimeng.activity.yuezhan.ActivityYuezhan;
import com.jishang.bimeng.entity.yuezhan.confirm.time.DataTimeEntity;
import com.jishang.bimeng.utils.ToastUtil;

/**
 * 日期时间选择UI
 * 
 * @author wangliang Jul 17, 2016
 */
public class DatapikerActivity extends BaseActivity implements OnClickListener {
    private DatePicker mData_data;

    private String Data;

    private TextView mBt_data, mBt_time;

    private String dandt = null;

    private TextView mTv_name, mTv_save;

    private RelativeLayout mRl_save, mRl_data, mRl_time;

    @Override
    public int initResource() {
        return R.layout.activity_data;
    }

    @Override
    public void initView() {
        mBt_data = (TextView)findViewById(R.id.activity_data_data);
        mBt_time = (TextView)findViewById(R.id.activity_data_time);
        mTv_name = (TextView)findViewById(R.id.activity_data_tv_name);
        mRl_save = (RelativeLayout)findViewById(R.id.activity_data_save);
        mRl_data = (RelativeLayout)findViewById(R.id.activity_data_rl_data);
        mRl_time = (RelativeLayout)findViewById(R.id.activity_data_rl_time);

        mTv_name.setText("设置时间");

    }

    public void back(View v) {
        finish();
    }

    @Override
    public void initData() {

    }

    @Override
    public void addListener() {
        mRl_data.setOnClickListener(this);
        mRl_time.setOnClickListener(this);
        mRl_save.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_data_rl_data:
                showdata();
                break;
            case R.id.activity_data_rl_time:
                showtime();
                break;
            case R.id.activity_data_save:
                String data = mBt_data.getText().toString();
                String time = mBt_time.getText().toString();

                if (data.equals("请选择日期") || time.equals("请选择时间")) {
                    ToastUtil.Toast(DatapikerActivity.this, "清先选择日期或者时间");
                } else {
                    DataTimeEntity entity = new DataTimeEntity();
                    dandt = data + "-" + time;
                    String start_time = dataOne(dandt);
                    entity.setStart_time(start_time);
                    entity.setDandt(dandt);

                    Intent intent = new Intent(DatapikerActivity.this, ActivityYuezhan.class);
                    intent.putExtra("entity", entity);
                    setResult(100, intent);
                    finish();
                    // Log.e("s", s);
                }

                break;

        }

    }

    /**
     * 掉此方法输入所要转换的时间输入例如（"2014-06-14-16-09-00"）返回时间戳
     * 
     * @param time
     * @return
     */
    public String dataOne(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss", Locale.CHINA);
        Date date;
        String times = null;
        try {
            date = sdr.parse(time);
            long l = date.getTime();
            String stf = String.valueOf(l);
            times = stf.substring(0, 10);
            Log.d("--444444---", times);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return times;
    }

    public void showdata() {
        final Calendar objTime = Calendar.getInstance();
        int iYear = objTime.get(Calendar.YEAR);
        int iMonth = objTime.get(Calendar.MONTH);
        int iDay = objTime.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dpd = new DatePickerDialog(this, new OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mBt_data.setText(new StringBuilder()
                        .append(year)
                        .append("-")
                        .append((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1)
                                : (monthOfYear + 1)).append("-")
                        .append((dayOfMonth < 10) ? "0" + dayOfMonth : dayOfMonth));
            }
        }, iYear, iMonth, iDay);
        dpd.show();
    }

    public void showtime() {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        TimePickerDialog tpd = new TimePickerDialog(this, new OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String time = hourOfDay + ":" + minute + ":" + "00";
                // String date = hourOfDay + "/" + minute;

                mBt_time.setText(new StringBuilder()
                        .append(hourOfDay < 10 ? "0" + hourOfDay : hourOfDay).append(":")
                        .append(minute < 10 ? "0" + minute : minute).append(":00"));

            }
        }, hour, minute, true);
        tpd.show();

    }

}
