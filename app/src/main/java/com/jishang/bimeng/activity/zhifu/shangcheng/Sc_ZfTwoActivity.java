
package com.jishang.bimeng.activity.zhifu.shangcheng;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jishang.bimeng.R;
import com.jishang.bimeng.activity.base.BaseActivity;
import com.jishang.bimeng.activity.wode.PwdSettingActivity;
import com.jishang.bimeng.entity.shangcheng.zhifu.Sc_ft_dataEntity;
import com.jishang.bimeng.entity.yuezhan.zf.ZftwoEntity;
import com.jishang.bimeng.entity.yuezhan.zf.Zftwo_data_wechatEntity;
import com.jishang.bimeng.ui.UIHandler;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.ToastUtil;
import com.jishang.bimeng.utils.Internet.MyHttpRequest;
import com.jishang.bimeng.utils.dialog.DialogUtils;
import com.jishang.bimeng.utils.url.UrlUtils;
import com.jishang.bimeng.wxapi.Constants;
import com.jishang.bimeng.zhifubao.ScPayDemoActivity;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * "支付选项"UI
 * 
 * @author wangliang Jul 16, 2016
 */
public class Sc_ZfTwoActivity extends BaseActivity implements OnClickListener {
    /**
     * "支付"按键
     */
    private TextView activity_zf_two_confirm;

    private TextView mTv_change, mTv_income;

    private CheckBox mCb_change, mCb_income, mCb_wechat, mCb_zfb;

    private int change_id = 1, income_id = 0, third_id = 0, weixin_id = 0;

    private Sc_ft_dataEntity entity;

    private Gson mGson;

    private Context mContext;

    public static Sc_ZfTwoActivity instance = null;

    private String back;// 之前UI的名称(由哪个UI进入到支付界面的)

    @Override
    public int initResource() {
        return R.layout.activity_zf_two;
    }

    @Override
    public void initView() {
        // 将该APP注册到微信
        msgApi.registerApp(Constants.APP_ID);
        instance = this;
        mContext = this;
        uiHandler = new Sc_ZfTwoHandler(this);
        Intent intent = getIntent();
        mGson = new Gson();
        entity = (Sc_ft_dataEntity)intent.getSerializableExtra("entity");
        back = intent.getStringExtra("back");

        mTv_change = (TextView)findViewById(R.id.activity_zf_two_change);
        mTv_income = (TextView)findViewById(R.id.activity_zf_two_income);
        activity_zf_two_confirm = (TextView)findViewById(R.id.activity_zf_two_confirm);
        mCb_change = (CheckBox)findViewById(R.id.activity_zf_two_cb_change);
        mCb_income = (CheckBox)findViewById(R.id.activity_zf_two_cb_income);

        mCb_wechat = (CheckBox)findViewById(R.id.activity_zf_two_cb_weichat);
        mCb_zfb = (CheckBox)findViewById(R.id.activity_zf_two_cb_zfb);

        initText();
    }

    public void initText() {
        mTv_change.setText(entity.getChange());
        mTv_income.setText(entity.getIncome());
    }

