
package com.jishang.bimeng.welcome;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;

import com.jishang.bimeng.R;
import com.jishang.bimeng.activity.LoginActivity;
import com.jishang.bimeng.fragment.FragmentActivityHome;
import com.jishang.bimeng.utils.SharUtil;

public class StartActivity extends Activity {
    private Boolean isFirst = true;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        mContext = this;
        initView();
    }

    public void initView() {

        new Thread() {
            public void run() {
                SystemClock.sleep(500);
                handler.sendEmptyMessage(1);

            };
        }.start();

    }

    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {

            switch (msg.what) {
                case 1:
                    isFirst = new SharUtil(StartActivity.this).getIsFirst();
                    if (isFirst) {
                        Intent intent = new Intent(StartActivity.this, ViewPagerActivity.class);
                        startActivity(intent);
                        finish();

                        new SharUtil(StartActivity.this).setIsFirst(false);
                        break;
                    } else {
                        // ToastUtil.Toast(StartActivity.this, "第二次");
                        String user = new SharUtil(mContext).getUserName();
                        if (user != null && !user.equals("")) {

                            Intent intent2 = new Intent(StartActivity.this,
                                    FragmentActivityHome.class);
                            startActivity(intent2);
                        } else {
                            Intent intent3 = new Intent(StartActivity.this, LoginActivity.class);
                            startActivity(intent3);
                        }
                        finish();
                    }
                case 2:

                    break;

            }
        };
    };
}
