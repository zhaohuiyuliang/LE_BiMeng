
package com.jishang.bimeng.activity.zhifu;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jishang.bimeng.R;
import com.jishang.bimeng.activity.base.BaseActivity;
import com.jishang.bimeng.activity.wode.PwdSettingActivity;
import com.jishang.bimeng.entity.yuezhan.zf.Zf_list_DataEntity;
import com.jishang.bimeng.entity.yuezhan.zf.ZftwoEntity;
import com.jishang.bimeng.entity.yuezhan.zf.Zftwo_data_wechatEntity;
import com.jishang.bimeng.ui.UIHandler;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.ToastUtil;
import com.jishang.bimeng.utils.Internet.MyHttpRequest;
import com.jishang.bimeng.utils.dialog.DialogUtils;
import com.jishang.bimeng.utils.url.UrlUtils;
import com.jishang.bimeng.zhifubao.ActivityAlipay;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * "选择支付选项"UI
 * 
 * @author wangliang Jul 18, 2016
 */
public class ActivityPayTypeChoose extends BaseActivity implements OnClickListener {
    /**
     * "支付"按键
     */
    private TextView mTv_confirm;

    private TextView mTv_change, mTv_income;

    private CheckBox mCb_change, mCb_income, activity_zf_two_cb_weichat, activity_zf_two_cb_zfb;

    private int change_id = 1, income_id = 0, third_id = 0, weixin_id = 0;

    private List<BasicNameValuePair> params;

    private Zf_list_DataEntity entity;

    private Intent intent;

    private Gson mGson;

    private Context mContext;

    public static ActivityPayTypeChoose instance = null;

    private IWXAPI api;

    private SharUtil mShare;

    @Override
    public int initResource() {
        return R.layout.activity_zf_two;
    }

    @Override
    public void initView() {
        instance = this;
        mContext = this;
        intent = getIntent();

        mShare = new SharUtil(mContext);
        mGson = new Gson();
        entity = (Zf_list_DataEntity)intent.getSerializableExtra("entity");

        params = new ArrayList<BasicNameValuePair>();
        mTv_change = (TextView)findViewById(R.id.activity_zf_two_change);
        mTv_income = (TextView)findViewById(R.id.activity_zf_two_income);
        mTv_confirm = (TextView)findViewById(R.id.activity_zf_two_confirm);
        mCb_change = (CheckBox)findViewById(R.id.activity_zf_two_cb_change);
        mCb_income = (CheckBox)findViewById(R.id.activity_zf_two_cb_income);
        activity_zf_two_cb_weichat = (CheckBox)findViewById(R.id.activity_zf_two_cb_weichat);
        activity_zf_two_cb_zfb = (CheckBox)findViewById(R.id.activity_zf_two_cb_zfb);
        api = WXAPIFactory.createWXAPI(this, "wxe094a1993e708fe4");
        initText();
    }

    public void initText() {
        mTv_change.setText(entity.getChange());
        mTv_income.setText(entity.getIncome());
    }

    @Override
    public void initData() {
        ((TextView)findViewById(R.id.tv_back_to_activity)).setVisibility(View.GONE);
        uiHandler = new PayTypeChooseHandler(this);
    }

