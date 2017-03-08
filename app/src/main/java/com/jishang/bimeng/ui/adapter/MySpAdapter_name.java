
package com.jishang.bimeng.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jishang.bimeng.BimmoApplication;
import com.jishang.bimeng.R;
import com.jishang.bimeng.entity.yuezhan.PhotoEntity;

/**
 * 游戏名称适配器
 * 
 * @author kangming
 * @param <T>所需费用适配器
 */
public class MySpAdapter_name extends BaseAdapter {
    protected List<PhotoEntity> mDatas;

    IModelGame model;

    PhotoEntity photoEntity;

    BimmoApplication application = BimmoApplication.getApplication();

    public MySpAdapter_name(List<PhotoEntity> mDatas, IModelGame model) {
        this.mDatas = mDatas;
        this.model = model;
    }

    @Override
    public int getCount() {
        if (mDatas != null) {
            return mDatas.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (mDatas != null) {
            return mDatas.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // 向list中添加数据
    public void refreshAdapter(List<PhotoEntity> arrayList) {
        mDatas = new ArrayList<PhotoEntity>();
        mDatas.add(new PhotoEntity());
        mDatas.addAll(arrayList);
        notifyDataSetChanged();
    }

    // 清空list集合
    public void clear() {
        mDatas.clear();
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder6 vh1 = null;
        if (convertView == null) {
            vh1 = new ViewHolder6();
            convertView = View.inflate(application, R.layout.yuezhan_sp_item, null);
            vh1.name = (TextView)convertView.findViewById(R.id.yuezhan_sp_item_name);
            vh1.rLayout_ = (RelativeLayout)convertView.findViewById(R.id.rLayout_);
            convertView.setTag(vh1);
        } else {
            vh1 = (ViewHolder6)convertView.getTag();
        }
        if (position == 0) {
            vh1.name.setText("请选择游戏");
        } else {
            convertView.setVisibility(View.VISIBLE);
            vh1.rLayout_.setVisibility(View.VISIBLE);
            final PhotoEntity photoEntity = mDatas.get(position);
            vh1.name.setText(photoEntity.getGm_name());
        }
        return convertView;
    }

    class ViewHolder6 {
        TextView name;

        RelativeLayout rLayout_;
    }

    public interface IModelGame {
        void setOnClickListener(PhotoEntity photoEntity);
    }

}
