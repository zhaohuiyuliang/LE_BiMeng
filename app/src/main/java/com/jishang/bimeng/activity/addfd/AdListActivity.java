
package com.jishang.bimeng.activity.addfd;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
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
import com.jishang.bimeng.entity.chat.addlist.AdListEntity;
import com.jishang.bimeng.entity.chat.addlist.AdList_dataEntity;
import com.jishang.bimeng.entity.chat.addlist.AdList_data_uEntity;
import com.jishang.bimeng.entity.tonyong.TYEntity;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.ToastUtil;
import com.jishang.bimeng.utils.Internet.MyHttpRequest;
import com.jishang.bimeng.utils.dialog.DialogUtils;
import com.jishang.bimeng.utils.url.UrlUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * 好友请求
 * 
 * @author wangliang Jul 14, 2016
 */
public class AdListActivity extends BaseActivity {
    private ListView mList;

    private List<BasicNameValuePair> params;

    private Gson mGson;

    protected ImageLoader imageLoader_head;

    private DisplayImageOptions options_head; // 设置图片显示相关参数

    private TextView mTv_name;

    private Context mContext;

    private List<AdList_dataEntity> entities = new ArrayList<AdList_dataEntity>();

    private ItemListAdapter adapter = null;

    private String token;

    @Override
    public int initResource() {
        return R.layout.activity_addlist;
    }

    @Override
    public void initView() {
        mContext = this;
        ((TextView)findViewById(R.id.tv_back_to_activity)).setText("");
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
        mList = (ListView)findViewById(R.id.activity_adlist_list);
        mTv_name = (TextView)findViewById(R.id.activity_addlist_name);
        adapter = new ItemListAdapter();
        token = new SharUtil(mContext).getAccess_token();
        mList.setAdapter(adapter);
        mTv_name.setText("好友请求");
    }

    @Override
    public void initData() {
        getMsg();
    }

    @Override
    public void addListener() {

    }

    public void getMsg() {
        DialogUtils.getInstance().createNotifier(mContext, "正在加载中");
        params.add(new BasicNameValuePair("11", "11"));

        new Thread() {
            public void run() {
                String url = UrlUtils.WAIT_FRIENDSHIP;
                String result = MyHttpRequest.getHttpPostBasic(url, params, token);
                if (result != null) {
                    Log.e("result", result.toString());
                    try {
                        AdListEntity entity = mGson.fromJson(result, AdListEntity.class);
                        if (entity.getStatus() == 0) {
                            hadler.sendEmptyMessage(0);
                        } else if (entity.getStatus() == 1) {
                            entities = entity.getData();
                            hadler.sendEmptyMessage(1);
                        }
                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                        hadler.sendEmptyMessage(0);

                    }

                }

            };
        }.start();
    }

    Handler hadler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            DialogUtils.getInstance().cancel();
            switch (msg.what) {
                case 0:
                    ToastUtil.Toast(mContext, "网络错误");
                    break;
                case 1:
                    adapter.notifyDataSetChanged();
                    break;
                case 2:
                    ToastUtil.Toast(mContext, "添加成功");
                    finish();
                    break;
                case 3:
                    ToastUtil.Toast(mContext, "添加失败");
                    break;

            }
        };
    };

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
                convertView = View.inflate(mContext, R.layout.addlist_list_item, null);
                viewHolder = new ViewHolder();
                viewHolder.mTv_name = (TextView)convertView
                        .findViewById(R.id.yzfenqian_list_item_name);
                viewHolder.mTv_confirm = (TextView)convertView
                        .findViewById(R.id.yzfenqian_list_item_confirm);
                viewHolder.mTv_id = (TextView)convertView.findViewById(R.id.yzfenqian_list_item_id);
                viewHolder.image = (ImageView)convertView
                        .findViewById(R.id.content_item_username_imgv_headimg);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder)convertView.getTag();
            }
            final AdList_data_uEntity entity = entities.get(position).getO_user();
            final AdList_dataEntity entity_data = entities.get(position);
            viewHolder.mTv_name.setText(entity.getUsername());
            viewHolder.mTv_id.setText("ID:" + entity.getUid());
            imageLoader_head.displayImage(entity.getHead_img(), viewHolder.image, options_head);

            viewHolder.mTv_confirm.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    Confrim(entity_data.getO_id());

                }
            });
            return convertView;
        }

        public class ViewHolder {
            public TextView mTv_yz_title, mTv_need_persons, mTv_server, mTv_peple_money,
                    mTv_money_name, mTv_yz_name, mTv_start_time, mTv_location, mTv_confirm,
                    mTv_cancel, mTv_name, mTv_id;

            private ImageView image;
        }

    }

    public void back(View v) {
        finish();

    }

    public void Confrim(String id) {
        params.clear();
        DialogUtils.getInstance().createNotifier(mContext, "正在加载中");
        params.add(new BasicNameValuePair("o_id", id));
        Log.e("id", id);
        new Thread() {
            public void run() {
                String url = UrlUtils.AGREE_FRIENDSHIP;
                String result = MyHttpRequest.getHttpPostBasic(url, params, token);
                if (result != null) {
                    try {
                        TYEntity entity = mGson.fromJson(result, TYEntity.class);
                        if (entity.getStatus() == 0) {
                            hadler.sendEmptyMessage(3);
                        } else if (entity.getStatus() == 1) {
                            hadler.sendEmptyMessage(2);
                        }
                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                    }
                }
            };
        }.start();

    }

}
