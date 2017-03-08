
package com.jishang.bimeng.activity.yuezhan.zhudian;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jishang.bimeng.R;
import com.jishang.bimeng.activity.base.BaseActivity;
import com.jishang.bimeng.entity.tonyong.TYEntity;
import com.jishang.bimeng.entity.yuezhan.zhdianqiehuan.ZhudianqhEntity;
import com.jishang.bimeng.entity.yuezhan.zhdianqiehuan.Zhudianqh_BsEntity;
import com.jishang.bimeng.entity.yuezhan.zhdianqiehuan.Zhudianqh_CityEntity;
import com.jishang.bimeng.entity.yuezhan.zhdianqiehuan.Zhudianqh_ProvinceEntity;
import com.jishang.bimeng.utils.CheckNulll;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.ToastUtil;
import com.jishang.bimeng.utils.Internet.MyHttpRequest;
import com.jishang.bimeng.utils.dialog.DialogUtils;
import com.jishang.bimeng.utils.url.UrlUtils;

/**
 * 切换主店UI
 * 
 * @author wangliang Jul 13, 2016
 */
public class QiehuanZdActivity extends BaseActivity implements OnClickListener {
    private TextView mTv_name, mTv_confirm;

    private Spinner mSp_province, mSp_city, mSp_zhudian;

    private Gson mGson;

    private Context mContext;

    private List<Zhudianqh_ProvinceEntity> provinces = new ArrayList<Zhudianqh_ProvinceEntity>();

    private List<Zhudianqh_CityEntity> citys = new ArrayList<Zhudianqh_CityEntity>();

    private List<Zhudianqh_BsEntity> businesss = new ArrayList<Zhudianqh_BsEntity>();

    private List<Zhudianqh_BsEntity> businesss_ = new ArrayList<Zhudianqh_BsEntity>();

    private MySpAdapter_business adapter_bus;

    private MySpAdapter_province adapter_pc;

    private MySpAdapter_city adapter_ct;

    private List<BasicNameValuePair> params;

    private String province, city, business, province_name, city_name, business_name;

    private TextView mTv_zhudianname;

    private String token;

    private RelativeLayout mRl_confrim;

    private SharUtil mShareUtil;

    @Override
    public int initResource() {
        return R.layout.activity_qiehuanzhudian;
    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        if (intent != null) {
            String back = intent.getStringExtra("back");
            ((TextView)findViewById(R.id.tv_back_to_activity)).setText(back);
        }
        params = new ArrayList<BasicNameValuePair>();
        mTv_name = (TextView)findViewById(R.id.activity_qiehuanzhudian_name);
        mSp_province = (Spinner)findViewById(R.id.activity_qiehuan_city);
        mSp_city = (Spinner)findViewById(R.id.activity_qiehuan_location);
        mSp_zhudian = (Spinner)findViewById(R.id.activity_qiehuan_zhudian);
        mTv_confirm = (TextView)findViewById(R.id.activity_qiehuan_confrim);
        mRl_confrim = (RelativeLayout)findViewById(R.id.activity_qiehuan_rl_confrim);
        mTv_zhudianname = (TextView)findViewById(R.id.activity_qiehuanzhudian_zhudianname);
        mGson = new Gson();
        mContext = this;
        mShareUtil = new SharUtil(mContext);
        token = new SharUtil(mContext).getAccess_token();

        adapter_bus = new MySpAdapter_business(businesss);
        adapter_ct = new MySpAdapter_city(citys);
        adapter_pc = new MySpAdapter_province(provinces);
        mSp_city.setAdapter(adapter_ct);
        mSp_province.setAdapter(adapter_pc);

        mTv_name.setText("切换主店");
        mTv_zhudianname.setText("您当前的网吧为：" + mShareUtil.getProvince() + mShareUtil.getCity()
                + mShareUtil.getBusiness());

    }

    @Override
    public void initData() {
        getMsg();

    }

