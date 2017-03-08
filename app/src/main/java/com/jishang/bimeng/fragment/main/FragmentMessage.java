
package com.jishang.bimeng.fragment.main;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMConversation.EMConversationType;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jishang.bimeng.R;
import com.jishang.bimeng.activity.TestActivity;
import com.jishang.bimeng.activity.addfd.fdlist.FdListActivity;
import com.jishang.bimeng.activity.dt.ActivityMydt;
import com.jishang.bimeng.activity.dt.fourway.PullListView;
import com.jishang.bimeng.activity.dt.fourway.PullListView.OnLoadMoreListener;
import com.jishang.bimeng.activity.dt.fourway.PullListView.OnRefreshListener;
import com.jishang.bimeng.activity.dt.fourway.RotateLayout;
import com.jishang.bimeng.activity.hxchat.ActivityChatHistory;
import com.jishang.bimeng.activity.pickphoto.view.PublishActivity;
import com.jishang.bimeng.entity.dt.nw.Dt_newEntity;
import com.jishang.bimeng.entity.dt.nw.Dt_new_dataEntity;
import com.jishang.bimeng.entity.tonyong.TYEntity;
import com.jishang.bimeng.fragment.base.BaseFragment;
import com.jishang.bimeng.ui.UIHandler;
import com.jishang.bimeng.ui.adapter.AdapterFriendDynamic;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.ToastUtil;
import com.jishang.bimeng.utils.Internet.MyHttpRequest;
import com.jishang.bimeng.utils.url.UrlUtils;

/**
 * "消息"UI
 * 
 * @author wangliang Jul 14, 2016
 */
public class FragmentMessage extends BaseFragment implements OnClickListener {
    /**
     * 好友动态列表
     */
    private PullListView refreshlistview;

    private RotateLayout rotateLayout;

    private RelativeLayout fragment_yuezhan_my;

    /**
     * 未读消息红点提示
     */
    private TextView headlayout_msg_number;

    private TextView mTv_hydt, mTv_fbdt, mTv_username, mTv_name;

    private int pageNo = 0;

    private int status = 0;//

    private AdapterFriendDynamic adapterFriendDynamic;

    private EditText mEdt_pinglun;

    private RelativeLayout mRl_pinglun;

    private RelativeLayout mRl_publish;

    private Dt_newEntity pagination;

    private UIHandler handler;

