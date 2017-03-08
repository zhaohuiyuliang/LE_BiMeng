
package com.jishang.bimeng.ui;

import android.content.IntentFilter;
import android.support.v4.app.FragmentActivity;

import com.easemob.chat.EMChat;
import com.easemob.chat.EMChatManager;
import com.jishang.bimeng.receiver.ReceiverHuanXinReceiver;

public class BaseFragmentActivity extends FragmentActivity {
    /**
     * 接受环信推送的消息广播
     */
    private ReceiverHuanXinReceiver msgReceiver;

    /**
     * 注册环信消息接收广播
     */
    public void registerHyReceiver() {
        // 只有注册了广播才能接收到新消息，目前离线消息，在线消息都是走接收消息的广播（离线消息目前无法监听，在登录以后，接收消息广播会执行一次拿到所有的离线消息）
        msgReceiver = new ReceiverHuanXinReceiver();
        String action = EMChatManager.getInstance().getNewMessageBroadcastAction();
        IntentFilter intentFilter = new IntentFilter(action);
        intentFilter.setPriority(3);
        registerReceiver(msgReceiver, intentFilter);
        EMChat.getInstance().setAppInited();
    }

    /**
     * 注销环信消息接收广播
     */
    public void unregisterHyReceiver() {
        if (msgReceiver != null) {
            unregisterReceiver(msgReceiver);
        }
    }
}
