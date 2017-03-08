
package com.jishang.bimeng.fragment.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.jishang.bimeng.ui.UIHandler;
import com.jishang.bimeng.utils.Internet.MyHttpRequest;
import com.jishang.bimeng.utils.InternetUtils;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.Time.CustomDigitalClock;
import com.jishang.bimeng.utils.Time.CustomDigitalClock.ClockListener;
import com.jishang.bimeng.utils.ToastUtil;
import com.jishang.bimeng.utils.dialog.DialogUtils;
import com.jishang.bimeng.utils.url.UrlUtils;

import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * "红包"UI
 * 
 * @author wangliang Jul 14, 2016
 */
public class FragmentRedEnvelope extends BaseFragment implements OnClickListener, ClockListener {

    private TextView mTv_name, mTv_huangjin_top, mTv_baiyin_top, mTv_xiaci, mTv_huangtong_top;

    /**
     * 显示倒计时时间控件
     */
    private CustomDigitalClock remainTime_cd;

    /**
     * 抢红包倒计时提示进度条
     */
    private ProgressBar progres_red_remain_time;

    /**
     * 抢红包按键
     */
    private TextView mTv_qiang;

    private TextView mTv_shangcheng;

    private TextView mTv_hbbg;

    private List<Hongbao_dataEntity> dataEntities = new ArrayList<Hongbao_dataEntity>();

    private Context mContext;

    private LinearLayout mL_bg;

