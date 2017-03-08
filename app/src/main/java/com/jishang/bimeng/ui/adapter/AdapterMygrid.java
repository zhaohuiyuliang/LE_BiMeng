
package com.jishang.bimeng.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jishang.bimeng.R;
import com.jishang.bimeng.entity.wode.MyDataEntity;
import com.jishang.bimeng.entity.yuezhan.yzlist.List_msgEntity;
import com.jishang.bimeng.utils.CheckNulll;
import com.jishang.bimeng.utils.SharUtil;

/**
 * "我的"UI 适配器
 * 
 * @author wangliang Jul 13, 2016
 */
public class AdapterMygrid extends BaseAdapter {
    private String[] names = {
            "红包", "钱包", "活跃度", "切换主店", "个人设置", "关于比盟", "未支付订单", "我的兑换码", "用户协议"
    };

    private int[] imgs = {
            R.drawable.wode_0_linqian, R.drawable.wode_1_shurubao, R.drawable.wode_2_huoyuedu,
            R.drawable.wode_3_qiehuanzhudian, R.drawable.wode_4_gerenshezhi,
            R.drawable.wode_6_ywsz, R.drawable.wode_8_weizhifu, R.drawable.wode_5_sqpw,
            R.drawable.icon_user_agreement
    };

    private Context context;

    public AdapterMygrid(Context context, List_msgEntity entity) {
        this.context = context;
        this.entity = entity;
    }

    @Override
    public int getCount() {
        if (names != null) {
            return names.length;
        }
        return 0;
    }

    private MyDataEntity data;

    private List_msgEntity entity;

    public void setData(MyDataEntity data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holderView = null;
        if (convertView == null) {
            holderView = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_my, null);
            holderView.tv = (TextView)convertView.findViewById(R.id.wode_list_item_tv);
            holderView.img = (ImageView)convertView.findViewById(R.id.wode_list_item_imgv);
            holderView.unReadMsgnum = (TextView)convertView
                    .findViewById(R.id.wode_list_item_msg_number);
            convertView.setTag(holderView);
        } else {
            holderView = (ViewHolder)convertView.getTag();
        }

        if (position == 1) {// 钱包
            if (data == null) {
                holderView.unReadMsgnum.setVisibility(View.GONE);
            } else {
                String lingqian = new SharUtil(context).getLingqian();
                String income = data.getIncome();
                float num = Float.valueOf(income);
                if (num == 0) {
                    holderView.unReadMsgnum.setVisibility(View.GONE);
                } else if (lingqian.compareTo(income) == 0) {
                    holderView.unReadMsgnum.setVisibility(View.GONE);
                } else {
                    holderView.unReadMsgnum.setVisibility(View.VISIBLE);
                }
            }
        } else if (position == 6) {// 未支付订单
            holderView.unReadMsgnum.setVisibility(View.VISIBLE);
            String num = entity.getYz_num();
            if (TextUtils.isEmpty(num)) {
                holderView.unReadMsgnum.setVisibility(View.GONE);
            } else if (num.compareTo("0") == 0) {
                holderView.unReadMsgnum.setVisibility(View.GONE);
            } else {
                holderView.unReadMsgnum.setVisibility(View.VISIBLE);
                holderView.unReadMsgnum.setText(num);
            }
        } else if (position == 7) {// 我的兑换码
            holderView.unReadMsgnum.setVisibility(View.VISIBLE);
            String num = entity.getCode_num();
            if (CheckNulll.check(num)) {
                if (num.equals("0")) {
                    holderView.unReadMsgnum.setVisibility(View.GONE);
                } else {
                    holderView.unReadMsgnum.setVisibility(View.VISIBLE);
                    holderView.unReadMsgnum.setText(num);
                }
            } else {
                holderView.unReadMsgnum.setVisibility(View.GONE);
            }

        }

        holderView.img.setImageResource(imgs[position]);
        holderView.tv.setText(names[position]);
        return convertView;
    }

    public class ViewHolder {
        TextView tv, unReadMsgnum;

        ImageView img;
    }
}
