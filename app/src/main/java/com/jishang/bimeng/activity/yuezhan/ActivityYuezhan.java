
package com.jishang.bimeng.activity.yuezhan;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jishang.bimeng.R;
import com.jishang.bimeng.activity.base.BaseActivity;
import com.jishang.bimeng.activity.yuezhan.datapick.DatapikerActivity;
import com.jishang.bimeng.activity.zhifu.ZfListActivity;
import com.jishang.bimeng.entity.yuezhan.GameEntity;
import com.jishang.bimeng.entity.yuezhan.Game_rtEntity;
import com.jishang.bimeng.entity.yuezhan.PhotoEntity;
import com.jishang.bimeng.entity.yuezhan.confirm.C_DataEntity;
import com.jishang.bimeng.entity.yuezhan.confirm.ConfirmEntity;
import com.jishang.bimeng.entity.yuezhan.confirm.time.DataTimeEntity;
import com.jishang.bimeng.entity.yuezhan.cyzf.CyzfEntity;
import com.jishang.bimeng.entity.yuezhan.cyzf.Cyzf_PpEntity;
import com.jishang.bimeng.entity.yuezhan.cyzf.Cyzf_ctEntity;
import com.jishang.bimeng.entity.yuezhan.cyzf.Cyzf_moneyEntity;
import com.jishang.bimeng.entity.yuezhan.cyzf.Cyzf_wtEntity;
import com.jishang.bimeng.ui.UIHandler;
import com.jishang.bimeng.ui.adapter.MySpAdapter;
import com.jishang.bimeng.ui.adapter.MySpAdapter_cxsj;
import com.jishang.bimeng.ui.adapter.MySpAdapter_cyfy;
import com.jishang.bimeng.ui.adapter.MySpAdapter_ddsj;
import com.jishang.bimeng.ui.adapter.MySpAdapter_khrs;
import com.jishang.bimeng.ui.adapter.MySpAdapter_name;
import com.jishang.bimeng.ui.adapter.MySpAdapter_name.IModelGame;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.ToastUtil;
import com.jishang.bimeng.utils.Internet.MyHttpRequest;
import com.jishang.bimeng.utils.dialog.DialogUtils;
import com.jishang.bimeng.utils.url.UrlUtils;

/**
 * "发起开黑"UI
 * 
 * @author kangming
 */
public class ActivityYuezhan extends BaseActivity implements OnClickListener, IModelGame {

    private TextView mTv_location, mTv_confirm, mTv_kssj;

    private Gson mGson;

    private List<String> servers = new ArrayList<String>();

    private List<String> pattern = new ArrayList<String>();

    private List<String> grade = new ArrayList<String>();

    private EditText mEdt_remark;

    private List<Cyzf_PpEntity> peples = new ArrayList<Cyzf_PpEntity>();

    private List<Cyzf_moneyEntity> moneys = new ArrayList<Cyzf_moneyEntity>();

    private List<Cyzf_wtEntity> waittimes = new ArrayList<Cyzf_wtEntity>();

    private List<Cyzf_ctEntity> continuetimes = new ArrayList<Cyzf_ctEntity>();

    private String token;

    // 该下拉框展示，获取游戏对象的游戏名称

    private Spinner mSp_game_name;

    private Spinner mSp_fwq, mSp_ms, mSp_dj, mSp_cyms, mSp_khrs, mSp_cyfy, mSp_cxsj, mSp_ddsj;

    private MySpAdapter<String> adaptersp;

    private MySpAdapter<String> ptadapter;

    private MySpAdapter<String> djadapter;

    private MySpAdapter<String> cymsadapter;

    private MySpAdapter_khrs khrsadapter;

    MySpAdapter_ddsj ddsjadapter;

    MySpAdapter_cxsj cxsjadapter;

    MySpAdapter_cyfy cyfyadapter;

    MySpAdapter_name nameadapter;

    /**
     * 游戏名称
     */
    private String yz_title;

    /**
     * 游戏图标
     */
    private String yz_img;

    /**
     * 游戏服务器
     */
    private String yz_server;

