
package com.jishang.bimeng.ui;

import android.app.Activity;
import android.view.View;

import com.jishang.bimeng.BimmoApplication;

public abstract class BaseActivity extends Activity {

    public BimmoApplication application = BimmoApplication.getApplication();

    public void back(View v) {
        finish();
    }

}
