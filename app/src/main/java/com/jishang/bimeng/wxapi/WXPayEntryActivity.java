
package com.jishang.bimeng.wxapi;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jishang.bimeng.R;
import com.jishang.bimeng.activity.base.BaseActivity;
import com.jishang.bimeng.activity.wode.DuihuanActivity;
import com.jishang.bimeng.activity.yuezhan.ActivityYuezhan;
import com.jishang.bimeng.activity.yuezhan.yzlist.ActivityBallGamesDetail;
import com.jishang.bimeng.activity.zhifu.ActivityPayTypeChoose;
import com.jishang.bimeng.activity.zhifu.ZfListActivity;
import com.jishang.bimeng.activity.zhifu.shangcheng.Sc_ZfListActivity;
import com.jishang.bimeng.activity.zhifu.shangcheng.Sc_dt_ListActivity;
import com.jishang.bimeng.entity.tonyong.TYEntity;
import com.jishang.bimeng.ui.UIHandler;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.ToastUtil;
import com.jishang.bimeng.utils.Internet.MyHttpRequest;
import com.jishang.bimeng.utils.dialog.DialogUtils;
import com.jishang.bimeng.utils.url.UrlUtils;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * 微信接收支付结果回调UI
 * 
 * @author kangming
 */
public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler {

    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

    private IWXAPI api;

    @Override
    public int initResource() {
        return R.layout.pay_result;
    }

    @Override
    public void initView() {
        uiHandler = new WXPayHandler(this);
        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID, false);
        api.handleIntent(getIntent(), this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void addListener() {

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
        Log.d(TAG, req.openId);
    }

    @Override
    public void onResp(BaseResp resp) {

        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            int zf_status = 0;
            switch (resp.errCode) {
                case 0: {// 展示成功页面
                    zf_status = 1;
                    putMsg(zf_status);
                }
                    break;
                case -1: {// 可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。
                    zf_status = 0;
                    putMsg(zf_status);
                }
                    break;
                case -2: {// 无需处理。发生场景：用户不支付了，点击取消，返回APP。
                    zf_status = 0;
                    finish();
                }
                    break;
                default:
                    break;
            }
        }
    }

    public void putSucess() {

    }

    public void putMsg(final int zf_status) {

        new Thread() {
            public void run() {
                String url = null;
                Gson mGson = new Gson();
                SharUtil mShareutil = new SharUtil(getApplicationContext());
                String paymethod = mShareutil.getPaymethod();
                String p_id = mShareutil.getP_id();
                String uid = mShareutil.getUid();
                String order_notice_sn = mShareutil.getOrder_notice_sn();
                String change = mShareutil.getChange();
                String income = mShareutil.getIncome();
                String third = mShareutil.getThird();
                String token = mShareutil.getAccess_token();
                List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
                if (paymethod.equals("0")) {
                    params.add(new BasicNameValuePair("p_id", p_id));
                    params.add(new BasicNameValuePair("order_notice_sn", order_notice_sn));
                    params.add(new BasicNameValuePair("uid", uid));
                    params.add(new BasicNameValuePair("change", change));
                    params.add(new BasicNameValuePair("income", income));
                    params.add(new BasicNameValuePair("third", third));
                } else if (paymethod.equals("1")) {
                    params.add(new BasicNameValuePair("bp_id", p_id));
                    params.add(new BasicNameValuePair("bp_order_notice", order_notice_sn));
                    params.add(new BasicNameValuePair("uid", uid));
                    params.add(new BasicNameValuePair("change", change));
                    params.add(new BasicNameValuePair("income", income));
                    params.add(new BasicNameValuePair("third", third));
                } else if (paymethod.equals("2")) {
                    params.add(new BasicNameValuePair("re_id", p_id));
                    params.add(new BasicNameValuePair("uid", uid));
                    params.add(new BasicNameValuePair("re_order_notice", order_notice_sn));
                    params.add(new BasicNameValuePair("third", third));

                }

                params.add(new BasicNameValuePair("zf_status", zf_status + ""));
                if (paymethod.equals("0")) {
                    url = UrlUtils.BASEURL + "v1/yz/weixin_pay_success_fail.json";
                } else if (paymethod.equals("1")) {
                    url = UrlUtils.BASEURL + "v1/busine/weixin_pay_success_fail.json";
                } else if (paymethod.equals("2")) {
                    url = UrlUtils.BASEURL + "v1/recharge/thrid_pay_success_fail.json";
                }
                String result = MyHttpRequest.getHttpPostBasic(url, params, token);
                try {
                    if (result != null) {

                        TYEntity entity = mGson.fromJson(result, TYEntity.class);
                        if (entity.getStatus() == 0) {
                            uiHandler.sendEmptyMessage(UIHandler.WEIXIN_PAY_FAILED);
                        } else if (entity.getStatus() == 1) {
                            uiHandler.sendEmptyMessage(UIHandler.WEIXIN_PAY_SUCCESS);
                        }
                    }
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    uiHandler.sendEmptyMessage(UIHandler.NETWORK_ERRORS);
                }
            };
        }.start();
    }

    class WXPayHandler extends UIHandler {
        public WXPayHandler(Activity activity) {
            super(activity);
        }

        public void onMessage(android.os.Message msg) {
            DialogUtils.getInstance().cancel();
            switch (msg.what) {
                case WEIXIN_PAY_FAILED:
                    ToastUtil.Toast(getApplicationContext(), "支付失败");
                    finish();
                    break;
                case WEIXIN_PAY_SUCCESS: {
                    ToastUtil.Toast(getApplicationContext(), "支付成功");
                    killActivity();
                    String back = application.getType();
                    if (!TextUtils.isEmpty(back) && "商城".compareTo(back) == 0) {
                        Intent intent = new Intent(WXPayEntryActivity.this, DuihuanActivity.class);
                        WXPayEntryActivity.this.startActivity(intent);
                        application.setType("");
                    }
                    finish();
                }
                    break;
                case NETWORK_ERRORS:
                    ToastUtil.Toast(getApplicationContext(), "网络错误");
                    break;
                default:
                    break;

            }
        };
    };

    /**
     * 杀死之前的界面，支付成功之后直接跳到首页
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

        finish();
    }

}
