
package com.jishang.bimeng.activity.dt;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jishang.bimeng.R;
import com.jishang.bimeng.activity.base.BaseActivity;
import com.jishang.bimeng.activity.dt.three.RefreshLayout;
import com.jishang.bimeng.activity.dt.three.RefreshLayout.OnLoadListener;
import com.jishang.bimeng.activity.pickphoto.view.PublishActivity;
import com.jishang.bimeng.entity.dt.DtEntity;
import com.jishang.bimeng.parse.ParseUtil;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.Internet.MyHttpRequest;
import com.jishang.bimeng.utils.url.UrlUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class DtActivity extends BaseActivity {
    private ListView mList;

    private List<BasicNameValuePair> params;

    private ProgressDialog dialog;

    private String[] imageUrls_head; // 图片路径

    private String[] imageUrls_content; // 图片路径

    private DisplayImageOptions options_head; // 设置图片显示相关参数

    private DisplayImageOptions options_cotnent; // 设置图片显示相关参数

    protected ImageLoader imageLoader_head;

    protected ImageLoader imageLoader_content;

    RefreshLayout myRefreshListView;

    @Override
    public int initResource() {
        return R.layout.activity_dt;
    }

    @Override
    public void initView() {
        myRefreshListView = (RefreshLayout)findViewById(R.id.swipe_layout);
        // 设置下拉刷新时的颜色值,颜色值需要定义在xml中
        myRefreshListView
                .setColorScheme(R.color.umeng_comm_text_topic_light_color,
                        R.color.umeng_comm_yellow, R.color.umeng_comm_green,
                        R.color.umeng_comm_linked_text);
        // 设置下拉刷新监听器
        // 设置下拉刷新监听器
        myRefreshListView.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh() {

                Toast.makeText(DtActivity.this, "refresh", Toast.LENGTH_SHORT).show();

                myRefreshListView.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        // 更新数据
                        // 更新完后调用该方法结束刷新
                        myRefreshListView.setRefreshing(false);
                    }
                }, 1000);
            }
        });

        // 加载监听器
        // 加载监听器
        myRefreshListView.setOnLoadListener(new OnLoadListener() {

            @Override
            public void onLoad() {

                Toast.makeText(DtActivity.this, "load", Toast.LENGTH_SHORT).show();

                myRefreshListView.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        /*
                         * datas.add(new Date().toGMTString());
                         * adapter.notifyDataSetChanged(); // 加载完后调用该方法
                         */myRefreshListView.setLoading(false);
                    }
                }, 1500);

            }
        });
        mList = (ListView)findViewById(R.id.activity_dt_list);

        params = new ArrayList<BasicNameValuePair>();

        // sv=(ScrollView) findViewById(R.id.scrollview);
        imageLoader_head = ImageLoader.getInstance();
        imageLoader_content = ImageLoader.getInstance();
        // 使用DisplayImageOptions.Builder()创建DisplayImageOptions
        options_head = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ic_stub) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.ic_empty) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.ic_error) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(10)) // 设置成圆角图片
                .build(); // 构建完成

        options_cotnent = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ic_stub) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.ic_empty) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.ic_error) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(1)) // 设置成圆角图片
                .build(); // 构建完成

    }

    public void getMsg() {
        final String token = new SharUtil(DtActivity.this).getAccess_token();
        params.add(new BasicNameValuePair("111", "111"));
        new Thread() {
            public void run() {
                String url = UrlUtils.BASEURL + "v1/user_content/select_by_business.json?";
                String result = MyHttpRequest.getHttpPostBasic(url, params, token);
                if (result != null) {
                    Log.e("result", result.toString());
                    Message msg = new Message();
                    msg.what = 0;
                    msg.obj = result;
                    handler.sendMessage(msg);

                }
            };
        }.start();
    }

    @Override
    public void initData() {
        getMsg();

    }

    @Override
    public void addListener() {

    }

    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            String result = (String)msg.obj;
            switch (msg.what) {
                case 0:
                    List<DtEntity> entities = ParseUtil.getBanner_dt(result);
                    imageUrls_head = new String[entities.size()];
                    imageUrls_content = new String[entities.size()];
                    for (int i = 0; i < entities.size(); i++) {
                        imageUrls_head[i] = entities.get(i).getHeadimg();
                        imageUrls_content[i] = entities.get(i).getThumb_img();
                    }
                    // Log.e("entities", entities.toString());
                    mList.setAdapter(new ItemListAdapter(entities));
                    // sv.smoothScrollTo(0, 0);
                    break;

            }
        };
    };

    public void fabu(View view) {
        Intent intent = new Intent(DtActivity.this, PublishActivity.class);
        startActivity(intent);

    }

    public void back(View view) {
        finish();
    }

    public class ItemListAdapter extends BaseAdapter {
        List<DtEntity> entities;

        public ItemListAdapter(List<DtEntity> entities) {
            this.entities = entities;
        }

        @Override
        public int getCount() {
            return imageUrls_head.length;
        }

        @Override
        public Object getItem(int position) {
            return imageUrls_head[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.content_item, null);
                viewHolder = new ViewHolder();
                viewHolder.image_head = (ImageView)convertView
                        .findViewById(R.id.content_item_username_imgv_headimg);
                viewHolder.img_content = (ImageView)convertView
                        .findViewById(R.id.content_item_username_imgv_contentimg);
                viewHolder.content = (TextView)convertView
                        .findViewById(R.id.content_item_tv_content);
                viewHolder.username = (TextView)convertView
                        .findViewById(R.id.content_item_tv_username);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder)convertView.getTag();
            }

            /**
             * imageUrl 图片的Url地址 imageView 承载图片的ImageView控件 options
             * DisplayImageOptions配置文件
             */
            imageLoader_content.displayImage(imageUrls_content[position], viewHolder.img_content,
                    options_cotnent);
            imageLoader_head.displayImage(imageUrls_head[position], viewHolder.image_head,
                    options_head);

            // viewHolder.text.setText("Item " + (position + 1)); //
            // TextView设置文本
            final DtEntity entity = entities.get(position);
            viewHolder.content.setText(entity.getContent());
            viewHolder.username.setText(entity.getUsername());
            return convertView;
        }

    }

    public class ViewHolder {
        public ImageView image_head;

        public TextView username;

        public TextView content;

        public ImageView img_content;
    }

    public void Setprogrese() {
        dialog = new ProgressDialog(DtActivity.this);
        dialog.setMessage("正在更新数据，请稍候。。。");
        dialog.show();
    }

}
