
package com.jishang.bimeng.activity.yuezhan.yzlist;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jishang.bimeng.R;
import com.jishang.bimeng.activity.base.BaseActivity;
import com.jishang.bimeng.activity.hxchat.ActivityChat;
import com.jishang.bimeng.activity.wode.wzf.AllwzfActivity;
import com.jishang.bimeng.activity.yuezhan.MyYuezhanActivity;
import com.jishang.bimeng.activity.zhifu.ZfListActivity;
import com.jishang.bimeng.entity.chat.Fdlist.FriendEntity;
import com.jishang.bimeng.entity.yuezhan.confirm.C_DataEntity;
import com.jishang.bimeng.entity.yuezhan.yzlist.List_dataEntity;
import com.jishang.bimeng.entity.yuezhan.yzlist.List_data_comentEntity;
import com.jishang.bimeng.entity.yuezhan.yzlist.cyyz.CyyzEntity;
import com.jishang.bimeng.entity.yuezhan.yzlist.cyyz.Cyyz_dataEntity;
import com.jishang.bimeng.ui.ActivityImg;
import com.jishang.bimeng.ui.ActivityOtherPeopleDynamic;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.ToastUtil;
import com.jishang.bimeng.utils.Internet.MyHttpRequest;
import com.jishang.bimeng.utils.dialog.DialogUtils;
import com.jishang.bimeng.utils.url.UrlUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * "开黑信息详情"UI
 * 
 * @author wangliang Jul 16, 2016
 */
public class ActivityBallGamesDetail extends BaseActivity implements OnClickListener {
    /**
     * 发起开黑人的头像
     */
    private ImageView mImg;

    /**
     * 游戏名称
     */
    private TextView activity_cyyz_tv_kaihei;

    private TextView mTv_fwq, mTv_ms;

    private TextView mTv_djxz;

    /**
     * 参与我的开黑人数/开黑人数(eg:2/3)
     */
    private TextView mTv_khrs;

    /**
     * 个人单价
     */
    private TextView mTv_cyfy;

    /**
     * 持续时间 ,单位换算成小时
     */
    private TextView mTv_cxsj;

    /**
     * 等待时间,换算成小时
     */
    private TextView mTv_ddsj;

    private TextView mTv_kssj, mTv_location, mTv_liuyan, mTv_lianxi,

    mTv_begin, mTv_username, mTv_zhudian, mTv_qianming, mTv_pinglun;

    private Intent mIntent;

    private List_dataEntity entity;

    private String token = null;

    private Gson mGson;

    C_DataEntity entity1 = null;

    public static ActivityBallGamesDetail instance = null;

    protected ImageLoader imageLoader_head;

    private DisplayImageOptions options_head; // 设置图片显示相关参数

    private List<List_data_comentEntity> comment_to_peoples = new ArrayList<List_data_comentEntity>();

    @Override
    public int initResource() {
        return R.layout.activity_cyyz;
    }

