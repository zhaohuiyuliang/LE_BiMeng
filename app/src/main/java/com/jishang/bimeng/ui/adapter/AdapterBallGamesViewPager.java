
package com.jishang.bimeng.ui.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jishang.bimeng.R;
import com.jishang.bimeng.activity.yuezhan.lunbo.ActivityAboutBiMeng;
import com.jishang.bimeng.entity.yuezhan.yzlist.lunbo.LunBoEntity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * 开黑 Tab
 * 
 * @author wangliang Jul 13, 2016
 */
public class AdapterBallGamesViewPager extends PagerAdapter {
    private List<LunBoEntity.Data> entities;

    private Context context;

    public AdapterBallGamesViewPager(Context context, List<LunBoEntity.Data> entities) {
        this.context = context;
        this.entities = entities;
        imageLoader_head = ImageLoader.getInstance();
        options_head = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ic_stub) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.ic_empty) // 设置图片URI为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.ic_error) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(5)) // 设置成圆角图片
                .build(); // 构建完成
    }

    public void setData(List<LunBoEntity.Data> entities) {
        this.entities = entities;
        notifyDataSetChanged();
    }

    public List<LunBoEntity.Data> getDatas() {
        return entities;
    }

    @Override
    public int getCount() {
        if (entities != null) {
            return entities.size();
        }
        return 0;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ImageView iv = (ImageView)View.inflate(context, R.layout.imgage, null);
        imageLoader_head.displayImage(entities.get(position).getImg(), iv, options_head);
        container.addView(iv);
        iv.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ActivityAboutBiMeng.class);
                intent.putExtra("url", entities.get(position).getUrl());
                context.startActivity(intent);

            }
        });
        return iv;
    }

    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        ((ViewPager)arg0).removeView((View)arg2);
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void restoreState(Parcelable arg0, ClassLoader arg1) {

    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void startUpdate(View arg0) {

    }

    @Override
    public void finishUpdate(View arg0) {

    }

    private DisplayImageOptions options_head; // 设置图片显示相关参数

    protected ImageLoader imageLoader_head;

}
