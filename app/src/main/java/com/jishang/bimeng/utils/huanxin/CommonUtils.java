/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jishang.bimeng.utils.huanxin;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.easemob.chat.EMMessage;
import com.easemob.chat.TextMessageBody;
import com.easemob.util.EMLog;
import com.jishang.bimeng.R;
/**
 * 甯歌甯姪宸ュ叿绫�
 * @author Administrator
 *
 */
public class CommonUtils {
	private static final String TAG = "CommonUtils";
	/**
	 * 妫�娴嬬綉缁滄槸鍚﹀彲鐢�
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetWorkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}

		return false;
	}

	/**
	 * 妫�娴婼dcard鏄惁瀛樺湪
	 * 
	 * @return
	 */
	public static boolean isExitsSdcard() {
		if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
			return true;
		else
			return false;
	}
	

	/**
     * 鏍规嵁娑堟伅鍐呭鍜屾秷鎭被鍨嬭幏鍙栨秷鎭唴瀹规彁绀�
     * 
     * @param message
     * @param context
     * @return
     */
    public static String getMessageDigest(EMMessage message, Context context) {
        String digest = "";
        switch (message.getType()) {
        case LOCATION: // 浣嶇疆娑堟伅
        case TXT: // 鏂囨湰娑堟伅
            if(!message.getBooleanAttribute("is_voice_call",false)){
                TextMessageBody txtBody = (TextMessageBody) message.getBody();
                digest = txtBody.getMessage();
            }else{
                TextMessageBody txtBody = (TextMessageBody) message.getBody();
            }
            break;
        default:
            EMLog.e(TAG, "error, unknow type");
            return "";
        }

        return digest;
    }
    /**
     * 鑾峰彇鎸囧畾ID鐨勫瓧绗︿覆
     * @param context
     * @param resId
     * @return
     */
    static String getString(Context context, int resId){
        return context.getResources().getString(resId);
    }
	
	/**
	 * 鑾峰彇鏍堥《Activity绫荤殑鍚嶇О
	 * @param context
	 * @return
	 */
	public static String getTopActivity(Context context) {
		ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1);

		if (runningTaskInfos != null)
			return runningTaskInfos.get(0).topActivity.getClassName();
		else
			return "";
	}

}
