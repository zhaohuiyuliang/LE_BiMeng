
package com.jishang.bimeng.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.jishang.bimeng.R;
import com.jishang.bimeng.entity.yuezhan.yzlist.List_dataEntity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * 展示图片放大图
 * 
 * @author wangliang Jul 16, 2016
 */
public class ActivityImg extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img);
        Intent intent = getIntent();
        entity = (List_dataEntity)intent.getSerializableExtra("entity");
        imageLoader_head = ImageLoader.getInstance();
        options_head = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ic_stub) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.ic_empty) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.ic_error) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(10)) // 设置成圆角图片
                .build(); // 构建完成
        initView();
        initData();
    }

    protected ImageLoader imageLoader_head;

    private DisplayImageOptions options_head; // 设置图片显示相关参数

    ImageView img_head;

    List_dataEntity entity;

    private void initView() {
        ((TextView)findViewById(R.id.tv_back_to_activity)).setText(entity.getYz_title());
        ((TextView)findViewById(R.id.lianxi_activity_tv_name)).setText(entity.getUsername());
        img_head = (ImageView)findViewById(R.id.img_head);
    }

    private void initData() {
        imageLoader_head.displayImage(entity.getHead_img(), img_head, options_head);

    }
}
