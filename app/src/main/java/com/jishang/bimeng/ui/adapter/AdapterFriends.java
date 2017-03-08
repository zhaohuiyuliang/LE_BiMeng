
package com.jishang.bimeng.ui.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jishang.bimeng.R;
import com.jishang.bimeng.entity.chat.Fdlist.FriendEntity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * 好友列表适配器
 * 
 * @author wangliang Jul 14, 2016
 */
public class AdapterFriends extends BaseAdapter {
    private Context context;

    protected ImageLoader imageLoader_head;

    private DisplayImageOptions options_head; // 设置图片显示相关参数

    private String[] imageUrls; // 图片路径

    private List<FriendEntity> entities;

    public AdapterFriends(Context context, IModelFriends model) {
        this.context = context;
        this.model = model;
        imageLoader_head = ImageLoader.getInstance();
        options_head = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ic_stub) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.ic_empty) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.ic_error) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(10)) // 设置成圆角图片
                .build(); // 构建完成
    }

    public void setData(List<FriendEntity> entities) {
        this.entities = entities;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (entities != null) {
            return entities.size();
        }
        return 0;
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
            convertView = View.inflate(context, R.layout.fdlist_item, null);
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
        imageLoader_head.displayImage(entity.getHead_img(), viewHolder.image, options_head);
        viewHolder.text.setText(entity.getUsername());
        convertView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                model.setOnClickListener(entity);
            }
        });
        return convertView;

    }

    public class ViewHolder {
        public TextView text;

        private ImageView image;
    }

    private IModelFriends model;

    public interface IModelFriends {
        void setOnClickListener(FriendEntity entity);
    }

}
