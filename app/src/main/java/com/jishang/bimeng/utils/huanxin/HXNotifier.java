/************************************************************
 *  * EaseMob CONFIDENTIAL 
 * __________________ 
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved. 
 *  
 * NOTICE: All information contained herein is, and remains 
 * the property of EaseMob Technologies.
 * Dissemination of this information or reproduction of this material 
 * is strictly forbidden unless prior written permission is obtained
 * from EaseMob Technologies.
 */
package com.jishang.bimeng.utils.huanxin;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;

import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMMessage;
import com.easemob.util.EMLog;
import com.easemob.util.EasyUtils;

/**
 * 鏂版秷鎭彁閱抍lass
 * 2.1.8鎶婃柊娑堟伅鎻愮ず鐩稿叧鐨刟pi绉婚櫎鍑簊dk锛屾柟渚垮紑鍙戣�呰嚜鐢变慨鏀�
 * 寮�鍙戣�呬篃鍙互缁ф壙姝ょ被瀹炵幇鐩稿叧鐨勬帴鍙�
 * 
 * this class is subject to be inherited and implement the relative APIs
 */
public class HXNotifier {
    private final static String TAG = "notify";
    Ringtone ringtone = null;

    protected final static String[] msg_eng = { "sent a message", "sent a picture", "sent a voice",
                                                "sent location message", "sent a video", "sent a file", "%1 contacts sent %2 messages"
                                              };
    protected final static String[] msg_ch = { "鍙戞潵涓�鏉℃秷鎭�", "鍙戞潵涓�寮犲浘鐗�", "鍙戞潵涓�娈佃闊�", "鍙戞潵浣嶇疆淇℃伅", "鍙戞潵涓�涓棰�", "鍙戞潵涓�涓枃浠�",
                                               "%1涓仈绯讳汉鍙戞潵%2鏉℃秷鎭�"
                                             };

    protected static int notifyID1 = 0525; // start notification id
    protected static int foregroundNotifyID1 = 0555;

    protected NotificationManager notificationManager = null;

    protected HashSet<String> fromUsers = new HashSet<String>();
    protected int notificationNum = 0;

    protected Context appContext;
    protected String packageName;
    protected String[] msgs;
    protected long lastNotifiyTime;
    protected AudioManager audioManager;
    protected Vibrator vibrator;
    protected HXNotificationInfoProvider notificationInfoProvider;

    public HXNotifier() {
    }
    
    /**
     * 寮�鍙戣�呭彲浠ラ噸杞芥鍑芥暟
     * this function can be override
     * @param context
     * @return
     */
    public HXNotifier init(Context context){
        appContext = context;
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        packageName = appContext.getApplicationInfo().packageName;
        if (Locale.getDefault().getLanguage().equals("zh")) {
            msgs = msg_ch;
        } else {
            msgs = msg_eng;
        }

        audioManager = (AudioManager) appContext.getSystemService(Context.AUDIO_SERVICE);
        vibrator = (Vibrator) appContext.getSystemService(Context.VIBRATOR_SERVICE);
        
        return this;
    }
    
    /**
     * 寮�鍙戣�呭彲浠ラ噸杞芥鍑芥暟
     * this function can be override
     */
    public void reset(){
        resetNotificationCount();
        cancelNotificaton();
    }

    void resetNotificationCount() {
        notificationNum = 0;
        fromUsers.clear();
    }
    
    void cancelNotificaton() {
        if (notificationManager != null)
            notificationManager.cancel(0525);
    }

    /**
     * 澶勭悊鏂版敹鍒扮殑娑堟伅锛岀劧鍚庡彂閫侀�氱煡
     * 
     * 寮�鍙戣�呭彲浠ラ噸杞芥鍑芥暟
     * this function can be override
     * 
     * @param message
     */
    public synchronized void onNewMsg(EMMessage message) {
        if(EMChatManager.getInstance().isSlientMessage(message)){
            return;
        }
        
        // 鍒ゆ柇app鏄惁鍦ㄥ悗鍙�
        if (!EasyUtils.isAppRunningForeground(appContext)) {
            EMLog.d(TAG, "app is running in backgroud");
            sendNotification(message, false);
        } else {
            sendNotification(message, false);//灏辩畻鏄湪鍏朵粬椤甸潰涔熸彁绀�
        }
        viberateAndPlayTone(message);
    }
    
