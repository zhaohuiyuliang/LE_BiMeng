
package com.jishang.bimeng.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import com.igexin.sdk.PushConsts;
import com.igexin.sdk.PushManager;
import com.jishang.bimeng.BimmoApplication;
import com.jishang.bimeng.ui.UIHandler;

/**
 * 接收个推消息广播
 * 
 * @author wangliang Jul 16, 2016
 */
public class ReceiverGetuiPush extends BroadcastReceiver {

    /**
     * 搴旂敤鏈惎锟�?, 涓�? service宸茬粡琚敜锟�?,淇濆瓨鍦ㄨ鏃堕棿娈靛唴绂荤嚎娑堟伅(姝ゆ�?
     * GetuiSdkDemoActivity.tLogView == null)
     */
    public static StringBuilder payloadData = new StringBuilder();

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Log.d("GetuiSdkDemo", "onReceive() action=" + bundle.getInt("action"));

        switch (bundle.getInt(PushConsts.CMD_ACTION)) {
            case PushConsts.GET_MSG_DATA:
                // 获取透传数据
                // String appid = bundle.getString("appid");
                byte[] payload = bundle.getByteArray("payload");

                String taskid = bundle.getString("taskid");
                String messageid = bundle.getString("messageid");

                // smartPush第三方回执调用接口，actionid范围90000-90999，可根据业务场景执行
                boolean result = PushManager.getInstance().sendFeedbackMessage(context, taskid,
                        messageid, 90001);
                if (payload != null) {
                    String data = new String(payload);

                    Log.d("GetuiSdkDemo", "receiver payload : " + data);

                    payloadData.append(data);
                    payloadData.append("\n");

                    // if (GetuiSdkDemoActivity.tLogView != null) {
                    // GetuiSdkDemoActivity.tLogView.append(data + "\n");
                    // }
                    if (application.getUIHandler() != null) {
                        Message msg = new Message();
                        msg.what = UIHandler.REFRESH_UI;
                        application.getUIHandler().sendMessage(msg);
                    }
                }
                break;

            case PushConsts.GET_CLIENTID:
                // 获取ClientID(CID)
                // 第三方应用需要将CID上传到第三方服务器，并且将当前用户帐号和CID进行关联，以便日后�?�过用户帐号查找CID进行消息推�??
                String cid = bundle.getString("clientid");
                // if (GetuiSdkDemoActivity.tView != null) {
                // GetuiSdkDemoActivity.tView.setText(cid);
                // }
                break;

            case PushConsts.THIRDPART_FEEDBACK:
                /*
                 * String appid = bundle.getString("appid"); String taskid =
                 * bundle.getString("taskid"); String actionid =
                 * bundle.getString("actionid"); String result =
                 * bundle.getString("result"); long timestamp =
                 * bundle.getLong("timestamp"); Log.d("GetuiSdkDemo", "appid = "
                 * + appid); Log.d("GetuiSdkDemo", "taskid = " + taskid);
                 * Log.d("GetuiSdkDemo", "actionid = " + actionid);
                 * Log.d("GetuiSdkDemo", "result = " + result);
                 * Log.d("GetuiSdkDemo", "timestamp = " + timestamp);
                 */
                break;

            default:
                break;
        }
    }

    BimmoApplication application = BimmoApplication.getApplication();
}
