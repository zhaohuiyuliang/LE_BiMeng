
package com.jishang.bimeng.activity.yuezhan.fq;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
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
import com.jishang.bimeng.activity.base.BaseActivity;
import com.jishang.bimeng.entity.yuezhan.fq.wfq.fenqian.FenqianEntity;
import com.jishang.bimeng.entity.yuezhan.fq.wfq.fenqian.Fenqian_confimEntity;
import com.jishang.bimeng.entity.yuezhan.fq.wfq.fenqian.Fenqian_dataEntity;
import com.jishang.bimeng.entity.yuezhan.fq.wfq.fenqian.Fenqian_data_juEntity;
import com.jishang.bimeng.ui.UIHandler;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.ToastUtil;
import com.jishang.bimeng.utils.Internet.MyHttpRequest;
import com.jishang.bimeng.utils.dialog.DialogUtils;
import com.jishang.bimeng.utils.url.UrlUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class FenqianActivity extends BaseActivity {
    private Gson mGson;

    private Context mContext;

    private List<BasicNameValuePair> params;

    private String token;

    private Intent mItent;

    private List<Fenqian_dataEntity> data = new ArrayList<Fenqian_dataEntity>();

    private ListView mList;

    private ItemListAdapter adapter = null;

    protected ImageLoader imageLoader_head;

    private DisplayImageOptions options_head; // 设置图片显示相关参数

    @Override
    public int initResource() {
        return R.layout.activity_fenqian;
    }

    @Override
    public void initView() {
        uiHandler = new FenqianHandler(this);
        mList = (ListView)findViewById(R.id.activity_fenqian_list);
        params = new ArrayList<BasicNameValuePair>();
        mContext = this;
        mGson = new Gson();
        mItent = getIntent();
        token = new SharUtil(mContext).getAccess_token();
        FenqianEntity entity = (FenqianEntity)mItent.getSerializableExtra("entity");
        data = entity.getData();
        adapter = new ItemListAdapter();
        mList.setAdapter(adapter);
        imageLoader_head = ImageLoader.getInstance();
        options_head = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ic_stub) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.ic_empty) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.ic_error) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(10)) // 设置成圆角图片
                .build(); // 构建完成
    }

    @Override
    public void initData() {

    }

    @Override
    public void addListener() {

    }

    private class FenqianHandler extends UIHandler {
        FenqianActivity fenqianActivity = (FenqianActivity)getActivity();

        public FenqianHandler(Activity activity) {
            super(activity);
        }

        public void onMessage(android.os.Message msg) {
            DialogUtils.getInstance().cancel();
            switch (msg.what) {
                case REQUEST_PAY_FAILED:
                    String erro = (String)msg.obj;
                    ToastUtil.Toast(fenqianActivity, erro);

                    break;
                case REQUEST_PAY_SUCCESS:
                    ToastUtil.Toast(fenqianActivity, "支付成功！");
                    adapter.notifyDataSetChanged();
                    break;
                default:
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = View.inflate(mContext, R.layout.yzfenqian_list_item, null);
                viewHolder = new ViewHolder();
                viewHolder.mTv_name = (TextView)convertView
                        .findViewById(R.id.yzfenqian_list_item_name);
                viewHolder.yzfenqian_list_item_confirm = (TextView)convertView
                        .findViewById(R.id.yzfenqian_list_item_confirm);
                viewHolder.image = (ImageView)convertView
                        .findViewById(R.id.content_item_username_imgv_headimg);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder)convertView.getTag();
            }
            final Fenqian_dataEntity fenqian_dataEntity = data.get(position);
            final Fenqian_data_juEntity entity = fenqian_dataEntity.getJoin_user();
            viewHolder.mTv_name.setText(entity.getUsername());
            imageLoader_head.displayImage(entity.getHead_img(), viewHolder.image, options_head);
            if (data.get(position).getMoney_status().compareTo("0") == 0) {
                viewHolder.yzfenqian_list_item_confirm.setText("确认");
                viewHolder.yzfenqian_list_item_confirm.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        payRequest(fenqian_dataEntity);
                    }
                });
            } else if (data.get(position).getMoney_status().compareTo("1") == 0) {
                viewHolder.yzfenqian_list_item_confirm.setText("已确认");
                viewHolder.yzfenqian_list_item_confirm.setOnClickListener(null);
            }

            return convertView;
        }

        /**
         * 支付
         * 
         * @param promoter_uid
         * @param join_peple_uid
         * @param yuezhan_id
         */
        public void payRequest(final Fenqian_dataEntity fenqian_dataEntity) {
            DialogUtils.getInstance().createNotifier(mContext, "正在加载中");
            String promoter_uid = fenqian_dataEntity.getPromoter_uid();
            String join_peple_uid = fenqian_dataEntity.getJoin_peple_uid();
            String yuezhan_id = fenqian_dataEntity.getYuezhan_id();
            params.add(new BasicNameValuePair("promoter_uid", promoter_uid));
            params.add(new BasicNameValuePair("join_peple_uid", join_peple_uid));
            params.add(new BasicNameValuePair("yuezhan_id", yuezhan_id));
            new Thread() {
                public void run() {
                    String url = UrlUtils.BASEURL + "v1/yz/start_game_confirm.json";
                    String result = MyHttpRequest.getHttpPostBasic(url, params, token);
                    if (result != null) {

                        try {
                            Fenqian_confimEntity entity = mGson.fromJson(result,
                                    Fenqian_confimEntity.class);
                            if (entity.getStatus() == 0) {
                                Message msg = new Message();
                                msg.what = UIHandler.REQUEST_PAY_FAILED;
                                msg.obj = entity.getErrors();
                                uiHandler.sendMessage(msg);
                            } else {
                                fenqian_dataEntity.setMoney_status("1");
                                uiHandler.sendEmptyMessage(UIHandler.REQUEST_PAY_SUCCESS);
                            }
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                        }

                    }
                };
            }.start();
        }

        public class ViewHolder {
            public TextView mTv_yz_title, mTv_need_persons, mTv_server, mTv_peple_money,
                    mTv_money_name, mTv_yz_name, mTv_start_time, mTv_location,
                    yzfenqian_list_item_confirm, mTv_cancel, mTv_name;

            private ImageView image;
        }

    }

    public void back(View v) {
        finish();

    }
}
