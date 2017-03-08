
package com.jishang.bimeng.ui;

import java.lang.ref.WeakReference;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public abstract class UIHandler extends Handler implements Parcelable {

    private static final String loggerName = "UIHandler";

    private WeakReference<Activity> mActivity;

    private String componentName = "";

    public static final int REFRESH_UI = 1005;// 刷新UI

    public static final int NETWORK_ERRORS = -10000;// 网络错误

    public static final int HAVE_NEW_VERSION = 101;// 有客户端有新版本

    public static final int REQUEST_FRIENDS_SUCCESS = 102;// 获取好友列表成功

    public static final int REQUEST_FRIENDS_FAILED = -102;// 获取好友列表失败

    public static final int REQUEST_CONVERSATION_SUCCESS = 103;// 获取会话成功

    public static final int REQUEST_CONVERSATION_FAILED = -103;// 获取会话失败

    public static final int REQUEST_PAY_SUCCESS = 104;// 支付成功

    public static final int REQUEST_PAY_FAILED = -104;// 支付失败

    public static final int BALL_GAMES_NEW_LOAD_SUCCESS = 1002;// 获取所有开黑

    public static final int REQUEST_GAMES_ALL_SUCCESS = 1003;// 获取发起开黑信息成功

    public static final int REQUEST_MY_LAUNCH_BALL_GAMES_SUCCESS = 1006;// 获取我发起的开黑信息

    public static final int REQUEST_MY_LAUNCH_BALL_GAMES_BEFORE_SUCCESS = 1007;// 获取我曾经发起的开黑信息

    public static final int WEIXIN_PAY_FAILED = 1008;// 微信支付失败

    public static final int WEIXIN_PAY_SUCCESS = 1009;// 微信支付成功

    public static final int INIT_RED_ENVELOPE_TIME = 1011;// 初始化抢红包时间

    public static final int REFRESH_REMAIN_TIME_PROGRESS = 1012;// 刷新倒计时提示进度条

    public static final int RED_ENVELOPE_REMAIN_TIME_END = 1013;// 抢红包倒计时结束

    public static final int UI_FRESH_ALIPAY_PAY_SUCCESS = 62;// 支付宝SDK支付成功

    public static final int UI_FRESH_AD = 63;// 广告

    public UIHandler(Activity activity) {
        this.mActivity = new WeakReference<Activity>(activity);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Activity getActivity() {
        return mActivity.get();
    }

    public String getComponentName() {
        return componentName;
    }

    @Override
    public void handleMessage(Message paramMessage) {
        Log.d(loggerName, "Recieved message  " + paramMessage.what);
        onMessage(paramMessage);
    }

    public abstract void onMessage(Message paramMessage);

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    @Override
    public void writeToParcel(Parcel paramParcel, int paramInt) {
    }

}
