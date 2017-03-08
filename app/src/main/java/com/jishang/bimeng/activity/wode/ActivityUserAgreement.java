
package com.jishang.bimeng.activity.wode;

import android.os.Bundle;
import android.widget.TextView;

import com.jishang.bimeng.R;
import com.jishang.bimeng.ui.BaseActivity;

/**
 * "用户协议"UI
 * 
 * @author wangliang Jul 18, 2016
 */
public class ActivityUserAgreement extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_agreement);
        ((TextView)findViewById(R.id.tv_back_to_activity)).setText(R.string.my);
    }

}
