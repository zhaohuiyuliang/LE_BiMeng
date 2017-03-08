
package com.jishang.bimeng.activity.yuezhan.zhudian;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jishang.bimeng.R;
import com.jishang.bimeng.activity.base.BaseActivity;
import com.jishang.bimeng.entity.yuezhan.zhudian.ZhudianDataEntity;
import com.jishang.bimeng.entity.yuezhan.zhudian.ZhudianEntity;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.ToastUtil;
import com.jishang.bimeng.utils.Internet.MyHttpRequest;
import com.jishang.bimeng.utils.dialog.DialogUtils;
import com.jishang.bimeng.utils.url.UrlUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * "我的主店"UI
 * 
 * @author wangliang Jul 13, 2016
 */
public class ActivityMainStroe extends BaseActivity {
    private Gson mGson;

    private Context mContext;

    private List<BasicNameValuePair> params;

    private String token;

    protected ImageLoader imageLoader_head;

    private DisplayImageOptions options_head; // 设置图片显示相关参数

    private TextView mTv_name, mTv_phone, mTv_local, mTv_huanjing, mTv_boss, mTv_price, mTv_jiqi,
            mTv_marks;

    private ImageView mImg;

    @Override
    public int initResource() {
        return R.layout.activity_main_store;
    }

    @Override
    public void initView() {
        params = new ArrayList<BasicNameValuePair>();
        mContext = this;
        mGson = new Gson();
        mTv_name = (TextView)findViewById(R.id.activity_zhudian_name);
        mImg = (ImageView)findViewById(R.id.activity_zhudian_image);
        mTv_phone = (TextView)findViewById(R.id.activity_zhudian_tv_phone);
        mTv_local = (TextView)findViewById(R.id.activity_zhudian_tv_local);
        mTv_huanjing = (TextView)findViewById(R.id.activity_zhudian_tv_huanjing);
        mTv_boss = (TextView)findViewById(R.id.activity_zhudian_tv_boss);
        mTv_price = (TextView)findViewById(R.id.activity_zhudian_tv_price);
        mTv_jiqi = (TextView)findViewById(R.id.activity_zhudian_tv_jiqi);
        mTv_marks = (TextView)findViewById(R.id.activity_zhudian_tv_marks);

        token = new SharUtil(mContext).getAccess_token();
        imageLoader_head = ImageLoader.getInstance();
        options_head = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ic_stub) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.ic_empty) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.ic_error) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(10)) // 设置成圆角图片
                .build(); // 构建完成
    }

    @Override
    public void initData() {
        getMsg();

    }

    @Override
    public void addListener() {

    }

    public void back(View v) {
        finish();
    }

    public void getMsg() {
        DialogUtils.getInstance().createNotifier(mContext, "正在加载中");
        params.add(new BasicNameValuePair("1", "1"));
        new Thread() {
            public void run() {
                String url = UrlUtils.BASEURL + "v1/wangba/my_business.json";
                String resusult = MyHttpRequest.getHttpPostBasic(url, params, token);
                if (resusult != null) {
                    try {
                        Log.e("result", resusult.toString());
                        ZhudianEntity entity = mGson.fromJson(resusult, ZhudianEntity.class);
                        if (entity.getStatus() == 1) {
                            Message msg = new Message();
                            msg.obj = entity.getData();
                            msg.what = 1;
                            handler.sendMessage(msg);
                        } else if (entity.getStatus() == 0) {
                            handler.sendEmptyMessage(0);
                        }
                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                    }

                }
            };
        }.start();
    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            DialogUtils.getInstance().cancel();
            switch (msg.what) {
                case 0:
                    ToastUtil.Toast(mContext, "网络错误");
                    break;
                case 1:
                    ZhudianDataEntity entity = (ZhudianDataEntity)msg.obj;
                    setText(entity);
                    break;

            }
        };
    };

    public void setText(ZhudianDataEntity entity) {
        imageLoader_head.displayImage(entity.getW_img(), mImg, options_head);

        mTv_phone.setText(entity.getKf_phone());
        mTv_local.setText(entity.getDetailed_address());
        mTv_huanjing.setText(entity.getHuanjing());
        mTv_boss.setText(entity.getKf_name());
        mTv_price.setText(entity.getPrice());
        mTv_jiqi.setText(entity.getMachine());
        mTv_marks.setText(entity.getBe_careful());
        mTv_name.setText(entity.getW_name());
    }

}
