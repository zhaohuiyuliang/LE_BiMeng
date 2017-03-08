
package com.jishang.bimeng.fragment;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.easemob.EMCallBack;
import com.easemob.EMEventListener;
import com.easemob.EMNotifierEvent;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMMessage;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jishang.bimeng.R;
import com.jishang.bimeng.activity.hxchat.ActivityChat;
import com.jishang.bimeng.entity.chat.Fdlist.FriendEntity;
import com.jishang.bimeng.entity.gengxin.VerEntity;
import com.jishang.bimeng.fragment.main.FragmentBallGames;
import com.jishang.bimeng.fragment.main.FragmentMessage;
import com.jishang.bimeng.fragment.main.FragmentRedEnvelope;
import com.jishang.bimeng.fragment.main.FragmentVideo;
import com.jishang.bimeng.fragment.main.Fragment_yuewan;
import com.jishang.bimeng.service.UpdateService;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.ToastUtil;
import com.jishang.bimeng.utils.Internet.MyHttpRequest;
import com.jishang.bimeng.utils.huanxin.DemoHXSDKHelper;
import com.jishang.bimeng.utils.huanxin.HXNotifier;
import com.jishang.bimeng.utils.huanxin.HXNotifier.HXNotificationInfoProvider;
import com.jishang.bimeng.utils.url.UrlUtils;
import com.umeng.analytics.MobclickAgent;

/**
 * @author Administrator
 */
