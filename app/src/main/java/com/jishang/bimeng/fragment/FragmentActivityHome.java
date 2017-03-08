
package com.jishang.bimeng.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMConversation.EMConversationType;
import com.igexin.sdk.PushManager;
import com.jishang.bimeng.BimmoApplication;
import com.jishang.bimeng.R;
import com.jishang.bimeng.activity.wode.GerenSzActivity;
import com.jishang.bimeng.entity.gengxin.VerEntity;
import com.jishang.bimeng.fragment.main.FragmentBallGames;
import com.jishang.bimeng.fragment.main.FragmentMessage;
import com.jishang.bimeng.fragment.main.FragmentRedEnvelope;
import com.jishang.bimeng.fragment.main.FragmentVideo;
import com.jishang.bimeng.service.UpdateService;
import com.jishang.bimeng.ui.BaseFragmentActivity;
import com.jishang.bimeng.ui.UIHandler;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.ToastUtil;
import com.jishang.bimeng.utils.radiongroup.NestRadioGroup;
import com.umeng.analytics.MobclickAgent;

/**
 * 程序主界面UI
 * 
 * @author Administrator
 */
public class FragmentActivityHome extends BaseFragmentActivity implements
        com.jishang.bimeng.utils.radiongroup.NestRadioGroup.OnCheckedChangeListener,
        Thread.UncaughtExceptionHandler {
    private String TAG = "FramActivity";

    private BimmoApplication application = BimmoApplication.getApplication();

    private NestRadioGroup rg;

    private FragmentBallGames mYuezhan;

    private FragmentRedEnvelope mHongbao;

    private FragmentVideo mShipin;

    private FragmentMessage mXiaoxi;

    private final String mPageName = "MainActivity";// 页面名字

    private Context mContext;

    public static FragmentActivityHome instance = null;

    private VerEntity entity;

    /**
     * 红点提示未读聊天消息个数
     */
    private TextView tv_unread_chat_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_framgment_home);
        homeHandler = new HomeHandler(this);
        initView();

        String gt_cid = PushManager.getInstance().getClientid(this);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        mYuezhan = new FragmentBallGames();
        transaction.add(R.id.fragment_ball_games, mYuezhan);
        mXiaoxi = new FragmentMessage();
        transaction.add(R.id.fragment_message, mXiaoxi);
        mHongbao = new FragmentRedEnvelope();
        transaction.add(R.id.fragment_red_envelope, mHongbao);
        mShipin = new FragmentVideo();
        transaction.add(R.id.fragment_video, mShipin);
        transaction.commit();
        setDefaultFragment();

        registerHyReceiver();
        getlocalVersion();
    }

    public void initView() {
        mContext = this;
        instance = this;
        rg = (NestRadioGroup)findViewById(R.id.activity_fram_rg);
        tv_unread_chat_num = (TextView)findViewById(R.id.fragment_yuezhan_msg_number);
        rg.setOnCheckedChangeListener(this);
        /* 设置友盟统计 */
        MobclickAgent.setDebugMode(true);
        MobclickAgent.openActivityDurationTrack(false);
        MobclickAgent.updateOnlineConfig(this);
        /* 设置友盟统计 */

    }

    /**
     * 刷新UI
     */
    private void refreshUI() {
        updateUnreadLabel();
        if (mXiaoxi != null && !mXiaoxi.isHidden()) {
            mXiaoxi.refreshUI();
        }
        if (mYuezhan != null && !mYuezhan.isHidden()) {
            mYuezhan.refreshUI();
        }
        if (mHongbao != null && mHongbao.isHidden()) {
            mHongbao.refreshUI();
        }
    }

    /**
     * 设置默认显示的UI
     */
    public void setDefaultFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        hide(transaction);
        transaction.show(mYuezhan);
        transaction.commit();
    }

    /**
     * 登录环信服务器
     */
    public void loginHyServer() {
        SharUtil sharUtil = new SharUtil(mContext);
        final String userName = sharUtil.getH_username();
        final String userPwd = sharUtil.getH_password();
        if (sharUtil.getH_username() != null) {
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

    /**
     * 刷新未读消息数
     */
    private void updateUnreadLabel() {
        int count = getUnreadMsgCountTotal();
        if (count > 0) {
            tv_unread_chat_num.setText(String.valueOf(count));
            tv_unread_chat_num.setVisibility(View.VISIBLE);
        } else {
            tv_unread_chat_num.setVisibility(View.GONE);
        }
    }

    /**
     * 获取未读消息数
     * 
     * @return
     */
    private int getUnreadMsgCountTotal() {
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
    public void updateVersion(final VerEntity entity) {
        if (getServiceVersion() > getlocalVersion()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(FragmentActivityHome.this);
            builder.setTitle("检查到新版本");
            builder.setMessage(entity.getData().getContent());
            builder.setNegativeButton(R.string.cancel, new OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setPositiveButton(R.string.ok, new OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(FragmentActivityHome.this, UpdateService.class);
                    intent.putExtra("entity", entity);
                    startService(intent);
                }
            });
            builder.create().show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case RESULT_OK: {// 由发布UI跳转到
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                hide(transaction);
                transaction.show(mXiaoxi);
                transaction.commit();
                refreshUI();
            }

                break;
            case GerenSzActivity.FINISH_EXIT: {
                finish();
            }
                break;

            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        application.setUIHandler(homeHandler);
        refreshUI();
        /**
         * 友盟统计用 onResume
         */
        MobclickAgent.onPageStart(mPageName);
        MobclickAgent.onResume(mContext);
        /**
         * 版本更新
         */
        application.getController().doVersionUpdateCheck(homeHandler);
        loginHyServer();
    }

    /**
     * 友盟统计用 onPause
     */
    @Override
    protected void onPause() {
        super.onPause();
        application.setUIHandler(null);
        MobclickAgent.onPageEnd(mPageName);
        MobclickAgent.onPause(mContext);
    }

    @Override
    public void onCheckedChanged(NestRadioGroup group, int checkedId) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        hide(transaction);
        switch (checkedId) {
            case R.id.activity_fram_yuezhan:

                transaction.show(mYuezhan);

                break;
            case R.id.activity_fram_hongbao:

                transaction.show(mHongbao);
                break;
            case R.id.activity_fram_shipin: {
                transaction.show(mShipin);
            }
                break;
            case R.id.activity_fram_xiaoxi:
                transaction.show(mXiaoxi);
                mXiaoxi.updateUnreadLabel();
                break;
            default:
                break;
        }
        transaction.commit();
        refreshUI();
    }

    private void hide(FragmentTransaction transaction) {
        if (mXiaoxi != null)
            transaction.hide(mXiaoxi);
        if (mHongbao != null)
            transaction.hide(mHongbao);
        if (mShipin != null)
            transaction.hide(mShipin);
        if (mYuezhan != null)
            transaction.hide(mYuezhan);

    }

    private HomeHandler homeHandler;

    class HomeHandler extends UIHandler {

        FragmentActivityHome fragmentActivityHome = (FragmentActivityHome)getActivity();

        public HomeHandler(Activity activity) {
            super(activity);
        }

        public void onMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    String erros = (String)msg.obj;
                    ToastUtil.Toast(mContext, erros);
                    break;
                case HAVE_NEW_VERSION:// 有新版本
                    entity = (VerEntity)msg.obj;
                    fragmentActivityHome.updateVersion(entity);
                    break;
                case NETWORK_ERRORS:
                    ToastUtil.Toast(mContext, "网络错误");
                    break;
                case REFRESH_UI:
                    fragmentActivityHome.refreshUI();
                    break;
                default:
                    break;

            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterHyReceiver();
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        System.exit(1);
    }

}
