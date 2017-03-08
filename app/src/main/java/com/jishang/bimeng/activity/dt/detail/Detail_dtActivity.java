
package com.jishang.bimeng.activity.dt.detail;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jishang.bimeng.R;
import com.jishang.bimeng.activity.base.BaseActivity;
import com.jishang.bimeng.entity.dt.detail.Dt_dtEntity;
import com.jishang.bimeng.entity.dt.detail.Dt_dt_data_clEntity;
import com.jishang.bimeng.entity.dt.detail.Dt_dt_data_cmEntity;
import com.jishang.bimeng.entity.tonyong.TYEntity;
import com.jishang.bimeng.ui.custom.view.ListViewComments;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.ToastUtil;
import com.jishang.bimeng.utils.Internet.MyHttpRequest;
import com.jishang.bimeng.utils.dialog.DialogUtils;
import com.jishang.bimeng.utils.url.UrlUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * 动态详情
 * 
 * @author wangliang Jul 14, 2016
 */
public class Detail_dtActivity extends BaseActivity implements OnClickListener {
    private TextView mTv_username, mTv_content, mTv_diannum, mTv_pinglunnum;

    private ImageView mImg_headimg, mImg_content;

    private ListViewComments mList;

    private List<BasicNameValuePair> params;

    private Gson mGson;

    protected ImageLoader imageLoader_head;

    private DisplayImageOptions options_head; // 设置图片显示相关参数

    private String token;

    private String uc_id;

    private Dt_dtEntity entity = new Dt_dtEntity();

    private List<Dt_dt_data_cmEntity> entities = new ArrayList<Dt_dt_data_cmEntity>();

    private ItemListAdapter adapter;

    private LinearLayout mRl_pinglun;

    private Button mBt_confrim;

    private ImageView mImg_pinglun;

    private EditText mEdt_content;