    @Override
    public View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_message, null);
        handler = new FragmentMsgHandler(getActivity());
        initView(view);
        addListener();
        initData(null);
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        adapterFriendDynamic = new AdapterFriendDynamic(getActivity(), mImg_headimg);
        refreshlistview.setAdapter(adapterFriendDynamic);
        pageNo = 1;
        application.getController().doRequestFriendsGynamicData(handler, status, pageNo);
    }

    public void initView(View view) {
        // 使用DisplayImageOptions.Builder()创建DisplayImageOptions
        refreshlistview = (PullListView)view.findViewById(R.id.refreshlistview);
        rotateLayout = (RotateLayout)view.findViewById(R.id.rotateLayout);
        mRl_publish = (RelativeLayout)view.findViewById(R.id.fragment_xiaoxi_publish);
        mTv_name = (TextView)view.findViewById(R.id.fragment_xiaoxi_name);
        mTv_name.setText(R.string.message);
        // 下拉刷新列表Head布局
        View pullView = LayoutInflater.from(getActivity()).inflate(R.layout.headlayout, null);
        mTv_hydt = (TextView)pullView.findViewById(R.id.head_haoyoudt);
        mTv_fbdt = (TextView)pullView.findViewById(R.id.head_fbdt);
        mTv_username = (TextView)pullView.findViewById(R.id.user_name);
        fragment_yuezhan_my = (RelativeLayout)pullView.findViewById(R.id.fragment_yuezhan_my);
        mTv_username.setText(new SharUtil(getActivity()).getUserName());
        mImg_headimg = (ImageView)pullView.findViewById(R.id.headlayout_headimg);
        headlayout_msg_number = (TextView)pullView.findViewById(R.id.headlayout_msg_number);

        refreshlistview.setPullHeaderView(pullView);
        refreshlistview.setPullHeaderViewHeight(100);
        refreshlistview.setRotateLayout(rotateLayout);
        refreshlistview.setCacheColorHint(Color.TRANSPARENT);

        // 以下三个为隐藏的布局
        mRl_pinglun = (RelativeLayout)view.findViewById(R.id.commentLinear);
        mEdt_pinglun = (EditText)view.findViewById(R.id.commentEdit);

    }

    private ImageView mImg_headimg;

    public void addListener() {
        mTv_fbdt.setOnClickListener(this);
        mTv_hydt.setOnClickListener(this);
        fragment_yuezhan_my.setOnClickListener(this);
        mTv_username.setOnClickListener(this);
        mRl_publish.setOnClickListener(this);
        mImg_headimg.setOnClickListener(this);
        // 设置回调监听
        refreshlistview.setOnLoadMoreListener(new OnLoadMoreListener<ListView>() {

            @Override
            public void onLoadMore(PullListView refreshView) {
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        if (pagination == null || pagination.getPagination() == null) {
                            refreshlistview.onCompleteRefresh();
                            return;
                        }
                        status = 2;
                        pageNo++;

                        String total = pagination.getPagination().getTotalCount();
                        int defaultPageSize_count = pagination.getPagination().getDefaultPageSize();
                        int totalcount = Integer.parseInt(total);
                        if (totalcount > defaultPageSize_count) {
                            if (pageNo > (totalcount / defaultPageSize_count + 1)) {
                                refreshlistview.onCompleteRefresh();
                            } else {
                                application.getController().doRequestFriendsGynamicData(handler,
                                        status, pageNo);
                            }
                        } else if (pageNo > 1) {
                            refreshlistview.onCompleteRefresh();
                        } else {
                            application.getController().doRequestFriendsGynamicData(handler,
                                    status, pageNo);
                        }
                    }
                }, 100);
            }

        });
        // 下拉刷新更多数据
        refreshlistview.setOnRefreshListener(new OnRefreshListener<ListView>() {

            @Override
            public void onRefresh(PullListView refreshView) {
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        status = 1;
                        pageNo = 1;
                        application.getController().doRequestFriendsGynamicData(handler, status,
                                pageNo);

                    }
                }, 100);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.headlayout_headimg: {// 个人动态
                Intent intent = new Intent(getActivity(), ActivityMydt.class);
                intent.putExtra("back", "消息");
                startActivity(intent);
            }
                break;
            case R.id.head_fbdt: {// 好友列表
                Intent intent = new Intent(getActivity(), FdListActivity.class);
                intent.putExtra("back", "消息");
                startActivity(intent);
            }
                break;
            case R.id.head_haoyoudt: {
                Intent intent = new Intent(getActivity(), TestActivity.class);
                intent.putExtra("back", "消息");
                // startActivity(intent);
            }
                break;
            case R.id.fragment_yuezhan_my: {// 历史聊天列表/消息
                Intent intent = new Intent(getActivity(), ActivityChatHistory.class);
                intent.putExtra("back", "消息");
                startActivity(intent);
            }
                break;
            case R.id.fragment_xiaoxi_publish: {// 进入发布动态界面
                Intent intent = new Intent(getActivity(), PublishActivity.class);
                intent.putExtra("back", "消息");
                startActivity(intent);
            }
                break;
            default:
                break;

        }

    }

    /**
     * 未读消息红点提示
     */
    public void updateUnreadLabel() {
        int count = getUnreadMsgCountTotal();
        if (count > 0) {
            headlayout_msg_number.setText(String.valueOf(count));
            headlayout_msg_number.setVisibility(View.VISIBLE);
        } else {
            headlayout_msg_number.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 获取未读消息数
     * 
     * @return
     */
    public int getUnreadMsgCountTotal() {
        int unreadMsgCountTotal = 0;
        int chatroomUnreadMsgCount = 0;
        unreadMsgCountTotal = EMChatManager.getInstance().getUnreadMsgsCount();
        for (EMConversation conversation : EMChatManager.getInstance().getAllConversations()
                .values()) {
            if (conversation.getType() == EMConversationType.ChatRoom)
                chatroomUnreadMsgCount = chatroomUnreadMsgCount + conversation.getUnreadMsgCount();
        }
        return unreadMsgCountTotal - chatroomUnreadMsgCount;
    }

    private List<Dt_new_dataEntity> entitiesAll;

    private class FragmentMsgHandler extends UIHandler {
        public FragmentMsgHandler(Activity activity) {
            super(activity);
        }

        public void onMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0: {// 进入消息UI自动刷新
                    Dt_newEntity entity = (Dt_newEntity)msg.obj;
                    pagination = entity;
                    List<Dt_new_dataEntity> entities = entity.getData();
                    entitiesAll = entities;
                    adapterFriendDynamic.setData(entities);
                }
                    break;
                case 1: {// 下拉刷新
                    Dt_newEntity entity = (Dt_newEntity)msg.obj;
                    pagination = entity;
                    List<Dt_new_dataEntity> entities = entity.getData();
                    if (entitiesAll == null) {
                        entitiesAll = entities;
                    } else {
                    }
                    adapterFriendDynamic.setData(entities);
                    refreshlistview.onCompleteRefresh();
                    pageNo = 1;
                }
                    break;
                case 2: {// 上拉刷新
                    Dt_newEntity entity = (Dt_newEntity)msg.obj;
                    pagination = entity;
                    List<Dt_new_dataEntity> entities = entity.getData();
                    if (entitiesAll == null) {
                        entitiesAll = entities;
                    } else {
                        entitiesAll.addAll(entities);
                    }
                    adapterFriendDynamic.setData(entitiesAll);
                    refreshlistview.onCompleteRefresh();
                }
                    break;
                case 3: {
                    mRl_pinglun.setVisibility(View.GONE);
                    mEdt_pinglun.setText("");
                    application.getController()
                            .doRequestFriendsGynamicData(handler, status, pageNo);
                }
                    break;
                case 4: {
                    adapterFriendDynamic.notifyDataSetChanged();
                    application.getController()
                            .doRequestFriendsGynamicData(handler, status, pageNo);
                }
                    break;

                case 5:
                    adapterFriendDynamic.notifyDataSetChanged();
                    break;
                case 6:// 请求处于出现异常
                    break;
                case 7:
                    break;
                case 8: {
                    String erros = (String)msg.obj;
                    ToastUtil.Toast(getActivity(), erros);
                }
                    break;
                case 9: {
                    pagination = (Dt_newEntity)msg.obj;
                    adapterFriendDynamic.setData(pagination.data);
                }
                    break;
                default:
                    break;
            }
        };
    };

    /**
     * @param ucid 璇勮鎺ュ彛
     */
    public void Putpinglun(String ucid) {
        final Gson mGson = new Gson();
        final String token = new SharUtil(getActivity()).getAccess_token();
        String content = mEdt_pinglun.getText().toString().trim();
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("message", content));
        params.add(new BasicNameValuePair("ucmc_id", ucid));
        new Thread() {
            public void run() {
                String url = UrlUtils.BASEURL + "v1/user_content/comment.json?";
                String result = MyHttpRequest.getHttpPostBasic(url, params, token);
                if (result != null) {
                    try {
                        TYEntity entity = mGson.fromJson(result, TYEntity.class);
                        if (entity.getStatus() == 1) {
                            handler.sendEmptyMessage(3);
                        }
                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                    }
                }
            };
        }.start();
    }

    public void Dianzan_huise(final String ucid) {
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        final String token = new SharUtil(getActivity()).getAccess_token();
        params.add(new BasicNameValuePair("1", "1"));
        new Thread() {
            public void run() {
                String url = UrlUtils.BASEURL + "v1/user_content/del_click.json?ucsc_id=" + ucid;
                String result = MyHttpRequest.getHttpPostBasic(url, params, token);
                if (result != null) {
                    handler.sendEmptyMessage(5);
                }

            };
        }.start();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUnreadLabel();
    }

    @Override
    public void refreshUI() {
        updateUnreadLabel();
        application.getController().doRequestFriendsGynamicData(handler, status, pageNo);
    }

}
