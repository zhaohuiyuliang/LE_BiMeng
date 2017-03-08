
package com.jishang.bimeng.net;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.jishang.bimeng.ui.UIHandler;

public class Controller {

    private static final String TAG = "Controller";

    private HandlerThread mHandlerThread;

    private volatile Looper mServiceLooper;

    private static Handler mMessageHandler;

    private static final int MSG_REQUEST_VERSION_UPDATE_CHECK = 1;// 版本更新检查

    private static final int MSG_REQUEST_FRIENDS = 2;// 请求好友列表

    private static final int MSG_REQUEST_CONVERSATION = 3;// 请求历史会话

    private static final int MSG_REQUEST_BALL_GAMES_ALL = 4;// 请求所有开黑

    private static final int MSG_REQUEST_LAUNCH_BALL_GAMES_INFO = 5;// 请求发起开黑信息

    private static final int MSG_UPLOAD_PERSON_HEAD_TO_SERVER = 6;// 上传个人头像到服务器

    private static final int MSG_REQUEST_FRIENDS_DYNAMIC_DATA = 7;// 获取好友动态数据

    private static final int MSG_REQUEST_MY_LAUNCH_BALL_GAME_DATA = 8;// 获取我发起的开黑列表

    private static final int MSG_REQUEST_MY_LAUNCH_BALL_GAME_DATA_BEFORE = 9;// 获取我曾经发起的开黑列表

    private static final int MSG_REQUEST_AD = 10;// 广告

    private static final int MSG_ALIPAY = 48;// 调用支付宝支付SDK支付

    public Controller() {
        if (mHandlerThread == null) {
            mHandlerThread = new HandlerThread("Controller Message Processing Thread");
            mHandlerThread.start();
        }
        if (mHandlerThread != null && !mHandlerThread.isAlive()) {
            mHandlerThread.start();
        }
        mServiceLooper = mHandlerThread.getLooper();
        mMessageHandler = new Handler(mServiceLooper) {
            @Override
            public void handleMessage(Message msg) {
                Controller.this.handleMessage(msg);
            }
        };
    }

    private void handleMessage(Message msg) {
        LoadData task = null;
        switch (msg.what) {
            case MSG_REQUEST_VERSION_UPDATE_CHECK: {
                UIHandler handler = (UIHandler)msg.obj;
                task = new TaskVersionUpdateCheck(handler);
            }
                break;
            case MSG_REQUEST_FRIENDS: {
                UIHandler handler = (UIHandler)msg.obj;
                task = new TaskRequestFriends(handler);
            }
                break;
            case MSG_REQUEST_CONVERSATION: {
                UIHandler handler = (UIHandler)msg.obj;
                task = new TaskRequestConversations(handler);
            }
                break;
            case MSG_REQUEST_BALL_GAMES_ALL: {
                task = new TaskRequestBalkGamesAll(msg);
            }
                break;
            case MSG_REQUEST_LAUNCH_BALL_GAMES_INFO: {
                UIHandler handler = (UIHandler)msg.obj;
                task = new TaskLaunchBallGamesInfo(handler);
            }
                break;
            case MSG_UPLOAD_PERSON_HEAD_TO_SERVER: {
                UIHandler handler = (UIHandler)msg.obj;
                String filePath = msg.getData().getString("filePath");
                task = new TaskUploadHeadBmpToServer(handler, filePath);
            }
                break;
            case MSG_REQUEST_FRIENDS_DYNAMIC_DATA: {
                task = new TaskRequestFriendsGynamicData(msg);
            }
                break;
            case MSG_REQUEST_MY_LAUNCH_BALL_GAME_DATA: {
                UIHandler uiHandler = (UIHandler)msg.obj;
                task = new TaskMyLaunchBallGames(uiHandler);
            }
                break;
            case MSG_REQUEST_MY_LAUNCH_BALL_GAME_DATA_BEFORE: {
                UIHandler uiHandler = (UIHandler)msg.obj;
                task = new TaskMyLaunchBallGamesBF(uiHandler);
            }
                break;
            case MSG_ALIPAY: {// 调用支付宝支付SDK支付
                task = new TaskAlipay(msg, context);
            }
                break;
            case MSG_REQUEST_AD: {// 广告
                UIHandler uiHandler = (UIHandler)msg.obj;
                task = new TaskLoadAD(uiHandler);
            }
                break;
            default:
                break;
        }
        if (task != null) {
            Log.d(TAG, "running task:" + task.getClass().getSimpleName());
            task.run();
        } else {
            Log.d(TAG, "no running task");
        }
    }