    public synchronized void onNewMesg(List<EMMessage> messages) {
        if(EMChatManager.getInstance().isSlientMessage(messages.get(messages.size()-1))){
            return;
        }
        // 鍒ゆ柇app鏄惁鍦ㄥ悗鍙�
        if (!EasyUtils.isAppRunningForeground(appContext)) {
            EMLog.d(TAG, "app is running in backgroud");
            sendNotification(messages, false);
        } else {
            sendNotification(messages, true);
        }
        viberateAndPlayTone(messages.get(messages.size()-1));
    }

    /**
     * 鍙戦�侀�氱煡鏍忔彁绀�
     * This can be override by subclass to provide customer implementation
     * @param messages
     * @param isForeground
     */
    protected void sendNotification (List<EMMessage> messages, boolean isForeground){
        for(EMMessage message : messages){
            if(!isForeground){
                notificationNum++;
                fromUsers.add(message.getFrom());
            }
        }
        sendNotification(messages.get(messages.size()-1), isForeground, false);
    }
    
    protected void sendNotification (EMMessage message, boolean isForeground){
        sendNotification(message, isForeground, true);
    }
    
    /**
     * 鍙戦�侀�氱煡鏍忔彁绀�
     * This can be override by subclass to provide customer implementation
     * @param message
     */
    protected void sendNotification(EMMessage message, boolean isForeground, boolean numIncrease) {
        String username = message.getFrom();
        try {
            String notifyText = username + " ";
            switch (message.getType()) {
            case TXT:
                notifyText += msgs[0];
                break;
            case IMAGE:
                notifyText += msgs[1];
                break;
            case VOICE:
                notifyText += msgs[2];
                break;
            case LOCATION:
                notifyText += msgs[3];
                break;
            case VIDEO:
                notifyText += msgs[4];
                break;
            case FILE:
                notifyText += msgs[5];
                break;
            }
            
            PackageManager packageManager = appContext.getPackageManager();
            String appname = (String) packageManager.getApplicationLabel(appContext.getApplicationInfo());
            
            // notification titile
            String contentTitle = appname;
            if (notificationInfoProvider != null) {
                String customNotifyText = notificationInfoProvider.getDisplayedText(message);
                String customCotentTitle = notificationInfoProvider.getTitle(message);
                if (customNotifyText != null){
                    // 璁剧疆鑷畾涔夌殑鐘舵�佹爮鎻愮ず鍐呭
                    notifyText = customNotifyText;
                }
                    
                if (customCotentTitle != null){
                    // 璁剧疆鑷畾涔夌殑閫氱煡鏍忔爣棰�
                    contentTitle = customCotentTitle;
                }   
            }

            // create and send notificaiton
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(appContext)
                
                .setSmallIcon(appContext.getApplicationInfo().icon)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true);
            Intent msgIntent = appContext.getPackageManager().getLaunchIntentForPackage(packageName);
            if (notificationInfoProvider != null) {
                // 璁剧疆鑷畾涔夌殑notification鐐瑰嚮璺宠浆intent
                msgIntent = notificationInfoProvider.getLaunchIntent(message);
            }

            PendingIntent pendingIntent = PendingIntent.getActivity(
                    appContext, 0525, msgIntent,PendingIntent.FLAG_UPDATE_CURRENT);

            if(numIncrease){
                // prepare latest event info section
                if(!isForeground){
                    notificationNum++;
                    fromUsers.add(message.getFrom());
                }
            }

            int fromUsersNum = fromUsers.size();
            String summaryBody = msgs[6].replaceFirst("%1", Integer.toString(fromUsersNum)).replaceFirst("%2",Integer.toString(notificationNum));
            
            if (notificationInfoProvider != null) {
                // lastest text
                String customSummaryBody = notificationInfoProvider.getLatestText(
                        message, fromUsersNum,notificationNum);
                if (customSummaryBody != null){
                    summaryBody = customSummaryBody;
                }
                
                // small icon
                int smallIcon = notificationInfoProvider.getSmallIcon(message);
                if (smallIcon != 0){
                    mBuilder.setSmallIcon(smallIcon);
                }
            }

            mBuilder.setContentTitle(contentTitle);
            mBuilder.setTicker(notifyText);
            mBuilder.setContentText(summaryBody);
            mBuilder.setContentIntent(pendingIntent);
            // mBuilder.setNumber(notificationNum);
            Notification notification = mBuilder.build();

            if (isForeground) {
                notificationManager.notify(0555, notification);
                notificationManager.cancel(0555);
            } else {
                notificationManager.notify(0525, notification);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 鎵嬫満闇囧姩鍜屽０闊虫彁绀�
     */
    public void viberateAndPlayTone(EMMessage message) {
        if(message != null){
            if(EMChatManager.getInstance().isSlientMessage(message)){
                return;
            } 
        }
        
        
        if (System.currentTimeMillis() - lastNotifiyTime < 1000) {
            // received new messages within 2 seconds, skip play ringtone
            return;
        }
        
        try {
            lastNotifiyTime = System.currentTimeMillis();
            
            // 鍒ゆ柇鏄惁澶勪簬闈欓煶妯″紡
            if (audioManager.getRingerMode() == AudioManager.RINGER_MODE_SILENT) {
                EMLog.e(TAG, "in slient mode now,鐜板湪澶勪簬闈欓煶妯″紡");
                return;
            }
                long[] pattern = new long[] { 0, 180, 80, 120 };
                vibrator.vibrate(pattern, -1);

                if (ringtone == null) {
                    Uri notificationUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    ringtone = RingtoneManager.getRingtone(appContext, notificationUri);
                    ringtone.play();
                    ringtone=null;
                    if (ringtone == null) {
                        EMLog.d(TAG, "鎵句笉鍒伴搩闊砪ant find ringtone at:" + notificationUri.getPath());
                        return;
                }
                
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 璁剧疆NotificationInfoProvider
     * @param provider
     */
    public void setNotificationInfoProvider(HXNotificationInfoProvider provider) {
        notificationInfoProvider = provider;
    }

    public interface HXNotificationInfoProvider {
        /**
         * 璁剧疆鍙戦�乶otification鏃剁姸鎬佹爮鎻愮ず鏂版秷鎭殑鍐呭(姣斿Xxx鍙戞潵浜嗕竴鏉″浘鐗囨秷鎭�)
         * 
         * @param message
         *            鎺ユ敹鍒扮殑娑堟伅
         * @return null涓轰娇鐢ㄩ粯璁�
         */
        String getDisplayedText(EMMessage message);

        /**
         * 璁剧疆notification鎸佺画鏄剧ず鐨勬柊娑堟伅鎻愮ず(姣斿2涓仈绯讳汉鍙戞潵浜�5鏉℃秷鎭�)
         * 
         * @param message
         *            鎺ユ敹鍒扮殑娑堟伅
         * @param fromUsersNum
         *            鍙戦�佷汉鐨勬暟閲�
         * @param messageNum
         *            娑堟伅鏁伴噺
         * @return null涓轰娇鐢ㄩ粯璁�
         */
        String getLatestText(EMMessage message, int fromUsersNum, int messageNum);

        /**
         * 璁剧疆notification鏍囬
         * 
         * @param message
         * @return null涓轰娇鐢ㄩ粯璁�
         */
        String getTitle(EMMessage message);

        /**
         * 璁剧疆灏忓浘鏍�
         * 
         * @param message
         * @return 0浣跨敤榛樿鍥炬爣
         */
        int getSmallIcon(EMMessage message);

        /**
         * 璁剧疆notification鐐瑰嚮鏃剁殑璺宠浆intent
         * 
         * @param message
         *            鏄剧ず鍦╪otification涓婃渶杩戠殑涓�鏉℃秷鎭�
         * @return null涓轰娇鐢ㄩ粯璁�
         */
        Intent getLaunchIntent(EMMessage message);
    }
}
