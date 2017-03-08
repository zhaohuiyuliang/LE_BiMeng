
package com.jishang.bimeng.ui.adapter;

import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jishang.bimeng.BimmoApplication;
import com.jishang.bimeng.R;
import com.jishang.bimeng.entity.yuezhan.cyzf.Cyzf_PpEntity;

/**
 * @author kangming
 * @param <T>开黑人数适配器
 */
public class MySpAdapter_khrs extends BaseAdapter {
    protected List<Cyzf_PpEntity> mDatas;

    public MySpAdapter_khrs(List<Cyzf_PpEntity> mDatas) {
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

    @Override
    public long getItemId(int position) {
        return position;
    }

    // 向list中添加数据
    public void refreshAdapter(List<Cyzf_PpEntity> arrayList) {
        mDatas.addAll(arrayList);
        notifyDataSetChanged();
    }

    // 清空list集合
    public void clear() {
        mDatas.clear();
        notifyDataSetChanged();
    }

    BimmoApplication application = BimmoApplication.getApplication();

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder2 vh1 = null;
        if (convertView == null) {
            vh1 = new ViewHolder2();
            convertView = View.inflate(application, R.layout.yuezhan_sp_item, null);
            vh1.name = (TextView)convertView.findViewById(R.id.yuezhan_sp_item_name);
            convertView.setTag(vh1);
        } else {
            vh1 = (ViewHolder2)convertView.getTag();

        }
        vh1.name.setText(mDatas.get(position).getTotal_peple());

        return convertView;

    }

    class ViewHolder2 {
        TextView name;
    }
}
