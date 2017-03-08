
package com.jishang.bimeng.activity.yuezhan.cj;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jishang.bimeng.R;
import com.jishang.bimeng.activity.yuezhan.fq.Fenqian_cyActivity;
import com.jishang.bimeng.activity.yuezhan.fq.Lianxi_cyActivity;
import com.jishang.bimeng.entity.tonyong.TYEntity;
import com.jishang.bimeng.entity.yuezhan.fq.wfq.WfqEntity;
import com.jishang.bimeng.entity.yuezhan.fq.wfq.Wfq_DataEntity;
import com.jishang.bimeng.entity.yuezhan.fq.wfq.fenqian_cy.Fenqian_cyEntity;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.ToastUtil;
import com.jishang.bimeng.utils.Internet.MyHttpRequest;
import com.jishang.bimeng.utils.dialog.DialogUtils;
import com.jishang.bimeng.utils.url.UrlUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class Fragment_cy extends Fragment {

    private Gson mGson;

    private Context mContext;

    private List<BasicNameValuePair> params;

    private String token;

    protected ImageLoader imageLoader_head;

    private DisplayImageOptions options_head; // 设置图片显示相关参数

    private List<Wfq_DataEntity> entities_wcy = new ArrayList<Wfq_DataEntity>();

    private List<Wfq_DataEntity> entities_wcjcy = new ArrayList<Wfq_DataEntity>();

    private ItemListAdapter adtapter;

    private ExpandableListView mElist;

    private List<String> parents = null;

    private Map<String, List<Wfq_DataEntity>> map = null;

    private RelativeLayout mRl_pinlun;

    private Button mBt_pinlun;

    private EditText mEdt_content;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fq_yuezhan, null);
        mContext = getActivity();
        initView(view);
        initData();
        addListener();
        return view;

    }

    public void initView(View view) {
        params = new ArrayList<BasicNameValuePair>();
        parents = new ArrayList<String>();
        map = new HashMap<String, List<Wfq_DataEntity>>();
        mGson = new Gson();

        mRl_pinlun = (RelativeLayout)view.findViewById(R.id.activity_fq_yuezhan_pinlun);
        mBt_pinlun = (Button)view.findViewById(R.id.activity_fq_yuezhan_bt_pinlun);
        mEdt_content = (EditText)view.findViewById(R.id.activity_fq_yuezhan_content);
        token = new SharUtil(mContext).getAccess_token();
        imageLoader_head = ImageLoader.getInstance();
        options_head = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ic_stub) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.ic_empty) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.ic_error) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(10)) // 设置成圆角图片
                .build(); // 构建完成

        mElist = (ExpandableListView)view.findViewById(R.id.activity_fq_yuezhan_ep);

    }

    public void initData() {
        map.put("我参与的开黑", entities_wcy);
        map.put("我曾经参与的开黑", entities_wcjcy);
        getWcy();
        getWcjcy();
        parents.add("我参与的开黑");
        parents.add("我曾经参与的开黑");
        adtapter = new ItemListAdapter();
        mElist.setAdapter(adtapter);

        int groupCount = mElist.getCount();
        for (int i = 0; i < groupCount; i++) {
            mElist.expandGroup(i);
        }
        ;

    }

    public void getWcy() {
        params.add(new BasicNameValuePair("1", "1"));
        new Thread() {
            public void run() {
                String url = UrlUtils.BASEURL + "v1/yz/my_join_yz_ing.json";
                String resusult = MyHttpRequest.getHttpPostBasic(url, params, token);
                try {
                    if (resusult != null) {
                        WfqEntity entity = mGson.fromJson(resusult, WfqEntity.class);
                        entities_wcy = entity.getData();
                        handler.sendEmptyMessage(0);

                    }
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
            };
        }.start();
    }

    public void getWcjcy() {
        params.add(new BasicNameValuePair("1", "1"));
        new Thread() {
            public void run() {
                String url = UrlUtils.BASEURL + "v1/yz/my_join_yz_past.json";
                String resusult = MyHttpRequest.getHttpPostBasic(url, params, token);
                if (resusult != null) {
                    try {
                        WfqEntity entity = mGson.fromJson(resusult, WfqEntity.class);
                        entities_wcjcy = entity.getData();
                        handler.sendEmptyMessage(1);
                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                    }

                }
            };
        }.start();
    }

    public void addListener() {

    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    map.put("我参与的开黑", entities_wcy);
                    adtapter.notifyDataSetChanged();
                    break;
                case 1:
                    map.put("我曾经参与的开黑", entities_wcjcy);
                    adtapter.notifyDataSetChanged();
                    break;
                case 2:
                    Fenqian_cyEntity entity = (Fenqian_cyEntity)msg.obj;
                    Intent intent = new Intent(mContext, Fenqian_cyActivity.class);
                    intent.putExtra("entity", entity);
                    startActivity(intent);
                    break;
                case 3:
                    String erros = (String)msg.obj;
                    ToastUtil.Toast(mContext, erros);
                    break;
                case 4:
                    DialogUtils.getInstance().cancel();
                    mRl_pinlun.setVisibility(View.GONE);
                    ToastUtil.Toast(mContext, "评论成功");
                    mEdt_content.setText("");
                    break;
                case 5:
                    ToastUtil.Toast(mContext, "网络异常");
                    break;
                case 6:
                    String erros1 = (String)msg.obj;
                    ToastUtil.Toast(mContext, erros1);
                    break;
                default:
                    break;

            }
            if (adtapter != null) {
                adtapter.notifyDataSetChanged();
            }
        };
    };

    public class ItemListAdapter extends BaseExpandableListAdapter {

        @Override
        public int getGroupCount() {
            return parents.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            String key = parents.get(groupPosition);
            int size = map.get(key).size();
            return size;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return parents.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            String key = parents.get(groupPosition);
            return (map.get(key).get(childPosition));
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

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
                ViewGroup parent) {
            if (convertView == null) {

                convertView = View.inflate(mContext, R.layout.layout_parent_cy, null);
            }
            TextView tv = (TextView)convertView.findViewById(R.id.parent_textview);
            tv.setText(parents.get(groupPosition));
            return tv;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = View.inflate(mContext, R.layout.fqlist_item, null);
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
            if (key.equals("我参与的开黑")) {
                viewHolder.mTv_begin.setText("游戏开始");
                if (entity.getPay_get().equals("1")) {
                    viewHolder.mTv_begin.setBackgroundResource(R.drawable.login_ok);
                } else if (entity.getPay_get().equals("0")) {
                    viewHolder.mTv_begin.setBackgroundResource(R.drawable.login_bg_user);
                }

                viewHolder.mTv_cancel.setText("联系发起人");

            } else if (key.equals("我曾经参与的开黑")) {
                viewHolder.mTv_begin.setText("举报发起人");
                viewHolder.mTv_begin.setBackgroundResource(R.drawable.login_ok);
                viewHolder.mTv_cancel.setText("评论开黑");
            }
            if ((entity.getPay_get()).equals("0")) {
                viewHolder.mTv_money_name.setText("人民币:  ");
                viewHolder.mTv_peple_money.setText(" -" + entity.getPay_peple_money());
                viewHolder.mTv_peple_money.setTextColor(getResources().getColor(R.color.green));
            } else {
                viewHolder.mTv_money_name.setText("人民币:  ");
                viewHolder.mTv_peple_money.setText("+" + entity.getPay_peple_money());
                viewHolder.mTv_peple_money.setTextColor(getResources().getColor(R.color.red));
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
                    if (str_begin.equals("游戏开始")) {
                        if (entity.getPay_get().equals("1")) {
                            startGame(entity.getYz_id());
                        } else if (entity.getPay_get().equals("0")) {

                        }
                    } else if (str_begin.equals("举报发起人")) {
                        ToastUtil.Toast(mContext, "举报成功！");

                    }

                }
            });
            final String str_cancel = viewHolder.mTv_cancel.getText().toString().trim();
            viewHolder.mTv_cancel.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (str_cancel.equals("联系发起人")) {
                        Intent intent = new Intent(mContext, Lianxi_cyActivity.class);
                        intent.putExtra("entity", entity);
                        startActivity(intent);
                    } else if (str_cancel.equals("评论开黑")) {
                        mRl_pinlun.setVisibility(View.VISIBLE);
                    }

                }
            });

            mBt_pinlun.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    Pinlun(entity.getYz_id());
                }
            });

            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

    }

    public class ViewHolder {
        public TextView mTv_yz_title, mTv_need_persons, mTv_server, mTv_peple_money,
                mTv_money_name, mTv_yz_name, mTv_start_time, mTv_location, mTv_begin, mTv_cancel;

        private ImageView image;
    }

    public void startGame(String yz_id) {
        params.add(new BasicNameValuePair("yz_id", yz_id));
        new Thread() {
            public void run() {
                String url = UrlUtils.BASEURL + "v1/yz/start_game.json";
                String result = MyHttpRequest.getHttpPostBasic(url, params, token);
                if (result != null) {
                    Log.e("result_开始", result.toString());
                    try {
                        Fenqian_cyEntity entity = mGson.fromJson(result, Fenqian_cyEntity.class);
                        // Log.e("entity", entity.toString());
                        if (entity.getStatus() == 1) {
                            Message msg = new Message();
                            msg.what = 2;
                            msg.obj = entity;
                            handler.sendMessage(msg);
                        } else if (entity.getStatus() == 0) {
                            Message msg = new Message();
                            msg.what = 3;
                            msg.obj = entity.getErrors();
                            handler.sendEmptyMessage(3);
                        }
                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                        handler.sendEmptyMessage(5);
                    }
                }
            };
        }.start();
    }

    public String times(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("MM-dd-HH:mm");
        @SuppressWarnings("unused")
        // long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;

    }

    public void Pinlun(String yz_id) {
        DialogUtils.getInstance().createNotifier(mContext, "正在加载中");
        String content = mEdt_content.getText().toString().trim();
        /* 清空前一次的数据，以免混淆 */
        params.clear();

        params.add(new BasicNameValuePair("yz_id", yz_id));
        params.add(new BasicNameValuePair("content", content));
        Log.e("content", content);
        new Thread() {
            public void run() {
                String url = UrlUtils.BASEURL + "v1/yz/comment.json";
                String result = MyHttpRequest.getHttpPostBasic(url, params, token);
                if (result != null) {
                    // Log.e("result", result.toString());
                    try {
                        TYEntity entity = mGson.fromJson(result, TYEntity.class);
                        if (entity.getStatus() == 0) {
                            Message msg = new Message();
                            msg.what = 6;
                            msg.obj = entity.getErrors();
                            handler.sendMessage(msg);

                        } else if (entity.getStatus() == 1) {
                            handler.sendEmptyMessage(4);

                        }
                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                        handler.sendEmptyMessage(5);
                    }

                }
            };
        }.start();
    }
}
