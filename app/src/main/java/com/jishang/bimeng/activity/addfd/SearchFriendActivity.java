
package com.jishang.bimeng.activity.addfd;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.gson.Gson;
import com.jishang.bimeng.R;
import com.jishang.bimeng.activity.base.BaseActivity;
import com.jishang.bimeng.entity.chat.UserDetailEntity;
import com.jishang.bimeng.entity.chat.UserDetail_dataEntity;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.ToastUtil;
import com.jishang.bimeng.utils.Internet.MyHttpRequest;
import com.jishang.bimeng.utils.dialog.DialogUtils;
import com.jishang.bimeng.utils.url.UrlUtils;

/**
 * "搜索好友"UI
 * 
 * @author wangliang Jul 14, 2016
 */
public class SearchFriendActivity extends BaseActivity implements OnClickListener {
    private EditText mEdt_content;

    private Button mBt_search;

    private List<BasicNameValuePair> params;

    private RelativeLayout mRel_tiejia;

    private TextView mTvusername;

    private Button mBt_tiejia;

    private UserDetailEntity entity = new UserDetailEntity();

    private TextView mTv_name;

    private Gson mGson;

    private UserDetail_dataEntity entitie_data = new UserDetail_dataEntity();

    private Context mContext;

    @Override
    public int initResource() {
        return R.layout.activity_searchfd;
    }

    @Override
    public void initView() {
        mContext = this;
        Intent intent = getIntent();
        if (intent != null) {
            String back = intent.getStringExtra("back");
            ((TextView)findViewById(R.id.tv_back_to_activity)).setText(back);
        }
        mEdt_content = (EditText)findViewById(R.id.activity_addfd_edt_content);
        mBt_search = (Button)findViewById(R.id.activity_addfd_bt_search);
        mRel_tiejia = (RelativeLayout)findViewById(R.id.rel_tiejia);
        mTvusername = (TextView)findViewById(R.id.activity_addfd_tv_nickname);
        mBt_tiejia = (Button)findViewById(R.id.activity_addfd_bt_chakan);
        mTv_name = (TextView)findViewById(R.id.activity_search_name);
        params = new ArrayList<BasicNameValuePair>();
        mGson = new Gson();
        mTv_name.setText("搜索好友");
    }

    @Override
    public void initData() {
        // TODO Auto-generated method stub

    }

    @Override
    public void addListener() {
        mBt_search.setOnClickListener(this);
    }

    /**
     * 返回
     * 
     * @param view
     */
    public void back(View view) {
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_addfd_bt_search:
                searchFriendByPhone();
                break;
            case R.id.activity_addfd_bt_chakan: {
                Intent intent = new Intent(SearchFriendActivity.this, AddFriendActivity.class);
                intent.putExtra("entity", entity);
                intent.putExtra("back", "好友列表");
                startActivity(intent);
                finish();
            }
                break;
        }

    }

    public void searchFriendByPhone() {
        DialogUtils.getInstance().createNotifier(mContext, "正在加载中");
        final String token = new SharUtil(SearchFriendActivity.this).getAccess_token();
        final String username = mEdt_content.getText().toString().trim();
        params.add(new BasicNameValuePair("username", username));
        new Thread() {
            public void run() {
                String url = UrlUtils.SEARCH;
                Log.e("token", token + "---" + username);
                String result = MyHttpRequest.getHttpPostBasic(url, params, token);
                if (result != null) {
                    Log.e("result", result.toString());
                    entity = mGson.fromJson(result, UserDetailEntity.class);
                    entitie_data = entity.getData();
                    if (entity.getStatus() == 0) {
                        Message msg = new Message();
                        msg.obj = entity.getErrors();
                        msg.what = 0;
                        handler.sendMessage(msg);
                    } else if (entity.getStatus() == 1) {
                        handler.sendEmptyMessage(1);
                    } else if (entity.getStatus() == 2) {
                        handler.sendEmptyMessage(2);
                    }

                }

            };
        }.start();
    }

    /**
     * 0为错误 1为可添加好友 2为已成为好友
     */
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            DialogUtils.getInstance().cancel();
            switch (msg.what) {
                case 0:
                    String erros = (String)msg.obj;
                    ToastUtil.Toast(mContext, erros);
                    break;
                case 1:
                    setText();
                    break;
                case 2:
                    setText();
                    break;

            }
        };
    };

    public void setText() {
        mRel_tiejia.setVisibility(View.VISIBLE);
        mTvusername.setText(entitie_data.getUsername());
        Log.i("entity", entity.toString());
        mBt_tiejia.setOnClickListener(this);
    }

    public class ViewHolder {
        public ImageView image;

        public TextView text;

        public Button bt;

        public ToggleButton tg;
    }

}