    /**
     * 游戏模式
     */
    private String yz_pattern;

    /**
     * 游戏等级限制
     */
    private String yz_grade;

    /**
     * 需要人数（1/3 1/4 1/5，如果是特殊权限为：0/3 0/4 0/5）
     */
    private String need_persons;

    /**
     * 参加人数（初始值为：1，如果是特殊权限为0）
     */
    private String need_peple_item;

    /**
     * 参与费用
     */
    private String pay_peple_money;

    /**
     * 平台费用（隐藏域）
     */
    private String pay_app_money;

    /**
     * 开始时间
     */
    private String start_time;

    /**
     * 等待时间
     */
    private String wait_time;

    /**
     * 持续时间
     */
    private String game_continue_time;

    /**
     * 备注
     */
    private String yz_remark;

    private List<String> cymss = new ArrayList<String>();

    /**
     * 0参与支付 1参与所得
     */
    private int cyms = 0;

    public static ActivityYuezhan instance = null;

    private Context mContext;

    private RelativeLayout mRl_kssj;

    @Override
    public int initResource() {
        return R.layout.activity_fqyz;
    }

    @Override
    public void initView() {
        instance = this;
        mContext = this;
        uiHandler = new YueZhanHandler(this);
        token = new SharUtil(ActivityYuezhan.this).getAccess_token();
        mGson = new Gson();
        mTv_location = (TextView)findViewById(R.id.fragment_yuezhan_tv_location);
        mTv_confirm = (TextView)findViewById(R.id.activity_fqyz_confirm);
        mTv_kssj = (TextView)findViewById(R.id.activity_fqyz_kssj);
        mEdt_remark = (EditText)findViewById(R.id.fragment_yuezhan_edt_liuyan);
        mSp_fwq = (Spinner)findViewById(R.id.fragment_yuezhan_spiner_fwq);
        mSp_ms = (Spinner)findViewById(R.id.fragment_yuezhan_spiner_ms);
        mSp_dj = (Spinner)findViewById(R.id.fragment_yuezhan_spiner_djxz);
        mSp_cyms = (Spinner)findViewById(R.id.fragment_yuezhan_spiner_cyms);

        mSp_khrs = (Spinner)findViewById(R.id.fragment_yuezhan_spiner_khrs);
        mSp_cyfy = (Spinner)findViewById(R.id.fragment_yuezhan_spiner_cyfy);
        mSp_cxsj = (Spinner)findViewById(R.id.fragment_yuezhan_spiner_cxsj);
        mSp_ddsj = (Spinner)findViewById(R.id.fragment_yuezhan_spiner_ddsj);
        mSp_game_name = (Spinner)findViewById(R.id.fragment_yuezhan_spiner_name);

        mRl_kssj = (RelativeLayout)findViewById(R.id.activity_fqyz_rl_kssj);

        djadapter = new MySpAdapter<String>(grade);
        ptadapter = new MySpAdapter<String>(pattern);
        adaptersp = new MySpAdapter<String>(servers);
        cymsadapter = new MySpAdapter<String>(cymss);

        khrsadapter = new MySpAdapter_khrs(peples);
        cyfyadapter = new MySpAdapter_cyfy(moneys);
        cxsjadapter = new MySpAdapter_cxsj(continuetimes);
        ddsjadapter = new MySpAdapter_ddsj(waittimes);
        nameadapter = new MySpAdapter_name(null, this);// 游戏名称

        setNull();

        mSp_fwq.setAdapter(adaptersp);
        mSp_ms.setAdapter(ptadapter);
        mSp_dj.setAdapter(djadapter);
        mSp_cyms.setAdapter(cymsadapter);
        mSp_khrs.setAdapter(khrsadapter);
        mSp_cyfy.setAdapter(cyfyadapter);
        mSp_cxsj.setAdapter(cxsjadapter);
        mSp_ddsj.setAdapter(ddsjadapter);
        mSp_game_name.setAdapter(nameadapter);
        mTv_location.setText(new SharUtil(mContext).getBusiness());

    }