    @Override
    public void addListener() {
        mCb_change.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    change_id = 1;

                } else {
                    change_id = 0;
                }
            }
        });
        mCb_income.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    income_id = 2;
                } else {
                    income_id = 0;
                }
            }
        });
        activity_zf_two_cb_weichat.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    weixin_id = 4;
                    activity_zf_two_cb_zfb.setChecked(false);
                } else {
                    weixin_id = 0;
                }

            }
        });
        activity_zf_two_cb_zfb.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    third_id = 3;
                    activity_zf_two_cb_weichat.setChecked(false);
                } else {
                    third_id = 0;
                }
            }
        });
        mTv_confirm.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_zf_two_confirm: {// "支付"按键点击事件触发
                putMsg();
            }
                break;
            default:
                break;

        }

    }

    /**
     * 获取订单信息
     */
    public void putMsg() {
        DialogUtils.getInstance().createNotifier(mContext, "正在加载中");
        final String token = mShare.getAccess_token();
        String p_id = entity.getP_id();
        String uid = entity.getUid();
        String order_notice_sn = entity.getOrder_notice();
        params.add(new BasicNameValuePair("p_id", p_id));
        params.add(new BasicNameValuePair("uid", uid));
        params.add(new BasicNameValuePair("order_notice_sn", order_notice_sn));
        params.add(new BasicNameValuePair("change_id", change_id + ""));
        params.add(new BasicNameValuePair("income_id", income_id + ""));
        params.add(new BasicNameValuePair("third_id", third_id + ""));
        params.add(new BasicNameValuePair("weixin_id", weixin_id + ""));
        new Thread() {
            public void run() {
                String url = UrlUtils.BASEURL + "v1/yz/yz_pay.json";
                String result = MyHttpRequest.getHttpPostBasic(url, params, token);
                try {
                    if (result != null) {
                        ZftwoEntity entity = mGson.fromJson(result, ZftwoEntity.class);
                        int status = entity.getStatus();
                        switch (status) {
                            case 0: { /* 各种错误 */
                                Message msg = new Message();
                                msg.what = 0;
                                msg.obj = entity.getErrors();
                                uiHandler.sendMessage(msg);
                            }
                                break;

                            case 1: {

                            }

                                break;
                            case 2: {// 零钱设置密码--跳转到我的设置密码
                                uiHandler.sendEmptyMessage(2);
                            }

                                break;
                            case 3: {/* 支付宝支付 */
                                ZftwoEntity.Data data = entity.getData();
                                Message msg1 = new Message();
                                msg1.obj = data;
                                msg1.what = 3;
                                uiHandler.sendMessage(msg1);
                            }
                                break;
                            case 4: { /* 微信支付 */
                                ZftwoEntity.Data data1 = entity.getData();
                                Log.e("data1", data1.toString());
                                Message msg2 = new Message();
                                msg2.what = 4;
                                msg2.obj = data1;
                                uiHandler.sendMessage(msg2);
                            }
                                break;
                            case 5: {/* 零钱支付--跳到支付密码界面 */
                                uiHandler.sendEmptyMessage(5);
                            }
                                break;
                            default:
                                break;
                        }
                    }
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }

            };
        }.start();
    }

    public class PayTypeChooseHandler extends UIHandler {

        public PayTypeChooseHandler(Activity activity) {
            super(activity);
        }

        public void onMessage(android.os.Message msg) {
            DialogUtils.getInstance().cancel();
            switch (msg.what) {
                case 0: { /* 各种错误 */
                    String erro = (String)msg.obj;
                    ToastUtil.Toast(mContext, erro);
                }
                    break;
                case 1:

                    break;
                case 2: {// 跳转到我的设置密码
                    Intent intent = new Intent(mContext, PwdSettingActivity.class);
                    startActivity(intent);
                }

                    break;
                case 3: { /* 支付宝支付 */
                    ZftwoEntity.Data data = (ZftwoEntity.Data)msg.obj;
                    Intent intent1 = new Intent(mContext, ActivityAlipay.class);
                    intent1.putExtra("entity", data);
                    startActivity(intent1);
                }
                    break;
                case 4: { /* 微信支付 */
                    ZftwoEntity.Data data1 = (ZftwoEntity.Data)msg.obj;
                    Zftwo_data_wechatEntity entity1 = data1.getWx();
                    initShare(data1);
                    WeiXinPay(entity1);
                }
                    break;
                case 5: { /* 跳到支付密码界面 */
                    entity.setChange_id(change_id);
                    entity.setIncome_id(income_id);
                    entity.setThird_id(third_id);
                    intent = new Intent(mContext, ActivityPayPasswordInput.class);
                    intent.putExtra("entity", entity);
                    startActivity(intent);
                }
                    break;
                default:
                    break;

            }
        };
    };

    /**
     * @param entity 初始化订单信息
     */
    public void initShare(ZftwoEntity.Data entity) {
        /* 设置支付来源，0为约战 */
        mShare.setPaymethod("0");
        mShare.setP_id(entity.getP_id());
        mShare.setUid(entity.getUid());
        mShare.setOrder_notice_sn(entity.getOut_trade_no());
        mShare.setChange(entity.getChange());
        mShare.setIncome(entity.getIncome());
        mShare.setThird(entity.getTotal_fee());
    }

    /**
     * @param entity 微信支付重要代码
     */
    public void WeiXinPay(Zftwo_data_wechatEntity entity) {
        PayReq request = new PayReq();
        request.appId = entity.getAppid();
        request.partnerId = entity.getPartnerid();
        request.prepayId = entity.getPrepayid();
        request.packageValue = "Sign=WXPay";
        request.nonceStr = entity.getNoncestr();
        request.timeStamp = entity.getTimestamp();
        request.sign = entity.getSign();
        api.sendReq(request);
    }

}
