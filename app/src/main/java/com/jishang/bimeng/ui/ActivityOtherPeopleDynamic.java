
package com.jishang.bimeng.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jishang.bimeng.R;
import com.jishang.bimeng.activity.base.BaseActivity;
import com.jishang.bimeng.activity.dt.fourway.PullListView;
import com.jishang.bimeng.activity.dt.fourway.PullListView.OnRefreshListener;
import com.jishang.bimeng.activity.dt.fourway.RotateLayout;
import com.jishang.bimeng.entity.dt.gs.Dt_dataEntity;
import com.jishang.bimeng.entity.dt.gs.DtgsEntity;
import com.jishang.bimeng.entity.login.LogEntity;
import com.jishang.bimeng.entity.yuezhan.yzlist.List_dataEntity;
import com.jishang.bimeng.parse.ParseUtil;
import com.jishang.bimeng.ui.adapter.AdapterOtherPeopleDynamic;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.ToastUtil;
import com.jishang.bimeng.utils.Internet.MyHttpRequest;
import com.jishang.bimeng.utils.url.UrlUtils;

/**
 * 其他人动态UI
 * 
 * @author wangliang Jul 20, 2016
 */
public class ActivityOtherPeopleDynamic extends BaseActivity {
    private PullListView pullToRefreshListView;

    private RotateLayout rotateLayout;

    private EditText mEdt_pinglun;

    private RelativeLayout mRl_pinglun;

    private int pageNo = 1;

    /**
     * 0:初始刷新；1：下拉；2：上拉。
     */
    private int status = 0;//

    private List<Dt_dataEntity> entities = new ArrayList<Dt_dataEntity>();

    private AdapterOtherPeopleDynamic adapterDynamic;

    @Override
    public int initResource() {
        return R.layout.activity_mydt;
    }

    private List_dataEntity entity;

    @Override
    public void initView() {
        Intent intent = getIntent();
        if (intent != null) {
            entity = (List_dataEntity)intent.getSerializableExtra("entity");
            String back = intent.getStringExtra("back");
            ((TextView)findViewById(R.id.tv_back_to_activity)).setText(back);
        }
        ((RelativeLayout)findViewById(R.id.fragment_mydt_publish)).setVisibility(View.GONE);
        ((TextView)findViewById(R.id.fragment_xiaoxi_name)).setText(entity.user.username);

        pullToRefreshListView = (PullListView)findViewById(R.id.refreshlistview);
        rotateLayout = (RotateLayout)findViewById(R.id.rotateLayout);
        mRl_pinglun = (RelativeLayout)findViewById(R.id.commentLinear);
        mEdt_pinglun = (EditText)findViewById(R.id.commentEdit);

        View pullView = LayoutInflater.from(this).inflate(R.layout.headlayout_my, null);
        ((TextView)pullView.findViewById(R.id.user_name)).setText(entity.user.username);
        mImg_headimg = (ImageView)pullView.findViewById(R.id.headlayout_headimg);
        pullToRefreshListView.setPullHeaderView(pullView);
        pullToRefreshListView.setPullHeaderViewHeight(100);
        pullToRefreshListView.setRotateLayout(rotateLayout);
        pullToRefreshListView.setCacheColorHint(Color.TRANSPARENT);

    }

