
package com.jishang.bimeng.activity.yuezhan.fq;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jishang.bimeng.R;
import com.jishang.bimeng.activity.base.BaseActivity;
import com.jishang.bimeng.activity.hxchat.ActivityChat;
import com.jishang.bimeng.entity.chat.Fdlist.FriendEntity;
import com.jishang.bimeng.entity.yuezhan.fq.wfq.Wfq_DataEntity;
import com.jishang.bimeng.entity.yuezhan.lianxi.LianxiEntity;
import com.jishang.bimeng.entity.yuezhan.lianxi.Lianxi_dataEntity;
import com.jishang.bimeng.entity.yuezhan.lianxi.Lianxi_dataJuEntity;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.ToastUtil;
import com.jishang.bimeng.utils.Internet.MyHttpRequest;
import com.jishang.bimeng.utils.url.UrlUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * "查看参与人"UI
 * 
 * @author wangliang Jul 19, 2016
 */
public class LianxiActivity extends BaseActivity {
    private ListView mList;

    private List<BasicNameValuePair> params;

    private String token;

    private Context mContext;

    private Intent mItent;

    private Wfq_DataEntity entity;

    private Gson mGson;

    private List<Lianxi_dataEntity> data = new ArrayList<Lianxi_dataEntity>();

    private ItemListAdapter adapter;

    protected ImageLoader imageLoader_head;

    private DisplayImageOptions options_head; // 设置图片显示相关参数

    public TextView mTv_yz_title, mTv_need_persons, mTv_server, mTv_peple_money, mTv_money_name,
            mTv_yz_name, mTv_start_time, mTv_location;

    private ImageView image_dingdan;

    @Override
    public int initResource() {
        return R.layout.activity_lianxi;
    }

    @Override
    public void initView() {
        mContext = this;
        mItent = getIntent();
        mGson = new Gson();
        imageLoader_head = ImageLoader.getInstance();
        options_head = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ic_stub) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.ic_empty) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.ic_error) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(10)) // 设置成圆角图片
                .build(); // 构建完成
        mList = (ListView)findViewById(R.id.activity_lianxi_list);

        mTv_yz_title = (TextView)findViewById(R.id.yzlist_item_tv_yz_title);
        mTv_need_persons = (TextView)findViewById(R.id.yzlist_item_tv_need_persons);
        mTv_server = (TextView)findViewById(R.id.yzlist_item_tv_yz_server);
        mTv_peple_money = (TextView)findViewById(R.id.yzlist_item_tv_pay_peple_money);
        mTv_yz_name = (TextView)findViewById(R.id.yzlist_item_tv_yz_name);
        mTv_start_time = (TextView)findViewById(R.id.yzlist_item_tv_start_time);
        image_dingdan = (ImageView)findViewById(R.id.img_launch_ball_games_head);
        mTv_money_name = (TextView)findViewById(R.id.yzlist_item_tv_pay_peple_money_name);
        entity = (Wfq_DataEntity)mItent.getSerializableExtra("entity");
        initText();
        params = new ArrayList<BasicNameValuePair>();
        token = new SharUtil(mContext).getAccess_token();

        adapter = new ItemListAdapter();
        mList.setAdapter(adapter);
        getMsg();

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

    public void getMsg() {
        final String yz_id = entity.getYz_id();
        params.add(new BasicNameValuePair("yz_id", yz_id));
        new Thread() {
            public void run() {
                String url = UrlUtils.BASEURL + "v1/yz/contact_join_people.json";
                String result = MyHttpRequest.getHttpPostBasic(url, params, token);
                if (result != null) {
                    // Log.e("result", result.toString());
                    try {
                        LianxiEntity entity = mGson.fromJson(result, LianxiEntity.class);
                        if (entity.getStatus() == 0) {
                            Message msg = new Message();
                            msg.what = 0;
                            msg.obj = entity.getErrors();
                            handler.sendMessage(msg);
                        } else if (entity.getStatus() == 1) {
                            data = entity.getData();
                            handler.sendEmptyMessage(1);
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
            switch (msg.what) {
                case 0:
                    String erros = (String)msg.obj;
                    ToastUtil.Toast(mContext, erros);
                    break;
                case 1:
                    adapter.notifyDataSetChanged();
                    break;
                case 2:

                    break;

            }
        };
    };

    public class ItemListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (data == null) {
                return 0;
            }
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = View.inflate(mContext, R.layout.yzlianxi_list_item, null);
                viewHolder = new ViewHolder();
                viewHolder.mTv_name = (TextView)convertView
                        .findViewById(R.id.yzlianxi_list_item_name);
                viewHolder.image = (ImageView)convertView
                        .findViewById(R.id.yzlianxi_list_username_imgv_headimg);
                viewHolder.mTv_call = (TextView)convertView
                        .findViewById(R.id.yzlianxi_list_item_call);
                viewHolder.mTv_sendmsg = (TextView)convertView
                        .findViewById(R.id.yzlianxi_list_item_sendmsg);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder)convertView.getTag();
            }
            final Lianxi_dataJuEntity entity = data.get(position).getJoin_user();
            viewHolder.mTv_name.setText(entity.getUsername());
            imageLoader_head.displayImage(entity.getHead_img(), viewHolder.image, options_head);
            final String number = entity.getPhone();
            viewHolder.mTv_call.setOnClickListener(new OnClickListener() {

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
            viewHolder.mTv_sendmsg.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    FriendEntity entity1 = new FriendEntity();
                    entity1.setH_username(entity.getH_username());
                    entity1.setUsername(entity.getUsername());
                    entity1.setHead_img(entity.getHead_img());
                    Intent intent = new Intent(mContext, ActivityChat.class);
                    intent.putExtra("entity", entity1);
                    startActivity(intent);
                }
            });
            return convertView;
        }

        public class ViewHolder {
            public TextView mTv_yz_title, mTv_need_persons, mTv_server, mTv_peple_money,
                    mTv_money_name, mTv_yz_name, mTv_start_time, mTv_location, mTv_confirm,
                    mTv_cancel, mTv_name, mTv_call, mTv_sendmsg;

            private ImageView image;
        }

    }

    public void back(View v) {
        finish();
    }

}