    private Context context;

    /**
     * 调用支付宝支付SDK支付
     * 
     * @param uiHandler
     */
    public void doALIPAY(UIHandler uiHandler,
            ProjectRewardPayChooseResult projectRewardPayChooseResult, Context context) {
        this.context = context;
        Message msg = Message.obtain(mMessageHandler, MSG_ALIPAY);
        msg.obj = uiHandler;
        Bundle bundle = new Bundle();
        bundle.putSerializable("projectRewardPayChooseResult", projectRewardPayChooseResult);
        msg.setData(bundle);
        sendMessageAtFrontOfQueue(msg);
    }

    public void sendMessageAtFrontOfQueue(Message msg) {
        mMessageHandler.sendMessageAtFrontOfQueue(msg);
    }

    /**
     * 请求我曾经发起的开黑列表
     * 
     * @param uiHandler
     */
    public void doRequestMyLaunchBallGameBefore(UIHandler uiHandler) {
        Message msg = Message.obtain(mMessageHandler, MSG_REQUEST_MY_LAUNCH_BALL_GAME_DATA_BEFORE);
        msg.obj = uiHandler;
        sendMessage(msg);

    }

    /**
     * 请求我发起的开黑列表
     * 
     * @param uiHandler
     */
    public void doRequestMyLaunchBallGame(UIHandler uiHandler) {
        Message msg = Message.obtain(mMessageHandler, MSG_REQUEST_MY_LAUNCH_BALL_GAME_DATA);
        msg.obj = uiHandler;
        sendMessage(msg);

    }

    /**
     * 请求好友动态
     */
    public void doRequestFriendsGynamicData(UIHandler handler, int status, int pageNo) {
        Message msg = Message.obtain(mMessageHandler, MSG_REQUEST_FRIENDS_DYNAMIC_DATA);
        msg.obj = handler;
        msg.arg1 = status;
        msg.arg2 = pageNo;
        sendMessage(msg);
    }

    /**
     * 请求所有开黑
     * 
     * @param handler
     */
    public void doRequestBallGamesAll(UIHandler handler, int status, int pageNo) {
        Message msg = Message.obtain(mMessageHandler, MSG_REQUEST_BALL_GAMES_ALL);
        msg.obj = handler;
        msg.arg1 = status;
        msg.arg2 = pageNo;
        sendMessage(msg);
    }

    /**
     * 请求广告
     * 
     * @param handler
     */
    public void doRequestAD(UIHandler handler) {
        Message msg = Message.obtain(mMessageHandler, MSG_REQUEST_AD);
        msg.obj = handler;
        sendMessage(msg);
    }

    /**
     * 版本更新检查
     */
    public void doVersionUpdateCheck(UIHandler handler) {
        Message msg = Message.obtain(mMessageHandler, MSG_REQUEST_VERSION_UPDATE_CHECK);
        msg.obj = handler;
        sendMessage(msg);
    }

    /**
     * 上传个人头像
     * 
     * @param handler
     */
    public void doUploadPersonHeadToServer(UIHandler handler, String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return;
        }
        Message msg = Message.obtain(mMessageHandler, MSG_UPLOAD_PERSON_HEAD_TO_SERVER);
        msg.obj = handler;
        Bundle bundle = new Bundle();
        bundle.putString("filePath", filePath);
        msg.setData(bundle);
        sendMessage(msg);
    }

    /**
     * 请求好友列表
     * 
     * @param handler
     */
    public void doRequestFriends(UIHandler handler) {
        Message msg = Message.obtain(mMessageHandler, MSG_REQUEST_FRIENDS);
        msg.obj = handler;
        sendMessage(msg);
    }

    /**
     * 请求发起开黑信息
     * 
     * @param handler
     */
    public void doRequestLaunchBallGames(UIHandler handler) {
        Message msg = Message.obtain(mMessageHandler, MSG_REQUEST_LAUNCH_BALL_GAMES_INFO);
        msg.obj = handler;
        sendMessage(msg);
    }

    /**
     * 请求历史会话
     * 
     * @param handler
     */
    public void doRequestConversation(UIHandler handler) {
        Message msg = Message.obtain(mMessageHandler, MSG_REQUEST_CONVERSATION);
        msg.obj = handler;
        sendMessage(msg);
    }

    public void sendMessage(Message msg) {
        mMessageHandler.sendMessage(msg);
    }
}
