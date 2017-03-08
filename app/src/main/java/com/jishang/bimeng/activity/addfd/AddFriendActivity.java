
package com.jishang.bimeng.activity.addfd;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jishang.bimeng.R;
import com.jishang.bimeng.activity.addfd.widget.PullScrollView;
import com.jishang.bimeng.activity.base.BaseActivity;
import com.jishang.bimeng.entity.chat.UserDetailEntity;
import com.jishang.bimeng.entity.tonyong.TYEntity;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.ToastUtil;
import com.jishang.bimeng.utils.Internet.MyHttpRequest;
import com.jishang.bimeng.utils.dialog.DialogUtils;
import com.jishang.bimeng.utils.url.UrlUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * "添加好友"UI
 * 
 * @author wangliang Jul 14, 2016
 */
public class AddFriendActivity extends BaseActivity implements PullScrollView.OnTurnListener,
        OnClickListener {
    private PullScrollView mScrollView;

    private ImageView mHeadImg, mBackImg;

    private Intent intent;

    private UserDetailEntity entity;

    private TextView mTv_user_name;

    private TextView mTv_user_post;

    private TextView mTv_user_book;

    protected ImageLoader imageLoader_head;

    private DisplayImageOptions options_head; // 设置图片显示相关参数

    private List<BasicNameValuePair> params;

    private Button mBt_add;

    private String release = "0";

    private ProgressDialog progressDialog;

    private String toAddUsername = "";

    private Context mContext;

    private String token;

    private Gson mGson;

    @Override
    public int initResource() {
        return R.layout.activity_addfd;
    }

    @Override
    public void initView() {
        // TODO Auto-generated method stub
        mContext = this;
        Intent intent = getIntent();
        if (intent != null) {
            String back = intent.getStringExtra("back");
            ((TextView)findViewById(R.id.tv_back_to_activity)).setText(back);
        }
        token = new SharUtil(mContext).getAccess_token();
        mGson = new Gson();
        params = new ArrayList<BasicNameValuePair>();
        imageLoader_head = ImageLoader.getInstance();
        options_head = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ic_stub) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.ic_empty) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.ic_error) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(10)) // 设置成圆角图片
                .build(); // 构建完成
        intent = getIntent();
        entity = (UserDetailEntity)intent.getSerializableExtra("entity");
        toAddUsername = entity.getData().getH_username();
        mScrollView = (PullScrollView)findViewById(R.id.scroll_view);
        mBackImg = (ImageView)findViewById(R.id.background_img);
        mHeadImg = (ImageView)findViewById(R.id.user_avatar);
        mTv_user_name = (TextView)findViewById(R.id.user_name);
        mTv_user_post = (TextView)findViewById(R.id.user_post);
        mTv_user_book = (TextView)findViewById(R.id.user_book);
        mBt_add = (Button)findViewById(R.id.activity_addfd_bt_add);
        if (entity.getStatus() == 1) {
            mBt_add.setVisibility(View.VISIBLE);
        } else {
            mBt_add.setVisibility(View.GONE);

        }
        mScrollView.setHeader(mBackImg);
        mScrollView.setOnTurnListener(this);
    }

    @Override
    public void initData() {
        mTv_user_name.setText(entity.getData().getUsername());
        mTv_user_post.setText("网吧名字：" + entity.getData().getBusiness().getW_name());
        mTv_user_book.setText("城市:" + entity.getData().getProvince().getS_provname()
                + entity.getData().getCity().getS_cityname());
        imageLoader_head.displayImage(entity.getData().getHead_img(), mHeadImg, options_head);
    }

    @Override
    public void addListener() {
        mBt_add.setOnClickListener(this);

    }

    @Override
    public void onTurn() {

    }

    public void back(View view) {
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_addfd_bt_add:
                AddFd();
                break;

        }

    }

    public void AddFd() {

        String f_id = entity.getData().getId();
        progressDialog = new ProgressDialog(this);
        String stri = "正在发送请求";
        progressDialog.setMessage(stri);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        params.add(new BasicNameValuePair("f_id", f_id));
        new Thread(new Runnable() {
            public void run() {
                String url = UrlUtils.BUILD_FRIENDSHIP;
                String result = MyHttpRequest.getHttpPostBasic(url, params, token);
                if (result != null) {
                    try {
                        TYEntity entity = mGson.fromJson(result, TYEntity.class);
                        if (entity.getStatus() == 0) {
                            Message msg = new Message();
                            msg.obj = entity.getErrors();
                            msg.what = 0;
                            handler.sendMessage(msg);
                        } else if (entity.getStatus() == 1) {
                            handler.sendEmptyMessage(1);
                        }
                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                    }

                }
            }
        }).start();
    }

    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            DialogUtils.getInstance().cancel();
            switch (msg.what) {
                case 0:
                    String erros = (String)msg.obj;
                    ToastUtil.Toast(AddFriendActivity.this, erros);
                    break;
                case 1:
                    ToastUtil.Toast(AddFriendActivity.this, "发送成功");
                    finish();
                    break;
                case 2:

                    break;

            }
        };
    };

}