    @Override
    public void initView() {
        instance = this;
        mGson = new Gson();
        mIntent = getIntent();
        entity = (List_dataEntity)mIntent.getSerializableExtra("entity");

        token = new SharUtil(this).getAccess_token();
        comment_to_peoples = entity.getComment_to_people();
        ll_yout_dynamic = (LinearLayout)findViewById(R.id.ll_yout_dynamic);
        activity_cyyz_tv_kaihei = (TextView)findViewById(R.id.activity_cyyz_tv_kaihei);
        mTv_fwq = (TextView)findViewById(R.id.activity_cyyz_tv_fwq);
        mTv_ms = (TextView)findViewById(R.id.activity_cyyz_tv_ms);
        mTv_djxz = (TextView)findViewById(R.id.activity_cyyz_tv_djxz);
        mTv_khrs = (TextView)findViewById(R.id.activity_cyyz_tv_khrs);
        mTv_cyfy = (TextView)findViewById(R.id.activity_cyyz_tv_cyfy);
        mTv_cxsj = (TextView)findViewById(R.id.activity_cyyz_tv_cxsj);
        mTv_ddsj = (TextView)findViewById(R.id.activity_cyyz_tv_ddsj);
        mTv_kssj = (TextView)findViewById(R.id.activity_cyyz_tv_kssj);
        mTv_location = (TextView)findViewById(R.id.activity_cyyz_tv_location);
        mTv_liuyan = (TextView)findViewById(R.id.activity_cyyz_tv_liuyan);
        mTv_lianxi = (TextView)findViewById(R.id.activity_cyyz_lianxi);
        mTv_begin = (TextView)findViewById(R.id.activity_cyyz_begin);
        mImg = (ImageView)findViewById(R.id.activity_cyyz_img);
        mTv_username = (TextView)findViewById(R.id.activity_cyyz_name);
        mTv_zhudian = (TextView)findViewById(R.id.activity_cyyz_zhudian);
        mTv_qianming = (TextView)findViewById(R.id.activity_cyyz_qianming);
        mTv_pinglun = (TextView)findViewById(R.id.activity_cyyz_tv_pinlun);

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
        initText();

    }

    public void initText() {
        ((TextView)findViewById(R.id.fragment_yuezhan_tv_name)).setText(entity.getYz_title());
        activity_cyyz_tv_kaihei.setText(entity.getYz_title());
        mTv_fwq.setText(entity.getYz_server());
        mTv_ms.setText(entity.getYz_pattern());
        mTv_djxz.setText(entity.getYz_grade());
        //
        mTv_khrs.setText(entity.getNeed_peple_item() + "/" + entity.getNeed_persons() + "个");
        if ((entity.getPay_get()).equals("0")) {
            mTv_cyfy.setText("+" + entity.getPay_peple_money() + "元");
        } else {
            mTv_cyfy.setText("-" + entity.getPay_peple_money() + "元");
        }
        mTv_cxsj.setText(transformTime(entity.getGame_continue_time()));
        mTv_ddsj.setText(transformTime(entity.getWait_time()));
        mTv_kssj.setText(times(entity.getStart_time()));
        mTv_location.setText(entity.getProvince().getS_provname()
                + entity.getCity().getS_cityname() + entity.getBusiness().getW_name());
        mTv_liuyan.setText(entity.getYz_remark());
        String pinglun = "";
        for (int i = 0; i < comment_to_peoples.size(); i++) {

            pinglun += comment_to_peoples.get(i).getComment_user().getUsername() + ":"
                    + comment_to_peoples.get(i).getContent() + "\r\n";
        }
        mTv_pinglun.setText(pinglun);

        imageLoader_head.displayImage(entity.getHead_img(), mImg, options_head);
        mTv_username.setText(entity.getUsername());
        mTv_zhudian.setText(entity.getUser().getBusiness().getW_name());
        mTv_qianming.setText(entity.getUser().getDescribetion_info());

    }

    private String transformTime(String timeSecond) {
        long timeS = Long.valueOf(timeSecond);
        String time = "";
        long hour = timeS / 3600;
        long second = timeS % 3600;
        if (hour > 0) {
            time += hour;
            long fen = second / 60;
            if (fen > 0) {
                time += "." + fen;
            }
            time += "小时";
        } else {
            long fen = second / 60;
            if (fen > 0) {
                time += fen + "分";
            }
        }
        return time;
    }

    public String times(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("MM-dd-HH:mm");
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;

    }

    public String getcyms(String s) {
        if (s.equals("0")) {
            return "参与支付";
        } else {
            return "参与所得";
        }

    }

