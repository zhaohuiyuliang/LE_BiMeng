
package com.jishang.bimeng.activity.yuezhan.yzlist;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jishang.bimeng.R;
import com.jishang.bimeng.activity.base.BaseActivity;
import com.jishang.bimeng.entity.yuezhan.yzlist.List_dataEntity;
import com.jishang.bimeng.entity.yuezhan.yzlist.YzListEntity;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.ToastUtil;
import com.jishang.bimeng.utils.Internet.MyHttpRequest;
import com.jishang.bimeng.utils.dialog.DialogUtils;
import com.jishang.bimeng.utils.url.UrlUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class YzListActivity extends BaseActivity {
    private ListView mList;

    private List<BasicNameValuePair> params;

    private Gson mGson;

    private Context context;

    private List<List_dataEntity> entities = new ArrayList<List_dataEntity>();

    private ItemListAdapter adtapter;

    private TextView mTv_name;

    protected ImageLoader imageLoader_head;

    private DisplayImageOptions options_head; // 设置图片显示相关参数

    private String token = null;

    private Context mContext;

    @Override
    public int initResource() {
        return R.layout.activity_yzlist;
    }

    @Override
    public void initView() {
        context = this;
        mContext = this;
        mGson = new Gson();
        mList = (ListView)findViewById(R.id.activity_yzlist_list);
        mTv_name = (TextView)findViewById(R.id.activity_yzlist_tv_name);

        options_head = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ic_stub) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.ic_empty) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.ic_error) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(10)) // 设置成圆角图片
                .build(); // 构建完成
        imageLoader_head = ImageLoader.getInstance();
        params = new ArrayList<BasicNameValuePair>();
        token = new SharUtil(context).getAccess_token();
        adtapter = new ItemListAdapter();
        mList.setAdapter(adtapter);
        mTv_name.setText("约战列表");

    }

    @Override
    public void initData() {
        getMsg();

    }

    public void getMsg() {
        DialogUtils.getInstance().createNotifier(mContext, "正在加载中");
        params.add(new BasicNameValuePair("1", "1"));
        new Thread() {
            public void run() {
                String url = UrlUtils.BASEURL + "v1/yz/yz_list.json";
                String result = MyHttpRequest.getHttpPostBasic(url, params, token);
                if (result != null) {
                    // Log.e("result", result.toString());
                    try {
                        YzListEntity entity = mGson.fromJson(result, YzListEntity.class);
                        entities = entity.getYz_list();
                        Log.e("entity", entities.toString());
                        handler.sendEmptyMessage(0);
                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                        handler.sendEmptyMessage(1);
                    }

                }

            };
        }.start();
    }

    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            DialogUtils.getInstance().cancel();
            switch (msg.what) {
                case 0:
                    adtapter.notifyDataSetChanged();
                    break;
                case 1:
                    ToastUtil.Toast(context, "网络出错");
                    break;

            }
        };
    };

    @Override
    public void addListener() {
        mList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List_dataEntity entity = entities.get(position);
                Intent intent = new Intent(context, ActivityBallGamesDetail.class);
                intent.putExtra("entity", entity);
                startActivity(intent);

            }
        });

    }

    public void back(View v) {
        finish();
    }

    public class ItemListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (entities == null) {
                return 0;
            }
            return entities.size();
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
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.yzlist_item, null);
                viewHolder = new ViewHolder();

                viewHolder.mTv_yz_title = (TextView)convertView
                        .findViewById(R.id.yzlist_item_tv_yz_title);
                viewHolder.mTv_need_persons = (TextView)convertView
                        .findViewById(R.id.yzlist_item_tv_need_persons);
                viewHolder.mTv_server = (TextView)convertView
                        .findViewById(R.id.yzlist_item_tv_yz_server);
                viewHolder.mTv_peple_money = (TextView)convertView
                        .findViewById(R.id.yzlist_item_tv_pay_peple_money);
                viewHolder.mTv_yz_name = (TextView)convertView
                        .findViewById(R.id.yzlist_item_tv_yz_name);
                viewHolder.mTv_start_time = (TextView)convertView
                        .findViewById(R.id.yzlist_item_tv_start_time);
                viewHolder.mTv_location = (TextView)convertView
                        .findViewById(R.id.yzlist_item_tv_location);
                viewHolder.image = (ImageView)convertView
                        .findViewById(R.id.img_launch_ball_games_head);
                viewHolder.mTv_money_name = (TextView)convertView
                        .findViewById(R.id.yzlist_item_tv_pay_peple_money_name);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder)convertView.getTag();
            }
            List_dataEntity entity = entities.get(position);
            if ((entity.getPay_get()).equals("0")) {
                viewHolder.mTv_money_name.setText("参与支付:");
            } else {
                viewHolder.mTv_money_name.setText("参与所得:");
            }

            viewHolder.mTv_yz_title.setText(entity.getYz_title());
            viewHolder.mTv_need_persons.setText(entity.getNeed_peple_item() + "/"
                    + entity.getNeed_persons());
            viewHolder.mTv_server.setText(entity.getYz_server());
            viewHolder.mTv_peple_money.setText(entity.getPay_peple_money());
            viewHolder.mTv_yz_name.setText("测试名字");
            viewHolder.mTv_start_time.setText(times(entity.getStart_time()));
            viewHolder.mTv_location.setText("测试地址");

            imageLoader_head.displayImage(entity.getYz_img(), viewHolder.image, options_head);
            return convertView;
        }

    }

    public class ViewHolder {
        public TextView mTv_yz_title, mTv_need_persons, mTv_server, mTv_peple_money,
                mTv_money_name, mTv_yz_name, mTv_start_time, mTv_location;

        private ImageView image;
    }

    public String times(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("MM月dd日HH时mm分");
        @SuppressWarnings("unused")
        // long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;

    }

}
