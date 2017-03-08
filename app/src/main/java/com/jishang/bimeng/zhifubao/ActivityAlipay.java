
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
import com.jishang.bimeng.activity.yuezhan.MyYuezhanActivity;
import com.jishang.bimeng.activity.yuezhan.ActivityYuezhan;
import com.jishang.bimeng.activity.yuezhan.yzlist.ActivityBallGamesDetail;
import com.jishang.bimeng.activity.zhifu.ActivityPayTypeChoose;
import com.jishang.bimeng.activity.zhifu.ZfListActivity;
import com.jishang.bimeng.entity.yuezhan.zf.ZftwoEntity;
import com.jishang.bimeng.entity.yuezhan.zf.zfb.ZfbEntity;
import com.jishang.bimeng.ui.UIHandler;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.ToastUtil;
import com.jishang.bimeng.utils.Internet.MyHttpRequest;
import com.jishang.bimeng.utils.url.UrlUtils;

/**
 * "支付宝支付"UI
 * 
 * @author wangliang Jul 18, 2016
 */
public class ActivityAlipay extends BaseActivity implements OnClickListener {

    /**
     * 标题提示语
     */
    private TextView mTv_barname;

    private TextView mTv_name, mTv_price, mTv_body;

    private Context mContext;

    private int zf_status = 1;

    @Override
    public int initResource() {
        return R.layout.activity_pay_external;
    }

    private ZftwoEntity.Data entity;

    @Override
    public void initView() {
        mContext = this;
        mTv_name = (TextView)findViewById(R.id.pay_external_name);
        mTv_body = (TextView)findViewById(R.id.pay_external_body);
        mTv_price = (TextView)findViewById(R.id.pay_external_price);
        mTv_barname = (TextView)findViewById(R.id.pay_extrnal_barname);

        Intent intent = getIntent();
        if (intent != null) {
            entity = (ZftwoEntity.Data)intent.getSerializableExtra("entity");
        }

        ((TextView)findViewById(R.id.tv_back_to_activity))
                .setText(R.string.the_order_details_details);
        mTv_barname.setText("支付宝");

    }

    @Override
    public void initData() {
        uiHandler = new AlipayHandler(this);
        if (entity != null) {
            mTv_name.setText(entity.getGift_name());
            mTv_body.setText(entity.getBody());
            mTv_price.setText(entity.getMoney());
        }
    }

    /**
     * 支付宝支付接口调用
     * 
     * @param v
     */
    public void apliPay(View v) {

    }

    @Override
    public void addListener() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case 1: {

            }
                break;

            default:
                break;
        }
    }

    private class AlipayHandler extends UIHandler {

        public AlipayHandler(Activity activity) {
            super(activity);
        }

        public void onMessage(Message msg) {
            switch (msg.what) {
                case UI_FRESH_ALIPAY_PAY_SUCCESS: {
                    PayResult payResult = new PayResult((String)msg.obj);
                    /**
                     * 
                     */
                    String resultInfo = payResult.getResult();

                    String resultStatus = payResult.getResultStatus();
                    if (TextUtils.equals(resultStatus, "9000")) {
                        zf_status = 1;
                    } else if (TextUtils.equals(resultStatus, "8000")) {

                    } else {
                        zf_status = 0;
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
                    kill();
                    Intent intent = new Intent(mContext, MyYuezhanActivity.class);
                    startActivity(intent);
                    finish();
                }
                    break;
                default:
                    break;

            }
        };
    };

    public void kill() {
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

    }

    /**
     * 向云集服务器传递支付宝支付结果数据
     * 
     * @param zf_status
     */
    public void putMsg(final int zf_status) {
        new Thread() {
            public void run() {
                String p_id = entity.getP_id();
                String uid = entity.getUid();
                String order_notice_sn = entity.getOrder_id();
                String change = entity.getChange();
                String income = entity.getIncome();
                String third = entity.getMoney();
                List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
                params.add(new BasicNameValuePair("p_id", p_id));
                params.add(new BasicNameValuePair("uid", uid));
                params.add(new BasicNameValuePair("order_notice_sn", order_notice_sn));
                params.add(new BasicNameValuePair("change", change));
                params.add(new BasicNameValuePair("income", income));
                params.add(new BasicNameValuePair("third", third));
                params.add(new BasicNameValuePair("zf_status", zf_status + ""));
                String url = UrlUtils.BASEURL + "v1/yz/thrid_pay_success_fail.json";
                String token = new SharUtil(mContext).getAccess_token();
                String result = MyHttpRequest.getHttpPostBasic(url, params, token);
                if (result != null) {
                    try {
                        Gson mGson = new Gson();
                        ZfbEntity entity = mGson.fromJson(result, ZfbEntity.class);
                        if (entity.getStatus() == 0) {
                            Message msg = new Message();
                            msg.obj = entity.getErrors();
                            msg.what = 2;
                            uiHandler.sendMessage(msg);
                        } else if (entity.getStatus() == 1) {
                            uiHandler.sendEmptyMessage(3);

                        } else {

                        }
                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                    }

                }
            };
        }.start();
    }

}
