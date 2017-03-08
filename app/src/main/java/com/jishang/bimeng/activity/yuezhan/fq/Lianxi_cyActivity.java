
package com.jishang.bimeng.activity.yuezhan.fq;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.jishang.bimeng.R;
import com.jishang.bimeng.activity.base.BaseActivity;
import com.jishang.bimeng.activity.hxchat.ActivityChat;
import com.jishang.bimeng.entity.chat.Fdlist.FriendEntity;
import com.jishang.bimeng.entity.yuezhan.fq.wfq.Wfq_DataEntity;
import com.jishang.bimeng.utils.ToastUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * 联系发起人UI 开黑项目 发起人的的昵称、拨打电话、发送信息
 * 
 * @author wangliang Jul 18, 2016
 */
public class Lianxi_cyActivity extends BaseActivity {

    private Context mContext;

    private Intent mItent;

    private Wfq_DataEntity entity;

    private TextView mTv_name;

    protected ImageLoader imageLoader_head;

    private DisplayImageOptions options_head; // 设置图片显示相关参数

    public TextView mTv_yz_title, mTv_need_persons, mTv_server, mTv_peple_money, mTv_money_name,
            mTv_yz_name, mTv_start_time, mTv_location, mTv_username, mTv_call, mTv_sendmsg;

    private ImageView image_dingdan, image;

    @Override
    public int initResource() {
        return R.layout.activity_lianxi_cy;
    }

    @Override
    public void initView() {
        mContext = this;
        mItent = getIntent();
        imageLoader_head = ImageLoader.getInstance();
        options_head = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ic_stub) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.ic_empty) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.ic_error) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(10)) // 设置成圆角图片
                .build(); // 构建完成
        mTv_name = (TextView)findViewById(R.id.lianxi_activity_tv_name);

        mTv_yz_title = (TextView)findViewById(R.id.yzlist_item_tv_yz_title);
        mTv_need_persons = (TextView)findViewById(R.id.yzlist_item_tv_need_persons);
        mTv_server = (TextView)findViewById(R.id.yzlist_item_tv_yz_server);
        mTv_peple_money = (TextView)findViewById(R.id.yzlist_item_tv_pay_peple_money);
        mTv_yz_name = (TextView)findViewById(R.id.yzlist_item_tv_yz_name);
        mTv_start_time = (TextView)findViewById(R.id.yzlist_item_tv_start_time);
        image_dingdan = (ImageView)findViewById(R.id.img_launch_ball_games_head);
        mTv_money_name = (TextView)findViewById(R.id.yzlist_item_tv_pay_peple_money_name);

        mTv_username = (TextView)findViewById(R.id.yzlianxi_list_item_name);
        image = (ImageView)findViewById(R.id.yzlianxi_list_username_imgv_headimg);
        mTv_call = (TextView)findViewById(R.id.yzlianxi_list_item_call);
        mTv_sendmsg = (TextView)findViewById(R.id.yzlianxi_list_item_sendmsg);

        entity = (Wfq_DataEntity)mItent.getSerializableExtra("entity");
        initText();

        mTv_name.setText("联系列表");

    }

    @Override
    public void initData() {

    }

    public void initText() {
        if ((entity.getPay_get()).equals("0")) {
            mTv_money_name.setText("人民币:  ");
            mTv_peple_money.setText("-" + entity.getPay_peple_money());
            mTv_peple_money.setTextColor(getResources().getColor(R.color.green));
        } else {
            mTv_money_name.setText("人民币:  ");
            mTv_peple_money.setText("+" + entity.getPay_peple_money());
            mTv_peple_money.setTextColor(getResources().getColor(R.color.red));
        }

        mTv_yz_title.setText("游戏名:  " + entity.getYz_title());
        mTv_need_persons.setText("玩家数:  " + entity.getNeed_peple_item() + "/"
                + entity.getNeed_persons());
        mTv_server.setText("服务器:  " + entity.getYz_server());

        mTv_yz_name.setText(entity.getUser().getUsername());
        mTv_start_time.setText("开始时:  " + times(entity.getStart_time()));
        imageLoader_head.displayImage(entity.getYz_img(), image_dingdan, options_head);

        mTv_username.setText(entity.getUser().getUsername());
        imageLoader_head.displayImage(entity.getUser().getHead_img(), image, options_head);
        final String number = entity.getUser().getPhone();
        mTv_call.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // 指定要拨打的电话号码
                Intent intent = new Intent();
                intent.setAction("android.intent.action.CALL");
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse("tel:" + number));
                startActivity(intent);
            }
        });
        mTv_sendmsg.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                FriendEntity entity1 = new FriendEntity();
                entity1.setH_username(entity.getUser().getH_username());
                entity1.setUsername(entity.getUser().getUsername());
                entity1.setHead_img(entity.getUser().getHead_img());
                Intent intent = new Intent(mContext, ActivityChat.class);
                intent.putExtra("entity", entity1);
                startActivity(intent);
            }
        });
    }

    public String times(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("MM-dd-HH:mm");
        @SuppressWarnings("unused")
        // long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;

    }

    @Override
    public void addListener() {

    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    String erros = (String)msg.obj;
                    ToastUtil.Toast(mContext, erros);
                    break;
                case 1:
                    break;
                case 2:

                    break;

            }
        };
    };

    public void back(View v) {
        finish();
    }

}
