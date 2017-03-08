
package com.jishang.bimeng.activity.dt;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.jishang.bimeng.activity.pickphoto.view.PublishActivity;
import com.jishang.bimeng.entity.dt.gs.Dt_dataEntity;
import com.jishang.bimeng.entity.dt.gs.DtgsEntity;
import com.jishang.bimeng.entity.login.LogEntity;
import com.jishang.bimeng.parse.ParseUtil;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.ToastUtil;
import com.jishang.bimeng.utils.Internet.MyHttpRequest;
import com.jishang.bimeng.utils.url.UrlUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * "我的动态"UI
 * 
 * @author wangliang Jul 14, 2016
 */
public class ActivityMydt extends BaseActivity implements OnClickListener {
    private PullListView pullToRefreshListView;

    private RotateLayout rotateLayout;

    private TextView mTv_username, mTv_name;

    private DisplayImageOptions options_head; // 设置图片显示相关参数

    private DisplayImageOptions options_cotnent; // 设置图片显示相关参数

    protected ImageLoader imageLoader_head;

    protected ImageLoader imageLoader_content;

    private int pageNo = 1;

    private ImageView mImg_headimg;

    /**
     * 0:初始刷新；1：下拉；2：上拉。
     */
    private int status = 0;//

    private List<Dt_dataEntity> entities = new ArrayList<Dt_dataEntity>();

    private List<Dt_dataEntity> entities_ = new ArrayList<Dt_dataEntity>();

    private ItemListAdapter adapter;

    private EditText mEdt_pinglun;

    private RelativeLayout mRl_pinglun;

    private Gson gson;

    private Context mContext;

    private String imgurl;

    private RelativeLayout mRl_publish;

    private SharUtil mShareUtil;

    @Override
    public int initResource() {
        return R.layout.activity_mydt;
    }

