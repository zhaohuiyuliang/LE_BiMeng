
package com.jishang.bimeng.ui.adapter;

import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jishang.bimeng.R;
import com.jishang.bimeng.entity.dt.gs.Dt_dataEntity;
import com.jishang.bimeng.entity.yuezhan.yzlist.List_dataEntity;
import com.jishang.bimeng.utils.SharUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * 其他人的动态列表适配器
 * 
 * @author wangliang Jul 20, 2016
 */
public class AdapterOtherPeopleDynamic extends BaseAdapter {
    private Context context;

    private DisplayImageOptions options_head; // 设置图片显示相关参数

    protected ImageLoader imageLoader_head;

    private List<Dt_dataEntity> entities_;

    private List_dataEntity entity;

    public AdapterOtherPeopleDynamic(Context context, List_dataEntity entity, ImageView mImg_headimg) {
        this.context = context;
        this.entity = entity;
        mShareUtil = new SharUtil(context);
        imageLoader_head = ImageLoader.getInstance();
        options_head = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ic_stub) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.ic_empty) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.ic_error) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
                .build(); // 构建完成
        imageLoader_content = ImageLoader.getInstance();
        options_cotnent = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ic_stub) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.ic_empty) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.ic_error) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
                .considerExifParams(true)// 是否考虑JPEG图像EXIF参数（旋转，翻转）
                .displayer(new RoundedBitmapDisplayer(1)) // 设置成圆角图片
                .build(); // 构建完成
        imageLoader_head.displayImage(entity.user.head_img, mImg_headimg, options_head);
    }

    protected ImageLoader imageLoader_content;

    private DisplayImageOptions options_cotnent; // 设置图片显示相关参数

    public void setData(List<Dt_dataEntity> entities_) {
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

    @Override
    public Object getItem(int position) {
        if (entities_ != null) {
            return entities_.get(position);
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
         * imageUrl 图片的URL地址 imageView 承载图片的ImageView控件 options
         * DisplayImageOptions配置文件
         */

        imageLoader_head.displayImage(entity.user.head_img, viewHolder.image_head, options_head);

        // TextView设置文本
        final Dt_dataEntity entity = entities_.get(position);
        String img = entity.getThumb_img();
        if (!TextUtils.isEmpty(img)) {
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

    private SharUtil mShareUtil;

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

}
