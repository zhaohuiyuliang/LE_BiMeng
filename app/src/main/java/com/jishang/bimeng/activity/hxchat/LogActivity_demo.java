
package com.jishang.bimeng.activity.hxchat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.jishang.bimeng.R;
import com.jishang.bimeng.utils.SharUtil;

public class LogActivity_demo extends Activity implements OnClickListener {
    private EditText mEdt_userName, mEdt_pwd;

    private Button mBt_comfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_demo);
        initView();
    }

    public void initView() {
        mEdt_userName = (EditText)findViewById(R.id.activity_log_username);
        mEdt_pwd = (EditText)findViewById(R.id.activity_log_pwd);
        mBt_comfirm = (Button)findViewById(R.id.activity_log_confirm);
        mBt_comfirm.setOnClickListener(this);
        mEdt_userName.setText(new SharUtil(LogActivity_demo.this).getUserName());
        mEdt_pwd.setText(new SharUtil(LogActivity_demo.this).getUserPwd());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_log_confirm:
                final String userName = mEdt_userName.getText().toString();
                final String userPwd = mEdt_pwd.getText().toString();

                EMChatManager.getInstance().login(userName, userPwd, new EMCallBack() {

                    @Override
                    public void onSuccess() {
                        EMChatManager.getInstance().loadAllConversations();
                        Intent intent = new Intent(LogActivity_demo.this, ActivityChat.class);
                        startActivity(intent);
                        new SharUtil(LogActivity_demo.this).setUserName(userName);
                        new SharUtil(LogActivity_demo.this).setUserPwd(userPwd);

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

                break;

        }
    }
}