    @Override
    public void initData() {
        ((TextView)findViewById(R.id.tv_back_to_activity))
                .setText(R.string.the_order_details_details);
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

        mCb_wechat.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mCb_zfb.setChecked(false);
                    weixin_id = 4;
                } else {
                    weixin_id = 0;
                }

            }
        });
        mCb_zfb.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    mCb_wechat.setChecked(false);
                    third_id = 3;
                } else {
                    third_id = 0;
                }
            }
        });
        activity_zf_two_confirm.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_zf_two_confirm:
                putMsg();
                break;
            default:
                break;

        }

    }

    /**
     * 发送支付要素到云集服务器
     */
    public void putMsg() {
        DialogUtils.getInstance().createNotifier(mContext, "正在加载中");
        final String token = new SharUtil(Sc_ZfTwoActivity.this).getAccess_token();
        new Thread() {
            public void run() {
                String bp_id = entity.getBp_id();
                String bp_uid = entity.getMoney();
                String bp_order_notice = entity.getBp_order_notice();
                List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
                params.add(new BasicNameValuePair("bp_id", bp_id));
                params.add(new BasicNameValuePair("money", bp_uid));
                params.add(new BasicNameValuePair("bp_order_notice", bp_order_notice));
                params.add(new BasicNameValuePair("change_id", change_id + ""));
                params.add(new BasicNameValuePair("income_id", income_id + ""));
                params.add(new BasicNameValuePair("third_id", third_id + ""));
                params.add(new BasicNameValuePair("weixin_id", weixin_id + ""));
                String url = UrlUtils.BASEURL + "v1/busine/b_pay.json";
                String result = MyHttpRequest.getHttpPostBasic(url, params, token);
                if (result == null) {
                    return;
                }
                try {
                    ZftwoEntity entity = mGson.fromJson(result, ZftwoEntity.class);
                    int status = entity.getStatus();
                    switch (status) {
                        case 0:
                            Message msg = new Message();
                            msg.what = 0;
                            msg.obj = entity.getErrors();
                            uiHandler.sendMessage(msg);

                            break;

                        case 1:

                            break;
                        case 2:
                            uiHandler.sendEmptyMessage(2);
                            // 跳转到我的设置密码

                            break;
                        case 3: {
                            /* 支付宝界面 */
                            ZftwoEntity.Data data = entity.getData();
                            Message msg1 = new Message();
                            msg1.obj = data;
                            msg1.what = 3;
                            uiHandler.sendMessage(msg1);
                        }
                            break;
                        case 4: {
                            /* 微信 */
                            ZftwoEntity.Data data1 = entity.getData();
                            Message msg2 = new Message();
                            msg2.what = 4;
                            msg2.obj = data1;
                            uiHandler.sendMessage(msg2);
                        }
                            break;
                        case 5: {
                            /* 跳到支付密码界面 */
                            uiHandler.sendEmptyMessage(5);
                        }
                            break;
                        default:
                            break;
                    }
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    Message msg = new Message();
                    msg.obj = result;
                    msg.what = UIHandler.NETWORK_ERRORS;
                    uiHandler.sendMessage(msg);

                }

            };
        }.start();
    }

    class Sc_ZfTwoHandler extends UIHandler {

        public Sc_ZfTwoHandler(Activity activity) {
            super(activity);
        }

        public void onMessage(android.os.Message msg) {
            DialogUtils.getInstance().cancel();
            switch (msg.what) {
                case 0: {
                    /* 各种错误 */
                    String erro = (String)msg.obj;
                    ToastUtil.Toast(mContext, erro);
                }
                    break;
                case 1:

                    break;
                case 2: {
                    Intent intent = new Intent(mContext, PwdSettingActivity.class);
                    startActivity(intent);
                    // 跳转到我的设置密码
                }
                    break;
                case 3: {// 跳转到支付宝UI * 支付宝数据 */
                    application.setType(back);
                    ZftwoEntity.Data data = (ZftwoEntity.Data)msg.obj;
                    Intent intent1 = new Intent(mContext, ScPayDemoActivity.class);
                    intent1.putExtra("entity", data);
                    application.setType(back);
                    startActivity(intent1);
                }
                    break;
                case 4: {
                    /* 微信 */
                    ZftwoEntity.Data data1 = (ZftwoEntity.Data)msg.obj;
                    Zftwo_data_wechatEntity entity1 = data1.getWx();
                    initShare(data1);
                    application.setType(back);
                    WeiXinPay(entity1);
                }
                    break;
                case 5: {
                    /* 跳到支付密码界面 */
                    entity.setChange_id(change_id);
                    entity.setIncome_id(income_id);
                    entity.setThird_id(third_id);
                    Intent intent = new Intent(mContext, Sc_Zf_threeActivity.class);
                    intent.putExtra("entity", entity);
                    intent.putExtra("back", back);
                    startActivityForResult(intent, 0);
                }
                    break;
                case NETWORK_ERRORS: {
                    String erro = (String)msg.obj;
                    ToastUtil.Toast(mContext, erro);
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
        /* 设置支付来源，1为商城，0为约战 */
        SharUtil mShare = new SharUtil(mContext);
        mShare.setPaymethod("1");
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
        // 商户在微信开放平台申请的应用id
        request.appId = entity.getAppid();
        // 商户id
        request.partnerId = entity.getPartnerid();
        // 预支付订单
        request.prepayId = entity.getPrepayid();
        // 商家根据文档填写的数据和签名
        request.packageValue = "Sign=WXPay";
        // 随机串，防重发
        request.nonceStr = entity.getNoncestr();
        // 时间戳，防重发
        request.timeStamp = entity.getTimestamp();
        // 商家根据微信开放平台文档对数据做的签名
        request.sign = entity.getSign();
        if (!request.checkArgs()) {
            Toast.makeText(mContext, "参数不合法", Toast.LENGTH_LONG).show();
        } else {
            // 调用API接口发送数据到微信
            msgApi.sendReq(request);
        }

    }

    final IWXAPI msgApi = WXAPIFactory.createWXAPI(application, null);

}