    /**
     * 强行将上一次的内容制空
     */
    public void setNull() {
        cymsadapter.clear();
        djadapter.clear();
        ptadapter.clear();
        adaptersp.clear();

    }

    /**
     * 将上一次内容强制制空
     */
    public void setNull_() {
        khrsadapter.clear();
        cxsjadapter.clear();
        ddsjadapter.clear();
        cyfyadapter.clear();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void initData() {
        application.getController().doRequestLaunchBallGames(uiHandler);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 100:
                DataTimeEntity entity = (DataTimeEntity)data.getSerializableExtra("entity");
                start_time = entity.getStart_time();
                mTv_kssj.setText(entity.getDandt());
                break;
            default:
                break;

        }
    }

    public void initset_(List<Cyzf_moneyEntity> moneys, List<Cyzf_PpEntity> peples,
            List<Cyzf_wtEntity> waittimes, List<Cyzf_ctEntity> continuetimes) {
        pay_peple_money = moneys.get(0).getMin_money().toString();
        pay_app_money = moneys.get(0).getApp_money().toString();
        wait_time = waittimes.get(0).getTime_key().toString();
        game_continue_time = continuetimes.get(0).getContinue_key().toString();
        need_persons = peples.get(0).getTotal_peple().toString();
        need_peple_item = peples.get(0).getJoin_peple();

    }

    /**
     * @param servers 游戏服务器
     * @param pattern 游戏模式
     * @param grade 游戏等级限制
     */
    public void initset(List<String> servers, List<String> pattern, List<String> grade) {
        yz_server = servers.get(0).toString();
        yz_pattern = pattern.get(0).toString();
        yz_grade = grade.get(0).toString();

    }

    public void setOther() {
        switch (cyms) {
            case 0:
                setRs_0();
                /*
                 * setCyfy_0(); setDdsj_0(); setCxsj_0();
                 */
                break;
            case 1:
                setRs_1();
                /*
                 * setCyfy_1(); setDdsj_1(); setCxsj_1();
                 */
                break;

        }
    }

    /**
     * 参与所得时数据
     */
    public void setRs_1() {
        new Thread() {
            public void run() {
                List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
                params.add(new BasicNameValuePair("1", "1"));

                String url = UrlUtils.BASEURL + "v1/yz/data_by_get.json";
                String result = MyHttpRequest.getHttpPostBasic(url, params, token);
                if (result != null) {
                    Message msg = new Message();
                    msg.what = 2;
                    msg.obj = result;
                    uiHandler.sendMessage(msg);
                }

            };
        }.start();
    }

    /**
     * 参与支付数据
     */
    public void setRs_0() {
        new Thread() {
            public void run() {
                List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
                params.add(new BasicNameValuePair("1", "1"));

                String url = UrlUtils.BASEURL + "v1/yz/data_by_pay.json";
                String result = MyHttpRequest.getHttpPostBasic(url, params, token);
                if (result != null) {
                    Message msg = new Message();
                    msg.what = 3;
                    msg.obj = result;
                    uiHandler.sendMessage(msg);
                }

            };
        }.start();

    }

    boolean b = true;

