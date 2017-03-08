
package com.jishang.bimeng.ui.adapter;

import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jishang.bimeng.BimmoApplication;
import com.jishang.bimeng.R;
import com.jishang.bimeng.entity.yuezhan.cyzf.Cyzf_moneyEntity;

/**
 * @author kangming
 * @param <T>所需费用适配器
 */
public class MySpAdapter_cyfy extends BaseAdapter {
    protected List<Cyzf_moneyEntity> mDatas;

    public MySpAdapter_cyfy(List<Cyzf_moneyEntity> mDatas) {
        this.mDatas = mDatas;
    }

    @Override
    public int getCount() {
        if (mDatas.size() != 0) {
            return mDatas.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    BimmoApplication application = BimmoApplication.getApplication();

    @Override
    public long getItemId(int position) {
        return position;
    }

    // 向list中添加数据
    public void refreshAdapter(List<Cyzf_moneyEntity> arrayList) {
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
        ViewHolder3 vh1 = null;
        if (convertView == null) {
            vh1 = new ViewHolder3();
            convertView = View.inflate(application, R.layout.yuezhan_sp_item, null);
            vh1.name = (TextView)convertView.findViewById(R.id.yuezhan_sp_item_name);
            convertView.setTag(vh1);
        } else {
            vh1 = (ViewHolder3)convertView.getTag();

        }
        vh1.name.setText(mDatas.get(position).getMin_money());

        return convertView;
    }

    class ViewHolder3 {
        TextView name;
    }
}
