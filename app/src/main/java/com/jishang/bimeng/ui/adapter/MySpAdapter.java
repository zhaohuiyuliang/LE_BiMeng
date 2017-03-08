
package com.jishang.bimeng.ui.adapter;

import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jishang.bimeng.BimmoApplication;
import com.jishang.bimeng.R;

/**
 * @author kangming
 * @param <T>利用泛型制作一个万能的适配器
 */
public class MySpAdapter<T> extends BaseAdapter {
    protected List<T> mDatas;

    public MySpAdapter(List<T> mDatas) {
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

    BimmoApplication application = BimmoApplication.getApplication();

    // 向list中添加数据
    public void refreshAdapter(List<T> arrayList) {
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
        ViewHolder1 vh1 = null;
        if (convertView == null) {
            vh1 = new ViewHolder1();
            convertView = View.inflate(application, R.layout.yuezhan_sp_item, null);
            vh1.name = (TextView)convertView.findViewById(R.id.yuezhan_sp_item_name);
            convertView.setTag(vh1);
        } else {
            vh1 = (ViewHolder1)convertView.getTag();

        }
        vh1.name.setText(mDatas.get(position).toString());

        return convertView;

    }

    class ViewHolder1 {
        TextView name;
    }
}
