
package com.jishang.bimeng.activity.shangcheng;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.jishang.bimeng.activity.zhifu.shangcheng.Sc_dt_ListActivity;
import com.jishang.bimeng.entity.shangcheng.ScEntity;
import com.jishang.bimeng.entity.shangcheng.Sc_dataEntity;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.ToastUtil;
import com.jishang.bimeng.utils.Internet.MyHttpRequest;
import com.jishang.bimeng.utils.dialog.DialogUtils;
import com.jishang.bimeng.utils.url.UrlUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * "商城"UI
 * 
 * @author wangliang Jul 16, 2016
 */
public class ActivityShangcheng extends BaseActivity implements
        SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout mSwipeLayout;

    private ListView mList;

    private String token;

    private Context mContext;

    private List<BasicNameValuePair> params;

    private Gson mGson;

    private TextView mTv_name;

    protected ImageLoader imageLoader_head;

    private DisplayImageOptions options_head; // 设置图片显示相关参数

    private List<Sc_dataEntity> entities_ = new ArrayList<Sc_dataEntity>();

    private ItemListAdapter adapter;

    /**
     * 0:初始刷新；1：下拉；2：上拉。
     */
    private int status = 0;//

    private int pageNo = 0;

    private ScEntity entity = new ScEntity();

    @Override
    public int initResource() {
        return R.layout.activity_shangcheng;
    }

    @Override
    public void initView() {
        mContext = this;
        Intent intent = getIntent();
        if (intent != null) {
            String back = intent.getStringExtra("back");
            ((TextView)findViewById(R.id.tv_back_to_activity)).setText(back);
        }
        pageNo = 1;
        status = 0;
        token = new SharUtil(mContext).getAccess_token();
        params = new ArrayList<BasicNameValuePair>();
        mGson = new Gson();
        imageLoader_head = ImageLoader.getInstance();
        options_head = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ic_stub) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.ic_empty) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.ic_error) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(10)) // 设置成圆角图片
                .build(); // 构建完成
        mSwipeLayout = (SwipeRefreshLayout)findViewById(R.id.id_swipe_ly);
        mList = (ListView)findViewById(R.id.id_listview);
        mTv_name = (TextView)findViewById(R.id.activity_sc_name);
        mTv_name.setText("商城");

    }

    @Override
    public void initData() {
        getMsg();

    }

    @Override
    public void addListener() {
        mSwipeLayout.setOnRefreshListener(this);
        mList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Sc_dataEntity entity = entities_.get((Integer)parent.getItemAtPosition(position));
                Intent intent = new Intent(mContext, Sc_dt_ListActivity.class);
                intent.putExtra("entity", entity);
                intent.putExtra("back", "商城");
                startActivity(intent);
            }
        });

    }

    public void getMsg() {
        DialogUtils.getInstance().createNotifier(mContext, "正在加载中");
        params.add(new BasicNameValuePair("1", "1"));
        new Thread() {
            public void run() {
                String url = UrlUtils.BASEURL + "v1/busine/list.json?page=" + pageNo;
                String result = MyHttpRequest.getHttpPostBasic(url, params, token);
                if (result != null) {
                    List<Sc_dataEntity> entities = new ArrayList<Sc_dataEntity>();
                    ScEntity entity = new ScEntity();
                    if (status == 1) {
                        entities.clear();
                        try {
                            entity = mGson.fromJson(result, ScEntity.class);
                            entities = entity.getData();

                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                            handler.sendEmptyMessage(5);
                        }
                    } else if (status == 2) {
                        try {
                            entity = mGson.fromJson(result, ScEntity.class);
                            entities = entity.getData();
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                            handler.sendEmptyMessage(5);
                        }

                    } else if (status == 0) {
                        entities_.clear();
                        try {
                            entity = mGson.fromJson(result, ScEntity.class);
                            entities = entity.getData();
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                            handler.sendEmptyMessage(5);

                        }

                    }
                    Message msg3 = new Message();
                    msg3.what = 4;
                    msg3.obj = entity;
                    handler.sendMessage(msg3);

                    switch (status) {
                        case 0:
                            Message msg = new Message();
                            msg.what = 1;
                            msg.obj = entities;
                            handler.sendMessage(msg);
                            break;
                        case 1:
                            Message msg1 = new Message();
                            msg1.obj = entities;
                            msg1.what = 2;
                            handler.sendMessage(msg1);
                            break;
                        case 2:
                            Message msg2 = new Message();
                            msg2.what = 3;
                            msg2.obj = entities;
                            handler.sendMessage(msg2);
                            break;

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
                    String erros = (String)msg.obj;
                    ToastUtil.Toast(mContext, erros);
                    break;

                case 1:
                    @SuppressWarnings("unchecked")
                    List<Sc_dataEntity> entities = (List<Sc_dataEntity>)msg.obj;
                    entities_.addAll(entities);
                    adapter = new ItemListAdapter(entities_);
                    mList.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    break;
                case 2: { // 下拉刷新结果
                    entities_.clear();
                    List<Sc_dataEntity> entities_1 = (List<Sc_dataEntity>)msg.obj;
                    entities_.addAll(entities_1);
                    adapter = new ItemListAdapter(entities_);
                    mList.setAdapter(adapter);
                    pageNo = 1;
                    adapter.notifyDataSetChanged();
                }
                    break;
                case 3: { // 上拉加载
                    List<Sc_dataEntity> entities_2 = (List<Sc_dataEntity>)msg.obj;
                    entities_.addAll(entities_2);
                    adapter = new ItemListAdapter(entities_);
                    mList.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    mList.setSelection(entities_.size() - entities_2.size());
                }
                    break;
                case 4:
                    entity = (ScEntity)msg.obj;
                    break;
                case 5:
                    ToastUtil.Toast(mContext, "网络错误");
                    break;
                default:
                    break;

            }
        };
    };

    public class ItemListAdapter extends BaseAdapter {
        List<Sc_dataEntity> entities_;

        public ItemListAdapter(List<Sc_dataEntity> entities) {
            this.entities_ = entities;
        }

        @Override
        public int getCount() {
            if (entities_ == null) {
                return 0;
            }
            return entities_.size();
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
            ViewHolder1 viewHolder = null;
            if (convertView == null) {
                convertView = View.inflate(mContext, R.layout.shangcheng_item, null);
                viewHolder = new ViewHolder1();
                viewHolder.image = (ImageView)convertView.findViewById(R.id.shangcheng_item_imgv);
                viewHolder.mTv_name = (TextView)convertView
                        .findViewById(R.id.shangcheng_item_tv_name);
                viewHolder.mTv_price = (TextView)convertView
                        .findViewById(R.id.shangcheng_item_tv_price);
                viewHolder.mTv_barname = (TextView)convertView.findViewById(R.id.shancheng_barname);
                viewHolder.mTv_detail = (TextView)convertView.findViewById(R.id.shancheng_detail);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder1)convertView.getTag();
            }

            Sc_dataEntity entity = entities_.get(position);
            viewHolder.mTv_name.setText(entity.getName());
            viewHolder.mTv_price.setText("￥" + entity.getPrice());
            imageLoader_head.displayImage(entity.getBs_img(), viewHolder.image, options_head);
            viewHolder.mTv_barname.setText("消费于:" + entity.getProvince().getS_provname()
                    + entity.getCity().getS_cityname() + entity.getWangba().getW_name());
            viewHolder.mTv_detail.setText(entity.getBs_describe());
            return convertView;
        }

    }

    public class ViewHolder1 {
        public TextView mTv_name, mTv_price, mTv_barname, mTv_detail;

        private ImageView image;
    }

    public void back(View v) {
        finish();
    }

    /**
     * 下拉刷新回调接口
     */
    @Override
    public void onRefresh() {
        status = 1;
        pageNo = 1;
        getMsg();
    }

}
