
package com.jishang.bimeng.activity.wode;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jishang.bimeng.R;
import com.jishang.bimeng.activity.base.BaseActivity;
import com.jishang.bimeng.entity.wode.chongzhi.ChongzhiEntity;
import com.jishang.bimeng.entity.wode.chongzhi.ChongzhiEntity.Data;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.ToastUtil;
import com.jishang.bimeng.utils.Internet.MyHttpRequest;
import com.jishang.bimeng.utils.dialog.DialogUtils;
import com.jishang.bimeng.utils.url.UrlUtils;
import com.jishang.bimeng.zhifubao.ActivityOrderForm;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class ActivityChongzhi extends BaseActivity implements OnClickListener,
        OnCheckedChangeListener {
    private EditText mEdt_jine;

    private TextView mTv_confirm, mTv_barname;

    private String token;

    private Context mContext;

    private List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();

    private Gson mGson;

    private RadioGroup mRg;

    public static ActivityChongzhi instance = null;

    /**
     * 3为支付宝，4为微信
     */
    private String zf_style = "4";

    private IWXAPI api;

    private SharUtil mShare;

    @Override
    public int initResource() {
        return R.layout.activity_chongzhi;
    }

    @Override
    public void initView() {
        instance = this;
        mContext = this;
        mTv_barname = (TextView)findViewById(R.id.activity_chongzhi_name);
        mEdt_jine = (EditText)findViewById(R.id.activity_chongzhi_jine);
        mTv_confirm = (TextView)findViewById(R.id.activity_chongzhi_confirm);
        mRg = (RadioGroup)findViewById(R.id.activity_chongzhi_two_rg);
        mGson = new Gson();
        token = new SharUtil(mContext).getAccess_token();
        mShare = new SharUtil(mContext);
        api = WXAPIFactory.createWXAPI(this, "wxe094a1993e708fe4");
    }

    @Override
    public void initData() {

    }

    @Override
    public void addListener() {
        mTv_confirm.setOnClickListener(this);
        mRg.setOnCheckedChangeListener(this);
    }

    public void back(View v) {
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_chongzhi_confirm: {
                chongzhi();// 充值
            }
                break;
            default:
                break;

        }
    }

    public void chongzhi() {
        DialogUtils.getInstance().createNotifier(mContext, "正在加载中");
        String re_money = mEdt_jine.getText().toString().trim();
        params.add(new BasicNameValuePair("re_money", re_money));
        params.add(new BasicNameValuePair("zf_style", zf_style));
        new Thread() {
            public void run() {
                String url = UrlUtils.BASEURL + "v1/recharge/pay_payment.json";
                String result = MyHttpRequest.getHttpPostBasic(url, params, token);
                if (result != null) {
                    try {
                        ChongzhiEntity entity = mGson.fromJson(result, ChongzhiEntity.class);
                        /* status,0为失败，3为支付宝数据，4为微信数据 */
                        if (entity.getStatus() == 0) {
                            Message msg = new Message();
                            msg.what = 0;
                            msg.obj = entity.getErrors();
                            handler.sendMessage(msg);

                        } else if (entity.getStatus() == 3) {
                            Message msg1 = new Message();
                            msg1.obj = entity;
                            msg1.what = 3;
                            handler.sendMessage(msg1);
                        } else if (entity.getStatus() == 4) {
                            Data data = entity.getData();
                            Message msg2 = new Message();
                            msg2.obj = data;
                            msg2.what = 4;
                            handler.sendMessage(msg2);
                        }
                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                    }

                }
            };
        }.start();
    }

    public Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            DialogUtils.getInstance().cancel();
            switch (msg.what) {
                case 0:
                    String erro = (String)msg.obj;
                    ToastUtil.Toast(mContext, erro);

                    break;
                case 1:

                    break;
                case 3: {// 充值--支付宝支付
                    ChongzhiEntity data = (ChongzhiEntity)msg.obj;
                    Intent intent1 = new Intent(mContext, ActivityOrderForm.class);
                    intent1.putExtra("ChongzhiEntity", data);
                    startActivity(intent1);
                }
                    break;
                case 4: {// 充值--微信支付
                    Data data1 = (Data)msg.obj;
                    Data.WX entity = data1.getWx();
                    Data.Payment payment = data1.getPayment();
                    initShare(payment);
                    Pay(entity);
                }
                    break;
                default:
                    break;

            }
        };
    };

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.activity_chongzhi_rb_weichat:
                zf_style = "4";
                break;
            case R.id.activity_chongzhi_rb_zfb:
                zf_style = "3";
                break;
            default:
                break;

        }
    }

    /**
     * @param entity 初始化订单信息
     */
    public void initShare(Data.Payment entity) {
        /* 设置支付来源，0为约战，2为充值 */
        mShare.setPaymethod("2");
        mShare.setP_id(entity.getRe_id());
        mShare.setUid(entity.getRe_uid());
        mShare.setOrder_notice_sn(entity.getRe_order_notice());
        mShare.setChange(entity.getIs_recharge());
        mShare.setThird(entity.getRe_money());
    }

    /**
     * @param entity 微信支付重要代码
     */
    public void Pay(Data.WX entity) {
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
