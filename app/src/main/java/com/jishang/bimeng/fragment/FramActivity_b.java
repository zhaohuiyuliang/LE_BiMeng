
package com.jishang.bimeng.fragment;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.widget.Toast;

import com.easemob.EMCallBack;
import com.easemob.EMChatRoomChangeListener;
import com.easemob.EMEventListener;
import com.easemob.EMNotifierEvent;
import com.easemob.chat.CmdMessageBody;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMMessage;
import com.easemob.util.EMLog;
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
import com.jishang.bimeng.utils.huanxin.HXNotifier;
import com.jishang.bimeng.utils.huanxin.HXNotifier.HXNotificationInfoProvider;
import com.jishang.bimeng.utils.url.UrlUtils;
import com.umeng.analytics.MobclickAgent;

/**
 * @author Administrator
 */
public class FramActivity_b extends FragmentActivity implements OnCheckedChangeListener,
        HXNotificationInfoProvider, EMEventListener {
    private RadioGroup rg;

    private FragmentBallGames mYuezhan;

    private FragmentRedEnvelope mHongbao;

    private FragmentVideo mShipin;

    private Fragment_yuewan mYuewan;

    private FragmentMessage mXiaoxi;

    private String TAG = "FramActivity";

    private final String mPageName = "MainActivity";// 页面名字

    private Context mContext;

    public static FramActivity_b instance = null;

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
        initEventListener();

        /* 设置友盟统计 */

    }

    /**
     * 全局事件监听 因为可能会有UI页面先处理到这个消息，所以一般如果UI页面已经处理，这里就不需要再次处理 activityList.size()
     * <= 0 意味着所有页面都已经在后台运行，或者已经离开Activity Stack
     */
    protected void initEventListener() {
        eventListener = new EMEventListener() {
            private BroadcastReceiver broadCastReceiver = null;

            @Override
            public void onEvent(EMNotifierEvent event) {
                EMMessage message = null;
                if (event.getData() instanceof EMMessage) {
                    message = (EMMessage)event.getData();
                    EMLog.d(TAG,
                            "receive the event : " + event.getEvent() + ",id : "
                                    + message.getMsgId());
                }

                switch (event.getEvent()) {
                    case EventNewMessage:
                        // 应用在后台，不需要刷新UI,通知栏提示新消息
                        // hxfier.viberateAndPlayTone(message);
                        hxfier.onNewMsg(message);
                        // if(MainActivity_demo.instance==null){
                        // MainActivity_demo.instance.refresh();
                        // }
                        break;
                    // below is just giving a example to show a cmd toast, the
                    // app should not follow this
                    // so be careful of this
                    case EventNewCMDMessage: {

                        EMLog.d(TAG, "收到透传消息");
                        // 获取消息body
                        CmdMessageBody cmdMsgBody = (CmdMessageBody)message.getBody();
                        final String action = cmdMsgBody.action;// 获取自定义action

                        // 获取扩展属性 此处省略
                        // message.getStringAttribute("");
                        EMLog.d(TAG,
                                String.format("透传消息：action:%s,message:%s", action,
                                        message.toString()));
                        final String str = "dfdf";

                        final String CMD_TOAST_BROADCAST = "easemob.demo.cmd.toast";
                        IntentFilter cmdFilter = new IntentFilter(CMD_TOAST_BROADCAST);

                        if (broadCastReceiver == null) {
                            broadCastReceiver = new BroadcastReceiver() {
                                @Override
                                public void onReceive(Context context, Intent intent) {
                                    // TODO Auto-generated method stub
                                    Toast.makeText(mContext, intent.getStringExtra("cmd_value"),
                                            Toast.LENGTH_SHORT).show();
                                }
                            };

                            // 注册广播接收者
                            mContext.registerReceiver(broadCastReceiver, cmdFilter);
                        }
                        Intent broadcastIntent = new Intent(CMD_TOAST_BROADCAST);
                        broadcastIntent.putExtra("cmd_value", str + action);
                        mContext.sendBroadcast(broadcastIntent, null);

                        break;
                    }
                    case EventDeliveryAck:
                        message.setDelivered(true);
                        break;
                    case EventReadAck:
                        message.setAcked(true);
                        break;
                    // add other events in case you are interested in
                    default:
                        break;
                }

            }
        };

        EMChatManager.getInstance().registerEventListener(eventListener);

        EMChatManager.getInstance().addChatRoomChangeListener(new EMChatRoomChangeListener() {
            private final static String ROOM_CHANGE_BROADCAST = "easemob.demo.chatroom.changeevent.toast";

            private final IntentFilter filter = new IntentFilter(ROOM_CHANGE_BROADCAST);

            private boolean registered = false;

            private void showToast(String value) {
                if (!registered) {
                    // 注册广播接收者
                    mContext.registerReceiver(new BroadcastReceiver() {

                        @Override
                        public void onReceive(Context context, Intent intent) {
                            Toast.makeText(mContext, intent.getStringExtra("value"),
                                    Toast.LENGTH_SHORT).show();
                        }

                    }, filter);

                    registered = true;
                }

                Intent broadcastIntent = new Intent(ROOM_CHANGE_BROADCAST);
                broadcastIntent.putExtra("value", value);
                mContext.sendBroadcast(broadcastIntent, null);
            }

            @Override
            public void onChatRoomDestroyed(String roomId, String roomName) {
                showToast(" room : " + roomId + " with room name : " + roomName + " was destroyed");
                Log.i("info", "onChatRoomDestroyed=" + roomName);
            }

            @Override
            public void onMemberJoined(String roomId, String participant) {
                showToast("member : " + participant + " join the room : " + roomId);
                Log.i("info", "onmemberjoined=" + participant);

            }

            @Override
            public void onMemberExited(String roomId, String roomName, String participant) {
                showToast("member : " + participant + " leave the room : " + roomId
                        + " room name : " + roomName);
                Log.i("info", "onMemberExited=" + participant);

            }

            @Override
            public void onMemberKicked(String roomId, String roomName, String participant) {
                showToast("member : " + participant + " was kicked from the room : " + roomId
                        + " room name : " + roomName);
                Log.i("info", "onMemberKicked=" + participant);

            }

        });
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
            AlertDialog.Builder builder = new AlertDialog.Builder(FramActivity_b.this);
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
                    Intent intent = new Intent(FramActivity_b.this, UpdateService.class);
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

        }

    }

    /*
     * // 事件分发
     * @Override public boolean onKeyDown(int keyCode, KeyEvent event) { if
     * (keyCode == KeyEvent.KEYCODE_BACK) { Hook(); return true; } return
     * super.onKeyDown(keyCode, event); }
     *//**
     * 点击返回键的时候执行的方法
     */
    /*
     * private void Hook() { final AlertDialog dlg = new
     * AlertDialog.Builder(this).create(); dlg.show(); dlg.setMessage("信息提示");
     * Window window = dlg.getWindow(); // *** 主要就是在这里实现这种效果的. //
     * 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容
     * window.setContentView(R.layout.shrew_exit_dialog); // 为确认按钮添加事件,执行退出应用操作
     * ImageButton ok = (ImageButton) window.findViewById(R.id.btn_ok);
     * ok.setOnClickListener(new View.OnClickListener() { public void
     * onClick(View v) { dlg.cancel(); exitApp(); // 退出应用... } }); //
     * 关闭alert对话框架 ImageButton cancel = (ImageButton)
     * window.findViewById(R.id.btn_cancel); cancel.setOnClickListener(new
     * View.OnClickListener() { public void onClick(View v) { dlg.cancel(); }
     * }); }
     *//**
     * 点击确定退出程序
     */
    /*
     * public void exitApp() { MobclickAgent.onKillProcess(mContext);//
     * 程序结束时通知友盟 finish(); // int pid = android.os.Process.myPid(); //
     * android.os.Process.killProcess(pid); }
     */
}
