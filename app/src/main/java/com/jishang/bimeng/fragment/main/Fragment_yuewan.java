
package com.jishang.bimeng.fragment.main;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jishang.bimeng.R;
import com.jishang.bimeng.activity.TestActivity;
import com.jishang.bimeng.activity.yuewan.Yw_detailActivity;
import com.jishang.bimeng.entity.yuewan.list.YwListEntity;
import com.jishang.bimeng.entity.yuewan.list.YwList_dataEntity;
import com.jishang.bimeng.fragment.base.BaseFragment;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.Internet.MyHttpRequest;
import com.jishang.bimeng.utils.dialog.DialogUtils;
import com.jishang.bimeng.utils.url.UrlUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class Fragment_yuewan extends BaseFragment implements OnClickListener {
    private GridView mGv;

    private String token;

    private List<BasicNameValuePair> params;

    private Gson mGson;

    protected ImageLoader imageLoader_head;

    private DisplayImageOptions options_head; // 设置图片显示相关参数

    private List<YwList_dataEntity> data = new ArrayList<YwList_dataEntity>();

    private Mygrid adapter;

    private RelativeLayout mRl_test;

    private Context mContext;

    @Override
    public View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_yuewan, null);

        initView(view);
        mContext = getActivity();
        getMsg();
        addListener();
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    public void initView(View view) {
        mGv = (GridView)view.findViewById(R.id.activity_ywlist_gv);
        mRl_test = (RelativeLayout)view.findViewById(R.id.fragment_yuewan_test);

        token = new SharUtil(getActivity()).getAccess_token();
        mGson = new Gson();
        params = new ArrayList<BasicNameValuePair>();

        options_head = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ic_stub) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.ic_empty) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.ic_error) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(10)) // 设置成圆角图片
                .build(); // 构建完成
        imageLoader_head = ImageLoader.getInstance();
        adapter = new Mygrid();
        mGv.setAdapter(adapter);

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
                    // finish();
                    break;
                case 3:
                    break;

            }
        };
    };

    public void getMsg() {
        DialogUtils.getInstance().createNotifier(mContext, "正在加载中..");
        params.add(new BasicNameValuePair("1", "1"));
        new Thread() {
            public void run() {
                String url = UrlUtils.BASEURL + "v1/user_details/list.json";
                String result = MyHttpRequest.getHttpPostBasic(url, params, token);
                if (result != null) {
                    Log.e("result", result.toString());
                    try {
                        YwListEntity entity = mGson.fromJson(result, YwListEntity.class);
                        data = entity.getData();
                        // Log.e("entity-----", entity.toString());
                        if (entity.getStatus() == 1) {
                            handler.sendEmptyMessage(1);
                        }
                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                    }

                }
            };
        }.start();

    }

    class Mygrid extends BaseAdapter {

        @Override
        public int getCount() {
            if (data.size() == 0) {
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
            ViewHolder vh = null;
            if (convertView == null) {
                vh = new ViewHolder();
                convertView = View.inflate(getActivity(), R.layout.activity_yzlist_item, null);
                vh.img = (ImageView)convertView.findViewById(R.id.activity_yzlist_item_headimg);
                vh.mTv_sex = (TextView)convertView.findViewById(R.id.activity_yzlist_item_sex);
                vh.mTv_name = (TextView)convertView.findViewById(R.id.activity_yzlist_item_name);
                vh.mTv_qianming = (TextView)convertView
                        .findViewById(R.id.activity_yzlist_item_qianming);
                vh.mTv_location = (TextView)convertView
                        .findViewById(R.id.activity_yzlist_item_loacation);
                vh.mTv_hyd = (TextView)convertView.findViewById(R.id.activity_yzlist_item_huoyuedu);

                convertView.setTag(vh);
            } else {
                vh = (ViewHolder)convertView.getTag();
            }
            YwList_dataEntity entity = data.get(position);
            vh.mTv_name.setText(entity.getUsername());
            vh.mTv_hyd.setText("活跃度:" + entity.getU_integral());
            vh.mTv_qianming.setText("签名:" + entity.getDescribetion_info());
            vh.mTv_location.setText("主店:" + entity.getProvince() + entity.getCity()
                    + entity.getBusiness());
            if (entity.getSex().equals("1")) {
                vh.mTv_sex.setText("男");
            } else if (entity.getSex().equals("0")) {
                vh.mTv_sex.setText("女");
            }
            imageLoader_head.displayImage(entity.getHead_img(), vh.img, options_head);
            return convertView;
        }

    }

    public void addListener() {
        mRl_test.setOnClickListener(this);
        mGv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                YwList_dataEntity entity = data.get(position);
                Intent intent = new Intent(getActivity(), Yw_detailActivity.class);
                intent.putExtra("entity", entity);
                startActivity(intent);

            }
        });

    }

    public class ViewHolder {
        TextView mTv_hyd, mTv_name, mTv_sex, mTv_location, mTv_qianming;

        ImageView img;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_yuewan_test:
                Intent intent = new Intent(getActivity(), TestActivity.class);
                startActivity(intent);
                break;
        }

    }

    @Override
    public void refreshUI() {
        // TODO Auto-generated method stub

    }

}
