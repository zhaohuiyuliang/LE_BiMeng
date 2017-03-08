
package com.jishang.bimeng.activity.addfd.fdlist;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jishang.bimeng.R;
import com.jishang.bimeng.activity.addfd.AdListActivity;
import com.jishang.bimeng.activity.addfd.SearchFriendActivity;
import com.jishang.bimeng.activity.base.BaseActivity;
import com.jishang.bimeng.activity.hxchat.ActivityChat;
import com.jishang.bimeng.entity.chat.Fdlist.FriendEntity;
import com.jishang.bimeng.entity.chat.addlist.AdListEntity;
import com.jishang.bimeng.entity.chat.addlist.AdList_dataEntity;
import com.jishang.bimeng.ui.UIHandler;
import com.jishang.bimeng.ui.adapter.AdapterFriends;
import com.jishang.bimeng.ui.adapter.AdapterFriends.IModelFriends;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.ToastUtil;
import com.jishang.bimeng.utils.Internet.MyHttpRequest;
import com.jishang.bimeng.utils.dialog.DialogUtils;
import com.jishang.bimeng.utils.url.UrlUtils;

/**
 * "好友列表"UI
 * 
 * @author wangliang Jul 14, 2016
 */
public class FdListActivity extends BaseActivity implements OnClickListener, IModelFriends {
    private ListView mList;

    private Gson mGson;

    private AdapterFriends adapterFriends;

    private TextView mTv_name;

    private Context mContext;

    private String msgnum;

    private TextView mTv_msgnum;

    // 请求添加你为朋友的人的列表
    private List<AdList_dataEntity> entities_num = new ArrayList<AdList_dataEntity>();

    private RelativeLayout mRl_addfds;

    @Override
    public int initResource() {
        return R.layout.activity_fdlist;
    }

    LinearLayout ll_add_friends;

    @Override
    public void initView() {
        mContext = this;
        Intent intent = getIntent();
        if (intent != null) {
            String back = intent.getStringExtra("back");
            ((TextView)findViewById(R.id.tv_back_to_activity)).setText(back);
        }

        mGson = new Gson();

        mList = (ListView)findViewById(R.id.activity_fdlist_list);
        mTv_name = (TextView)findViewById(R.id.activity_fdlist_name);
        mRl_addfds = (RelativeLayout)findViewById(R.id.activity_fdlist_addfs);

        mTv_name.setText("好友列表");
        adapterFriends = new AdapterFriends(this, this);

        LayoutInflater mLayoutInflater = LayoutInflater.from(this);
        View mHeaderView = mLayoutInflater.inflate(R.layout.listview_head, null);
        mTv_msgnum = (TextView)mHeaderView.findViewById(R.id.activity_fdlist_msg_number);
        ll_add_friends = (LinearLayout)mHeaderView.findViewById(R.id.ll_add_friends);
        mList.addHeaderView(mHeaderView);
        mList.setAdapter(adapterFriends);
        ll_add_friends.setOnClickListener(this);
        loadNewFriendsNum();
        uiHandler = new FdListHandler(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        application.getController().doRequestFriends(uiHandler);
    }

    /**
     * 获取请求添加你为好友的数量
     */
    public void loadNewFriendsNum() {
        final String token = new SharUtil(mContext).getAccess_token();

        new Thread() {
            public void run() {
                List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
                params.add(new BasicNameValuePair("11", "11"));
                String url = UrlUtils.WAIT_FRIENDSHIP;
                Log.i("url--", url);
                String result = MyHttpRequest.getHttpPostBasic(url, params, token);
                if (result != null) {
                    Log.i("result--", result.toString());
                    try {
                        AdListEntity entity = mGson.fromJson(result, AdListEntity.class);
                        if (entity.getStatus() == 0) {
                            uiHandler.sendEmptyMessage(1);
                        } else if (entity.getStatus() == 1) {
                            entities_num = entity.getData();
                            uiHandler.sendEmptyMessage(2);
                        }
                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                        uiHandler.sendEmptyMessage(0);
                    }

                }

            };
        }.start();

    }

    @Override
    public void initData() {

    }

    class FdListHandler extends UIHandler {
        FdListActivity activity = (FdListActivity)getActivity();

        public FdListHandler(Activity activity) {
            super(activity);
        }

        public void onMessage(android.os.Message msg) {
            switch (msg.what) {
                case REQUEST_FRIENDS_SUCCESS:
                    DialogUtils.getInstance().cancel();
                    List<FriendEntity> entities = (ArrayList<FriendEntity>)msg.obj;
                    adapterFriends.setData(entities);
                    break;
                case 1:
                    DialogUtils.getInstance().cancel();
                    ToastUtil.Toast(activity, "无数据，清先添加");
                    break;
                case 2:
                    if (entities_num != null && !entities_num.isEmpty()) {
                        mTv_msgnum.setVisibility(View.VISIBLE);
                        mTv_msgnum.setText(entities_num.size() + "");
                    } else {
                        mTv_msgnum.setVisibility(View.GONE);
                    }
                    break;

            }
        };
    };

    @Override
    public void addListener() {
        mRl_addfds.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_fdlist_addfs: {
                Intent intent = new Intent(mContext, SearchFriendActivity.class);
                intent.putExtra("back", "好友列表");
                startActivity(intent);
            }
                break;
            case R.id.ll_add_friends: {// 新的朋友
                Intent intent = new Intent(mContext, AdListActivity.class);
                startActivity(intent);
            }
                break;

        }

    }

    public void back(View v) {
        finish();
    }

    @Override
    public void setOnClickListener(FriendEntity entity) {
        Intent intent = new Intent(FdListActivity.this, ActivityChat.class);
        intent.putExtra("entity", entity);
        startActivity(intent);
    }

}
