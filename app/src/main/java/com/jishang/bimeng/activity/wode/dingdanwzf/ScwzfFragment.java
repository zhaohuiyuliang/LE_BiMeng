
package com.jishang.bimeng.activity.wode.dingdanwzf;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jishang.bimeng.R;
import com.jishang.bimeng.activity.zhifu.shangcheng.Sc_ZfListActivity;
import com.jishang.bimeng.entity.shangcheng.goumai.GoumaiDataEntity;
import com.jishang.bimeng.entity.wode.scwzf.ScwzfEntity;
import com.jishang.bimeng.entity.wode.scwzf.Scwzf_dataEntity;
import com.jishang.bimeng.entity.yuezhan.wzf.CancelEntity;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.ToastUtil;
import com.jishang.bimeng.utils.Internet.MyHttpRequest;
import com.jishang.bimeng.utils.url.UrlUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class ScwzfFragment extends Fragment {
    private View v;

    private Gson mGson;

    private Context mContext;

    private List<BasicNameValuePair> params;

    private String token;

    protected ImageLoader imageLoader_head;

    private DisplayImageOptions options_head; // 设置图片显示相关参数

    private List<Scwzf_dataEntity> entities;

    private ItemListAdapter adtapter;

    private ListView mList;

    GoumaiDataEntity entity1 = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_scwzf, null);
        mContext = getActivity();
        initView(v);
        getMsg();
        return v;
    }

    public void initView(View v) {
        params = new ArrayList<BasicNameValuePair>();
        adtapter = new ItemListAdapter();
        mGson = new Gson();
        token = new SharUtil(mContext).getAccess_token();
        mList = (ListView)v.findViewById(R.id.activity_scwzf_list);
        imageLoader_head = ImageLoader.getInstance();
        options_head = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ic_stub) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.ic_empty) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.ic_error) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(10)) // 设置成圆角图片
                .build(); // 构建完成

        mList.setAdapter(adtapter);

    }

    public void getMsg() {
        params.add(new BasicNameValuePair("1", "1"));
        new Thread() {
            public void run() {
                String url = UrlUtils.BASEURL + "v1/busine/my_b_pay_no.json";
                String resusult = MyHttpRequest.getHttpPostBasic(url, params, token);
                Log.e("result", resusult.toString());
                if (resusult != null) {
                    Log.e("result", resusult.toString());
                    try {
                        ScwzfEntity entity = mGson.fromJson(resusult, ScwzfEntity.class);
                        entities = entity.getData();
                        handler.sendEmptyMessage(0);
                        // Log.e("entity", entity.toString());
                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                    }

                }
            };
        }.start();
    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    adtapter.notifyDataSetChanged();
                    break;
                case 1:
                    ToastUtil.Toast(mContext, "取消成功");
                    break;

            }
        };
    };

    public class ItemListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (entities == null) {
                return 0;
            }
            return entities.size();
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
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = View.inflate(mContext, R.layout.scwzflist_item, null);
                viewHolder = new ViewHolder();

                viewHolder.image = (ImageView)convertView.findViewById(R.id.shangcheng_item_imgv);
                viewHolder.mTv_name = (TextView)convertView
                        .findViewById(R.id.shangcheng_item_tv_name);
                viewHolder.mTv_price = (TextView)convertView
                        .findViewById(R.id.shangcheng_item_tv_price);
                viewHolder.mTv_barname = (TextView)convertView.findViewById(R.id.shancheng_barname);
                viewHolder.mTv_detail = (TextView)convertView.findViewById(R.id.shancheng_detail);
                viewHolder.mTv_begin = (TextView)convertView.findViewById(R.id.yzlist_item_begin);
                viewHolder.mTv_cancel = (TextView)convertView.findViewById(R.id.yzlist_item_cancel);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder)convertView.getTag();
            }
            final Scwzf_dataEntity entity = entities.get(position);

            viewHolder.mTv_name.setText(entity.getBusiness().getName());
            viewHolder.mTv_price.setText("￥" + entity.getBusiness().getPrice());
            imageLoader_head.displayImage(entity.getBusiness().getBs_img(), viewHolder.image,
                    options_head);
            viewHolder.mTv_barname.setText("消费于:");
            viewHolder.mTv_detail.setText(entity.getBusiness().getBs_describe());
            setEntity(entity);
            viewHolder.mTv_begin.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, Sc_ZfListActivity.class);
                    intent.putExtra("entity", entity1);
                    intent.putExtra("back", "");
                    startActivity(intent);

                }
            });
            viewHolder.mTv_cancel.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    cancel(entity);

                }
            });

            return convertView;
        }
    }

    public void cancel(Scwzf_dataEntity entity) {
        params.add(new BasicNameValuePair("bp_id", entity.getBp_id()));
        params.add(new BasicNameValuePair("bp_uid", entity.getBp_uid()));
        params.add(new BasicNameValuePair("bp_order_notice", entity.getBp_order_notice()));
        Log.e("--",
                entity.getBp_id() + " " + entity.getBp_uid() + "  " + entity.getBp_order_notice());
        new Thread() {
            public void run() {
                String url = UrlUtils.BASEURL + "v1/busine/off_b_payment.json";
                String result = MyHttpRequest.getHttpPostBasic(url, params, token);
                if (result != null) {
                    // Log.e("result", result.toString());
                    CancelEntity entity = mGson.fromJson(result, CancelEntity.class);
                    if (entity.getStatus() == 1) {
                        handler.sendEmptyMessage(1);
                    }
                }
            };
        }.start();
    }

    /**
     * @param data 初始化订单信息
     */
    public void setEntity(Scwzf_dataEntity entity) {
        entity1 = new GoumaiDataEntity();
        entity1.setBp_id(entity.getBp_id());
        entity1.setBp_uid(entity.getBp_uid());
        entity1.setBp_order_notice(entity.getBp_order_notice());
        entity1.setName(entity.getBusiness().getName());
        entity1.setMoney(entity.getBusiness().getPrice());
        entity1.setBs_describe(entity.getBusiness().getBs_describe());

    }

    public class ViewHolder {
        public TextView mTv_name, mTv_price, mTv_barname, mTv_detail, mTv_begin, mTv_cancel;

        private ImageView image;
    }

    public String times(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("MM月dd日HH时mm分");
        @SuppressWarnings("unused")
        // long lcc = Long.valueOf(time);Sc_wzfActivity
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;

    }

}
