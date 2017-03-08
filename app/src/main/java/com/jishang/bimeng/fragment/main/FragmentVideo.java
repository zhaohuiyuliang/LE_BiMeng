
package com.jishang.bimeng.fragment.main;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.jishang.bimeng.activity.shipin.ShipindetailActivity;
import com.jishang.bimeng.entity.shipin.ShipinEntity;
import com.jishang.bimeng.entity.shipin.Shipin_dataEntity;
import com.jishang.bimeng.fragment.base.BaseFragment;
import com.jishang.bimeng.utils.InternetUtils;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.ToastUtil;
import com.jishang.bimeng.utils.Internet.MyHttpRequest;
import com.jishang.bimeng.utils.dialog.DialogUtils;
import com.jishang.bimeng.utils.url.UrlUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * "视频"UI
 * 
 * @author wangliang Jul 18, 2016
 */
public class FragmentVideo extends BaseFragment implements OnClickListener,
        SwipeRefreshLayout.OnRefreshListener {
    /**
     * 视频列表
     */
    private SwipeRefreshLayout mSwipeLayout;

    protected ImageLoader imageLoader;

    private DisplayImageOptions options; // 设置图片显示相关参数

    private Context mContext;

    private List<Shipin_dataEntity> entities_ = new ArrayList<Shipin_dataEntity>();

    /**
     * 0:初始刷新；1：下拉；2：上拉。
     */
    private int status = 0;//

    private int pageNo = 0;

    /**
     * 视频列表适配器
     */
    private ItemListAdapter adapter;

    private ListView mList;

    @Override
    public View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_video, null);
        initView(view);
        mContext = getActivity();
        initData(null);
        inigetData();
        addListener();
        return view;
    }

    public void initView(View view) {
        mSwipeLayout = (SwipeRefreshLayout)view.findViewById(R.id.id_swipe_ly);
        mList = (ListView)view.findViewById(R.id.id_listview);
    }

    @Override
    public void initData(Bundle savedInstanceState) {

        imageLoader = ImageLoader.getInstance();
        // 使用DisplayImageOptions.Builder()创建DisplayImageOptions
        options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ic_stub) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.ic_empty) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.ic_error) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(1)) // 设置成圆角图片
                .build(); // 构建完成
    }

    public void addListener() {
        mSwipeLayout.setOnRefreshListener(this);
    }

    public void inigetData() {
        final String token = new SharUtil(getActivity()).getAccess_token();

        new Thread() {
            public void run() {
                if (!InternetUtils.isNetworkAvailable(getActivity())) {
                    handler.sendEmptyMessage(7);
                    return;
                }
                List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
                params.add(new BasicNameValuePair("111", "111"));
                String url = UrlUtils.BASEURL + "v1/video/list.json";
                String result = MyHttpRequest.getHttpPostBasic(url, params, token);
                // 判断网络地址是不是能用
                if (result.equals("0")) {
                    handler.sendEmptyMessage(0);
                    return;
                }
                Gson mGson = new Gson();
                if (result != null) {
                    List<Shipin_dataEntity> entities = new ArrayList<Shipin_dataEntity>();
                    ShipinEntity entity = new ShipinEntity();

                    if (status == 1) {
                        entities.clear();
                        try {
                            entity = mGson.fromJson(result, ShipinEntity.class);
                            entities = entity.getData();

                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                            handler.sendEmptyMessage(5);
                        }
                    } else if (status == 2) {
                        try {
                            entity = mGson.fromJson(result, ShipinEntity.class);
                            entities = entity.getData();
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                            handler.sendEmptyMessage(5);
                        }

                    } else if (status == 0) {
                        entities_.clear();
                        try {
                            entity = mGson.fromJson(result, ShipinEntity.class);
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
                case 0: {
                    String erros = (String)msg.obj;
                    ToastUtil.Toast(mContext, erros);
                }
                    break;
                case 1: {
                    @SuppressWarnings("unchecked")
                    List<Shipin_dataEntity> entities = (List<Shipin_dataEntity>)msg.obj;
                    entities_.addAll(entities);
                    adapter = new ItemListAdapter(entities_);
                    mList.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
                    break;
                case 2: {
                    // 下拉结果
                    entities_.clear();
                    @SuppressWarnings("unchecked")
                    List<Shipin_dataEntity> entities_1 = (List<Shipin_dataEntity>)msg.obj;
                    entities_.addAll(entities_1);
                    adapter = new ItemListAdapter(entities_);
                    mList.setAdapter(adapter);
                    pageNo = 1;
                    adapter.notifyDataSetChanged();
                    mSwipeLayout.setRefreshing(false);
                }
                    break;
                case 3: {
                    // 上拉加载
                    @SuppressWarnings("unchecked")
                    List<Shipin_dataEntity> entities_2 = (List<Shipin_dataEntity>)msg.obj;
                    entities_.addAll(entities_2);
                    adapter = new ItemListAdapter(entities_);
                    mList.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    mList.setSelection(entities_.size() - entities_2.size());
                }
                    break;
                case 4: {
                }
                    break;
                case 5: {
                    ToastUtil.Toast(mContext, "网络错误");
                }
                    break;
                default:
                    break;

            }
        };
    };

    public class ItemListAdapter extends BaseAdapter {
        List<Shipin_dataEntity> entities;

        public ItemListAdapter(List<Shipin_dataEntity> entities) {
            this.entities = entities;
        }

        @Override
        public int getCount() {
            return entities.size();
        }

        @Override
        public Object getItem(int position) {
            return 0;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = View.inflate(getActivity(), R.layout.item_list, null);
                viewHolder = new ViewHolder();
                viewHolder.text = (TextView)convertView.findViewById(R.id.tv_introduce);
                viewHolder.image = (ImageView)convertView.findViewById(R.id.iv_image);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder)convertView.getTag();
            }

            /**
             * imageUrl 图片的Url地址 imageView 承载图片的ImageView控件 options
             * DisplayImageOptions配置文件
             */

            final Shipin_dataEntity entity = entities.get(position);
            viewHolder.text.setText(entity.getTitle());

            Log.e("entity", entity.toString());
            imageLoader.displayImage(entity.getVid_thumb(), viewHolder.image, options);
            viewHolder.image.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // 点击播放视频
                    Intent intent = new Intent(getActivity(), ShipindetailActivity.class);
                    intent.putExtra("entity", entity);
                    startActivity(intent);

                }
            });

            viewHolder.text.setText(entity.getTitle());
            return convertView;
        }

    }

    public class ViewHolder {
        public TextView text;

        private ImageView image;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onRefresh() {
        status = 1;
        inigetData();
    }

    @Override
    public void refreshUI() {

    }

}
