
package com.jishang.bimeng.activity.zhifu.shangcheng;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jishang.bimeng.R;
import com.jishang.bimeng.activity.base.BaseActivity;
import com.jishang.bimeng.entity.shangcheng.goumai.GoumaiDataEntity;
import com.jishang.bimeng.entity.shangcheng.zhifu.ScFirstEntity;
import com.jishang.bimeng.entity.shangcheng.zhifu.Sc_ft_dataEntity;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.ToastUtil;
import com.jishang.bimeng.utils.Internet.MyHttpRequest;
import com.jishang.bimeng.utils.dialog.DialogUtils;
import com.jishang.bimeng.utils.url.UrlUtils;

/**
 * 订单详情UI 需用户点击确认订单-->跳转到支付选项UI
 * 
 * @author wangliang Jul 16, 2016
 */
public class Sc_ZfListActivity extends BaseActivity implements OnClickListener {
    private Intent intent;

    /**
     * 确认订单,"确认"按键
     */
    private TextView activity_zflist_confirm;

    private TextView mTv_name, mTv_price, mTv_bs_describe, mTv_zfy;

    private GoumaiDataEntity entity;

    private String token;

    private Context mContext;

    private List<BasicNameValuePair> params;

    private Gson mGson;

    public static Sc_ZfListActivity instance = null;

    /**
     * 返回到之前UI提示
     */
    public String back;

    @Override
    public int initResource() {
        return R.layout.activity_sc_zflist;
    }

    @Override
    public void initView() {
        instance = this;
        params = new ArrayList<BasicNameValuePair>();
        mGson = new Gson();
        mContext = this;
        intent = getIntent();
        token = new SharUtil(mContext).getAccess_token();
        back = intent.getStringExtra("back");
        ((TextView)findViewById(R.id.tv_back_to_activity)).setText(back);
        entity = (GoumaiDataEntity)intent.getSerializableExtra("entity");
        activity_zflist_confirm = (TextView)findViewById(R.id.activity_zflist_confirm);
        mTv_name = (TextView)findViewById(R.id.activity_zflist_name);
        mTv_price = (TextView)findViewById(R.id.activity_zflist_price);
        mTv_bs_describe = (TextView)findViewById(R.id.activity_zflist_bs_describe);
        mTv_zfy = (TextView)findViewById(R.id.activity_zflist_zfy);

    }

    @Override
    public void initData() {
        mTv_name.setText(entity.getName());
        mTv_price.setText(entity.getMoney());
        mTv_zfy.setText(entity.getMoney());
        mTv_bs_describe.setText(entity.getBs_describe());

    }

    @Override
    public void addListener() {
        activity_zflist_confirm.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_zflist_confirm:// 确认订单
                getMsg();
                break;
            default:
                break;

        }

    }

    /**
     * 确认订单请求
     */
    public void getMsg() {
        DialogUtils.getInstance().createNotifier(mContext, "正在加载中");
        String bp_id = entity.getBp_id();
        String bp_uid = entity.getBp_uid();
        String bp_order_notice = entity.getBp_order_notice();

        params.add(new BasicNameValuePair("bp_id", bp_id));
        params.add(new BasicNameValuePair("bp_uid", bp_uid));
        params.add(new BasicNameValuePair("bp_order_notice", bp_order_notice));
        new Thread() {
            public void run() {
                String url = UrlUtils.BASEURL + "v1/busine/b_pay_confirm.json";
                String result = MyHttpRequest.getHttpPostBasic(url, params, token);
                if (result != null) {
                    try {
                        ScFirstEntity entity = mGson.fromJson(result, ScFirstEntity.class);
                        if (entity.getStatus() == 1) {
                            Message msg = new Message();
                            msg.obj = entity.getData();
                            msg.what = 2;
                            handler.sendMessage(msg);
                        }
                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                    }

                }

            };
        }.start();

    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            DialogUtils.getInstance().cancel();
            switch (msg.what) {
                case 0:
                    ToastUtil.Toast(mContext, "网络出错");
                    break;
                case 1:

                    break;
                case 2: { // 跳转到"支付选项"UI
                    Sc_ft_dataEntity entity1 = (Sc_ft_dataEntity)msg.obj;
                    Intent intent = new Intent(mContext, Sc_ZfTwoActivity.class);
                    intent.putExtra("entity", entity1);
                    intent.putExtra("back", back);// 返回到之前UI提示(前一个UI名称)
                    startActivity(intent);
                }
                    break;

            }
        };
    };

}