    @Override
    public void addListener() {
        mRl_confrim.setOnClickListener(this);
        mSp_province.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                province = provinces.get(position).getId();
                province_name = provinces.get(position).getS_provname();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
        mSp_city.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                city = citys.get(position).getId();
                city_name = citys.get(position).getS_cityname();
                adapter_bus.clear();
                businesss_.clear();
                // for (int i = 0; i < citys.size(); i++) {
                // if(citys.get(position).getId().equals(businesss.get(i).getId())){
                // businesss_.add(businesss.get(i));
                // }
                // }

                for (int i = 0; i < businesss.size(); i++) {
                    Zhudianqh_BsEntity entity = new Zhudianqh_BsEntity();
                    // Log.e("city", city);
                    String str = businesss.get(i).getW_citid();
                    Log.e("str", str);
                    if (str.equals(city)) {
                        entity = businesss.get(i);

                    }
                    if (entity.getId() != null) {
                        businesss_.add(entity);
                    }

                }
                mSp_zhudian.setAdapter(adapter_bus);
                adapter_bus.refreshAdapter(businesss_);
                // Log.e("businesss_", businesss_.toString());
                if (businesss_ == null || businesss_.isEmpty()) {
                    business = null;
                    business_name = null;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
        mSp_zhudian.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("businesss_1", businesss_.toString());
                if (businesss_ == null || businesss_.isEmpty()) {
                    business = null;
                    business_name = null;
                } else {
                    business = businesss_.get(position).getId();
                    business_name = businesss_.get(position).getW_name();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void getMsg() {
        DialogUtils.getInstance().createNotifier(mContext, "正在加载中");
        new Thread() {
            public void run() {
                String url = UrlUtils.BASEURL + "v1/province/get.json";
                String result = MyHttpRequest.getHttpGet(url, null);
                Log.e("result", result.toString());
                if (result != null) {
                    Message msg = new Message();
                    msg.obj = result;
                    msg.what = 1;
                    handler.sendMessage(msg);
                }
            };
        }.start();
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            DialogUtils.getInstance().cancel();
            switch (msg.what) {
                case 0:
                    ToastUtil.Toast(mContext, "网络错误");
                    break;
                case 1:
                    String result = (String)msg.obj;
                    try {
                        ZhudianqhEntity entity = mGson.fromJson(result, ZhudianqhEntity.class);
                        provinces = entity.getProvince();
                        citys = entity.getCity();
                        businesss = entity.getBusiness();
                        adapter_pc.refreshAdapter(provinces);
                        adapter_ct.refreshAdapter(citys);

                        // province = provinces.get(0).getId();
                        // city = citys.get(0).getId();
                        // business = businesss.get(0).getId();
                        // province_name = provinces.get(0).getS_provname();
                        // city_name = citys.get(0).getS_cityname();
                        // business_name = businesss.get(0).getW_name();
                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                    }

                    break;
                case 2:
                    String erros = (String)msg.obj;
                    ToastUtil.Toast(mContext, erros);
                    break;
                case 3:
                    ToastUtil.Toast(mContext, "修改成功");
                    SetSp();
                    break;

            }
        };
    };

    public void SetSp() {
        SharUtil mSharUtil = new SharUtil(mContext);
        mSharUtil.setProvince(province_name);
        mSharUtil.setCity(city_name);
        mSharUtil.setBusiness(business_name);
    }

    public void back(View v) {
        finish();
    }

    /**
     * @author kangming
     * @param <T>所需费用适配器
     */
    public class MySpAdapter_province extends BaseAdapter {
        protected List<Zhudianqh_ProvinceEntity> mDatas;

        public MySpAdapter_province(List<Zhudianqh_ProvinceEntity> mDatas) {
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
        public void refreshAdapter(List<Zhudianqh_ProvinceEntity> arrayList) {
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
            vh1.name.setText(mDatas.get(position).getS_provname());

            return convertView;
        }

    }

    class ViewHolder1 {
        TextView name;
    }

    /**
     * @author kangming
     * @param <T>所需费用适配器
     */
    public class MySpAdapter_city extends BaseAdapter {
        protected List<Zhudianqh_CityEntity> mDatas;

        public MySpAdapter_city(List<Zhudianqh_CityEntity> mDatas) {
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
        public void refreshAdapter(List<Zhudianqh_CityEntity> arrayList) {
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
            vh1.name.setText(mDatas.get(position).getS_cityname());

            return convertView;
        }

    }

    /**
     * @author kangming
     * @param <T>所需费用适配器
     */
    public class MySpAdapter_business extends BaseAdapter {
        protected List<Zhudianqh_BsEntity> mDatas;

        public MySpAdapter_business(List<Zhudianqh_BsEntity> mDatas) {
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
        public void refreshAdapter(List<Zhudianqh_BsEntity> arrayList) {
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
            vh1.name.setText(mDatas.get(position).getW_name());

            return convertView;
        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.activity_qiehuan_rl_confrim:
                new AlertDialog.Builder(mContext).setTitle("警告")
                        .setMessage("切换主店零钱包会清零，活跃度也会清零，请谨慎操作")
                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                putMsg();

                            }
                        }).setPositiveButton("取消", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).show();

                break;

        }
    }

    public void putMsg() {
        if (!CheckNulll.check(province) || !CheckNulll.check(business) || !CheckNulll.check(city)) {
            ToastUtil.Toast(mContext, "请选择城市或者网吧");
            return;
        }
        DialogUtils.getInstance().createNotifier(mContext, "正在加载中");
        params.add(new BasicNameValuePair("province", province));
        params.add(new BasicNameValuePair("business", business));
        params.add(new BasicNameValuePair("city", city));
        Log.e("=-----------", province + "   " + business + "   " + city);
        new Thread() {
            public void run() {
                String url = UrlUtils.BASEURL + "v1/user/update_business.json";
                String result = MyHttpRequest.getHttpPostBasic(url, params, token);
                if (result != null) {
                    Log.e("result", result.toString());
                    try {
                        TYEntity entity = mGson.fromJson(result, TYEntity.class);
                        if (entity.getStatus() == 0) {
                            Message msg = new Message();
                            msg.what = 2;
                            msg.obj = entity.getErrors();
                            handler.sendMessage(msg);
                        } else if (entity.getStatus() == 1) {
                            handler.sendEmptyMessage(3);
                        }
                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                        handler.sendEmptyMessage(0);
                    }

                }

            };
        }.start();

    }

}
