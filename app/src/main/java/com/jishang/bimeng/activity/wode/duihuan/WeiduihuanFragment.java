
package com.jishang.bimeng.activity.wode.duihuan;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jishang.bimeng.R;
import com.jishang.bimeng.entity.wode.wzf.WdhEntity;
import com.jishang.bimeng.entity.wode.wzf.Wdh_dataBuEntity;
import com.jishang.bimeng.entity.wode.wzf.Wdh_dataEntity;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.Internet.MyHttpRequest;
import com.jishang.bimeng.utils.dialog.DialogUtils;
import com.jishang.bimeng.utils.url.UrlUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class WeiduihuanFragment extends Fragment {
    private ListView mList;

    private Gson mGson;

    private Context mContext;

    private List<BasicNameValuePair> params;

    private String token;

    protected ImageLoader imageLoader_head;

    private DisplayImageOptions options_head; // 设置图片显示相关参数

    private List<Wdh_dataEntity> data = new ArrayList<Wdh_dataEntity>();

    private ItemListAdapter adapter;

    private View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_weiduihuan, null);
        mContext = getActivity();
        initView(v);
        getMsg();
        return v;
    }

    public void initView(View v) {
        mList = (ListView)v.findViewById(R.id.activity_yiduihuan_list);
        params = new ArrayList<BasicNameValuePair>();
        mGson = new Gson();
        token = new SharUtil(mContext).getAccess_token();

        imageLoader_head = ImageLoader.getInstance();
        options_head = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ic_stub) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.ic_empty) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.ic_error) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(10)) // 设置成圆角图片
                .build(); // 构建完成
        adapter = new ItemListAdapter();
        mList.setAdapter(adapter);

    }

    public void getMsg() {

        DialogUtils.getInstance().createNotifier(mContext, "正在加载中");
        params.add(new BasicNameValuePair("1", "1"));
        new Thread() {
            public void run() {
                String url = UrlUtils.BASEURL + "v1/busine/my_redeem_code_no.json";
                String resusult = MyHttpRequest.getHttpPostBasic(url, params, token);
                Log.e("result_wdh", resusult.toString());
                if (resusult != null) {
                    try {
                        WdhEntity entity = mGson.fromJson(resusult, WdhEntity.class);
                        data = entity.getData();
                        if (entity.getStatus() == 0) {

                        } else if (entity.getStatus() == 1) {
                            handler.sendEmptyMessage(1);
                        }
                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                        handler.sendEmptyMessage(3);

                    }

                }
            };
        }.start();
    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            DialogUtils.getInstance().cancel();
            switch (msg.what) {
                case 0:

                    break;
                case 1:
                    adapter.notifyDataSetChanged();
                    break;
                case 2:
                    break;
                case 3:
                    break;

            }
        };
    };

    public class ItemListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (data == null) {
                return 0;
            }
            return data.size();
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
            ViewHolder1 viewHolder = null;
            if (convertView == null) {
                convertView = View.inflate(mContext, R.layout.wdh_item, null);
                viewHolder = new ViewHolder1();
                viewHolder.image = (ImageView)convertView.findViewById(R.id.shangcheng_item_imgv);
                viewHolder.mTv_name = (TextView)convertView
                        .findViewById(R.id.shangcheng_item_tv_name);
                viewHolder.mTv_price = (TextView)convertView
                        .findViewById(R.id.shangcheng_item_tv_price);
                viewHolder.mTv_barname = (TextView)convertView.findViewById(R.id.shancheng_barname);
                viewHolder.mTv_detail = (TextView)convertView.findViewById(R.id.shancheng_detail);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder1)convertView.getTag();
            }

            Wdh_dataBuEntity entity = data.get(position).getBusiness();
            viewHolder.mTv_name.setText(entity.getName());
            viewHolder.mTv_price.setText("￥" + entity.getPrice());
            imageLoader_head.displayImage(entity.getBs_img(), viewHolder.image, options_head);
            viewHolder.mTv_barname.setText("消费于:" + entity.getProvince().getS_provname()
                    + entity.getCity().getS_cityname() + entity.getWangba().getW_name());
            Wdh_dataEntity entity1 = data.get(position);
            viewHolder.mTv_detail.setText("兑换码:" + entity1.getRedeem_code());
            return convertView;
        }

    }

    public class ViewHolder1 {
        public TextView mTv_name, mTv_price, mTv_barname, mTv_detail;

        private ImageView image;
    }
}
