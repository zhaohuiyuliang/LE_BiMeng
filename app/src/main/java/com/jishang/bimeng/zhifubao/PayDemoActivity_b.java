
package com.jishang.bimeng.zhifubao;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.jishang.bimeng.R;
import com.jishang.bimeng.entity.yuezhan.zf.ZftwoEntity;

public class PayDemoActivity_b extends FragmentActivity {

    private ZftwoEntity.Data entity;

    private Intent intent;

    private static final int SDK_PAY_FLAG = 1;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String)msg.obj);
                    String resultInfo = payResult.getResult();// 鍚屾杩斿洖闇�瑕侀獙璇佺殑淇℃伅

                    String resultStatus = payResult.getResultStatus();
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(PayDemoActivity_b.this, "鏀粯鎴愬姛", Toast.LENGTH_SHORT).show();
                    } else {
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(PayDemoActivity_b.this, "鏀粯缁撴灉纭涓�",
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(PayDemoActivity_b.this, "鏀粯澶辫触", Toast.LENGTH_SHORT)
                                    .show();

                        }
                    }
                    break;
                }
                default:
                    break;
            }
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_main);
        intent = getIntent();
        entity = (ZftwoEntity.Data)intent.getSerializableExtra("entity");

    }

    /**
     * call alipay sdk pay. 璋冪敤SDK鏀粯
     */
    public void pay(View v) {

    }

}
