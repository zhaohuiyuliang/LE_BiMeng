
package com.jishang.bimeng.activity.hxchat;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jishang.bimeng.R;
import com.jishang.bimeng.activity.base.BaseActivity;
import com.jishang.bimeng.entity.chat.chaitghis.Chaiths_dataEntity;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.db.PersonEntity;
import com.jishang.bimeng.utils.db.SQLOperateImpl;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class PeopleActivity extends BaseActivity {
    private ImageView mImg_head;

    private TextView mTv_name;

    private Intent intent;

    private Chaiths_dataEntity entity = new Chaiths_dataEntity();

    protected ImageLoader imageLoader_head;

    private DisplayImageOptions options_head; // 设置图片显示相关参数

    private Context mContext;

    private List<Chaiths_dataEntity> entities = new ArrayList<Chaiths_dataEntity>();

    private Gson mGson;

    private SharUtil mShare;

    @Override
    public int initResource() {
        return R.layout.activity_people;
    }

    @Override
    public void initView() {
        mImg_head = (ImageView)findViewById(R.id.activity_people_headimg);
        mTv_name = (TextView)findViewById(R.id.activity_people_username);
        intent = getIntent();
        entity = (Chaiths_dataEntity)intent.getSerializableExtra("entity");
        mContext = this;
        mShare = new SharUtil(mContext);
        entities.add(entity);
        imageLoader_head = ImageLoader.getInstance();
        options_head = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ic_stub) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.ic_empty) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.ic_error) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(10)) // 设置成圆角图片
                .build(); // 构建完成
        mGson = new Gson();

    }

    @Override
    public void initData() {
        imageLoader_head.displayImage(entity.getHead_img(), mImg_head, options_head);
        mTv_name.setText(entity.getUsername());

    }

    @Override
    public void addListener() {

    }

    public void back(View v) {

        SQLOperateImpl sql = new SQLOperateImpl(mContext);
        PersonEntity p = new PersonEntity();
        List<PersonEntity> enties = sql.find();
        List<String> ids = new ArrayList<String>();
        Log.e("-----", sql.find().size() + "===");

        if (enties.isEmpty()) {
            p.setHuanid(entity.getH_username());
            p.setImg(entity.getHead_img());
            p.setName(entity.getUsername());
            Log.e("111", "111");
            sql.insert(p);
        } else {
            Log.e("hh", entity.getH_username());

            // if(sql.findById(entity.getH_username())==null){
            // p.setHuanid(entity.getH_username());
            // p.setImg(entity.getHead_img());
            // p.setName(entity.getUsername());
            // Log.e("sdasd", "222");
            // sql.add(p);
            // }
            for (int i = 0; i < enties.size(); i++) {

                ids.add(enties.get(i).getHuanid());

                /*
                 * else { p.setHuanid(entity.getH_username());
                 * p.setImg(entity.getHead_img());
                 * p.setName(entity.getUsername()); Log.e("333", "333");
                 * sql.add(p); }
                 */
            }

            if (!ids.contains(entity.getH_username())) {
                p.setHuanid(entity.getH_username());
                p.setImg(entity.getHead_img());
                p.setName(entity.getUsername());
                Log.e("kk", "kk");
                sql.insert(p);
            }
        }

        Intent intent = new Intent(mContext, ActivityChatHistory.class);
        intent.putExtra("imaghead", entity.getHead_img());
        setResult(200, intent);
        finish();
    }

}
