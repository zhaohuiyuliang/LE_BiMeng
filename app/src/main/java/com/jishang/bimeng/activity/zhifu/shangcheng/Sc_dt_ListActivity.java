
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
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jishang.bimeng.R;
import com.jishang.bimeng.activity.base.BaseActivity;
import com.jishang.bimeng.entity.shangcheng.Sc_dataEntity;
import com.jishang.bimeng.entity.shangcheng.goumai.GoumaiDataEntity;
import com.jishang.bimeng.entity.shangcheng.goumai.GoumaiEntity;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.ToastUtil;
import com.jishang.bimeng.utils.Internet.MyHttpRequest;
import com.jishang.bimeng.utils.dialog.DialogUtils;
import com.jishang.bimeng.utils.url.UrlUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * 商城单个实物详情UI
 * 
 * @author wangliang Jul 16, 2016
 */
public class Sc_dt_ListActivity extends BaseActivity implements OnClickListener {
    private Intent intent;

    /**
     * 确认支付按键
     */
    private TextView activity_zflist_confirm;

    private TextView mTv_name, mTv_price, mTv_bs_describe, mTv_zfy, mTv_bs_promotion_remarks;

    private Sc_dataEntity entity;

    private String token;

    private Context mContext;

    private Gson mGson;

    public static Sc_dt_ListActivity instance = null;

    private TextView mTv_barname;

    protected ImageLoader imageLoader_head;

    private DisplayImageOptions options_head; // 设置图片显示相关参数

    private ImageView mImgv;

    @Override
    public int initResource() {
        return R.layout.activity_sc_dt_zflist;
    }

    @Override
    public void initView() {
        instance = this;

        mGson = new Gson();
        mContext = this;
        intent = getIntent();
        token = new SharUtil(mContext).getAccess_token();
        entity = (Sc_dataEntity)intent.getSerializableExtra("entity");
        String back = intent.getStringExtra("back");
        ((TextView)findViewById(R.id.tv_back_to_activity)).setText(back);
        activity_zflist_confirm = (TextView)findViewById(R.id.activity_zflist_confirm);
        mTv_name = (TextView)findViewById(R.id.activity_zflist_name);
        mTv_price = (TextView)findViewById(R.id.activity_zflist_price);
        mTv_bs_describe = (TextView)findViewById(R.id.activity_zflist_bs_describe);
        mTv_bs_promotion_remarks = (TextView)findViewById(R.id.activity_zflist_bs_remarks);
        mImgv = (ImageView)findViewById(R.id.shangcheng_item_imgv);

        imageLoader_head = ImageLoader.getInstance();
        options_head = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ic_stub) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.ic_empty) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.ic_error) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(10)) // 设置成圆角图片
                .build(); // 构建完成

        mTv_barname = (TextView)findViewById(R.id.activity_sc_dt_zflist_name);
        mTv_barname.setText(entity.getName());

    }

    @Override
    public void initData() {
        mTv_name.setText(entity.getName());
        mTv_price.setText("￥" + entity.getPrice());
        // mTv_zfy.setText("总费用:"+entity.getPrice());
        mTv_bs_describe.setText(entity.getBs_describe());
        mTv_bs_promotion_remarks.setText(entity.getBs_promotion_remarks());
        imageLoader_head.displayImage(entity.getBg_img(), mImgv, options_head);

    }

    @Override
    public void addListener() {
        activity_zflist_confirm.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_zflist_confirm:// 确认支付按钮
                payBuy(entity.getBs_id());
                break;
            default:
                break;

        }

    }

    public void payBuy(final String bs_id) {
        DialogUtils.getInstance().createNotifier(mContext, "正在加载中");
        new Thread() {
            public void run() {
                List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
                params.add(new BasicNameValuePair("bs_id", bs_id));
                String url = UrlUtils.BASEURL + "v1/busine/b_pay_business.json";
                String result = MyHttpRequest.getHttpPostBasic(url, params, token);
                try {
                    if (result != null) {

                        GoumaiEntity entity = mGson.fromJson(result, GoumaiEntity.class);
                        if (entity.getStatus() == 1) {
                            Message msg = new Message();
                            msg.obj = entity.getData();
                            msg.what = 2;
                            handler.sendMessage(msg);
                        } else {

                        }

                    }
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    Message msg = new Message();
                    msg.obj = e.getMessage();
                    msg.what = 0;
                    handler.sendMessage(msg);
                }

            };
        }.start();

    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            DialogUtils.getInstance().cancel();
            switch (msg.what) {
                case 0:
                    ToastUtil.Toast(mContext, (String)msg.obj);
                    break;
                case 1:

                    break;
                case 2: { // 跳转到订单详情UI
                    GoumaiDataEntity entity1 = (GoumaiDataEntity)msg.obj;
                    entity1.setName(entity.getName());
                    entity1.setBs_describe(entity.getBs_describe());
                    Intent intent2 = new Intent(mContext, Sc_ZfListActivity.class);
                    intent2.putExtra("entity", entity1);
                    intent2.putExtra("back", "商城");// 支付完成后-->兑换码-->商城
                    startActivity(intent2);
                }
                    break;
                default:
                    break;

            }
        };
    };

    public void back(View v) {
        finish();

    }
}
