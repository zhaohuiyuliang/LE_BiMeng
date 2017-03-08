
package com.jishang.bimeng.activity.wode;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.jishang.bimeng.R;
import com.jishang.bimeng.activity.base.BaseActivity;

public class HuoyueduActivity extends BaseActivity {
    private TextView mTv_huoyuedu;

    private Intent intent;

    // 我的活跃度值
    private String huoyuedu;

    private TextView mTv_name;

    private WebView web_note_content;

    @Override
    public int initResource() {
        return R.layout.activity_huoyuedu;
    }

    @Override
    public void initView() {
        intent = getIntent();
        huoyuedu = (String)intent.getSerializableExtra("huoyuedu");
        mTv_huoyuedu = (TextView)findViewById(R.id.activity_huoyuedu_money);
        mTv_name = (TextView)findViewById(R.id.activity_huoyuedu_name);
        web_note_content = (WebView)findViewById(R.id.web_note_content);
        mTv_name.setText("我的活跃度");
        ((TextView)findViewById(R.id.tv_back_to_activity)).setText(R.string.my);
    }

    @Override
    public void initData() {
        mTv_huoyuedu.setText(huoyuedu);
        try {
            WebSettings settings = web_note_content.getSettings();
            web_note_content.getSettings().setDefaultTextEncodingName("UTF -8");// 设置默认为utf-8
            web_note_content.setBackgroundColor(0x00000000); // 透明背景
            settings.setTextSize(WebSettings.TextSize.SMALLER);
            web_note_content.loadData(
                    URLEncoder.encode(getResources().getString(R.string.note_content), "UTF-8"),
                    "text/html; charset=UTF-8", null);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addListener() {

    }

    public void back(View v) {
        finish();
    }

}
