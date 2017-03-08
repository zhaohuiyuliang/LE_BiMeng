
package com.jishang.bimeng.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.igexin.sdk.PushManager;
import com.jishang.bimeng.R;
import com.jishang.bimeng.activity.base.BaseActivity;
import com.jishang.bimeng.entity.login.LogEntity;
import com.jishang.bimeng.fragment.FragmentActivityHome;
import com.jishang.bimeng.utils.Internet.MyHttpRequest;
import com.jishang.bimeng.utils.InternetUtils;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.ToastUtil;
import com.jishang.bimeng.utils.url.UrlUtils;

import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * 登录界面UI
 * 
 * @author kangming
 */
public class LoginActivity extends BaseActivity implements OnClickListener {
    private EditText mEdt_userPhone;

    private EditText mEdt_userPwd;

    private TextView mTv_login;

    private TextView mTv_reg;

    private List<BasicNameValuePair> params;

    private ProgressDialog dialog;

    private Context mContext;

    private Gson mGson;

    @Override
    public int initResource() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        mEdt_userPhone = (EditText)findViewById(R.id.activity_log_edt_phone);
        mEdt_userPwd = (EditText)findViewById(R.id.activity_log_edt_pwd);
        mTv_login = (TextView)findViewById(R.id.activity_log_bt_login);
        mTv_reg = (TextView)findViewById(R.id.activity_log_tv_register);
        mEdt_userPhone.setText(new SharUtil(LoginActivity.this).getUserPhone());
        mEdt_userPwd.setText(new SharUtil(LoginActivity.this).getUserPwd());
        mContext = this;
        mGson = new Gson();
        params = new ArrayList<>();
        // String gt_cid = PushManager.getInstance().getClientid(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_log_tv_register:// 注册

                Intent intent = new Intent(LoginActivity.this, RegistActivity.class);
                startActivity(intent);
                break;
            case R.id.activity_log_bt_login:
                getDate();
                break;
            default:
                break;

        }

    }

    @Override
    public void initData() {

    }

    /**
     * 连接服务器，获取数据
     */
    public void getDate() {
        Setprogrese();
        String gt_cid = PushManager.getInstance().getClientid(this);
        String phone = mEdt_userPhone.getText().toString().trim();
        String password = mEdt_userPwd.getText().toString().trim();
        params.add(new BasicNameValuePair("phone", phone));
        params.add(new BasicNameValuePair("password", password));
        params.add(new BasicNameValuePair("gt_cid", gt_cid));
        new Thread() {
            public void run() {

                if (!InternetUtils.isNetworkAvailable(LoginActivity.this)) {
                    handler.sendEmptyMessage(7);
                    return;
                }
                String url = UrlUtils.LOGIN_URl;
                String result = MyHttpRequest.getHttpPost(url, params);
                // 判断网络地址是不是能用
                if (result.equals("0")) {
                    handler.sendEmptyMessage(3);
                    return;
                }
                if (result != null) {
                    Log.e("result", result.toString());
                    try {
                        LogEntity entity = mGson.fromJson(result, LogEntity.class);
                        if (entity.getStatus() == 0) {
                            Message msg = new Message();
                            msg.obj = entity.getErrors();
                            msg.what = 0;
                            handler.sendMessage(msg);
                        } else if (entity.getStatus() == 1) {
                            setSp(entity);// 设置共享参数
                            handler.sendEmptyMessage(1);

                        } else if (entity.getStatus() == 2) {
                            Message msg = new Message();
                            msg.what = 2;
                            msg.obj = entity.getErrors();
                            handler.sendMessage(msg);
                        }
                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                        handler.sendEmptyMessage(3);
                    }

                }

            };
        }.start();
    }

    Handler handler = new Handler() {

        public void handleMessage(android.os.Message msg) {
            dialog.cancel();
            switch (msg.what) {
                case 0:
                    String erros = (String)msg.obj;
                    ToastUtil.Toast(LoginActivity.this, erros);

                    break;
                case 1:
                    // ToastUtil.Toast(LogActivity.this, "登陆成功!");
                    setHx();
                    // finish();\
                    break;
                case 2:
                    String erros1 = (String)msg.obj;
                    Intent intent = new Intent(mContext, RegistActivity3.class);
                    startActivity(intent);
                    ToastUtil.Toast(mContext, erros1);
                    Log.e("erros1", erros1);
                    break;
                case 3:
                    ToastUtil.Toast(mContext, "网络错误");
                    break;
                case 7:
                    ToastUtil.Toast(mContext, "您还没有联网，清先联网");
                    break;

            }
        };
    };

    /**
     * 登录环信服务器
     */
    public void setHx() {
        SharUtil sharUtil = new SharUtil(LoginActivity.this);
        final String userName = sharUtil.getH_username();
        final String userPwd = sharUtil.getH_password();
        if (sharUtil.getH_username() != null) {
            Log.e("userName", userName);
            EMChatManager.getInstance().login(userName, userPwd, new EMCallBack() {

                @Override
                public void onSuccess() {
                    EMChatManager.getInstance().loadAllConversations();
                    Intent intent = new Intent(mContext, FragmentActivityHome.class);
                    startActivity(intent);
                    finish();

                }

                @Override
                public void onProgress(int arg0, String arg1) {

                }

                @Override
                public void onError(int arg0, String arg1) {
                    Log.e("111", arg1);

                }
            });
        } else {
            ToastUtil.Toast(LoginActivity.this, getString(R.string.huan_chat_erro));
        }
    }

    /**
     * @param entity 登录成功之后把用户名、密码、uid、手机号设置到共享参数中
     */
    public void setSp(LogEntity entity) {
        SharUtil shara = new SharUtil(mContext);
        shara.setUserPhone(mEdt_userPhone.getText().toString().trim());
        shara.setUserPwd(mEdt_userPwd.getText().toString().trim());
        shara.setUid(entity.getData().getUid());
        shara.setPhone(entity.getData().getPhone());
        shara.setAccess_token(entity.getData().getAccess_token());
        shara.setH_username(entity.getData().getH_username());
        shara.setUserName(entity.getData().getUsername());
        shara.setH_password(entity.getData().getH_password());
        shara.setHead_img(entity.getData().getHead_img());
        shara.setDescribetion_info(entity.getData().getDescribetion_info());
        shara.setProvince(entity.getData().getS_provname());
        shara.setCity(entity.getData().getS_cityname());
        shara.setBusiness(entity.getData().getW_name());
        shara.setSex(entity.getData().getSex());

    }

    /**
     * 初始化进度条
     */
    public void Setprogrese() {
        dialog = new ProgressDialog(LoginActivity.this);
        dialog.setMessage(getString(R.string.loading_login));
        dialog.show();
    }

    @Override
    public void addListener() {
        mTv_reg.setOnClickListener(this);
        mTv_login.setOnClickListener(this);
    }

}
