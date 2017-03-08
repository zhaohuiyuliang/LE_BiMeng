
package com.jishang.bimeng.fragment.main;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jishang.bimeng.R;
import com.jishang.bimeng.activity.hongbao.ChaihbActivity;
import com.jishang.bimeng.activity.shangcheng.ActivityShangcheng;
import com.jishang.bimeng.entity.hongbao.Hongbao_dataEntity;
import com.jishang.bimeng.entity.hongbao.RedEnvelopeEntity;
import com.jishang.bimeng.entity.hongbao.dian.DianEntity;
import com.jishang.bimeng.entity.hongbao.dian.Dian_dataEntity;
import com.jishang.bimeng.fragment.base.BaseFragment;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.ToastUtil;
import com.jishang.bimeng.utils.Internet.MyHttpRequest;
import com.jishang.bimeng.utils.Time.CustomDigitalClock;
import com.jishang.bimeng.utils.Time.CustomDigitalClock.ClockListener;
import com.jishang.bimeng.utils.dialog.DialogUtils;
import com.jishang.bimeng.utils.url.UrlUtils;

public class Fragment_hongbao_1 extends BaseFragment implements OnClickListener, ClockListener {
    private String token;

    private List<BasicNameValuePair> params;

    private Gson mGson;

    private TextView mTv_name, mTv_huangjin_top, mTv_baiyin_top, mTv_huangtong_top;

    private CustomDigitalClock mCd;

    private Long t;

    private TextView mTv_qiang, mTv_shangcheng, mTv_hbbg;

    private List<Hongbao_dataEntity> dataEntities = new ArrayList<Hongbao_dataEntity>();

    private Context mContext;

    private LinearLayout mL_bg;

    private ProgressBar proBar;

    private int all_time = 0;

    private int iCount = 0;

