
package com.jishang.bimeng.ui.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jishang.bimeng.BimmoApplication;
import com.jishang.bimeng.R;
import com.jishang.bimeng.entity.yuezhan.fq.wfq.Wfq_DataEntity;
import com.jishang.bimeng.utils.SharUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * 我发起的"开黑"
 * 
 * @author wangliang Jul 15, 2016
 */
public class AdapterMyLaunchBallGames extends BaseExpandableListAdapter {
    protected ImageLoader imageLoader_head;

    private BimmoApplication application = BimmoApplication.getApplication();

    private DisplayImageOptions options_head; // 设置图片显示相关参数

    private Map<String, List<Wfq_DataEntity>> map = null;

    Context context;

    private List<Wfq_DataEntity> entities_wfq;

    String ballGameNum = "";

    public AdapterMyLaunchBallGames(Context context, IModelMyLaunchBallGames model,
            String ballGameNum) {
        this.context = context;
        this.model = model;
        this.ballGameNum = ballGameNum;
        imageLoader_head = ImageLoader.getInstance();
        options_head = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ic_stub) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.ic_empty) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.ic_error) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(10)) // 设置成圆角图片
                .build(); // 构建完成
        parents = new ArrayList<String>();
        parents.add("我发起的开黑");
        parents.add("我曾经发起的开黑");

        map = new HashMap<String, List<Wfq_DataEntity>>();

    }

    private List<String> parents = null;

    public void setDataWfq(List<Wfq_DataEntity> entities_wfq) {
        map.put("我发起的开黑", entities_wfq);
        notifyDataSetChanged();

    }

    public void setDataWcjfq(List<Wfq_DataEntity> entities_wcjfq) {
        map.put("我曾经发起的开黑", entities_wcjfq);
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        if (parents != null) {
            return parents.size();
        }
        return 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (parents != null) {
            String key = parents.get(groupPosition);
            List<Wfq_DataEntity> listEntities = map.get(key);
            if (listEntities != null) {
                return listEntities.size();
            }
        }
        return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        if (parents != null) {
            return parents.get(groupPosition);
        }
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if (parents != null) {
            String key = parents.get(groupPosition);
            List<Wfq_DataEntity> listEntities = map.get(key);
            return listEntities.get(childPosition);
        }
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @SuppressLint("CutPasteId")
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
            ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.layout_parent, null);
            viewHolder = new ViewHolder();
            viewHolder.mTv_yz_title = (TextView)convertView.findViewById(R.id.parent_textview);
            viewHolder.mTv_hongdian = (TextView)convertView.findViewById(R.id.parent_hongdian);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.mTv_yz_title.setText(parents.get(groupPosition));
        if (viewHolder.mTv_yz_title.getText().equals("我发起的开黑")) {
            String num = new SharUtil(context).getList();
            if (ballGameNum == null || ballGameNum.compareTo(num) == 0) {
                viewHolder.mTv_hongdian.setVisibility(View.GONE);
            } else {
                new SharUtil(context).setList(ballGameNum);
                viewHolder.mTv_hongdian.setVisibility(View.VISIBLE);
            }
        } else {
            viewHolder.mTv_hongdian.setVisibility(View.GONE);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
            View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.fqlist_item, null);
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
            viewHolder.mTv_begin = (TextView)convertView.findViewById(R.id.yzlist_item_begin);
            viewHolder.mTv_cancel = (TextView)convertView.findViewById(R.id.yzlist_item_cancel);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        String key = parents.get(groupPosition);
        final Wfq_DataEntity entity = map.get(key).get(childPosition);
        if (key.equals("我发起的开黑")) {
            viewHolder.mTv_begin.setText("开始游戏");
            viewHolder.mTv_cancel.setText("查看参与人");
            if (entity.getPay_get().equals("0")) {
                viewHolder.mTv_begin.setBackgroundResource(R.drawable.login_ok);
            } else if (entity.getPay_get().equals("1")) {
                viewHolder.mTv_begin.setBackgroundResource(R.drawable.login_bg_user);
            }
            viewHolder.mTv_cancel.setBackgroundResource(R.drawable.login_ok);
            viewHolder.mTv_need_persons.setTextColor(application.getResources().getColor(
                    R.color.red));
        } else if (key.equals("我曾经发起的开黑")) {
            viewHolder.mTv_begin.setBackgroundResource(R.drawable.login_bg_user);
            viewHolder.mTv_cancel.setBackgroundResource(R.drawable.login_bg_user);
            viewHolder.mTv_cancel.setText("评论开黑");
            viewHolder.mTv_begin.setText("举报发起人");
            viewHolder.mTv_need_persons.setTextColor(application.getResources().getColor(
                    R.color.hui_text));
        }
        if ((entity.getPay_get()).equals("0")) {
            viewHolder.mTv_money_name.setText("人民币:  ");
            viewHolder.mTv_peple_money.setText("-" + entity.getPay_peple_money());
            viewHolder.mTv_peple_money.setTextColor(context.getResources().getColor(R.color.green));
        } else {
            viewHolder.mTv_money_name.setText("人民币:  ");
            viewHolder.mTv_peple_money.setText("+" + entity.getPay_peple_money());
            viewHolder.mTv_peple_money.setTextColor(context.getResources().getColor(R.color.red));
        }

        viewHolder.mTv_yz_title.setText("游戏名:  " + entity.getYz_title());
        viewHolder.mTv_need_persons.setText("玩家数:  " + entity.getNeed_peple_item() + "/"
                + entity.getNeed_persons());
        viewHolder.mTv_server.setText("服务器:  " + entity.getYz_server());

        viewHolder.mTv_yz_name.setText(entity.getUser().getUsername());
        viewHolder.mTv_start_time.setText("开始时:  " + times(entity.getStart_time()));
        imageLoader_head.displayImage(entity.getUser().getHead_img(), viewHolder.image,
                options_head);
        final String str_begin = viewHolder.mTv_begin.getText().toString().trim();
        viewHolder.mTv_begin.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                model.setOnClickListener(entity, str_begin);
            }
        });
        final String str_cancel = viewHolder.mTv_cancel.getText().toString().trim();
        viewHolder.mTv_cancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                model.setOnClickListener(entity, str_cancel);
            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public class ViewHolder {
        public TextView mTv_yz_title, mTv_need_persons, mTv_server, mTv_peple_money,
                mTv_money_name, mTv_yz_name, mTv_start_time, mTv_location, mTv_begin, mTv_cancel,
                mTv_hongdian;

        private ImageView image;
    }

    /**
     * @param time
     * @return 格式化时间
     */
    public String times(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("MM-dd-HH:mm");
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;

    }

    public interface IModelMyLaunchBallGames {
        void setOnClickListener(Wfq_DataEntity entity, String type);
    }

    private IModelMyLaunchBallGames model;
}
