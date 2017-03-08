
package com.jishang.bimeng.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jishang.bimeng.R;
import com.jishang.bimeng.activity.dt.detail.Detail_dtActivity;
import com.jishang.bimeng.entity.dt.nw.Dt_new_dataEntity;
import com.jishang.bimeng.entity.dt.nw.Dt_new_data_clEntity;
import com.jishang.bimeng.entity.dt.nw.Dt_new_data_cmEntity;
import com.jishang.bimeng.ui.adapter.mode.DZResult;
import com.jishang.bimeng.ui.adapter.mode.DZResultEntitiy;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.Internet.MyHttpRequest;
import com.jishang.bimeng.utils.url.UrlUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * 消息列表适配器
 * 
 * @author wangliang Jul 13, 2016
 */
public class AdapterFriendDynamic extends BaseAdapter {
    private Context context;

    protected ImageLoader imageLoader_content;

    private List<Dt_new_dataEntity> entities_;

    protected ImageLoader imageLoader_head;

    private DisplayImageOptions options_cotnent;

    private DisplayImageOptions options_head; // 璁剧疆鍥剧墖鏄剧ず鐩稿叧鍙傛暟

    public AdapterFriendDynamic(Context context, ImageView mImg_headimg) {
        this.context = context;
        handler = new MyHandler();
        imageLoader_content = ImageLoader.getInstance();
        imageLoader_head = ImageLoader.getInstance();
        params = new ArrayList<BasicNameValuePair>();
        mGson = new Gson();
        options_cotnent = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ic_stub)// 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.ic_empty)// 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.ic_error) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(1)) // 设置成圆角图片
                .considerExifParams(true)// 是否考虑JPEG图像EXIF参数（旋转，翻转）
                .build(); // 构建完成
        options_head = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ic_stub)
                .showImageForEmptyUri(R.drawable.ic_empty).showImageOnFail(R.drawable.ic_error)
                .cacheInMemory(true).cacheOnDisk(true).displayer(new RoundedBitmapDisplayer(20))
                .build();
        String imgurl = new SharUtil(context).getHead_img();
        imageLoader_head.displayImage(imgurl, mImg_headimg, options_head);
    }

    public void setData(List<Dt_new_dataEntity> entities_) {
        this.entities_ = entities_;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (entities_ != null) {
            return entities_.size();
        }
        return 0;
    }

    private String[] imageUrls_head;

    @Override
    public Object getItem(int position) {
        if (imageUrls_head != null) {
            return imageUrls_head[position];
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.content_item, null);
            viewHolder = new ViewHolder();
            viewHolder.image_head = (ImageView)convertView
                    .findViewById(R.id.content_item_username_imgv_headimg);
            viewHolder.img_content = (ImageView)convertView
                    .findViewById(R.id.content_item_username_imgv_contentimg);
            viewHolder.content = (TextView)convertView.findViewById(R.id.content_item_tv_content);
            viewHolder.username = (TextView)convertView.findViewById(R.id.content_item_tv_username);
            viewHolder.img_pinglun = (ImageView)convertView
                    .findViewById(R.id.content_item_username_imgv_pinglun);
            viewHolder.mTv_pinglun = (TextView)convertView
                    .findViewById(R.id.content_item_username_tv_pinglun);
            viewHolder.imgv_dianzan_huise = (ImageView)convertView
                    .findViewById(R.id.content_item_username_imgv_dianzan_huise);
            viewHolder.mTv_dianzan_num = (TextView)convertView
                    .findViewById(R.id.content_item_username_dianzannum);
            viewHolder.mTv_pinglun_num = (TextView)convertView
                    .findViewById(R.id.content_item_username_pinglunnum);
            viewHolder.img_pinglun = (ImageView)convertView
                    .findViewById(R.id.content_item_username_imgv_pinglun);

            viewHolder.imgv_dianzan_red = (ImageView)convertView
                    .findViewById(R.id.content_item_username_imgv_dianzan_red);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        // 获取动态对象
        final Dt_new_dataEntity entity = entities_.get(position);
        String img = entity.getThumb_img();

        if (TextUtils.isEmpty(img)) {
            viewHolder.img_content.setVisibility(View.GONE);
        } else {
            imageLoader_content.displayImage(entity.getThumb_img(), viewHolder.img_content,
                    options_cotnent);
            viewHolder.img_content.setVisibility(View.VISIBLE);
        }
        // 头像URL
        imageLoader_head.displayImage(entity.getUser_post().getHead_img(), viewHolder.image_head,
                options_head);
        viewHolder.content.setText(entity.getContent());
        viewHolder.username.setText(entity.getUser_post().getUsername());

        List<Dt_new_data_cmEntity> comment_count = entity.getComment_count();
        List<Dt_new_data_clEntity> click_count = entity.getClick_count();
        // 点赞
        if (click_count != null && !click_count.isEmpty()) {
            String count = click_count.size() + "";
            viewHolder.mTv_dianzan_num.setText(count);
            String uid = new SharUtil(context).getUid();
            if (click_count.contains(uid)) {
                viewHolder.imgv_dianzan_huise.setVisibility(View.VISIBLE);
                viewHolder.imgv_dianzan_red.setVisibility(View.GONE);
            } else {
                viewHolder.imgv_dianzan_huise.setVisibility(View.GONE);
                viewHolder.imgv_dianzan_red.setVisibility(View.VISIBLE);
            }
        } else {
            viewHolder.mTv_dianzan_num.setText("0");
        }
        // 评论
        if (comment_count != null && !comment_count.isEmpty()) {

            String comment = comment_count.size() + "";
            viewHolder.mTv_pinglun_num.setText(comment);
        } else {
            viewHolder.mTv_pinglun_num.setText("0");

        }
        // 评论点击事件监听
        viewHolder.img_pinglun.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Detail_dtActivity.class);
                intent.putExtra("uc_id", entity.getUc_id());
                intent.putExtra("back", "消息");
                context.startActivity(intent);
            }
        });
        // 点赞点击事件监听
        viewHolder.imgv_dianzan_red.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Dianzan(entity);
            }
        });

        return convertView;
    }

    private List<BasicNameValuePair> params;

    /**
     * 点赞
     * 
     * @param ucid
     */
    public void Dianzan(final Dt_new_dataEntity entity) {
        String ucid = entity.getUc_id();
        final String token = new SharUtil(context).getAccess_token();
        params.add(new BasicNameValuePair("ucsc_id", ucid));
        Log.i("ucid", ucid);
        new Thread() {
            public void run() {

                String url = UrlUtils.BASEURL + "v1/user_content/click.json";
                String result = MyHttpRequest.getHttpPostBasic(url, params, token);
                if (result != null) {
                    Log.i("result", result.toString());
                    try {
                        DZResultEntitiy entityDZResult = mGson.fromJson(result,
                                DZResultEntitiy.class);
                        if (entityDZResult.getStatus() == 0) {
                            Message msg = new Message();
                            msg.what = -1;
                            msg.obj = entityDZResult.getErrors();
                            handler.sendMessage(msg);
                        } else if (entityDZResult.getStatus() == 1) {
                            Message msg = new Message();
                            msg.what = 1;
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("Dt_new_dataEntity", entity);
                            bundle.putSerializable("DZResult", entityDZResult.data);
                            msg.setData(bundle);
                            handler.sendMessage(msg);
                        }
                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                    }

                }

            };
        }.start();
    }

    private Gson mGson;

    MyHandler handler;

    class MyHandler extends Handler {
        public MyHandler() {
        }

        // 子类必须重写此方法，接受数据
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:// 点赞成功
                    Bundle bundle = msg.getData();
                    Dt_new_dataEntity entity = (Dt_new_dataEntity)bundle
                            .getSerializable("Dt_new_dataEntity");
                    DZResult entityDZResult = (DZResult)bundle.getSerializable("DZResult");
                    List<Dt_new_data_clEntity> listZ = entity.getClick_count();
                    listZ.add(new Dt_new_data_clEntity("1", entityDZResult.ucsc_id,
                            entityDZResult.ucsp_id));
                    notifyDataSetChanged();
                    break;
                case -1:// 点赞失败
                    break;

                default:
                    break;
            }
            AdapterFriendDynamic.this.notifyDataSetChanged();
        }
    }

    public class ViewHolder {
        public ImageView image_head;

        public TextView username, mTv_fenge, mTv_dianzan_num, mTv_pinglun_num;

        public TextView content;

        public ImageView img_content;

        public ImageView img_pinglun;

        public TextView mTv_pinglun;

        public TextView ucid;

        public ImageView imgv_dianzan_red, imgv_dianzan_huise;

        public TextView mTv_dianzan;

        public LinearLayout mRl_pinglundz;
    }

}
