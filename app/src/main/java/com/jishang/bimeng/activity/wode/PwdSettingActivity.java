
package com.jishang.bimeng.activity.wode;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.method.DialerKeyListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jishang.bimeng.R;
import com.jishang.bimeng.activity.base.BaseActivity;
import com.jishang.bimeng.entity.yuezhan.zf.Zf_threeEntity;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.ToastUtil;
import com.jishang.bimeng.utils.Internet.MyHttpRequest;
import com.jishang.bimeng.utils.dialog.DialogUtils;
import com.jishang.bimeng.utils.url.UrlUtils;

/**
 * "设置密码"UI
 * 
 * @author wangliang Jul 30, 2016
 */
public class PwdSettingActivity extends BaseActivity implements OnClickListener {
    private EditText mEdt_pwd1, mEdt_pwd2;

    private Context mContext;

    private TextView mBt_confirm;

    private List<BasicNameValuePair> params;

    private Gson mGson;

    private String token;

    private TextView mTv_name;

    @Override
    public int initResource() {
        return R.layout.activity_pwdsetting;
    }

    @Override
    public void initView() {
        mContext = this;
        mGson = new Gson();
        token = new SharUtil(mContext).getAccess_token();
        params = new ArrayList<BasicNameValuePair>();
        mEdt_pwd1 = (EditText)findViewById(R.id.activity_pwdsetting_pwd_1);
        mEdt_pwd2 = (EditText)findViewById(R.id.activity_pwdsetting_pwd_2);
        mBt_confirm = (TextView)findViewById(R.id.activity_setting_confirm);
        mTv_name = (TextView)findViewById(R.id.activity_pwdsetting_name);
        mTv_name.setText("设置支付密码");

    }

    @Override
    public void initData() {

    }

    @Override
    public void addListener() {
        mBt_confirm.setOnClickListener(this);
        mEdt_pwd1.setKeyListener(DialerKeyListener.getInstance());// 设置为数字键盘
        mEdt_pwd2.setKeyListener(DialerKeyListener.getInstance());// 设置为数字键盘

    }

    public void putMsg(String pwd) {
        DialogUtils.getInstance().createNotifier(mContext, "正在加载中");
        params.add(new BasicNameValuePair("pp_password", pwd));
        new Thread() {
            public void run() {
                String url = UrlUtils.BASEURL + "v1/pay/set_pay_password.json";
                String result = MyHttpRequest.getHttpPostBasic(url, params, token);
                if (result != null) {
                    // Log.e("result", result.toString());
                    try {
                        Zf_threeEntity entity = mGson.fromJson(result, Zf_threeEntity.class);
                        if (entity.getStatus() == 1) {
                            handler.sendEmptyMessage(1);
                        } else if (entity.getStatus() == 0) {
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
                    ToastUtil.Toast(mContext, "设置成功");
                    finish();
                    break;
                case 2:
                    String e = (String)msg.obj;
                    ToastUtil.Toast(mContext, e);
                    break;

            }
        };
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_setting_confirm:
                String pwd1 = mEdt_pwd1.getText().toString().trim();
                String pwd2 = mEdt_pwd2.getText().toString().trim();
                if (pwd1.endsWith(pwd2)) {
                    putMsg(pwd1);
                } else {
                    ToastUtil.Toast(mContext, "两次输入的密码不一致");
                }
                break;

        }

    }

    public void back(View v) {
        finish();
    }

}