    @Override
    public void initView() {
        mContext = this;
        Intent intent = getIntent();
        if (intent != null) {
            String back = intent.getStringExtra("back");
            ((TextView)findViewById(R.id.tv_back_to_activity)).setText(back);
        }
        mShareUtil = new SharUtil(mContext);
        gson = new Gson();
        imageLoader_head = ImageLoader.getInstance();
        imageLoader_content = ImageLoader.getInstance();
        options_head = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ic_stub) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.ic_empty) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.ic_error) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
                .build(); // 构建完成

        options_cotnent = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ic_stub) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.ic_empty) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.ic_error) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(1)) // 设置成圆角图片
                .build(); // 构建完成

        pullToRefreshListView = (PullListView)findViewById(R.id.refreshlistview);
        rotateLayout = (RotateLayout)findViewById(R.id.rotateLayout);
        View pullView = LayoutInflater.from(mContext).inflate(R.layout.headlayout_my, null);
        mTv_username = (TextView)pullView.findViewById(R.id.user_name);
        mTv_username.setText(new SharUtil(mContext).getUserName());
        mTv_name = (TextView)findViewById(R.id.fragment_xiaoxi_name);
        mImg_headimg = (ImageView)pullView.findViewById(R.id.headlayout_headimg);

        mRl_publish = (RelativeLayout)findViewById(R.id.fragment_mydt_publish);

        mRl_pinglun = (RelativeLayout)findViewById(R.id.commentLinear);
        mEdt_pinglun = (EditText)findViewById(R.id.commentEdit);

        pullToRefreshListView.setPullHeaderView(pullView);
        pullToRefreshListView.setPullHeaderViewHeight(100);
        pullToRefreshListView.setRotateLayout(rotateLayout);
        pullToRefreshListView.setCacheColorHint(Color.TRANSPARENT);
        adapter = new ItemListAdapter();
        pullToRefreshListView.setAdapter(adapter);
        mTv_name.setText(R.string.person_dynamic);
        imgurl = new SharUtil(mContext).getHead_img();
        imageLoader_head.displayImage(imgurl, mImg_headimg, options_head);
        loadPersonDynamicData();
    }

    /**
     * 加载个人动态
     */
    public void loadPersonDynamicData() {
        new Thread() {
            public void run() {
                String token = new SharUtil(mContext).getAccess_token();
                List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
                params.add(new BasicNameValuePair("111", "1"));
                String url = UrlUtils.BASEURL + "v1/user_content/my_contents.json?page=" + pageNo
                        + "";
                String result = MyHttpRequest.getHttpPostBasic(url, params, token);
                if (result == null) {
                    return;
                }
                if (status == 1) {
                    entities_.clear();
                    entities.clear();
                    try {
                        DtgsEntity entity = gson.fromJson(result, DtgsEntity.class);
                        entities = entity.getData();
                        entities_.addAll(entities);
                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                    }
                } else if (status == 2) {
                    entities.clear();
                    try {
                        DtgsEntity entity = gson.fromJson(result, DtgsEntity.class);
                        entities = entity.getData();
                        entities_.addAll(entities);
                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                    }

                } else if (status == 0) {
                    entities_.clear();
                    entities.clear();
                    try {
                        DtgsEntity entity = gson.fromJson(result, DtgsEntity.class);
                        entities = entity.getData();
                        entities_.addAll(entities);
                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();

                    }

                }

                switch (status) {
                    case 0:

                        handler.sendEmptyMessageDelayed(0, 500);
                        break;
                    case 1:
                        handler.sendEmptyMessageDelayed(1, 500);
                        break;
                    case 2:
                        handler.sendEmptyMessageDelayed(2, 500);
                        break;
                    default:
                        break;

                }
            };
        }.start();
    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    adapter.notifyDataSetChanged();
                    break;
                case 1:
                    adapter.notifyDataSetChanged();
                    pullToRefreshListView.onCompleteRefresh();
                    pageNo = 1;
                    break;
                case 2:
                    adapter.notifyDataSetChanged();
                    pullToRefreshListView.onCompleteRefresh();
                    break;
                case 3:
                    ToastUtil.Toast(mContext, "评论成功");
                    mRl_pinglun.setVisibility(View.GONE);
                    mEdt_pinglun.setText("");
                    loadPersonDynamicData();

                    break;
                case 4:
                    adapter.notifyDataSetChanged();
                    break;

                case 5:
                    adapter.notifyDataSetChanged();
                    break;
                default:
                    break;

            }
        };
    };

    public class ItemListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (entities_.size() == 0) {
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
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.content_item, null);
                viewHolder = new ViewHolder();
                viewHolder.image_head = (ImageView)convertView
                        .findViewById(R.id.content_item_username_imgv_headimg);
                viewHolder.img_content = (ImageView)convertView
                        .findViewById(R.id.content_item_username_imgv_contentimg);
                viewHolder.content = (TextView)convertView
                        .findViewById(R.id.content_item_tv_content);
                viewHolder.username = (TextView)convertView
                        .findViewById(R.id.content_item_tv_username);
                viewHolder.img_pinglun = (ImageView)convertView
                        .findViewById(R.id.content_item_username_imgv_pinglun);
                viewHolder.mTv_pinglun = (TextView)convertView
                        .findViewById(R.id.content_item_username_tv_pinglun);
                viewHolder.imgv_dianzan = (ImageView)convertView
                        .findViewById(R.id.content_item_username_imgv_dianzan_red);
                viewHolder.mTv_dianzan = (TextView)convertView
                        .findViewById(R.id.content_item_username_tv_dianzan);
                viewHolder.imgv_dianzan_huise = (ImageView)convertView
                        .findViewById(R.id.content_item_username_imgv_dianzan_huise);
                viewHolder.mRl_pinglundz = (LinearLayout)convertView
                        .findViewById(R.id.content_item_pinglundz);
                viewHolder.mTv_fenge = (TextView)convertView.findViewById(R.id.content_iten_fenge);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder)convertView.getTag();
            }

            /**
             * imageUrl 图片的Url地址 imageView 承载图片的ImageView控件 options
             * DisplayImageOptions配置文件
             */

            imageLoader_head.displayImage(imgurl, viewHolder.image_head, options_head);

            // TextView设置文本
            final Dt_dataEntity entity = entities_.get(position);
            String img = entity.getThumb_img();
            if (img != null & !img.equals("")) {
                viewHolder.img_content.setVisibility(View.VISIBLE);
                imageLoader_content.displayImage(entity.getThumb_img(), viewHolder.img_content,
                        options_cotnent);
            } else {
                viewHolder.img_content.setVisibility(View.GONE);
            }
            viewHolder.content.setText(entity.getContent());
            viewHolder.username.setText(mShareUtil.getUserName());
            return convertView;
        }

    }

    /**
     * @param ucid 评论接口
     */
    public void Putpinglun(final String ucid) {
        new Thread() {
            public void run() {
                String token = new SharUtil(mContext).getAccess_token();
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
                String token = new SharUtil(mContext).getAccess_token();
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
                String token = new SharUtil(mContext).getAccess_token();
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

    public class ViewHolder {
        public ImageView image_head;

        public TextView username, mTv_fenge;

        public TextView content;

        public ImageView img_content;

        public ImageView img_pinglun;

        public TextView mTv_pinglun;

        public TextView ucid;

        public ImageView imgv_dianzan;

        public TextView mTv_dianzan;

        public ImageView imgv_dianzan_huise;

        public LinearLayout mRl_pinglundz;
    }

    @Override
    public void initData() {

    }

    @Override
    public void addListener() {
        mRl_publish.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.fragment_mydt_publish:
                Intent intent = new Intent(mContext, PublishActivity.class);
                startActivity(intent);
                break;

        }
    }

}