    /**
     * 加载个人动态
     */
    public void loadPersonDynamicData() {
        new Thread() {
            public void run() {
                String token = new SharUtil(ActivityOtherPeopleDynamic.this).getAccess_token();
                List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
                params.add(new BasicNameValuePair("uid", entity.user.uid));
                String url = "http://apicms.gbimoo.com/v1/user_content/other_contents.json";
                String result = MyHttpRequest.getHttpPostBasic(url, params, token);
                if (result == null) {
                    return;
                }
                try {
                    Gson gson = new Gson();
                    List<Dt_dataEntity> entities_ = new ArrayList<Dt_dataEntity>();
                    if (status == 1) {
                        DtgsEntity entity = gson.fromJson(result, DtgsEntity.class);
                        Message msg = new Message();
                        msg.obj = entity;
                        msg.what = 1;
                        handler.sendMessage(msg);
                    } else if (status == 2) {
                        DtgsEntity entity = gson.fromJson(result, DtgsEntity.class);
                        Message msg = new Message();
                        msg.what = 2;
                        msg.obj = entity;
                        handler.sendMessage(msg);
                    } else if (status == 0) {
                        entities.clear();
                        DtgsEntity entity = gson.fromJson(result, DtgsEntity.class);
                        entities = entity.getData();
                        entities_.addAll(entities);
                        Message msg = new Message();
                        msg.what = 0;
                        msg.obj = entity;
                        handler.sendMessage(msg);

                    }
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }

            };
        }.start();
    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0: {
                    DtgsEntity entity = (DtgsEntity)msg.obj;
                    List<Dt_dataEntity> entities = entity.getData();
                    adapterDynamic.setData(entities);
                    pullToRefreshListView.onCompleteRefresh();
                }
                    break;
                case 1: {
                    DtgsEntity entity = (DtgsEntity)msg.obj;
                    List<Dt_dataEntity> entities = entity.getData();
                    adapterDynamic.setData(entities);
                    pullToRefreshListView.onCompleteRefresh();
                    pageNo = 1;
                }
                    break;
                case 2: {
                    DtgsEntity entity = (DtgsEntity)msg.obj;
                    List<Dt_dataEntity> entities = entity.getData();
                    adapterDynamic.setData(entities);
                    pullToRefreshListView.onCompleteRefresh();
                }
                    break;
                case 3:
                    ToastUtil.Toast(ActivityOtherPeopleDynamic.this, "评论成功");
                    mRl_pinglun.setVisibility(View.GONE);
                    mEdt_pinglun.setText("");
                    loadPersonDynamicData();

                    break;
                case 4:
                    adapterDynamic.notifyDataSetChanged();
                    break;

                case 5:
                    adapterDynamic.notifyDataSetChanged();
                    break;
                default:
                    break;

            }
        };
    };

    /**
     * @param ucid 评论接口
     */
    public void Putpinglun(final String ucid) {
        new Thread() {
            public void run() {
                String token = new SharUtil(ActivityOtherPeopleDynamic.this).getAccess_token();
                String content = mEdt_pinglun.getText().toString().trim();
                List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
                params.add(new BasicNameValuePair("message", content));
                params.add(new BasicNameValuePair("ucmc_id", ucid));
                String url = UrlUtils.BASEURL + "v1/user_content/comment.json?";
                String result = MyHttpRequest.getHttpPostBasic(url, params, token);
                if (result != null) {
                    LogEntity entity = ParseUtil.getBanner_pinglun(result);
                    if (entity.getStatus() == 1) {
                        handler.sendEmptyMessage(3);
                    }
                }
            };
        }.start();
    }

    public void Dianzan_huise(final String ucid) {

        new Thread() {
            public void run() {
                String token = new SharUtil(ActivityOtherPeopleDynamic.this).getAccess_token();
                List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
                params.add(new BasicNameValuePair("1", "1"));
                String url = UrlUtils.BASEURL + "v1/user_content/del_click.json?ucsc_id=" + ucid;
                String result = MyHttpRequest.getHttpPostBasic(url, params, token);
                if (result != null) {
                    Log.e("result", result.toString());
                    handler.sendEmptyMessage(5);
                }

            };
        }.start();
    }

    /**
     * @param ucid 点赞接口
     */
    public void Dianzan(final String ucid) {

        new Thread() {
            public void run() {
                String token = new SharUtil(ActivityOtherPeopleDynamic.this).getAccess_token();
                List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
                params.add(new BasicNameValuePair("ucsc_id", ucid));
                String url = UrlUtils.BASEURL + "v1/user_content/click.json";
                String result = MyHttpRequest.getHttpPostBasic(url, params, token);
                if (result != null) {
                    Log.e("result", result.toString());
                    handler.sendEmptyMessage(4);
                }

            };
        }.start();
    }

    ImageView mImg_headimg;

    @Override
    public void initData() {

        adapterDynamic = new AdapterOtherPeopleDynamic(this, entity, mImg_headimg);
        pullToRefreshListView.setAdapter(adapterDynamic);
        loadPersonDynamicData();
    }

    @Override
    public void addListener() {
        // 下拉刷新更多数据
        pullToRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {

            @Override
            public void onRefresh(PullListView refreshView) {
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        status = 1;
                        loadPersonDynamicData();

                    }
                }, 100);
            }
        });
    }

}
