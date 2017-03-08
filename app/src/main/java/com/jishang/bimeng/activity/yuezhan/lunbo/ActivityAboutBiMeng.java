
package com.jishang.bimeng.activity.yuezhan.lunbo;

import android.content.Intent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.jishang.bimeng.R;
import com.jishang.bimeng.activity.base.BaseActivity;

/**
 * 关于比盟
 * 
 * @author wangliang Jul 13, 2016
 */
public class ActivityAboutBiMeng extends BaseActivity {
    private WebView web;

    private TextView mTv_name;

    private Intent intent;

    private String url;

    @Override
    public int initResource() {
        return R.layout.activity_about_bimeng;
    }

    @Override
    public void initView() {
        intent = getIntent();
        url = (String)intent.getSerializableExtra("url");
        web = (WebView)findViewById(R.id.activity_web_web);
        mTv_name = (TextView)findViewById(R.id.activity_web_tv_name);

        web.loadUrl(url);
        WebSettings settings = web.getSettings();
        settings.setJavaScriptEnabled(true);
        // 覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        web.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                // 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
        ((TextView)findViewById(R.id.tv_back_to_activity)).setText(R.string.my);
    }

    @Override
    public void initData() {

    }

    @Override
    public void addListener() {

    }

    public void back(View v) {
        finish();
    }

}