    @Override
    public void addListener() {
        mSp_game_name.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (b) {
                    // 主要功能代码；
                    view.setVisibility(View.INVISIBLE);
                } else {
                    view.setVisibility(View.VISIBLE);
                    PhotoEntity photoEntity = (PhotoEntity)mSp_game_name.getSelectedItem();
                    String pid = photoEntity.getYz_gm_id();
                    setNull();
                    loadRmsg(pid);
                }
                b = false;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });

        mSp_cyms.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        cyms = 0;
                        // ToastUtil.Toast(YuezhanActivity.this, cyms + "");
                        break;
                    case 1:
                        cyms = 1;
                        // ToastUtil.Toast(YuezhanActivity.this, cyms + "");
                        break;

                }
                setNull_();
                setOther();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
        mSp_fwq.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                yz_server = servers.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mSp_ms.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                yz_pattern = pattern.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        mSp_dj.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                yz_grade = grade.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        mSp_khrs.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                need_persons = peples.get(position).getTotal_peple().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mSp_cyfy.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pay_peple_money = moneys.get(position).getMin_money().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mSp_ddsj.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                wait_time = waittimes.get(position).getTime_key().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mSp_cxsj.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                game_continue_time = continuetimes.get(position).getContinue_key();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mTv_confirm.setOnClickListener(this);
        mRl_kssj.setOnClickListener(this);

    }

    /**
     * 加载
     * 
     * @param pid
     */
    public void loadRmsg(final String pid) {
        final String token = new SharUtil(ActivityYuezhan.this).getAccess_token();
        new Thread() {
            public void run() {
                List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
                params.add(new BasicNameValuePair("1", "1"));

                String url = UrlUtils.BASEURL + "v1/yz/game_by_id.json?yz_gm_id=" + pid;
                String result = MyHttpRequest.getHttpPostBasic(url, params, token);
                if (result != null) {
                    Log.e("result---", result.toString());
                    Message msg = new Message();
                    msg.what = 1;
                    msg.obj = result;
                    uiHandler.sendMessage(msg);
                }

            };
        }.start();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_fqyz_confirm: {// 确认发起按钮事件触发
                // 确认发起开黑请求
                putMsg();
            }
                break;
            case R.id.fragment_yuezhan_tv_kssj:
                // Log.e("-----", yz_title + "   " + yz_img + "  " + yz_server +
                // "  "
                // + yz_pattern + "  " + yz_grade + "  " + cyms + ""
                // + need_persons + "  " + need_peple_item + "  "
                // + pay_peple_money + " " + pay_app_money + " " + start_time
                // + "  " + wait_time + "  " + game_continue_time + "  "
                // + yz_remark);
                // Intent intent = new Intent(YuezhanActivity.this,
                // DatapikerActivity.class);
                // startActivityForResult(intent, 200);

                break;
            case R.id.activity_fqyz_rl_kssj:// 日期时间选择按键触发
                Intent intent = new Intent(ActivityYuezhan.this, DatapikerActivity.class);
                startActivityForResult(intent, 200);

                break;
            default:
                break;

        }

    }

    /**
     * 确认发起开黑请求
     */
    public void putMsg() {
        DialogUtils.getInstance().createNotifier(mContext, "正在加载中");
        yz_remark = mEdt_remark.getText().toString().trim();
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();

        params.add(new BasicNameValuePair("yz_title", yz_title));
        params.add(new BasicNameValuePair("yz_img", yz_img));
        params.add(new BasicNameValuePair("yz_server", yz_server));
        params.add(new BasicNameValuePair("yz_pattern", yz_pattern));
        params.add(new BasicNameValuePair("yz_grade", yz_grade));
        params.add(new BasicNameValuePair("pay_get", cyms + ""));
        params.add(new BasicNameValuePair("need_persons", need_persons));
        params.add(new BasicNameValuePair("need_peple_item", need_peple_item));
        params.add(new BasicNameValuePair("pay_peple_money", pay_peple_money));
        params.add(new BasicNameValuePair("pay_app_money", pay_app_money));
        params.add(new BasicNameValuePair("start_time", start_time));
        params.add(new BasicNameValuePair("wait_time", wait_time));
        params.add(new BasicNameValuePair("game_continue_time", game_continue_time));
        params.add(new BasicNameValuePair("yz_remark", yz_remark));
        new Thread() {
            public void run() {
                String url = UrlUtils.BASEURL + "v1/yz/create_yz.json";
                String result = MyHttpRequest.getHttpPostBasic(url, params, token);
                try {
                    if (result != null) {
                        ConfirmEntity entity = mGson.fromJson(result, ConfirmEntity.class);
                        if (entity.getStatus() == 0) {
                            Message msg = new Message();
                            msg.what = 6;
                            msg.obj = entity;
                            uiHandler.sendMessage(msg);
                        } else {
                            C_DataEntity entity2 = entity.getData();
                            Message msg = new Message();
                            msg.what = 4;
                            msg.obj = entity2;
                            uiHandler.sendMessage(msg);
                        }

                    }
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    uiHandler.sendEmptyMessage(5);
                }

            };
        }.start();

    }

    public void back(View v) {
        finish();
    }

    /**
     * 点击游戏名称的回调函数
     */
    @Override
    public void setOnClickListener(PhotoEntity photoEntity) {

    }

    class YueZhanHandler extends UIHandler {

        public YueZhanHandler(Activity activity) {
            super(activity);
        }

        public void onMessage(android.os.Message msg) {
            DialogUtils.getInstance().cancel();
            switch (msg.what) {
                case REQUEST_GAMES_ALL_SUCCESS:// 获取所有游戏列表成功
                    String result = (String)msg.obj;
                    try {
                        GameEntity entity = mGson.fromJson(result, GameEntity.class);
                        List<PhotoEntity> entities = entity.getGame_list();
                        nameadapter.refreshAdapter(entities);
                        if (entities != null && entities.size() > 0) {
                            mSp_game_name.setSelection(0, true);
                        }
                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                        ToastUtil.Toast(mContext, "网络错误");
                    }

                    break;
                case 1: {
                    String result1 = (String)msg.obj;
                    try {
                        Game_rtEntity entity2 = mGson.fromJson(result1, Game_rtEntity.class);
                        servers = entity2.getData().getGm_server();
                        pattern = entity2.getData().getGm_pattern();
                        grade = entity2.getData().getGm_grade();
                        yz_title = entity2.getData().getGm_name();
                        yz_img = entity2.getData().getGm_img();
                        setNull();
                        initset(servers, pattern, grade);

                        cymss.add("我给钱");
                        cymss.add("我赚钱");
                        adaptersp.refreshAdapter(servers);
                        ptadapter.refreshAdapter(pattern);
                        djadapter.refreshAdapter(grade);
                        cymsadapter.notifyDataSetChanged();
                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                        ToastUtil.Toast(mContext, "网络错误");
                    }
                }
                    break;
                case 2: {
                    String result3 = (String)msg.obj;
                    try {
                        CyzfEntity entity5 = mGson.fromJson(result3, CyzfEntity.class);
                        peples = entity5.getPeple();
                        moneys = entity5.getMoney();
                        waittimes = entity5.getWaittime();
                        continuetimes = entity5.getContinuetime();
                        initset_(moneys, peples, waittimes, continuetimes);
                        khrsadapter.refreshAdapter(peples);
                        ddsjadapter.refreshAdapter(waittimes);
                        cxsjadapter.refreshAdapter(continuetimes);
                        cyfyadapter.refreshAdapter(moneys);
                    } catch (JsonSyntaxException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        ToastUtil.Toast(mContext, "网络错误");
                    }
                }
                    break;
                case 3: {
                    try {
                        String result4 = (String)msg.obj;
                        CyzfEntity entity4 = mGson.fromJson(result4, CyzfEntity.class);
                        peples = entity4.getPeple();
                        moneys = entity4.getMoney();
                        waittimes = entity4.getWaittime();
                        continuetimes = entity4.getContinuetime();
                        initset_(moneys, peples, waittimes, continuetimes);
                        khrsadapter.refreshAdapter(peples);
                        ddsjadapter.refreshAdapter(waittimes);
                        cxsjadapter.refreshAdapter(continuetimes);
                        cyfyadapter.refreshAdapter(moneys);
                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                        ToastUtil.Toast(mContext, "网络错误");
                    }
                }
                    break;
                case 4: {
                    C_DataEntity entity1 = (C_DataEntity)msg.obj;
                    Intent intent = new Intent(ActivityYuezhan.this, ZfListActivity.class);
                    intent.putExtra("entity1", entity1);
                    startActivity(intent);
                }
                    break;
                case 5: {
                    ToastUtil.Toast(ActivityYuezhan.this, "网络出错");
                }
                    break;
                case 6: {
                    ConfirmEntity entity6 = (ConfirmEntity)msg.obj;
                    String erro = entity6.getErrors();
                    ToastUtil.Toast(ActivityYuezhan.this, erro);
                }
                    break;

            }
        };
    };

}
