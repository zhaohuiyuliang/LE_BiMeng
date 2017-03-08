
package com.jishang.bimeng.fragment.xiaoxi;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
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
import com.jishang.bimeng.activity.addfd.AdListActivity;
import com.jishang.bimeng.activity.hxchat.ActivityChat;
import com.jishang.bimeng.entity.chat.Fdlist.FriendEntity;
import com.jishang.bimeng.entity.chat.Fdlist.FdlistEntity;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.ToastUtil;
import com.jishang.bimeng.utils.Internet.MyHttpRequest;
import com.jishang.bimeng.utils.dialog.DialogUtils;
import com.jishang.bimeng.utils.url.UrlUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class Fragment_txl extends Fragment implements OnClickListener {
    private View v;

    private ListView mList;

    private List<BasicNameValuePair> params;

    private Gson gson;

    protected ImageLoader imageLoader_head;

    private DisplayImageOptions options_head; // 设置图片显示相关参数

    private FdListAdatpter adapter;

    List<FriendEntity> entities = new ArrayList<FriendEntity>();

    private String[] imageUrls; // 图片路径

    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_txl, null);
        initView(v);
        return v;
    }

    public void initView(View v) {

        mContext = getActivity();
        params = new ArrayList<BasicNameValuePair>();
        gson = new Gson();
        imageLoader_head = ImageLoader.getInstance();
        options_head = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ic_stub) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.ic_empty) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.ic_error) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(10)) // 设置成圆角图片
                .build(); // 构建完成
        mList = (ListView)v.findViewById(R.id.activity_fdlist_list);
        adapter = new FdListAdatpter();

        LayoutInflater mLayoutInflater = LayoutInflater.from(getActivity());
        View mHeaderView = mLayoutInflater.inflate(R.layout.listview_head, null);
        mList.addHeaderView(mHeaderView);
        mList.setAdapter(adapter);
        mList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                    // 加headview之后位置变了
                    // ToastUtil.Toast(mContext, "第一个");
                    Intent intent = new Intent(mContext, AdListActivity.class);
                    startActivity(intent);

                } else {
                    Intent intent = new Intent(getActivity(), ActivityChat.class);
                    FriendEntity entity = entities.get(position - 1);
                    intent.putExtra("entity", entity);
                    startActivity(intent);
                }

            }
        });
        getMsg();

    }

    public void getMsg() {
        DialogUtils.getInstance().createNotifier(mContext, "正在加载中");
        params.add(new BasicNameValuePair("11", "11"));
        final String token = new SharUtil(getActivity()).getAccess_token();
        new Thread() {
            public void run() {
                String url = UrlUtils.BASEURL + "v1/user/check_friends.json";
                String result = MyHttpRequest.getHttpPostBasic(url, params, token);
                if (result != null) {
                    Log.e("result", result.toString());
                    FdlistEntity entity = null;
                    try {
                        entity = gson.fromJson(result, FdlistEntity.class);
                        entities = entity.getData();
                        /*
                         * imageUrls = new String[entities.size()]; for (int i =
                         * 0; i < entities.size(); i++) { imageUrls[i] =
                         * entities.get(i).getHead_img(); }
                         */
                        hadler.sendEmptyMessage(0);
                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                        hadler.sendEmptyMessage(1);

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
                    adapter.notifyDataSetChanged();

                    break;
                case 1:
                    ToastUtil.Toast(mContext, "无数据，清先添加");
                    break;

            }
        };
    };

    public class FdListAdatpter extends BaseAdapter {

        @Override
        public int getCount() {
            return entities.size();
        }

        @Override
        public Object getItem(int position) {
            return imageUrls[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = View.inflate(mContext, R.layout.fdlist_item, null);
                viewHolder = new ViewHolder();
                viewHolder.text = (TextView)convertView.findViewById(R.id.fdlist_item_tv_username);
                viewHolder.image = (ImageView)convertView.findViewById(R.id.fdlist_item_imgv_head);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder)convertView.getTag();
            }

            /**
             * imageUrl 图片的Url地址 imageView 承载图片的ImageView控件 options
             * DisplayImageOptions配置文件
             */

            final FriendEntity entity = entities.get(position);
            // Log.e("entity", entity.toString());
            imageLoader_head.displayImage(entity.getHead_img(), viewHolder.image, options_head);

            viewHolder.text.setText(entity.getUsername());
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

}
