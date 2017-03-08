
package com.jishang.bimeng.net;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jishang.bimeng.entity.chat.Fdlist.FriendEntity;
import com.jishang.bimeng.entity.chat.Fdlist.FdlistEntity;
import com.jishang.bimeng.ui.UIHandler;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.Internet.MyHttpRequest;
import com.jishang.bimeng.utils.url.UrlUtils;

/**
 * 请求好友列表
 * 
 * @author wangliang Jul 15, 2016
 */
public class TaskRequestFriends extends LoadData {
    private Handler handler;

    public TaskRequestFriends(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        loadFriends();
    }

    /**
     * 加载好友列表数据
     */
    public void loadFriends() {
        // DialogUtils.getInstance().createNotifier(application, "正在加载中");
        final String token = new SharUtil(application).getAccess_token();
        List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("11", "11"));
        String url = UrlUtils.CHECK_FRIENDS;
        Log.i("url", url);
        String result = MyHttpRequest.getHttpPostBasic(url, params, token);
        if (result != null) {
            Log.e("result", result.toString());
            try {
                Gson mGson = new Gson();
                FdlistEntity entity = mGson.fromJson(result, FdlistEntity.class);
                if (entity.getStatus() == 1) {
                    List<FriendEntity> entities = entity.getData();
                    Message msg = new Message();
                    msg.obj = entities;
                    msg.what = UIHandler.REQUEST_FRIENDS_SUCCESS;
                    handler.sendMessage(msg);
                } else if (entity.getStatus() == 0) {
                    handler.sendEmptyMessage(UIHandler.REQUEST_FRIENDS_FAILED);
                }

            } catch (JsonSyntaxException e) {
                e.printStackTrace();
                handler.sendEmptyMessage(UIHandler.REQUEST_FRIENDS_FAILED);

            }
        }
    }

}