    @Override
    public int initResource() {
        return R.layout.activity_dynamic_detail;
    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        if (intent != null) {
            String back = intent.getStringExtra("back");
            ((TextView)findViewById(R.id.tv_back_to_activity)).setText(back);
        }
        intent = getIntent();
        uc_id = (String)intent.getSerializableExtra("uc_id");
        params = new ArrayList<BasicNameValuePair>();
        mGson = new Gson();
        imageLoader_head = ImageLoader.getInstance();
        options_head = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ic_stub) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.ic_empty) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.ic_error) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
                .considerExifParams(true)// 是否考虑JPEG图像EXIF参数（旋转，翻转）
                .displayer(new RoundedBitmapDisplayer(10)) // 设置成圆角图片
                .build(); // 构建完成

        token = new SharUtil(this).getAccess_token();
        mList = (ListViewComments)findViewById(R.id.activity_dt_detail_list);
        mTv_username = (TextView)findViewById(R.id.activity_dt_detail_tv_username);
        mImg_headimg = (ImageView)findViewById(R.id.activity_dt_detail_imgv_headimg);
        mTv_content = (TextView)findViewById(R.id.activity_dt_detail_tv_content);
        mImg_content = (ImageView)findViewById(R.id.activity_dt_detail_imgv_contentimg);
        mRl_pinglun = (LinearLayout)findViewById(R.id.activity_dt_detail_rl_pinglun);
        mBt_confrim = (Button)findViewById(R.id.activity_dt_detail_bt_confrim);
        mImg_pinglun = (ImageView)findViewById(R.id.activity_dt_detail_dianzan_pinglun);
        mEdt_content = (EditText)findViewById(R.id.activity_dt_detail_content);
        mTv_diannum = (TextView)findViewById(R.id.activity_dt_detail_username_dianzannum);
        mTv_pinglunnum = (TextView)findViewById(R.id.activity_dt_detail_username_pinglunnum);
        adapter = new ItemListAdapter();
        mList.setAdapter(adapter);

    }

    @Override
    public void initData() {
        getMsg();

    }

    @Override
    public void addListener() {
        mRl_pinglun.setOnClickListener(this);
        mBt_confrim.setOnClickListener(this);
        mImg_pinglun.setOnClickListener(this);

    }

    public void back(View v) {
        finish();
    }

    public void getMsg() {
        DialogUtils.getInstance().createNotifier(this, "正在加载中");
        params.add(new BasicNameValuePair("uc_id", uc_id));

        new Thread() {
            public void run() {
                String url = UrlUtils.BASEURL + "v1/user_content/view.json";
                String result = MyHttpRequest.getHttpPostBasic(url, params, token);
                if (result != null) {
                    Log.e("result", result.toString());
                    try {
                        entity = mGson.fromJson(result, Dt_dtEntity.class);
                        entities = entity.getData().getComments();
                        if (entity.getStatus() == 0) {
                            handler.sendEmptyMessage(0);
                        } else if (entity.getStatus() == 1) {
                            handler.sendEmptyMessage(1);
                        }
                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                        handler.sendEmptyMessage(0);

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
                    ToastUtil.Toast(Detail_dtActivity.this, "网络错误");
                    break;
                case 1:

                    setText();
                    adapter.notifyDataSetChanged();
                    break;
                case 2:
                    ToastUtil.Toast(Detail_dtActivity.this, "添加成功");
                    break;
                case 3:
                    mRl_pinglun.setVisibility(View.GONE);
                    mEdt_content.setText("");
                    getMsg();
                    break;

            }
        };
    };

    public void setText() {
        mTv_username.setText(entity.getData().getUser_post().getUsername());
        mTv_content.setText(entity.getData().getContent());
        imageLoader_head.displayImage(entity.getData().getUser_post().getHead_img(), mImg_headimg,
                options_head);

        String img = entity.getData().getThumb_img();
        if (img != null & !img.equals("")) {
            mImg_content.setVisibility(View.VISIBLE);
            imageLoader_head.displayImage(img, mImg_content, options_head);
        } else {
            mImg_content.setVisibility(View.GONE);
        }

        List<Dt_dt_data_cmEntity> comment_count = entity.getData().getComments();
        List<Dt_dt_data_clEntity> click_count = entity.getData().getClicks();
        if (!click_count.isEmpty() && !click_count.equals("")) {
            String count = click_count.size() + "";
            mTv_diannum.setText(count);
        } else {
            mTv_diannum.setText("0");
        }
        if (!comment_count.isEmpty() && !comment_count.equals("")) {
            String comment = comment_count.size() + "";
            mTv_pinglunnum.setText(comment);
        } else {
            mTv_pinglunnum.setText("0");

        }
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = View
                        .inflate(Detail_dtActivity.this, R.layout.item_dt_detail_dt, null);
                viewHolder = new ViewHolder();
                viewHolder.mTv_content = (TextView)convertView
                        .findViewById(R.id.detail_dt_item_tv_content);
                viewHolder.mTv_username = (TextView)convertView
                        .findViewById(R.id.detail_dt_item_tv_username);
                viewHolder.image_head = (ImageView)convertView
                        .findViewById(R.id.detail_dt_item_imgv_headimg);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder)convertView.getTag();
            }
            final Dt_dt_data_cmEntity entity = entities.get(position);
            viewHolder.mTv_username.setText(entity.getComment_users().getUsername());
            viewHolder.mTv_content.setText(entity.getMessage());
            imageLoader_head.displayImage(entity.getComment_users().getHead_img(),
                    viewHolder.image_head, options_head);

            return convertView;
        }

        public class ViewHolder {
            public TextView mTv_username, mTv_content;

            private ImageView image_head;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_dt_detail_bt_confrim:
                Putpinglun(entity.getData().getUc_id());
                break;
            case R.id.activity_dt_detail_dianzan_pinglun:
                mRl_pinglun.setVisibility(View.VISIBLE);
                break;

        }

    }

    /**
     * @param ucid 评论接口
     */
    public void Putpinglun(String ucid) {
        DialogUtils.getInstance().createNotifier(this, "正在加载中");
        final String token = new SharUtil(this).getAccess_token();
        String content = mEdt_content.getText().toString().trim();
        params.add(new BasicNameValuePair("message", content));
        params.add(new BasicNameValuePair("ucmc_id", ucid));
        new Thread() {
            public void run() {
                String url = UrlUtils.BASEURL + "v1/user_content/comment.json?";
                String result = MyHttpRequest.getHttpPostBasic(url, params, token);
                if (result != null) {
                    try {
                        TYEntity entity = mGson.fromJson(result, TYEntity.class);
                        if (entity.getStatus() == 1) {

                            handler.sendEmptyMessage(3);
                        } else if (entity.getStatus() == 0) {

                        }
                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                        handler.sendEmptyMessage(0);
                    }
                }
            };
        }.start();
    }

}
