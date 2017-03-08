
package com.jishang.bimeng.activity.yuewan;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jishang.bimeng.R;
import com.jishang.bimeng.activity.base.BaseActivity;
import com.jishang.bimeng.activity.yuezhan.datapick.DatapikerActivity;
import com.jishang.bimeng.activity.zhifu.Yw_ZfListActivity;
import com.jishang.bimeng.entity.yuewan.confirm.Yw_listConfrimEntity;
import com.jishang.bimeng.entity.yuewan.confirm.Yw_listConfrim_dataEntity;
import com.jishang.bimeng.entity.yuewan.list.YwList_dataEntity;
import com.jishang.bimeng.entity.yuewan.list.Ywlist_data_ugEntity;
import com.jishang.bimeng.entity.yuezhan.confirm.C_DataEntity;
import com.jishang.bimeng.entity.yuezhan.confirm.time.DataTimeEntity;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.ToastUtil;
import com.jishang.bimeng.utils.Internet.MyHttpRequest;
import com.jishang.bimeng.utils.dialog.DialogUtils;
import com.jishang.bimeng.utils.url.UrlUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class Yw_listConfrimActivity extends BaseActivity implements OnClickListener {
    private Spinner mSp_khyx, mSp_fwq, mSp_ms, mSp_dj, mSp_position;

    private TextView mTv_xf;

    private EditText mEdtv_num_time, mEdt_remarks;

    private Intent mItent;

    private ListView mList;

    private String token;

    private List<BasicNameValuePair> params;

    private Gson mGson;

    private Context mContext;

    protected ImageLoader imageLoader_head;

    private DisplayImageOptions options_head; // 设置图片显示相关参数

    List<Ywlist_data_ugEntity> usergames = new ArrayList<Ywlist_data_ugEntity>();

    private TextView mTv_starttime, mTv_confirm;

    private YwList_dataEntity entity;

    private List<String> gamename = new ArrayList<String>();

    private List<String> servers = new ArrayList<String>();

    private List<String> pattern = new ArrayList<String>();

    private List<String> grade = new ArrayList<String>();

    private List<String> position_ = new ArrayList<String>();

    private MySpAdapter<String> gamename_adapter;

    private MySpAdapter<String> servers_adapter;

    private MySpAdapter<String> pattern_adapter;

    private MySpAdapter<String> grade_adapter;

    private MySpAdapter<String> position_adapter;

    C_DataEntity entity1 = null;

    /**
     * 开始时间
     */
    private String yw_start_time;

    /**
     * 陪玩人uid
     */
    private String pw_uid;

    /**
     * 游戏名称
     */
    private String yw_title;

    /**
     * 游戏服务器
     */
    private String yw_server;

    /**
     * 等级
     */
    private String yw_grade;

    /**
     * 游戏模式
     */
    private String yw_pattern;

    /**
     * 游戏位置
     */
    private String yw_position;

    /**
     * 游戏时长
     */
    private String yw_num_time;

    /**
     * 小费
     */
    private String item_money;

    /**
     * 游戏图标
     */
    private String yw_img;

    /**
     * 备注
     */
    private String yw_remarks;

    @Override
    public int initResource() {
        return R.layout.activity_ywlist_confirm;
    }

    @Override
    public void initView() {
        mContext = this;
        mItent = getIntent();
        entity = (YwList_dataEntity)mItent.getSerializableExtra("entity");
        token = new SharUtil(mContext).getAccess_token();
        // Log.e("eee", entity.toString());
        params = new ArrayList<BasicNameValuePair>();
        mGson = new Gson();
        usergames = entity.getUsergames();
        pw_uid = entity.getUid();
        mSp_khyx = (Spinner)findViewById(R.id.activity_ywlist_confirm_spiner_kaihei);
        mSp_fwq = (Spinner)findViewById(R.id.activity_ywlist_confirm_spiner_fwq);
        mSp_ms = (Spinner)findViewById(R.id.activity_ywlist_confirm_spiner_ms);
        mSp_dj = (Spinner)findViewById(R.id.activity_ywlist_confirm_spiner_djxz);
        mSp_position = (Spinner)findViewById(R.id.activity_ywlist_confirm_spiner_position);
        mTv_starttime = (TextView)findViewById(R.id.fragment_yuezhan_tv_kssj);
        mTv_xf = (TextView)findViewById(R.id.fragment_yuezhan_tv_u_integral);
        mEdtv_num_time = (EditText)findViewById(R.id.fragment_yuezhan_edt_yw_num_time);
        mTv_confirm = (TextView)findViewById(R.id.activity_ywlist_confirm_confirm);
        mEdt_remarks = (EditText)findViewById(R.id.fragment_yuezhan_edt_yw_remarks);

        options_head = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ic_stub) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.ic_empty) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.ic_error) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(10)) // 设置成圆角图片
                .build(); // 构建完成
        imageLoader_head = ImageLoader.getInstance();

        gamename_adapter = new MySpAdapter<String>(gamename);
        servers_adapter = new MySpAdapter<String>(servers);
        pattern_adapter = new MySpAdapter<String>(pattern);
        grade_adapter = new MySpAdapter<String>(grade);
        position_adapter = new MySpAdapter<String>(position_);

        mSp_khyx.setAdapter(gamename_adapter);
        mSp_fwq.setAdapter(servers_adapter);
        mSp_dj.setAdapter(grade_adapter);
        mSp_ms.setAdapter(pattern_adapter);
        mSp_position.setAdapter(position_adapter);
        mTv_xf.setText(entity.getUserdetails().getW_tip());
    }

    @Override
    public void initData() {
        for (int i = 0; i < usergames.size(); i++) {
            servers.add(usergames.get(i).getYw_server());
            pattern.add(usergames.get(i).getYw_pattern());
            grade.add(usergames.get(i).getYw_grade());
            position_.add(usergames.get(i).getYw_position());
            gamename.add(usergames.get(i).getYw_title());
        }
        initSet();
        if (gamename_adapter != null) {
            gamename_adapter.notifyDataSetChanged();

        }
        if (servers_adapter != null) {
            servers_adapter.notifyDataSetChanged();
        }
        if (pattern_adapter != null) {
            pattern_adapter.notifyDataSetChanged();
        }
        if (grade_adapter != null) {
            grade_adapter.notifyDataSetChanged();
        }
        if (position_adapter != null) {
            position_adapter.notifyDataSetChanged();
        }

    }

    public void initSet() {
        yw_title = gamename.get(0).toString();
        yw_server = servers.get(0).toString();
        yw_pattern = pattern.get(0).toString();
        yw_grade = grade.get(0).toString();
        yw_position = position_.get(0).toString();
        yw_img = usergames.get(0).getYw_img();
    }

    @Override
    public void addListener() {
        mTv_starttime.setOnClickListener(this);
        mSp_khyx.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                yw_title = gamename.get(position).toString();
                yw_img = usergames.get(position).getYw_img();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
        mSp_fwq.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                yw_server = servers.get(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
        mSp_ms.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                yw_pattern = pattern.get(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
        mSp_dj.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                yw_grade = grade.get(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mSp_position.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                yw_position = position_.get(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
        mTv_confirm.setOnClickListener(this);

    }

    public void back(View v) {
        finish();
    }

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
                convertView = View.inflate(mContext, R.layout.yuezhan_sp_item, null);
                vh1.name = (TextView)convertView.findViewById(R.id.yuezhan_sp_item_name);
                convertView.setTag(vh1);
            } else {
                vh1 = (ViewHolder1)convertView.getTag();

            }
            vh1.name.setText(mDatas.get(position).toString());

            return convertView;
        }

    }

    class ViewHolder1 {
        TextView name;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_yuezhan_tv_kssj:
                Intent intent = new Intent(mContext, DatapikerActivity.class);
                startActivityForResult(intent, 200);
                break;
            case R.id.activity_ywlist_confirm_confirm:

                putMsg();
                break;

        }

    }

    public void putMsg() {
        DialogUtils.getInstance().createNotifier(mContext, "正在加载中");
        item_money = mTv_xf.getText().toString();
        yw_num_time = mEdtv_num_time.getText().toString();
        yw_remarks = mEdt_remarks.getText().toString().trim();

        params.add(new BasicNameValuePair("pw_uid", pw_uid));
        params.add(new BasicNameValuePair("yw_title", yw_title));
        params.add(new BasicNameValuePair("yw_server", yw_server));
        params.add(new BasicNameValuePair("yw_grade", yw_grade));
        params.add(new BasicNameValuePair("yw_pattern", yw_pattern));
        params.add(new BasicNameValuePair("yw_position", yw_position));
        params.add(new BasicNameValuePair("yw_start_time", yw_start_time));
        params.add(new BasicNameValuePair("yw_num_time", yw_num_time));
        params.add(new BasicNameValuePair("item_money", item_money));
        params.add(new BasicNameValuePair("yw_img", yw_img));
        params.add(new BasicNameValuePair("yw_remarks", yw_remarks));
        Log.e("----", pw_uid + " " + yw_title + " " + yw_server + " " + yw_grade + " " + yw_pattern
                + " " + yw_position + " " + yw_start_time + " " + yw_num_time + " " + item_money);
        new Thread() {
            public void run() {

                String url = UrlUtils.BASEURL + "v1/yw/create_yw.json";
                String result = MyHttpRequest.getHttpPostBasic(url, params, token);
                if (result != null) {
                    // Log.e("result", result.toString());
                    Yw_listConfrimEntity entity = mGson
                            .fromJson(result, Yw_listConfrimEntity.class);
                    // Log.e("entity", entity.toString());
                    if (entity.getStatus() == 0) {
                        Message msg = new Message();
                        msg.what = 0;
                        msg.obj = entity.getErrors();
                        handler.sendMessage(msg);
                    } else {
                        Message msg = new Message();
                        msg.what = 1;
                        msg.obj = entity;
                        handler.sendMessage(msg);
                    }

                }
            };

        }.start();
    }

    /**
     * @param data 初始化订单信息
     */
    public void setEntity(Yw_listConfrim_dataEntity entity) {
        entity1 = new C_DataEntity();
        entity1.setTotal_money(entity.getYpn_total_mondy());
        entity1.setOne_peple_money(entity.getOne_money());
        entity1.setP_id(entity.getYwp_id());
        entity1.setF_uid(entity.getPnyw_uid());
        entity1.setOrder_notice_sn(entity.getYw_order_notice());
        entity1.setCreate_time(entity.getNum_time());
    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            DialogUtils.getInstance().cancel();
            switch (msg.what) {
                case 0:
                    String erros = (String)msg.obj;
                    ToastUtil.Toast(mContext, erros);
                    break;
                case 1:
                    Yw_listConfrimEntity entity4 = (Yw_listConfrimEntity)msg.obj;
                    Yw_listConfrim_dataEntity entity = entity4.getData();
                    setEntity(entity);
                    // 跳转到支付界面
                    Intent intent = new Intent(mContext, Yw_ZfListActivity.class);
                    intent.putExtra("entity1", entity1);
                    startActivity(intent);
                    break;

            }
        };
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 100:
                DataTimeEntity entity = (DataTimeEntity)data.getSerializableExtra("entity");
                yw_start_time = entity.getStart_time();
                mTv_starttime.setText(entity.getDandt());
                break;

        }
    }

}
