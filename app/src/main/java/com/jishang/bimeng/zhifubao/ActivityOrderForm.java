
package com.jishang.bimeng.zhifubao;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jishang.bimeng.R;
import com.jishang.bimeng.activity.base.BaseActivity;
import com.jishang.bimeng.activity.wode.ActivityChongzhi;
import com.jishang.bimeng.activity.yuezhan.ActivityYuezhan;
import com.jishang.bimeng.activity.yuezhan.yzlist.ActivityBallGamesDetail;
import com.jishang.bimeng.activity.zhifu.ActivityPayTypeChoose;
import com.jishang.bimeng.activity.zhifu.ZfListActivity;
import com.jishang.bimeng.activity.zhifu.shangcheng.Sc_ZfListActivity;
import com.jishang.bimeng.activity.zhifu.shangcheng.Sc_dt_ListActivity;
import com.jishang.bimeng.entity.wode.chongzhi.ChongzhiEntity;
import com.jishang.bimeng.entity.yuezhan.zf.zfb.ZfbEntity;
import com.jishang.bimeng.net.ProjectRewardPayChooseResult;
import com.jishang.bimeng.ui.UIHandler;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.ToastUtil;
import com.jishang.bimeng.utils.Internet.MyHttpRequest;
import com.jishang.bimeng.utils.dialog.DialogUtils;
import com.jishang.bimeng.utils.url.UrlUtils;

/**
 * "充值订单"UI
 * 
 * @author kangming
 */
public class ActivityOrderForm extends BaseActivity implements OnClickListener {

    private ChongzhiEntity entity;

    private Intent intent;

    private List<BasicNameValuePair> params;

    private TextView mTv_name, mTv_price, mTv_body, mTv_barname;

    private Context mContext;

    private int zf_status = 1;

    private String token;

    private Gson mGson;

    @Override
    public int initResource() {
        return R.layout.activity_pay_external;
    }

    @Override
    public void initView() {
        mContext = this;
        mTv_name = (TextView)findViewById(R.id.pay_external_name);
        mTv_body = (TextView)findViewById(R.id.pay_external_body);
        mTv_price = (TextView)findViewById(R.id.pay_external_price);
        mTv_barname = (TextView)findViewById(R.id.pay_extrnal_barname);
        params = new ArrayList<BasicNameValuePair>();
        intent = getIntent();
        entity = (ChongzhiEntity)intent.getSerializableExtra("ChongzhiEntity");
        mGson = new Gson();

        token = new SharUtil(mContext).getAccess_token();
        mTv_barname.setText("订单详情");

    }

    @Override
    public void initData() {
        mTv_name.setText("充值");
        mTv_body.setText("钱包充值");
        mTv_price.setText(entity.getData().getPayment().getRe_money());
        uiHandler = new OrderFormHandler(this);
    }

    /**
     * call alipay sdk pay.
     */
    public void pay(View v) {
        ProjectRewardPayChooseResult result = new ProjectRewardPayChooseResult();
        result.data = result.new Data();
        result.data.zfb = result.data.new ZFB();
        result.data.zfb.partner = entity.getData().getZfb().getPartner();
        result.data.money = entity.getData().getPayment().getRe_money();
        result.data.gift_name = mTv_name.getText().toString();
        result.data.body = mTv_body.getText().toString();
        result.data.order_id = entity.getData().getPayment().getRe_order_notice();
        result.data.zfb.rsa_private = entity.getData().getZfb().getRsa_private();
        application.getController().doALIPAY(uiHandler, result, ActivityOrderForm.this);
    }

    @Override
    public void addListener() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case 1:

                break;

            default:
                break;
        }
    }

    private class OrderFormHandler extends UIHandler {
        public OrderFormHandler(Activity activity) {
            super(activity);
        }

        @SuppressWarnings("unused")
        public void onMessage(Message msg) {
            DialogUtils.getInstance().cancel();
            switch (msg.what) {
                case UI_FRESH_ALIPAY_PAY_SUCCESS: {
                    PayResult payResult = new PayResult((String)msg.obj);

                    String resultInfo = payResult.getResult();

                    String resultStatus = payResult.getResultStatus();
                    if (TextUtils.equals(resultStatus, "9000")) {
                        zf_status = 1;
                    } else {
                        if (TextUtils.equals(resultStatus, "8000")) {

                        } else {
                            zf_status = 0;

                        }
                    }
                    putMsg(zf_status);
                }
                    break;
                case 2: {
                    String erros = (String)msg.obj;
                    ToastUtil.Toast(mContext, erros);
                }
                    break;
                case 3: {
                    ToastUtil.Toast(mContext, "鏀粯鎴愬姛");
                    killActivity();
                }
                    break;
            }
        };
    };

    /**
     * 鏉�姝讳箣鍓嶇殑鐣岄潰锛屾敮浠樻垚鍔熶箣鍚庣洿鎺ヨ烦鍒伴椤�
     */
    public void killActivity() {
        if (ActivityPayTypeChoose.instance != null) {
            ActivityPayTypeChoose.instance.finish();
        }
        if (ZfListActivity.instance != null) {
            ZfListActivity.instance.finish();
        }
        if (ActivityYuezhan.instance != null) {
            ActivityYuezhan.instance.finish();
        }
        if (ActivityBallGamesDetail.instance != null) {
            ActivityBallGamesDetail.instance.finish();
        }
        if (Sc_dt_ListActivity.instance != null) {
            Sc_dt_ListActivity.instance.finish();
        }
        if (Sc_ZfListActivity.instance != null) {
            Sc_ZfListActivity.instance.finish();
        }
        if (Sc_ZfListActivity.instance != null) {
            Sc_ZfListActivity.instance.finish();
        }
        if (ActivityChongzhi.instance != null) {
            ActivityChongzhi.instance.finish();
        }

        finish();
    }

    public void putMsg(int zf_status) {
        DialogUtils.getInstance().createNotifier(mContext, "数据加载中...");
        params.add(new BasicNameValuePair("re_id", entity.getData().getPayment().getRe_id()));
        params.add(new BasicNameValuePair("uid", entity.getData().getPayment().getRe_uid()));
        params.add(new BasicNameValuePair("re_order_notice", entity.getData().getPayment()
                .getRe_order_notice()));
        params.add(new BasicNameValuePair("third", entity.getData().getPayment().getRe_money()));
        params.add(new BasicNameValuePair("zf_status", zf_status + ""));

        new Thread() {
            public void run() {
                String url = UrlUtils.BASEURL + "v1/recharge/thrid_pay_success_fail.json";
                String result = MyHttpRequest.getHttpPostBasic(url, params, token);
                if (result != null) {
                    try {
                        ZfbEntity entity = mGson.fromJson(result, ZfbEntity.class);
                        if (entity.getStatus() == 0) {
                            Message msg = new Message();
                            msg.obj = entity.getErrors();
                            msg.what = 2;
                            uiHandler.sendMessage(msg);
                        } else if (entity.getStatus() == 1) {
                            uiHandler.sendEmptyMessage(3);

                        }
                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                    }
                }
            };
        }.start();
    }

    public void back(View v) {
        finish();
    }

}
