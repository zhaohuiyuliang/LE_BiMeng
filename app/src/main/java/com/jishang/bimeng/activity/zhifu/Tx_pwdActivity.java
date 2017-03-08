
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
import com.jishang.bimeng.activity.wode.tixian.TixianActivity;
import com.jishang.bimeng.activity.yuezhan.ActivityYuezhan;
import com.jishang.bimeng.activity.yuezhan.yzlist.ActivityBallGamesDetail;
import com.jishang.bimeng.activity.zhifu.pwd.OnPasswordInputFinish;
import com.jishang.bimeng.activity.zhifu.pwd.PasswordView;
import com.jishang.bimeng.entity.tonyong.TYEntity;
import com.jishang.bimeng.entity.wode.tixian.TixianEntity;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.ToastUtil;
import com.jishang.bimeng.utils.Internet.MyHttpRequest;
import com.jishang.bimeng.utils.dialog.DialogUtils;
import com.jishang.bimeng.utils.url.UrlUtils;

public class Tx_pwdActivity extends BaseActivity {
    private PasswordView pwdview;

    private Intent intent;

    private Context mContext;

    private List<BasicNameValuePair> params;

    private Gson mGson;

    private String pwd;

    private TixianEntity entity;

    @Override
    public int initResource() {
        return R.layout.activity_zfthree;
    }

    @Override
    public void initView() {
        mGson = new Gson();
        params = new ArrayList<BasicNameValuePair>();
        mContext = this;
        intent = getIntent();
        entity = (TixianEntity)intent.getSerializableExtra("entity");
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
        String e_order_no = entity.getData().getE_order_no();
        params.add(new BasicNameValuePair("e_order_no", e_order_no));
        params.add(new BasicNameValuePair("pp_password", pwd));
        new Thread() {
            public void run() {
                String url = UrlUtils.BASEURL + "v1/extract/extrac_by_password.json";
                String result = MyHttpRequest.getHttpPostBasic(url, params, token);
                if (result != null) {
                    try {
                        Log.e("result", result.toString());
                        TYEntity entity = mGson.fromJson(result, TYEntity.class);
                        if (entity.getStatus() == 0) {
                            Message msg = new Message();
                            msg.what = 0;
                            msg.obj = entity.getErrors();
                            handler.sendMessage(msg);
                        } else if (entity.getStatus() == 1) {
                            handler.sendEmptyMessage(1);
                        }
                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                        handler.sendEmptyMessage(2);
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
                    String e = (String)msg.obj;
                    ToastUtil.Toast(mContext, e);
                    break;
                case 1:
                    ToastUtil.Toast(mContext, "提现成功，金额将在72小时打到支付宝账户");
                    killActivity();
                    break;
                case 2:
                    ToastUtil.Toast(mContext, "网络错误");
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
        if (TixianActivity.instance != null) {
            TixianActivity.instance.finish();
        }

        finish();
    }

}
