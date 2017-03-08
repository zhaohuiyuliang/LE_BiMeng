
package com.jishang.bimeng.net;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jishang.bimeng.entity.dt.nw.Dt_newEntity;
import com.jishang.bimeng.entity.dt.nw.Dt_new_dataEntity;
import com.jishang.bimeng.ui.UIHandler;
import com.jishang.bimeng.utils.InternetUtils;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.Internet.MyHttpRequest;
import com.jishang.bimeng.utils.url.UrlUtils;

/**
 * 获取好友动态
 * 
 * @author wangliang Jul 16, 2016
 */
public class TaskRequestFriendsGynamicData extends LoadData {
    private UIHandler handler;

    /**
     * 0:初始刷新；1：下拉；2：上拉。
     */

    int status;

    int pageNo;

    public TaskRequestFriendsGynamicData(Message msg) {
        this.handler = (UIHandler)msg.obj;
        pageNo = msg.arg2;
        status = msg.arg1;
    }

    @Override
    public void run() {
        loadFriendsDynamicData();
    }

    /**
     * 获取好友动态数据
     */
    public void loadFriendsDynamicData() {
        // final String token=new SharUtil(DtActivity.this).getAccess_token();
        List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("111", "1"));
        String token = new SharUtil(application).getAccess_token();
        Gson mGson = new Gson();
        // 没有网络
        if (!InternetUtils.isNetworkAvailable(application)) {
            handler.sendEmptyMessage(7);
            return;
        }
        String url = UrlUtils.BASEURL + "v1/user_content/list.json?page=" + pageNo + "";
        String result = MyHttpRequest.getHttpPostBasic(url, params, token);

        // 获取数据失败
        if (result == null) {
            return;
        }
        if (result.equals("0")) {
            handler.sendEmptyMessage(6);
            return;
        }
        Log.i("result", result.toString());
        try {
            if (status == 1) {

                Dt_newEntity entity = mGson.fromJson(result, Dt_newEntity.class);

                Message msg3 = new Message();
                msg3.what = 1;
                msg3.obj = entity;
                handler.sendMessage(msg3);

            } else if (status == 2) {
                Dt_newEntity entity = mGson.fromJson(result, Dt_newEntity.class);
                List<Dt_new_dataEntity> entities = entity.getData();
                Message msg3 = new Message();
                msg3.what = 2;
                msg3.obj = entity;
                handler.sendMessage(msg3);

            } else if (status == 0) {
                Dt_newEntity entity = mGson.fromJson(result, Dt_newEntity.class);
                List<Dt_new_dataEntity> entities = entity.getData();
                Message msg3 = new Message();
                msg3.what = 0;
                msg3.obj = entity;
                handler.sendMessage(msg3);

            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            handler.sendEmptyMessage(6);
        }

    }

}
