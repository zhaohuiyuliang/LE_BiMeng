
package com.jishang.bimeng.activity.wode;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jishang.bimeng.R;
import com.jishang.bimeng.activity.base.BaseActivity;
import com.jishang.bimeng.entity.tonyong.TYEntity;
import com.jishang.bimeng.utils.CheckNulll;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.ToastUtil;
import com.jishang.bimeng.utils.Internet.MyHttpRequest;
import com.jishang.bimeng.utils.url.UrlUtils;

public class XiugaiqmActivity extends BaseActivity {
    private TextView mTv_name, mEdt_content;

    private List<BasicNameValuePair> params;

    private Gson mGson;

    private Context mContext;

    private SharUtil mShareUtil;

    private String token;

    private String describetion_info;

    @Override
    public int initResource() {
        return R.layout.activity_xiugaiqm;
    }

    @Override
    public void initView() {
        ((TextView)findViewById(R.id.tv_back_to_activity)).setText("个人设置");
        params = new ArrayList<BasicNameValuePair>();
        mGson = new Gson();
        mContext = this;
        mShareUtil = new SharUtil(mContext);
        token = mShareUtil.getAccess_token();

        mTv_name = (TextView)findViewById(R.id.activity_xiugaiqm_name);
        mEdt_content = (TextView)findViewById(R.id.activity_xiugaiqm_edt_nicheng);
        mTv_name.setText("交友宣言");
    }

    @Override
    public void initData() {

    }

    @Override
    public void addListener() {

    }

    public void save(View v) {
        describetion_info = mEdt_content.getText().toString().trim();
        if (CheckNulll.check(describetion_info)) {
            getMsg();
        } else {
            ToastUtil.Toast(mContext, "清先输入要修改的内容");
        }
    }

    public void back(View v) {
        finish();
    }

    public void getMsg() {

        params.add(new BasicNameValuePair("describetion_info", describetion_info));
        new Thread() {
            public void run() {
                String url = UrlUtils.BASEURL + "v1/user/update_personal.json";
                String result = MyHttpRequest.getHttpPostBasic(url, params, token);
                if (result != null) {
                    // Log.e("result", result.toString());
                    TYEntity entity = mGson.fromJson(result, TYEntity.class);
                    if (entity.getStatus() == 1) {
                        handler.sendEmptyMessage(1);
                    } else if (entity.getStatus() == 0) {
                        Message msg = new Message();
                        msg.obj = entity.getErrors();
                        msg.what = 0;
                        handler.sendMessage(msg);
                    }

                }
            };
        }.start();
    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    String erros = (String)msg.obj;
                    ToastUtil.Toast(mContext, erros);
                    break;
                case 1:
                    String content = mEdt_content.getText().toString().trim();
                    Intent intent = new Intent(XiugaiqmActivity.this, GerenSzActivity.class);
                    intent.putExtra("content", content);
                    setResult(201, intent);
                    ToastUtil.Toast(mContext, "修改成功");
                    mShareUtil.setDescribetion_info(content);
                    finish();
                    break;
                default:
                    break;

            }
        };
    };

}
