
package com.jishang.bimeng.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.igexin.sdk.PushManager;
import com.jishang.bimeng.R;
import com.jishang.bimeng.entity.regist.RegistEntity;
import com.jishang.bimeng.entity.regist.Regist_dataEntity;
import com.jishang.bimeng.entity.regist.regist3.Re_wangbaEntity;
import com.jishang.bimeng.utils.InternetUtils;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.ToastUtil;
import com.jishang.bimeng.utils.Internet.MyHttpRequest;
import com.jishang.bimeng.utils.url.UrlUtils;

/**
 * @author kangming 注册的第二个界面，个人信息，包括性别、昵称，手机号
 */
public class RegistActivity1 extends Activity implements OnClickListener {
    private TextView mTv_send, mTv_wangba;

    private EditText mEdt_userName, mEdt_userpwd, mEdt_userpwdagain;

    private RadioGroup mRg;

    private List<BasicNameValuePair> params;

    private int sex = 1;

    private ProgressDialog dialog;

    private Intent intent;

    private Gson mGson;

    private Context mContext;

    private Re_wangbaEntity entity = new Re_wangbaEntity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist1);
        initView();
    }

    public void initView() {
        mContext = this;
        mTv_send = (TextView)findViewById(R.id.activity_regist_bt_send);
        mEdt_userName = (EditText)findViewById(R.id.activity_regist_edt_username);
        mEdt_userpwd = (EditText)findViewById(R.id.activity_regist_edt_userpwd);
        mEdt_userpwdagain = (EditText)findViewById(R.id.activity_regist_edt_userpwd_again);
        mRg = (RadioGroup)findViewById(R.id.activity_regist_rg);
        mTv_wangba = (TextView)findViewById(R.id.activity_regist_edt_wangba);
        intent = getIntent();
        params = new ArrayList<BasicNameValuePair>();
        mTv_send.setOnClickListener(this);
        mTv_wangba.setOnClickListener(this);
        mGson = new Gson();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_regist_bt_send:
                putMsg();
                break;
            case R.id.activity_regist_edt_wangba:
                Intent intent = new Intent(mContext, RegistActivity3.class);
                startActivityForResult(intent, 200);
                break;

        }

    }

    public void RbListener() {
        mRg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.activity_regist_rb_man:
                        sex = 1;
                        break;
                    case R.id.activity_regist_rb_woman:
                        sex = 0;
                        break;

                }
            }
        });
    }

    public void putMsg() {
        String gt_cid = PushManager.getInstance().getClientid(this);
        String userName = mEdt_userName.getText().toString().trim();
        String userPwd = mEdt_userpwd.getText().toString().trim();
        String userPwdagain = mEdt_userpwdagain.getText().toString().trim();
        String phone = (String)intent.getSerializableExtra("userphone");
        String province = entity.getProvince();
        String city = entity.getCity();
        String business = entity.getBusiness();

        if (userPwd.equals(userPwdagain)) {
            Setprogrese();// 启动进度条
            params.add(new BasicNameValuePair("phone", phone));
            params.add(new BasicNameValuePair("username", userName));
            params.add(new BasicNameValuePair("password", userPwd));
            params.add(new BasicNameValuePair("sex", sex + ""));
            params.add(new BasicNameValuePair("province", province));
            params.add(new BasicNameValuePair("city", city));
            params.add(new BasicNameValuePair("business", business));
            params.add(new BasicNameValuePair("gt_cid", gt_cid));
            new Thread() {
                public void run() {

                    if (!InternetUtils.isNetworkAvailable(RegistActivity1.this)) {
                        handler.sendEmptyMessage(7);
                        return;
                    }
                    // String url =
                    // "http://apicms.gbimoo.com/v1/site/signup.json";
                    String url = UrlUtils.REGISTE_SIGNUP;
                    String result = MyHttpRequest.getHttpPost(url, params);
                    // 判断网络地址是不是能用
                    if (result.equals("0")) {
                        handler.sendEmptyMessage(2);
                        return;
                    }
                    if (result != null) {
                        Log.e("result", result.toString());
                        try {
                            RegistEntity entity = mGson.fromJson(result, RegistEntity.class);
                            if (entity.getStatus() == 0) {
                                Message msg = new Message();
                                msg.obj = entity.getErrors();
                                msg.what = 0;
                                handler.sendMessage(msg);
                            } else if (entity.getStatus() == 1) {
                                Regist_dataEntity entity1 = entity.getData();
                                Message msg = new Message();
                                msg.obj = entity1;
                                msg.what = 1;
                                handler.sendMessage(msg);
                            }
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                            handler.sendEmptyMessage(2);
                        }
                    }

                };
            }.start();
        } else {
            ToastUtil.Toast(RegistActivity1.this, getString(R.string.password_not_same));
        }
    }

    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            dialog.cancel();
            switch (msg.what) {
                case 0:
                    String result = (String)msg.obj;
                    ToastUtil.Toast(RegistActivity1.this, result);
                    break;
                case 1:
                    Regist_dataEntity entity = (Regist_dataEntity)msg.obj;
                    setSp(entity);// 设置共享参数
                    ToastUtil.Toast(RegistActivity1.this, "注册成功");
                    finish();
                    break;
                case 2:
                    ToastUtil.Toast(mContext, mContext.getString(R.string.erro_internet));
                    break;
                case 7:
                    ToastUtil.Toast(mContext, "您没有联网，请先联网");
                    break;

            }
        };
    };

    /**
     * @param entity 登录成功之后把用户名、密码、uid、手机号设置到共享参数中
     */
    public void setSp(Regist_dataEntity entity) {
        SharUtil shara = new SharUtil(RegistActivity1.this);
        shara.setUid(entity.getUid());
        shara.setPhone(entity.getPhone());

    }

    public void Setprogrese() {
        dialog = new ProgressDialog(RegistActivity1.this);
        dialog.setMessage(getString(R.string.operate_loading));
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 100:
                entity = (Re_wangbaEntity)data.getSerializableExtra("entity");
                Log.e("entity", entity.toString());
                mTv_wangba.setText(entity.getBusiness_name());
                break;

        }

    }

}
