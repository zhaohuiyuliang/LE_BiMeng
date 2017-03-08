
package com.jishang.bimeng.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Message;

import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMMessage;
import com.easemob.chat.EMMessage.ChatType;
import com.jishang.bimeng.BimmoApplication;
import com.jishang.bimeng.ui.UIHandler;

/**
 * 接受环信消息推送广播
 * 
 * @author wangliang Jul 16, 2016
 */
public class ReceiverHuanXinReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // 注销广播
        abortBroadcast();

        // 消息id（每条消息都会生成唯一的一个id，目前是SDK生成）
        String msgId = intent.getStringExtra("msgid");
        // 发送方
        String username = intent.getStringExtra("from");
        // 收到这个广播的时候，message已经在DB和内存里了，可以通过id获取message对象
        EMMessage message = EMChatManager.getInstance().getMessage(msgId);
        EMConversation conversation = EMChatManager.getInstance().getConversation(username);
        // 如果是群聊消息，获取到group id
        if (message != null && message.getChatType() == ChatType.GroupChat) {
            username = message.getTo();
        }
        if (username != null && !username.equals(username)) {
            // 消息不是发给当前会话，return
            return;
        }
        /*
         * //如果是当前会话的消息，刷新聊天页面 if(username.equals(toChatUserName)){
         * //声音和震动提示有新消息 new HXNotifier().viberateAndPlayTone(message); }else{
         * //如果消息不是和当前聊天ID的消息 new HXNotifier().onNewMsg(message); }
         */

        if (application.getUIHandler() != null) {
            Message message2 = new Message();
            message2.what = UIHandler.REFRESH_UI;
            application.getUIHandler().sendMessage(message2);
        }

    }

    BimmoApplication application = BimmoApplication.getApplication();
}