    @Override
    public View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_red_envelope, null);
        mContext = getActivity();
        initView(view);
        return view;
    }

    public void initView(View view) {

        mTv_name = (TextView)view.findViewById(R.id.fragment_hongbao_name);
        remainTime_cd = (CustomDigitalClock)view.findViewById(R.id.remainTime_cd);
        mTv_qiang = (TextView)view.findViewById(R.id.fragment_hongbao_qiang);
        progres_red_remain_time = (ProgressBar)view.findViewById(R.id.progres_red_remain_time);

        mTv_huangjin_top = (TextView)view.findViewById(R.id.fragment_hongbao_huangjin_top);
        mTv_baiyin_top = (TextView)view.findViewById(R.id.fragment_hongbao_baiyin_top);
        mTv_huangtong_top = (TextView)view.findViewById(R.id.fragment_hongbao_huangtong_top);

        mTv_shangcheng = (TextView)view.findViewById(R.id.fragment_hongbao_shangcheng);
        mTv_hbbg = (TextView)view.findViewById(R.id.fragment_hongbao_hbbg);
        mL_bg = (LinearLayout)view.findViewById(R.id.fragment_hongbao_bg);
        mTv_xiaci = (TextView)view.findViewById(R.id.fragment_hongbao_tv_xiaci);
        setListener();
        mTv_name.setText("红包");
        mTv_xiaci.setText("距下次青铜红包来袭还剩:");

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        remainTime_cd.setFragment(FragmentRedEnvelope.this);
    }

    public void setListener() {
        mTv_qiang.setOnClickListener(this);
        remainTime_cd.setClockListener(this);
        mTv_shangcheng.setOnClickListener(this);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * 请求抢红包时间
     */
    public void getMsg() {
        isRunningThreadRefreshProgress = false;
        new Thread() {
            public void run() {
                if (!InternetUtils.isNetworkAvailable(getActivity())) {
                    handler.sendEmptyMessage(10);
                    return;
                }
                List<BasicNameValuePair> params = new ArrayList<>();
                params.add(new BasicNameValuePair("1", "1"));
                String token = new SharUtil(getActivity()).getAccess_token();
                String url = UrlUtils.BASEURL + "v1/redbag/display_hb_by_business_integral.json";
                String result = MyHttpRequest.getHttpPostBasic(url, params, token);

                // 判断网络地址是不是能用
                if (result.equals("0")) {
                    handler.sendEmptyMessage(0);
                    return;
                }
                try {
                    if (result != null) {
                        Gson mGson = new Gson();
                        RedEnvelopeEntity entity = mGson.fromJson(result, RedEnvelopeEntity.class);
                        if (entity.getStatus() == 1) {
                            Message msg = new Message();
                            msg.what = UIHandler.INIT_RED_ENVELOPE_TIME;
                            msg.obj = entity;
                            handler.sendMessage(msg);
                        } else {
                            handler.sendEmptyMessage(0);
                        }
                    }
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    handler.sendEmptyMessage(0);
                }
            };
        }.start();
    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            DialogUtils.getInstance().cancel();
            switch (msg.what) {
                case 0:
                    ToastUtil.Toast(mContext, mContext.getString(R.string.erro_internet));
                    break;

                case UIHandler.INIT_RED_ENVELOPE_TIME: {// 初始化抢红包时间
                    RedEnvelopeEntity entity = (RedEnvelopeEntity)msg.obj;
                    dataEntities = entity.getData();
                    if (dataEntities != null && !dataEntities.isEmpty()) { // 判断data数据是否为空，没红包的时候不判断会崩溃
                        String lever = dataEntities.get(0).getHb_level();
                        String time = dataEntities.get(0).getF_start_time();
                        String all_time = dataEntities.get(0).getJiange_number();
                        setPro(all_time, time);
                        initSet(lever);
                    }
                }

                    break;
                case 2: {// 抢到红包
                    // 结束进度条刷新进程
                    isRunningThreadRefreshProgress = false;
                    // 进度条设置为空
                    progres_red_remain_time.setProgress(0);
                    Dian_dataEntity entity1 = (Dian_dataEntity)msg.obj;
                    Intent intent = new Intent(getActivity(), ChaihbActivity.class);
                    intent.putExtra("entity", entity1);
                    startActivity(intent);
                }
                    break;
                case 3: {
                    ToastUtil.Toast(mContext, "此轮已抢过");
                }
                    break;
                case 4: {
                    ToastUtil.Toast(mContext, "此轮已抢光");
                }
                    break;
                case 5: {
                    ToastUtil.Toast(mContext, "红包已过期");
                }
                    break;
                case 6: {
                    ToastUtil.Toast(mContext, "此轮已抢完");
                }
                    break;
                case 7: {
                    ToastUtil.Toast(mContext, "还没到抢红包时间");
                }
                    break;
                case UIHandler.RED_ENVELOPE_REMAIN_TIME_END: {// 抢红包倒计时结束，刷新UI
                    isRunningThreadRefreshProgress = false;
                    // 进度条设置为空
                    progres_red_remain_time.setProgress(0);
                }
                    break;
                case UIHandler.REFRESH_REMAIN_TIME_PROGRESS: {// 刷新倒计时提示进度条
                    synchronized (FragmentRedEnvelope.class) {
                        if (isRunningThreadRefreshProgress) {
                            int iCount = msg.arg1;
                            progres_red_remain_time.setProgress(iCount);
                        } else {
                            progres_red_remain_time.setProgress(0);
                        }
                    }
                }
                    break;
                case 10: {
                    ToastUtil.Toast(mContext, "您还没有联网，清先联网");
                }
                    break;
                default:
                    break;

            }
        };
    };

    /**
     * 刷新进度条线程
     */
    private Thread threadRefreshProgress;

    private boolean isRunningThreadRefreshProgress = false;

    /**
     * 刷新界面,初始化倒计时时间布局
     * 
     * @param all_time_s
     * @param time
     */
    public void setPro(String all_time_s, String time) {

        Long t = Long.parseLong(time);
        long y = t * 1000;
        // 设置结束时间
        if (remainTime_cd.setEndTime(y)) {// 时间相等

        }
        long currentTime = System.currentTimeMillis();
        long distanceTime = (y - currentTime) / 1000;
        final int distancetime_int1 = new Long(distanceTime).intValue();
        final int all_time = Integer.parseInt(all_time_s) * 3600;

        final int start = all_time - distancetime_int1;
        progres_red_remain_time.setMax(all_time);
        progres_red_remain_time.setProgress(start);
        if (threadRefreshProgress != null) {
            isRunningThreadRefreshProgress = false;
            threadRefreshProgress = null;
        }
        isRunningThreadRefreshProgress = true;
        threadRefreshProgress = new Thread(new Runnable() {
            public void run() {
                for (int i = start; isRunningThreadRefreshProgress && i < all_time; i++) {
                    try {
                        int iCount = i + 2;
                        Thread.sleep(1000);
                        if (i == all_time) {// 倒计时结束
                            Message msg = new Message();
                            msg.what = UIHandler.RED_ENVELOPE_REMAIN_TIME_END;
                            handler.sendMessage(msg);
                            break;
                        } else {// 刷新倒计时进度条
                            Message msg = new Message();
                            msg.what = UIHandler.REFRESH_REMAIN_TIME_PROGRESS;
                            msg.arg1 = iCount;
                            handler.sendMessage(msg);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                isRunningThreadRefreshProgress = false;
                // 进度条设置为空
                progres_red_remain_time.setProgress(0);
            }
        });
        threadRefreshProgress.start();
    }

    /**
     * 初始化抢红包布局背景
     * 
     * @param lever
     */
    public void initSet(String lever) {
        if (lever.equals("0")) {
            mTv_huangtong_top.setVisibility(View.VISIBLE);
            mTv_baiyin_top.setVisibility(View.GONE);
            mTv_huangjin_top.setVisibility(View.GONE);
            mTv_qiang.setBackgroundResource(R.drawable.qingtong_button_qiang);
            mTv_hbbg.setBackgroundResource(R.drawable.z_qingtong);
            mTv_shangcheng.setBackgroundResource(R.drawable.tp_qingtong);
            mL_bg.setBackgroundResource(R.color.qingtong);
            progres_red_remain_time.setBackgroundResource(R.drawable.progress_bg_qingtong);
            mTv_xiaci.setText("距下次青铜红包来袭还剩:");

        } else if (lever.equals("1")) {
            mTv_baiyin_top.setVisibility(View.VISIBLE);
            mTv_huangjin_top.setVisibility(View.GONE);
            mTv_huangtong_top.setVisibility(View.GONE);
            mTv_qiang.setBackgroundResource(R.drawable.baiyin_button_qiang);
            mTv_hbbg.setBackgroundResource(R.drawable.z_baiyin);
            mTv_shangcheng.setBackgroundResource(R.drawable.tp_baiyin);
            mL_bg.setBackgroundResource(R.color.baiyin);
            progres_red_remain_time.setBackgroundResource(R.drawable.progress_bg_baiyin);
            mTv_xiaci.setText("距下次白银红包来袭还剩:");
        } else if (lever.equals("2")) {
            mTv_huangjin_top.setVisibility(View.VISIBLE);
            mTv_baiyin_top.setVisibility(View.GONE);
            mTv_huangtong_top.setVisibility(View.GONE);
            mTv_qiang.setBackgroundResource(R.drawable.huangjin_button_qiang);
            mTv_hbbg.setBackgroundResource(R.drawable.z_huangjin);
            mTv_shangcheng.setBackgroundResource(R.drawable.tp_huangjin);
            mL_bg.setBackgroundResource(R.color.huangjin);
            progres_red_remain_time.setBackgroundResource(R.drawable.progress_bg_huangjin);
            mTv_xiaci.setText("距下次黄金红包来袭还剩:");
        }

    }

    public void qiangRedEnvelope() {
        DialogUtils.getInstance().createNotifier(mContext, "正在加载中");
        if (dataEntities == null || dataEntities.isEmpty()) {
            ToastUtil.Toast(mContext, "还没到抢红包时间");
            DialogUtils.getInstance().cancel();
            return;
        }
        new Thread() {
            public void run() {
                String token = new SharUtil(getActivity()).getAccess_token();
                String red_id = dataEntities.get(0).getRed_id();
                String one_bag_money = dataEntities.get(0).getOne_bag_money();
                String one_bag_person = dataEntities.get(0).getOne_bag_person();
                List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
                params.add(new BasicNameValuePair("red_id", red_id));
                params.add(new BasicNameValuePair("one_bag_money", one_bag_money));
                params.add(new BasicNameValuePair("one_bag_person", one_bag_person));
                String url = UrlUtils.BASEURL + "v1/redbag/qiang_hb_money.json";
                String result = MyHttpRequest.getHttpPostBasic(url, params, token);
                try {
                    if (result != null) {
                        Gson mGson = new Gson();
                        DianEntity entity = mGson.fromJson(result, DianEntity.class);
                        switch (entity.getStatus()) {
                            case 0: {

                            }

                                break;
                            case 1: {
                                Message msg = new Message();
                                msg.obj = entity.getData();
                                msg.what = 2;
                                handler.sendMessage(msg);
                            }
                                break;
                            case 2: {
                                handler.sendEmptyMessage(3);
                            }
                                break;
                            case 3: {
                                handler.sendEmptyMessage(4);
                            }
                                break;
                            case 4: {
                                handler.sendEmptyMessage(5);
                            }
                                break;
                            case 5: {
                                handler.sendEmptyMessage(6);
                            }
                                break;
                            case 6: {
                                handler.sendEmptyMessage(7);
                            }
                                break;
                            case 7:

                                break;
                            case 8:

                                break;
                            default:
                                break;

                        }
                    }
                } catch (JsonSyntaxException e) {
                    handler.sendEmptyMessage(0);
                }

            };

        }.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_hongbao_qiang: {// 抢红包
                qiangRedEnvelope();
            }
                break;
            case R.id.fragment_hongbao_shangcheng: {// 进入商城
                Intent intent = new Intent(getActivity(), ActivityShangcheng.class);
                intent.putExtra("back", "红包");
                startActivity(intent);
            }
                break;
            default:
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

    /**
     * 刷新UI
     */
    @Override
    public void refreshUI() {
        getMsg();
    }

}
