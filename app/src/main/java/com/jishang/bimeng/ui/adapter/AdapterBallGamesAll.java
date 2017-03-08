
package com.jishang.bimeng.ui.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jishang.bimeng.R;
import com.jishang.bimeng.entity.yuezhan.yzlist.List_dataEntity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * 所有的 约战/开黑 列表显示 适配器
 * 
 * @author wangliang Jul 15, 2016
 */
public class AdapterBallGamesAll extends BaseAdapter {
    List<List_dataEntity> entities_;

    Context context;

    public AdapterBallGamesAll(Context contenxt, List<List_dataEntity> entities,
            IModelBallGames model) {
        this.model = model;
        this.entities_ = entities;
        this.context = contenxt;
        imageLoader_head = ImageLoader.getInstance();
        options_head = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ic_stub) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.ic_empty) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.ic_error) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(5)) // 设置成圆角图片
                .build(); // 构建完成
    }

    public void setData(List<List_dataEntity> entities) {
        this.entities_ = entities;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (entities_ == null) {
            return 0;
        }
        return entities_.size();
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
            convertView = View.inflate(context, R.layout.yzlist_item, null);
            viewHolder = new ViewHolder();

            viewHolder.mTv_yz_title = (TextView)convertView
                    .findViewById(R.id.yzlist_item_tv_yz_title);
            viewHolder.mTv_need_persons = (TextView)convertView
                    .findViewById(R.id.yzlist_item_tv_need_persons);
            viewHolder.mTv_server = (TextView)convertView
                    .findViewById(R.id.yzlist_item_tv_yz_server);
            viewHolder.mTv_peple_money = (TextView)convertView
                    .findViewById(R.id.yzlist_item_tv_pay_peple_money);
            viewHolder.mTv_yz_name = (TextView)convertView
                    .findViewById(R.id.yzlist_item_tv_yz_name);
            viewHolder.mTv_start_time = (TextView)convertView
                    .findViewById(R.id.yzlist_item_tv_start_time);
            viewHolder.image = (ImageView)convertView.findViewById(R.id.img_launch_ball_games_head);
            viewHolder.mTv_money_name = (TextView)convertView
                    .findViewById(R.id.yzlist_item_tv_pay_peple_money_name);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        final List_dataEntity entity = entities_.get(position);
        if ((entity.getPay_get()).equals("0")) {
            viewHolder.mTv_peple_money.setText("人民币:  " + "+" + entity.getPay_peple_money());
        } else {
            viewHolder.mTv_peple_money.setText("人民币:  " + "-" + entity.getPay_peple_money());
        }

        viewHolder.mTv_yz_title.setText("游戏名:  " + entity.getYz_title());
        viewHolder.mTv_need_persons.setText("玩家数:  " + entity.getNeed_peple_item() + "/"
                + entity.getNeed_persons());
        viewHolder.mTv_server.setText("服务器:  " + entity.getYz_server());

        viewHolder.mTv_yz_name.setText("发起人:  " + entity.getUsername());
        viewHolder.mTv_start_time.setText("开始时:  " + times(entity.getStart_time()));

        imageLoader_head.displayImage(entity.getHead_img(), viewHolder.image, options_head);
        convertView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                model.setOnClickListener(entity);
            }
        });
        return convertView;
    }

    public class ViewHolder {
        public TextView mTv_yz_title, mTv_need_persons, mTv_server, mTv_peple_money,
                mTv_money_name, mTv_yz_name, mTv_start_time, mTv_location, mTv_name_;

        private ImageView image;
    }

    public String times(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("MM-dd-HH:mm");
        @SuppressWarnings("unused")
        // long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;

    }

    IModelBallGames model;

    public interface IModelBallGames {
        void setOnClickListener(List_dataEntity entity);
    }

    private DisplayImageOptions options_head; // 设置图片显示相关参数

    protected ImageLoader imageLoader_head;

    public List<List_dataEntity> getData() {
        return entities_;
    }

}
