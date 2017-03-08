
package com.jishang.bimeng.activity.base;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jishang.bimeng.BimmoApplication;
import com.jishang.bimeng.entity.wode.MyEntity;
import com.jishang.bimeng.ui.UIHandler;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.Internet.MyHttpRequest;
import com.jishang.bimeng.utils.dialog.DialogUtils;
import com.jishang.bimeng.utils.url.UrlUtils;

public abstract class BaseActivity extends Activity {
    public UIHandler uiHandler;

    public BimmoApplication application = BimmoApplication.getApplication();

    /**
     * 初始化布局资源文件
     */
    public abstract int initResource();

    /**
     * 初始化组件
     */
    public abstract void initView();

    /**
     * 初始化数据
     */
    public abstract void initData();

    /**
     * 添加监听
     */
    public abstract void addListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(initResource());
        initView();
        initData();
        addListener();
    }

    public void setHandler(UIHandler handler) {
        this.uiHandler = handler;
    }

    public void back(View v) {
        finish();
    }

    /**
     * 加载我的数据
     */
    public void loadMyData() {
        DialogUtils.getInstance().createNotifier(this, "正在加载...");
        new Thread() {
            public void run() {
                Gson mGson = new Gson();
                String token = new SharUtil(BaseActivity.this).getAccess_token();
                List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
                params.add(new BasicNameValuePair("1", "1"));
                String url = UrlUtils.BASEURL + "v1/user/change_income_integral.json";
                String result = MyHttpRequest.getHttpPostBasic(url, params, token);
                if (result != null) {
                    Log.i("result", result.toString());
                    try {
                        MyEntity entity = mGson.fromJson(result, MyEntity.class);
                        if (entity.getStatus() == 1) {
                            Message msg = new Message();
                            msg.obj = entity.getData();
                            msg.what = MY_DATA_LOAD_SUCCESS;
                            if (uiHandler != null) {
                                uiHandler.sendMessage(msg);
                            }
                        } else {

                        }
                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                    }
                }
            };
        }.start();
    }

    public static final int MY_DATA_LOAD_SUCCESS = 102;

    public static final int MY_DATA_LOAD_FAILED = -102;
}
