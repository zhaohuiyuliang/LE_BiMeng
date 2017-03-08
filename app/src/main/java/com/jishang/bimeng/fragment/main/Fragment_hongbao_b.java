
package com.jishang.bimeng.fragment.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.jishang.bimeng.R;
import com.jishang.bimeng.fragment.base.BaseFragment;
import com.jishang.bimeng.utils.ToastUtil;
import com.jishang.bimeng.utils.Time.CustomDigitalClock;
import com.jishang.bimeng.utils.Time.CustomDigitalClock.ClockListener;

public class Fragment_hongbao_b extends BaseFragment {
    private CustomDigitalClock remainTime_qingtong, remainTime_baiyin, remainTime_huangjin;

    long time_qingtong = System.currentTimeMillis() + 50 * 1000;

    long time_baiyin = System.currentTimeMillis() + 100 * 1000;

    long time_huangjin = System.currentTimeMillis() + 900 * 1000;

    @Override
    public View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_hongbao_b, null);
        initView(view);
        return view;
    }

    public void initView(View view) {
        remainTime_qingtong = (CustomDigitalClock)view.findViewById(R.id.remainTime_qingtong);
        remainTime_baiyin = (CustomDigitalClock)view.findViewById(R.id.remainTime_baiyin);
        remainTime_huangjin = (CustomDigitalClock)view.findViewById(R.id.remainTime_huangjin);

        remainTime_qingtong.setEndTime(time_qingtong);
        remainTime_baiyin.setEndTime(time_baiyin);
        remainTime_huangjin.setEndTime(time_huangjin);
        setListener();

    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    public void setListener() {
        remainTime_qingtong.setClockListener(new ClockListener() {

            @Override
            public void timeEnd() {
                ToastUtil.Toast(getActivity(), "青铜红包可以抢了");

            }

            @Override
            public void remainFiveMinutes() {

            }
        });
        remainTime_baiyin.setClockListener(new ClockListener() {

            @Override
            public void timeEnd() {
                ToastUtil.Toast(getActivity(), "白银红包可以抢了");

            }

            @Override
            public void remainFiveMinutes() {

            }
        });
        remainTime_huangjin.setClockListener(new ClockListener() {

            @Override
            public void timeEnd() {
                ToastUtil.Toast(getActivity(), "黄金红包可以抢了");

            }

            @Override
            public void remainFiveMinutes() {

            }
        });
    }

    @Override
    public void refreshUI() {

    }

}
