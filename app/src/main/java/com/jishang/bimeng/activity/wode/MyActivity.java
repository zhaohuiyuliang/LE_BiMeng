
package com.jishang.bimeng.activity.wode;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;

import com.jishang.bimeng.R;
import com.jishang.bimeng.activity.base.BaseActivity;
import com.jishang.bimeng.activity.wode.wzf.AllwzfActivity;
import com.jishang.bimeng.activity.yuezhan.lunbo.ActivityAboutBiMeng;
import com.jishang.bimeng.activity.yuezhan.zhudian.QiehuanZdActivity;
import com.jishang.bimeng.entity.wode.MyDataEntity;
import com.jishang.bimeng.entity.yuezhan.yzlist.List_msgEntity;
import com.jishang.bimeng.ui.UIHandler;
import com.jishang.bimeng.ui.adapter.AdapterMygrid;
import com.jishang.bimeng.utils.dialog.DialogUtils;
import com.jishang.bimeng.utils.url.UrlUtils;

/**
 * "我的"UI
 * 
 * @author wangliang Jul 13, 2016
 */
public class MyActivity extends BaseActivity {
    private TextView mTv_name;

    private GridView mGv;

    private AdapterMygrid adapter;

    private MyDataEntity data;

    private final String URL_we = UrlUtils.BASEURL + "about/";

    private List_msgEntity entity;

    private Intent intent;

    @Override
    public int initResource() {
        return R.layout.activity_my;
    }

    @Override
    public void initView() {
        intent = getIntent();
        uiHandler = new MyHandler(this);
        entity = (List_msgEntity)intent.getSerializableExtra("entity");

        mGv = (GridView)findViewById(R.id.activity_wode_gv);
        mTv_name = (TextView)findViewById(R.id.activity_wode_name);
        adapter = new AdapterMygrid(this, entity);
        mGv.setAdapter(adapter);
        mTv_name.setText(R.string.my);
    }

    @Override
    public void initData() {
        loadMyData();

    }

    @Override
    protected void onResume() {
        super.onResume();
        setHandler(uiHandler);
        adapter.setData(data);
    }

    class MyHandler extends UIHandler {
        MyActivity myActivity = (MyActivity)getActivity();

        public MyHandler(Activity activity) {
            super(activity);
        }

        public void onMessage(android.os.Message msg) {
            DialogUtils.getInstance().cancel();
            switch (msg.what) {
                case MY_DATA_LOAD_SUCCESS: {// 请求我的数据成功
                    data = (MyDataEntity)msg.obj;
                    adapter.setData(data);
                }
                    break;
                case MY_DATA_LOAD_FAILED: {// 我的数据加载失败

                }
                    break;
                default:
                    break;
            }
        };
    };

    @Override
    public void addListener() {
        mGv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                switch (position) {
                    case 0: {// 我的红包
                        intent.setClass(MyActivity.this, LinqianActivity.class);
                        intent.putExtra("linqian", data.getChange());
                        startActivity(intent);
                    }
                        break;
                    case 1: {// 我的钱包
                        intent.setClass(MyActivity.this, ActivityWallet.class);
                        intent.putExtra("shouru", data.getIncome());
                        startActivity(intent);
                    }
                        break;
                    case 2: {// 活跃度
                        intent.setClass(MyActivity.this, HuoyueduActivity.class);
                        intent.putExtra("huoyuedu", data.getIntegral());
                        startActivity(intent);
                    }
                        break;
                    case 3: {
                        intent.setClass(MyActivity.this, QiehuanZdActivity.class);
                        intent.putExtra("back", "我");
                        startActivity(intent);
                    }
                        break;
                    case 4: {// 个人设置
                        intent.setClass(MyActivity.this, GerenSzActivity.class);
                        startActivityForResult(intent, 0);
                    }
                        break;
                    case 5: {
                        intent.setClass(MyActivity.this, ActivityAboutBiMeng.class);
                        intent.putExtra("url", URL_we);
                        startActivity(intent);
                    }
                        break;
                    case 6: {
                        intent.setClass(MyActivity.this, AllwzfActivity.class);
                        startActivity(intent);
                    }
                        break;
                    case 7: {// 我的兑换码
                        intent.setClass(MyActivity.this, DuihuanActivity.class);
                        intent.putExtra("back", "我");
                        startActivity(intent);
                    }
                        break;
                    case 8: {// 用户协议
                        intent.setClass(MyActivity.this, ActivityUserAgreement.class);
                        startActivity(intent);
                    }
                        break;

                    case 9: {
                        intent.setClass(MyActivity.this, PwdSettingActivity.class);
                        startActivity(intent);
                    }
                        break;
                    default:
                        break;

                }

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case GerenSzActivity.FINISH_EXIT: {
                setResult(GerenSzActivity.FINISH_EXIT);
                finish();
            }

                break;

            default:
                break;
        }
    }

}
