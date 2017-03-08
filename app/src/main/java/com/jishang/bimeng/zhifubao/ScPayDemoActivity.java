
package com.jishang.bimeng.zhifubao;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jishang.bimeng.R;
import com.jishang.bimeng.activity.base.BaseActivity;
import com.jishang.bimeng.activity.wode.DuihuanActivity;
import com.jishang.bimeng.entity.yuezhan.zf.ZftwoEntity;
import com.jishang.bimeng.entity.yuezhan.zf.zfb.ZfbEntity;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.ToastUtil;
import com.jishang.bimeng.utils.Internet.MyHttpRequest;
import com.jishang.bimeng.utils.url.UrlUtils;

/**
 * 支付宝
 * 
 * @author wangliang Jul 18, 2016
 */
public class ScPayDemoActivity extends BaseActivity implements OnClickListener {

    private ZftwoEntity.Data entity;

    private Intent intent;

    private List<BasicNameValuePair> params;

    private TextView mTv_name, mTv_price, mTv_body, mTv_barname;

    private static final int SDK_PAY_FLAG = 1;

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
        entity = (ZftwoEntity.Data)intent.getSerializableExtra("entity");
        mGson = new Gson();

        token = new SharUtil(mContext).getAccess_token();
        mTv_barname.setText(R.string.aliPay);

    }

    @Override
    public void initData() {
        mTv_name.setText(entity.getGift_name());
        mTv_body.setText(entity.getBody());
        mTv_price.setText(entity.getMoney());
        ((TextView)findViewById(R.id.tv_back_to_activity)).setText(R.string.pay);
    }

    /**
     * 调用支付宝SDK支付 call alipay sdk pay.
     */
    public void pay(View v) {

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

    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String)msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/
                     * doc2/ alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // Toast.makeText(mContext, "支付成功",
                        // Toast.LENGTH_SHORT).show();
                        zf_status = 1;
                        putMsg(zf_status);
                    } else if (TextUtils.equals(resultStatus, "8000")) {
                        // Toast.makeText(mContext, "支付结果确认中",
                        // Toast.LENGTH_SHORT)
                        // .show();
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）

                    } else if (TextUtils.equals(resultStatus, "4000")) {
                        // resultStatus={4000};memo={系统繁忙，请稍后再试};result={}
                        Toast.makeText(mContext, payResult.getMemo(), Toast.LENGTH_SHORT).show();
                    } else {
                        // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                        // Toast.makeText(mContext, "支付失败",
                        // Toast.LENGTH_SHORT)
                        zf_status = 0;
                    }

                    break;
                }
                case 2:
                    String erros = (String)msg.obj;
                    ToastUtil.Toast(mContext, erros);
                    break;
                case 3: {// 支付宝支付成功
                    ToastUtil.Toast(mContext, "支付成功");
                    String type = application.getType();
                    if (!TextUtils.isEmpty(type) && "商城".compareTo(type) == 0) {
                        Intent intent = new Intent(ScPayDemoActivity.this, DuihuanActivity.class);
                        startActivity(intent);
                        application.setType("");
                    }

                }
                    break;
                default:
                    break;
            }
        };
    };

    public void putMsg(int zf_status) {
        String p_id = entity.getP_id();
        String uid = entity.getUid();
        String order_notice_sn = entity.getOrder_id();
        String change = entity.getChange();
        String income = entity.getIncome();
        String third = entity.getMoney();
        params.add(new BasicNameValuePair("bp_id", p_id));
        params.add(new BasicNameValuePair("uid", uid));
        params.add(new BasicNameValuePair("bp_order_notice", order_notice_sn));
        params.add(new BasicNameValuePair("change", change));
        params.add(new BasicNameValuePair("income", income));
        params.add(new BasicNameValuePair("third", third));
        params.add(new BasicNameValuePair("zf_status", zf_status + ""));
        new Thread() {
            public void run() {
                String url = UrlUtils.BASEURL + "v1/busine/thrid_pay_success_fail.json";
                String result = MyHttpRequest.getHttpPostBasic(url, params, token);
                if (result != null) {
                    // Log.e("result", result.toString());
                    try {
                        ZfbEntity entity = mGson.fromJson(result, ZfbEntity.class);
                        if (entity.getStatus() == 0) {
                            Message msg = new Message();
                            msg.obj = entity.getErrors();
                            msg.what = 2;
                            mHandler.sendMessage(msg);
                        } else if (entity.getStatus() == 1) {
                            mHandler.sendEmptyMessage(3);

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