public class FramActivity_b1 extends FragmentActivity implements OnCheckedChangeListener,
        EMEventListener, HXNotificationInfoProvider {
    private RadioGroup rg;

    private FragmentBallGames mYuezhan;

    private FragmentRedEnvelope mHongbao;

    private FragmentVideo mShipin;

    private Fragment_yuewan mYuewan;

    private FragmentMessage mXiaoxi;

    private String TAG = "FramActivity";

    private final String mPageName = "MainActivity";// 页面名字

    private Context mContext;

    public static FramActivity_b1 instance = null;

    private List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();

    private Gson mGson;

    private VerEntity entity = new VerEntity();

    private HXNotifier hxfier;

    protected EMEventListener eventListener = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_framgment_home);

        initView();
        setDefaultFragment();
        mGson = new Gson();
        getlocalVersion();
        getMsg();

    }

    public void initView() {
        mContext = this;
        instance = this;
        // IntentFilter inviteIntentFilter = new
        // IntentFilter(EMChatManager.getInstance().getContactInviteEventBroadcastAction());
        // inviteIntentFilter.addAction("android.intent.action.MY_BROADCAST");
        // registerReceiver(contactInviteReceiver, inviteIntentFilter);
        //
        // EMContactManager.getInstance().setContactListener(new
        // MyContactListener());
        rg = (RadioGroup)findViewById(R.id.activity_fram_rg);
        rg.setOnCheckedChangeListener(this);
        /* 设置友盟统计 */
        MobclickAgent.setDebugMode(true);
        MobclickAgent.openActivityDurationTrack(false);
        MobclickAgent.updateOnlineConfig(this);
        hxfier = new HXNotifier().init(mContext);
        hxfier.setNotificationInfoProvider(this);

        /* 设置友盟统计 */

    }

    /**
     * 登录环信服务器
     */
    public void setHx() {
        SharUtil sharUtil = new SharUtil(mContext);
        final String userName = sharUtil.getH_username();
        final String userPwd = sharUtil.getH_password();
        if (sharUtil.getH_username() != null) {
            Log.e("userName", userName);
            EMChatManager.getInstance().login(userName, userPwd, new EMCallBack() {

                @Override
                public void onSuccess() {
                    EMChatManager.getInstance().loadAllConversations();

                }

                @Override
                public void onProgress(int arg0, String arg1) {

                }

                @Override
                public void onError(int arg0, String arg1) {
                    Log.e("111", arg1);

                }
            });
        } else {
            ToastUtil.Toast(mContext, getString(R.string.huan_chat_erro));
        }
    }

    public void setDefaultFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        mYuezhan = new FragmentBallGames();
        transaction.replace(R.id.fragment_ball_games, mYuezhan);
        transaction.commit();
    }

    public void getMsg() {
        final String token = "tllB4rNVwnZan7MKhWhLh63ecYY78Nwv";
        params.add(new BasicNameValuePair("1", "1"));
        new Thread() {
            public void run() {
                String url = UrlUtils.BASEURL + "v1/new/android.json";
                String result = MyHttpRequest.getHttpPostBasic(url, params, token);
                if (result != null) {
                    Log.e("result", result.toString());
                    try {
                        entity = mGson.fromJson(result, VerEntity.class);
                        if (entity.getStatus() == 0) {
                            Message msg = new Message();
                            msg.what = 0;
                            msg.obj = entity.getErrors();
                            handler.sendEmptyMessage(0);
                        } else if (entity.getStatus() == 1) {
                            // Log.e("entity", entity.toString());
                            handler.sendEmptyMessage(1);
                        }
                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                        handler.sendEmptyMessage(2);
                    }
                }
            };
        }.start();
    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    String erros = (String)msg.obj;
                    ToastUtil.Toast(mContext, erros);
                    break;
                case 1:
                    updateVersion();
                    break;
                case 2:
                    ToastUtil.Toast(mContext, "网络错误");
                    break;

            }
        };
    };

    /**
     * 获取本地版本号
     * 
     * @return
     */
    public int getlocalVersion() {
        int localversion = 0;
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
            localversion = info.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return localversion;
    }

    /**
     * 获取服务器版本号
     * 
     * @return
     */
    public int getServiceVersion() {
        int serviceversion = Integer.parseInt(entity.getData().getVersion());
        return serviceversion;
    }

    /**
     * 版本更新
     * 
     * @param v
     */
    public void updateVersion() {
        if (getServiceVersion() > getlocalVersion()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(FramActivity_b1.this);
            builder.setTitle("检查到新版本");
            builder.setMessage("是否更新");
            builder.setNegativeButton("取消", new OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.setPositiveButton("确定", new OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(FramActivity_b1.this, UpdateService.class);
                    intent.putExtra("entity", entity);
                    startService(intent);
                    // startService(new Intent(MainActivity.this,
                    // UpdateService.class));
                }
            });
            builder.create().show();
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        switch (checkedId) {
            case R.id.activity_fram_yuezhan:
                if (mYuezhan == null) {
                    mYuezhan = new FragmentBallGames();
                }
                transaction.replace(R.id.fragment_ball_games, mYuezhan);

                break;
            case R.id.activity_fram_hongbao:
                if (mHongbao == null) {
                    mHongbao = new FragmentRedEnvelope();
                }
                transaction.replace(R.id.fragment_ball_games, mHongbao);
                break;
            case R.id.activity_fram_shipin:
                if (mShipin == null) {
                    mShipin = new FragmentVideo();
                }
                transaction.replace(R.id.fragment_ball_games, mShipin);
                break;
            /*
             * case R.id.activity_fram_yuewan: if (mYuewan == null) { mYuewan =
             * new Fragment_yuewan(); } transaction.replace(R.id.activity_fram,
             * mYuewan); break;
             */case R.id.activity_fram_xiaoxi:
                if (mXiaoxi == null) {
                    mXiaoxi = new FragmentMessage();
                }
                transaction.replace(R.id.fragment_ball_games, mXiaoxi);
                break;

        }
        transaction.commit();
    }

    /**
     * 友盟统计用 onResume
     */
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(mPageName);
        MobclickAgent.onResume(mContext);
    }

    /**
     * 友盟统计用 onPause
     */
    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(mPageName);
        MobclickAgent.onPause(mContext);
    }

    @Override
    public void onEvent(EMNotifierEvent event) {

        switch (event.getEvent()) {
            case EventNewMessage: // 普通消息
            {
                EMMessage message = (EMMessage)event.getData();
                // 提示新消息
                // HXSDKHelper.getInstance().getNotifier().onNewMsg(message);
                hxfier.onNewMsg(message);
                break;
            }
            case EventOfflineMessage: {
                break;
            }
            case EventConversationListChanged: {
                break;
            }

            default:
                break;
        }

    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();

        // DemoHXSDKHelper sdkHelper = (DemoHXSDKHelper)
        // DemoHXSDKHelper.getInstance();
        // sdkHelper.pushActivity(FramActivity.this);

        // register the event listener when enter the foreground
        EMChatManager.getInstance().registerEventListener(
                this,
                new EMNotifierEvent.Event[] {
                        EMNotifierEvent.Event.EventNewMessage,
                        EMNotifierEvent.Event.EventOfflineMessage,
                        EMNotifierEvent.Event.EventConversationListChanged
                });

    }

    @Override
    public String getDisplayedText(EMMessage message) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getLatestText(EMMessage message, int fromUsersNum, int messageNum) {
        return null;
    }

    @Override
    public String getTitle(EMMessage message) {
        return null;
    }

    @Override
    public int getSmallIcon(EMMessage message) {
        return 0;
    }

    @Override
    public Intent getLaunchIntent(EMMessage message) {
        // 设置点击通知栏跳转事件
        FriendEntity entity = new FriendEntity();
        Intent intent = new Intent(mContext, ActivityChat.class);
        entity.setUsername("陌生人");
        entity.setHead_img(new SharUtil(mContext).getHead_img());
        entity.setH_username(message.getFrom());
        intent.putExtra("entity", entity);
        return intent;
    }

    @Override
    protected void onStop() {
        EMChatManager.getInstance().unregisterEventListener(this);
        DemoHXSDKHelper sdkHelper = (DemoHXSDKHelper)DemoHXSDKHelper.getInstance();
        super.onStop();
    }

}
