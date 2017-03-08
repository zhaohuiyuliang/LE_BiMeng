
package com.jishang.bimeng.activity.zhifu;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jishang.bimeng.R;
import com.jishang.bimeng.activity.base.BaseActivity;
import com.jishang.bimeng.activity.yuezhan.ActivityYuezhan;
import com.jishang.bimeng.activity.yuezhan.yzlist.ActivityBallGamesDetail;
import com.jishang.bimeng.activity.zhifu.pwd.OnPasswordInputFinish;
import com.jishang.bimeng.activity.zhifu.pwd.PasswordView;
import com.jishang.bimeng.entity.yuezhan.zf.Zf_list_DataEntity;
import com.jishang.bimeng.entity.yuezhan.zf.Zf_threeEntity;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.ToastUtil;
import com.jishang.bimeng.utils.Internet.MyHttpRequest;
import com.jishang.bimeng.utils.dialog.DialogUtils;
import com.jishang.bimeng.utils.url.UrlUtils;

public class Yw_Zf_threeActivity extends BaseActivity {
    private PasswordView pwdview;

    private Intent intent;

    Zf_list_DataEntity entity;

    private Context mContext;

    private List<BasicNameValuePair> params;

    private Gson mGson;

    private String pwd;

    @Override
    public int initResource() {
        return R.layout.activity_yw_zfthree;
    }

    @Override
    public void initView() {
        mGson = new Gson();
        params = new ArrayList<BasicNameValuePair>();
        mContext = this;
        intent = getIntent();
        entity = (Zf_list_DataEntity)intent.getSerializableExtra("entity");
        pwdview = (PasswordView)findViewById(R.id.activity_zfthree_pass);

    }

    @Override
    public void initData() {

    }

    @Override
    public void addListener() {
        pwdview.setOnFinishInput(new OnPasswordInputFinish() {

            @Override
            public void inputFinish() {
                pwd = pwdview.getStrPassword();
                putMsg(pwd);

            }
        });
        pwdview.getCancelImageView().setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });

    }

    public void putMsg(String pwd) {
        DialogUtils.getInstance().createNotifier(mContext, "正在加载中");
        final String token = new SharUtil(mContext).getAccess_token();
        Log.e("entity", entity.toString());
        String p_id = entity.getYpn_id();
        String uid = entity.getPnyw_uid();
        String order_notice_sn = entity.getYw_order_notice();
        String change_id = entity.getChange_id() + "";
        String income_id = entity.getIncome_id() + "";
        String third_id = entity.getThird_id() + "";
        params.add(new BasicNameValuePair("ypn_id", p_id));
        params.add(new BasicNameValuePair("pnyw_uid", uid));
        params.add(new BasicNameValuePair("yw_order_notice", order_notice_sn));
        params.add(new BasicNameValuePair("change_id", change_id + ""));
        params.add(new BasicNameValuePair("income_id", income_id + ""));
        params.add(new BasicNameValuePair("third_id", third_id + ""));
        params.add(new BasicNameValuePair("pp_password", pwd));
        new Thread() {
            public void run() {
                String url = UrlUtils.BASEURL + "v1/yw/yw_pay.json";
                String result = MyHttpRequest.getHttpPostBasic(url, params, token);
                if (result != null) {
                    // Log.e("result", result.toString());
                    try {
                        Zf_threeEntity entity = mGson.fromJson(result, Zf_threeEntity.class);
                        Log.e("entity", entity.toString());
                        int status = entity.getStatus();
                        if (status == 1) {
                            handler.sendEmptyMessage(1);
                        } else {
                            Message msg = new Message();
                            msg.obj = entity.getErrors();
                            msg.what = 2;
                            handler.sendMessage(msg);
                        }
                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                        handler.sendEmptyMessage(0);
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
                    ToastUtil.Toast(mContext, "支付成功");
                    killActivity();
                    break;
                case 2:
                    String e = (String)msg.obj;
                    ToastUtil.Toast(mContext, e);
                    // 跳转到我的设置密码
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

        finish();
    }

}