    /*
     * 0为错误 1为返回订单信息 2为参与支付返回的成功的信息 3为未支付跳转到我的页面
     */
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            DialogUtils.getInstance().cancel();
            switch (msg.what) {
                case 0:
                    String s = (String)msg.obj;
                    ToastUtil.Toast(ActivityBallGamesDetail.this, s);

                    break;
                case 1: {
                    Cyyz_dataEntity data = (Cyyz_dataEntity)msg.obj;
                    setEntity(data);
                    Intent intent = new Intent(ActivityBallGamesDetail.this, ZfListActivity.class);
                    intent.putExtra("entity1", entity1);
                    startActivity(intent);
                }
                    break;
                case 2:
                    ToastUtil.Toast(ActivityBallGamesDetail.this, "参与支付参与成功");
                    Intent intent2 = new Intent(ActivityBallGamesDetail.this,
                            MyYuezhanActivity.class);
                    startActivity(intent2);
                    finish();
                    break;
                case 3: {
                    Intent intent3 = new Intent(ActivityBallGamesDetail.this, AllwzfActivity.class);
                    startActivity(intent3);
                }
                    break;
                default:
                    break;

            }
        };
    };

    /**
     * @param data 初始化订单信息
     */
    public void setEntity(Cyyz_dataEntity data) {
        entity1 = new C_DataEntity();
        entity1.setNeed_persons(data.getNeed_persons());
        entity1.setApp_money(data.getApp_money());
        entity1.setTotal_money(data.getTotal_money());
        entity1.setOne_peple_money(data.getOne_peple_money());
        entity1.setP_id(data.getP_id());
        entity1.setF_uid(data.getUser_uid());
        entity1.setOrder_notice_sn(data.getOrder_notice_sn());
    }

    public void Send(final String yz_id) {
        DialogUtils.getInstance().createNotifier(ActivityBallGamesDetail.this, "正在加载中");
        new Thread() {
            public void run() {
                List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
                String url = UrlUtils.BASEURL + "v1/yz/join_yz.json";
                params.add(new BasicNameValuePair("yz_id", yz_id));
                String result = MyHttpRequest.getHttpPostBasic(url, params, token);
                if (result != null) {
                    try {
                        Log.e("result", result.toString());
                        CyyzEntity entity = mGson.fromJson(result, CyyzEntity.class);

                        switch (entity.getStatus()) {
                            case 0:
                                Message msg1 = new Message();
                                msg1.what = 0;
                                msg1.obj = entity.getErrors();
                                handler.sendMessage(msg1);
                                break;
                            case 1:
                                Message msg = new Message();
                                msg.what = 1;
                                msg.obj = entity.getData();
                                handler.sendMessage(msg);
                                break;
                            case 2:
                                handler.sendEmptyMessage(2);

                                break;
                            case 3:
                                handler.sendEmptyMessage(3);

                                break;
                            default:
                                break;

                        }

                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                        handler.sendEmptyMessage(0);
                    }

                }

            };
        }.start();
    }

    @Override
    public void addListener() {
        mTv_begin.setOnClickListener(this);
        mTv_lianxi.setOnClickListener(this);
        mImg.setOnClickListener(this);
        ll_yout_dynamic.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_cyyz_lianxi: {
                FriendEntity entity1 = new FriendEntity();
                entity1.setH_username(entity.getH_username());
                entity1.setUsername(entity.getUsername());
                entity1.setHead_img(entity.getHead_img());
                Intent intent = new Intent(ActivityBallGamesDetail.this, ActivityChat.class);
                intent.putExtra("entity", entity1);
                startActivity(intent);
            }

                break;
            case R.id.activity_cyyz_begin:
                Send(entity.getYz_id());
                break;
            case R.id.activity_cyyz_img: {// 点击开黑人的头像事件
                Intent intent = new Intent(ActivityBallGamesDetail.this, ActivityImg.class);
                intent.putExtra("entity", entity);
                startActivity(intent);
            }
                break;
            case R.id.ll_yout_dynamic: {
                // 进入到开黑人的动态UI
                Intent intent = new Intent(ActivityBallGamesDetail.this,
                        ActivityOtherPeopleDynamic.class);
                intent.putExtra("entity", entity);
                intent.putExtra("back", entity.getYz_title());
                startActivity(intent);
            }
                break;
            default:
                break;

        }
    }

    private LinearLayout ll_yout_dynamic;

}
