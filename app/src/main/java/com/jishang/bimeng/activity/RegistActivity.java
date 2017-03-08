
package com.jishang.bimeng.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.jishang.bimeng.R;
import com.jishang.bimeng.entity.regist.YzmEntity;
import com.jishang.bimeng.parse.ParseUtil;
import com.jishang.bimeng.utils.ToastUtil;
import com.jishang.bimeng.utils.Internet.MyHttpRequest;
import com.jishang.bimeng.utils.dialog.DialogUtils;
import com.jishang.bimeng.utils.url.UrlUtils;

/**
 * 注册第一个界面UI, 短信验证 ，把手机号传送到第二个页面
 * 
 * @author kangming
 */
public class RegistActivity extends Activity implements OnClickListener {
    private TextView mTv_send, mTv_confirm, mTv_login;

    private int recLen = 60;

    private EditText mEdt_userphone, mEdt_yanzhengma;

    private List<BasicNameValuePair> params;

    // private ProgressDialog dialog;
    private String Yanzhenma_fwq = null;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        initView();
    }

    public void initView() {
        mContext = this;
        params = new ArrayList<BasicNameValuePair>();
        mTv_send = (TextView)findViewById(R.id.activity_regist_bt_send);
        mEdt_userphone = (EditText)findViewById(R.id.activity_regist_edt_userphone);
        mEdt_yanzhengma = (EditText)findViewById(R.id.activity_regist_edt_yanzhengma);
        mTv_confirm = (TextView)findViewById(R.id.activity_regist_tv_confirm);
        mTv_login = (TextView)findViewById(R.id.activity_regist_login);
        mTv_send.setOnClickListener(this);
        mTv_confirm.setOnClickListener(this);
        mTv_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_regist_bt_send:
                getData();
                break;
            case R.id.activity_regist_tv_confirm:
                if (Yanzhenma_fwq != null) {
                    String Yanzhengma_bendi = mEdt_yanzhengma.getText().toString().trim();
                    String userphone = mEdt_userphone.getText().toString();
                    if (Yanzhenma_fwq.equals(Yanzhengma_bendi)) {
                        ToastUtil.Toast(RegistActivity.this,
                                getString(R.string.verification_successful));
                        Intent intent = new Intent(RegistActivity.this, RegistActivity1.class);
                        intent.putExtra("userphone", userphone);
                        startActivity(intent);
                        finish();

                    } else {
                        ToastUtil
                                .Toast(RegistActivity.this, getString(R.string.please_enter_again));
                    }

                } else {
                    ToastUtil.Toast(RegistActivity.this,
                            getString(R.string.send_verification_code_first));
                }
                break;
            case R.id.activity_regist_login:
                Intent intent = new Intent(mContext, LoginActivity.class);
                startActivity(intent);
                finish();
                break;

        }

    }

    private void sendMsgCode() {
        mTv_send.setClickable(false);
        final Timer timer = new Timer();
        recLen = 60;
        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recLen--;
                        mTv_send.setText("重新发送(" + recLen + ")");
                        if (recLen == 0) {
                            timer.cancel();
                            mTv_send.setText(R.string.re_send);
                            mTv_send.setClickable(true);
                        }
                    }
                });
            }
        };
        timer.schedule(task, 1000, 1000);
    }

    /**
	 * 
	 * 
	 */
    public void getData() {
        DialogUtils.getInstance().createNotifier(mContext, "正在加载中");
        final String userphone = mEdt_userphone.getText().toString().trim();
        String yanzhengma = mEdt_yanzhengma.getText().toString().trim();
        new Thread() {
            public void run() {
                params.add(new BasicNameValuePair("phone", userphone));
                String url = UrlUtils.REGISTE_SMS;
                String result = MyHttpRequest.getHttpPost(url, params);
                if (result != null) {
                    Log.e("result", result.toString());
                    // if(entity.getStatus()==0){
                    Message msg = new Message();
                    msg.obj = result;
                    msg.what = 0;
                    handler.sendMessage(msg);
                    // }else{
                    // handler.sendEmptyMessage(1);
                    // }

                }
            };
        }.start();
    }

    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            DialogUtils.getInstance().cancel();
            String result = (String)msg.obj;
            switch (msg.what) {
                case 0:
                    YzmEntity entity = ParseUtil.getBanner_yanzhengma(result);
                    if (entity.getStatus() == 0) {
                        ToastUtil.Toast(RegistActivity.this, entity.getErrors());
                    } else if (entity.getStatus() == 1) {
                        ToastUtil.Toast(RegistActivity.this, getString(R.string.send_successful));
                        sendMsgCode();
                        Yanzhenma_fwq = entity.getVerify_code();
                        // finish();
                        // if(yam.equals(entity.getVerify_code())){
                        //
                        // }else{
                        //
                        // }
                    }
                    break;

            }
        };
    };

}
