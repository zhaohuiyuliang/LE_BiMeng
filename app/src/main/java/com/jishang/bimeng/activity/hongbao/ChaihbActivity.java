
package com.jishang.bimeng.activity.hongbao;

import java.util.ArrayList;
import java.util.List;

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
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jishang.bimeng.R;
import com.jishang.bimeng.entity.hongbao.chai.ChaiEntity;
import com.jishang.bimeng.entity.hongbao.dian.Dian_dataEntity;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.ToastUtil;
import com.jishang.bimeng.utils.Internet.MyHttpRequest;
import com.jishang.bimeng.utils.url.UrlUtils;

public class ChaihbActivity extends Activity implements OnClickListener {
    private TextView mTv_chai;

    private Gson mGson;

    private Context mContext;

    private List<BasicNameValuePair> params;

    private String token;

    private Intent intent;

    private Dian_dataEntity entity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chaihb);
        initView();
        initData();
        addListener();
    }

    public void initView() {
        intent = getIntent();
        mTv_chai = (TextView)findViewById(R.id.activity_chaihb_bt_qiang);

        params = new ArrayList<BasicNameValuePair>();
        mContext = this;
        mGson = new Gson();
        token = new SharUtil(mContext).getAccess_token();
        entity = (Dian_dataEntity)intent.getSerializableExtra("entity");

    }

    public void initData() {

    }

    public void addListener() {
        mTv_chai.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_chaihb_bt_qiang:
                getHb();
                break;

        }
    }

    public void getHb() {
        String uid = new SharUtil(mContext).getUid();
        String q_id = entity.getQ_id();
        String qb_money = entity.getQb_money();
        String hb_id = entity.getHb_id();
        params.add(new BasicNameValuePair("uid", uid));
        params.add(new BasicNameValuePair("q_id", q_id));
        params.add(new BasicNameValuePair("qb_money", qb_money));
        params.add(new BasicNameValuePair("hb_id", hb_id));
        new Thread() {
            public void run() {
                String url = UrlUtils.BASEURL + "v1/redbag/chai_hb_view.json";
                String result = MyHttpRequest.getHttpPostBasic(url, params, token);
                if (result != null) {
                    Log.e("result---", result.toString());
                    try {
                        ChaiEntity entity1 = mGson.fromJson(result, ChaiEntity.class);
                        if (entity1.getStatus() == 0) {
                            Message msg = new Message();
                            msg.what = 0;
                            msg.obj = entity1.getErrors();
                            handler.sendMessage(msg);
                        } else if (entity1.getStatus() == 1) {
                            Message msg = new Message();
                            msg.what = 1;
                            msg.obj = entity1.getQ_one().getQb_money();
                            handler.sendMessage(msg);
                        }

                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                        handler.sendEmptyMessage(2);
                    }

                }
            };

        }.start();
    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    String erro = (String)msg.obj;
                    ToastUtil.Toast(mContext, erro);
                    break;
                case 1:
                    String money = (String)msg.obj;
                    // ToastUtil.Toast(mContext, "恭喜抢到"+money);
                    Intent intent = new Intent(mContext, GetMoneyActivity.class);
                    intent.putExtra("money", money);
                    startActivity(intent);
                    finish();
                    break;
                case 2:
                    ToastUtil.Toast(mContext, "网络错误");
                    break;

            }
        };
    };

}