    @Override
    public View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_red_envelope, null);
        mContext = getActivity();
        initView(view);
        return view;
    }

    public void initView(View view) {
        t = (long)0;
        token = new SharUtil(getActivity()).getAccess_token();
        params = new ArrayList<BasicNameValuePair>();
        mGson = new Gson();
        mTv_name = (TextView)view.findViewById(R.id.fragment_hongbao_name);
        mCd = (CustomDigitalClock)view.findViewById(R.id.remainTime_cd);
        mTv_qiang = (TextView)view.findViewById(R.id.fragment_hongbao_qiang);
        proBar = (ProgressBar)view.findViewById(R.id.progres_red_remain_time);

        mTv_huangjin_top = (TextView)view.findViewById(R.id.fragment_hongbao_huangjin_top);
        mTv_baiyin_top = (TextView)view.findViewById(R.id.fragment_hongbao_baiyin_top);
        mTv_huangtong_top = (TextView)view.findViewById(R.id.fragment_hongbao_huangtong_top);

        mTv_shangcheng = (TextView)view.findViewById(R.id.fragment_hongbao_shangcheng);
        mTv_hbbg = (TextView)view.findViewById(R.id.fragment_hongbao_hbbg);
        mL_bg = (LinearLayout)view.findViewById(R.id.fragment_hongbao_bg);
        getMsg();
        setListener();
        mTv_name.setText("红包");

    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    public void setListener() {
        mTv_qiang.setOnClickListener(this);
        mCd.setClockListener(this);
        mTv_shangcheng.setOnClickListener(this);

    }

    public void getMsg() {
        DialogUtils.getInstance().createNotifier(mContext, "正在加载中");
        params.add(new BasicNameValuePair("1", "1"));
        new Thread() {
            public void run() {
                String url = UrlUtils.BASEURL + "v1/redbag/display_hb_by_business_integral.json";
                String result = MyHttpRequest.getHttpPostBasic(url, params, token);
                if (result != null) {
                    Log.e("result", result.toString());
                    try {
                        RedEnvelopeEntity entity = mGson.fromJson(result, RedEnvelopeEntity.class);
                        // Log.e("entity", entity.toString());
                        if (entity.getStatus() == 1) {
                            Message msg = new Message();
                            msg.what = 1;
                            msg.obj = entity;
                            handler.sendMessage(msg);
                        } else {
                            handler.sendEmptyMessage(0);
                        }
                    } catch (JsonSyntaxException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            };
        }.start();
    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            DialogUtils.getInstance().cancel();
            switch (msg.what) {
                case 0:
                    ToastUtil.Toast(mContext, "网络错误");
                    break;

                case 1:
                    RedEnvelopeEntity entity = (RedEnvelopeEntity)msg.obj;
                    dataEntities = entity.getData();
                    if (dataEntities != null && !dataEntities.isEmpty()) { // 判断data数据是否为空，没红包的时候不判断会崩溃
                        String lever = dataEntities.get(0).getHb_level();
                        String time = dataEntities.get(0).getF_start_time();
                        String time_all = dataEntities.get(0).getJiange_number();
                        // String time_test = "1462287038";
                        setPro(time_all, time);
                        initSet(lever, time);
                    } else {
                        ToastUtil.Toast(mContext, "还没有红包可以抢");
                    }

                    break;
                case 2:
                    Dian_dataEntity entity1 = (Dian_dataEntity)msg.obj;
                    Intent intent = new Intent(getActivity(), ChaihbActivity.class);
                    intent.putExtra("entity", entity1);
                    startActivity(intent);
                    break;
                case 3:
                    ToastUtil.Toast(mContext, "此轮已抢过");
                    break;
                case 4:
                    ToastUtil.Toast(mContext, "此轮已抢光");
                    break;
                case 5:
                    ToastUtil.Toast(mContext, "红包已过期");
                    break;
                case 6:
                    ToastUtil.Toast(mContext, "此轮已抢完");
                    break;
                case 7:
                    ToastUtil.Toast(mContext, "还没到抢红包时间");
                    break;
                case 8:
                    Thread.currentThread().interrupt();
                    all_time = 0;
                    // showPro.setText("1");
                    break;
                case 9:
                    if (!Thread.currentThread().isInterrupted()) {
                        proBar.setProgress(iCount);
                    }
                    break;

            }
        };
    };

    public void setPro(String all_time_s, String time) {
        t = Long.parseLong(time);
        long y = t * 1000;
        long currentTime = System.currentTimeMillis();
        long distanceTime = (y - currentTime) / 1000;
        final int distancetime_int = new Long(distanceTime).intValue();
        Log.e("-distancetime_int-", distancetime_int + "");

        all_time = Integer.parseInt(all_time_s) * 3600;
        final int start = all_time - distancetime_int;
        Log.e("--start--", start + "");
        proBar.setMax(all_time);
        proBar.setProgress(0);
        Thread thread = new Thread(new Runnable() {

            public void run() {
                // TODO Auto-generated method stub
                for (int i = start; i < all_time; i++) {
                    try {

                        iCount = start;
                        Log.e("iCount", iCount + "  " + distancetime_int + "  " + start);
                        Thread.sleep(1000);
                        if (i == all_time) {
                            Message msg = new Message();
                            msg.what = 8;
                            handler.sendMessage(msg);
                            break;
                        } else {
                            Message msg = new Message();
                            msg.what = 9;
                            handler.sendMessage(msg);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

    public void initSet(String lever, String time) {
        t = Long.parseLong(time);
        long y = t * 1000;
        mCd.setEndTime(y);

        if (lever.equals("0")) {
            // ToastUtil.Toast(mContxt, "青铜");
            mTv_huangtong_top.setVisibility(View.VISIBLE);
            mTv_baiyin_top.setVisibility(View.GONE);
            mTv_huangjin_top.setVisibility(View.GONE);
            mTv_qiang.setBackgroundResource(R.drawable.qingtong_button_qiang);
            mTv_hbbg.setBackgroundResource(R.drawable.z_qingtong);
            // mTv_shangcheng.setBackgroundResource(R.drawable.qingtong_tp);
            mL_bg.setBackgroundResource(R.color.qingtong);

        } else if (lever.equals("1")) {
            // ToastUtil.Toast(getActivity(), "白银");
            mTv_baiyin_top.setVisibility(View.VISIBLE);
            mTv_huangjin_top.setVisibility(View.GONE);
            mTv_huangtong_top.setVisibility(View.GONE);
            mTv_qiang.setBackgroundResource(R.drawable.baiyin_button_qiang);
            mTv_hbbg.setBackgroundResource(R.drawable.z_baiyin);
            // mTv_shangcheng.setBackgroundResource(R.drawable.baiyin_tp);
            mL_bg.setBackgroundResource(R.color.baiyin);
        } else if (lever.equals("2")) {
            // ToastUtil.Toast(mContxt, "黄金");
            mTv_huangjin_top.setVisibility(View.VISIBLE);
            mTv_baiyin_top.setVisibility(View.GONE);
            mTv_huangtong_top.setVisibility(View.GONE);
            mTv_qiang.setBackgroundResource(R.drawable.huangjin_button_qiang);
            mTv_hbbg.setBackgroundResource(R.drawable.z_huangjin);
            // mTv_shangcheng.setBackgroundResource(R.drawable.huangjin_tp);
            mL_bg.setBackgroundResource(R.color.huangjin);
        }

    }

    public void qiang() {
        DialogUtils.getInstance().createNotifier(mContext, "正在加载中");
        if (dataEntities.isEmpty() || dataEntities.equals("")) {
            ToastUtil.Toast(mContext, "还没到抢红包时间");
            DialogUtils.getInstance().cancel();
            return;
        }
        String red_id = dataEntities.get(0).getRed_id();
        String one_bag_money = dataEntities.get(0).getOne_bag_money();
        String one_bag_person = dataEntities.get(0).getOne_bag_person();
        params.add(new BasicNameValuePair("red_id", red_id));
        params.add(new BasicNameValuePair("one_bag_money", one_bag_money));
        params.add(new BasicNameValuePair("one_bag_person", one_bag_person));
        new Thread() {
            public void run() {
                String url = UrlUtils.BASEURL + "v1/redbag/qiang_hb_money.json";
                String result = MyHttpRequest.getHttpPostBasic(url, params, token);
                if (result != null) {
                    try {
                        // Log.e("result-", result.toString());
                        DianEntity entity = mGson.fromJson(result, DianEntity.class);
                        switch (entity.getStatus()) {
                            case 0:

                                break;
                            case 1:
                                Message msg = new Message();
                                msg.obj = entity.getData();
                                msg.what = 2;
                                handler.sendMessage(msg);
                                break;
                            case 2:
                                handler.sendEmptyMessage(3);
                                break;
                            case 3:
                                handler.sendEmptyMessage(4);
                                break;
                            case 4:
                                handler.sendEmptyMessage(5);
                                break;
                            case 5:
                                handler.sendEmptyMessage(6);
                                break;
                            case 6:
                                handler.sendEmptyMessage(7);
                                break;
                            case 7:

                                break;
                            case 8:

                                break;

                        }
                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                        handler.sendEmptyMessage(0);
                    }

                }

            };

        }.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_hongbao_qiang:
                qiang();
                break;
            case R.id.fragment_hongbao_shangcheng:
                Intent intent = new Intent(getActivity(), ActivityShangcheng.class);
                startActivity(intent);
                break;

        }
    }

    @Override
    public void timeEnd() {
        ToastUtil.Toast(mContext, "可以抢红包了");
    }

    @Override
    public void remainFiveMinutes() {
        ToastUtil.Toast(mContext, "只剩下五分钟了");
    }

    @Override
    public void refreshUI() {
        // TODO Auto-generated method stub

    }

}
