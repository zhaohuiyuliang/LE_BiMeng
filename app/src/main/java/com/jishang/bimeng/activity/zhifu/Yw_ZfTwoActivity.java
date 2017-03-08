
package com.jishang.bimeng.activity.zhifu;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jishang.bimeng.R;
import com.jishang.bimeng.activity.base.BaseActivity;
import com.jishang.bimeng.entity.yuezhan.zf.Zf_list_DataEntity;
import com.jishang.bimeng.entity.yuezhan.zf.ZftwoEntity;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.ToastUtil;
import com.jishang.bimeng.utils.Internet.MyHttpRequest;
import com.jishang.bimeng.utils.dialog.DialogUtils;
import com.jishang.bimeng.utils.url.UrlUtils;
import com.jishang.bimeng.zhifubao.Yw_PayDemoActivity;

public class Yw_ZfTwoActivity extends BaseActivity implements
        android.widget.RadioGroup.OnCheckedChangeListener, OnClickListener {
    private TextView mTv_change, mTv_income, mTv_confirm;

    private CheckBox mCb_change, mCb_income;

    private int change_id = 1, income_id = 0, third_id = 0;

    private RadioGroup mRg;

    private List<BasicNameValuePair> params;

    private Zf_list_DataEntity entity;

    private Intent intent;

    private Gson mGson;

    private Context mContext;

    public static Yw_ZfTwoActivity instance = null;

    @Override
    public int initResource() {
        return R.layout.activity_zf_two;
    }

    @Override
    public void initView() {
        instance = this;
        mContext = this;
        intent = getIntent();
        mGson = new Gson();
        entity = (Zf_list_DataEntity)intent.getSerializableExtra("entity");
        params = new ArrayList<BasicNameValuePair>();
        mRg = (RadioGroup)findViewById(R.id.activity_zf_two_rg);
        mTv_change = (TextView)findViewById(R.id.activity_zf_two_change);
        mTv_income = (TextView)findViewById(R.id.activity_zf_two_income);
        mTv_confirm = (TextView)findViewById(R.id.activity_zf_two_confirm);
        mCb_change = (CheckBox)findViewById(R.id.activity_zf_two_cb_change);
        mCb_income = (CheckBox)findViewById(R.id.activity_zf_two_cb_income);
        initText();
    }

    public void initText() {
        mTv_change.setText(entity.getChange());
        mTv_income.setText(entity.getIncome());
    }

    @Override
    public void initData() {

    }

    @Override
    public void addListener() {
        mCb_change.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    change_id = 1;

                } else {
                    change_id = 0;
                }
            }
        });
        mCb_income.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    income_id = 2;
                } else {
                    income_id = 0;
                }
            }
        });
        mRg.setOnCheckedChangeListener(this);
        mTv_confirm.setOnClickListener(this);

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.activity_zf_two_rb_weichat:
                third_id = 3;
                break;
            case R.id.activity_zf_two_rb_zfb:
                third_id = 3;
                break;

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_zf_two_confirm:
                putMsg();
                break;

        }

    }

    public void putMsg() {
        DialogUtils.getInstance().createNotifier(mContext, "正在加载中");
        final String token = new SharUtil(Yw_ZfTwoActivity.this).getAccess_token();
        String p_id = entity.getYpn_id();
        String uid = entity.getPnyw_uid();
        String order_notice_sn = entity.getYw_order_notice();
        params.add(new BasicNameValuePair("ypn_id", p_id));
        params.add(new BasicNameValuePair("pnyw_uid", uid));
        params.add(new BasicNameValuePair("yw_order_notice", order_notice_sn));
        params.add(new BasicNameValuePair("change_id", change_id + ""));
        params.add(new BasicNameValuePair("income_id", income_id + ""));
        params.add(new BasicNameValuePair("third_id", third_id + ""));
        Log.e("----test", p_id + "  " + uid + "  " + order_notice_sn + "  " + change_id + "  "
                + income_id + "  " + third_id);
        new Thread() {
            public void run() {
                String url = UrlUtils.BASEURL + "v1/yw/yw_pay.json";
                String result = MyHttpRequest.getHttpPostBasic(url, params, token);
                if (result != null) {
                    try {
                        Log.e("result--", result.toString());
                        ZftwoEntity entity = mGson.fromJson(result, ZftwoEntity.class);
                        Log.e("entity", entity.toString());
                        int status = entity.getStatus();
                        switch (status) {
                            case 0:
                                Message msg = new Message();
                                msg.what = 0;
                                msg.obj = entity.getErrors();
                                handler.sendMessage(msg);

                                break;

                            case 1:

                                break;
                            case 2:
                                handler.sendEmptyMessage(2);

                                break;
                            case 3:
                                ZftwoEntity.Data data = entity.getData();
                                Message msg1 = new Message();
                                msg1.obj = data;
                                msg1.what = 3;
                                handler.sendMessage(msg1);
                                break;
                        }
                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                    }

                }

            };
        }.start();
    }

    public Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            DialogUtils.getInstance().cancel();
            switch (msg.what) {
                case 0:
                    String erro = (String)msg.obj;
                    ToastUtil.Toast(mContext, erro);

                    break;
                case 1:

                    break;
                case 2:
                    entity.setChange_id(change_id);
                    entity.setIncome_id(income_id);
                    entity.setThird_id(third_id);
                    Intent intent = new Intent(mContext, Yw_Zf_threeActivity.class);
                    intent.putExtra("entity", entity);
                    startActivity(intent);
                    break;
                case 3:
                    ZftwoEntity.Data data = (ZftwoEntity.Data)msg.obj;
                    Intent intent1 = new Intent(mContext, Yw_PayDemoActivity.class);
                    intent1.putExtra("entity", data);
                    startActivity(intent1);
                    break;

            }
        };
    };

}
