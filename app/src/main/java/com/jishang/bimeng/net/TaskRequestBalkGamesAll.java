
package com.jishang.bimeng.net;

import android.os.Message;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jishang.bimeng.entity.yuezhan.yzlist.YzListEntity;
import com.jishang.bimeng.ui.UIHandler;
import com.jishang.bimeng.utils.Internet.MyHttpRequest;
import com.jishang.bimeng.utils.InternetUtils;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.url.UrlUtils;

import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * 加载开黑数据
 * 
 * @author wangliang Jul 15, 2016
 */
public class TaskRequestBalkGamesAll extends LoadData {
    UIHandler handler;

    /**
     * 0:初始刷新；1：下拉；2：上拉。
     */

    int status;

    int pageNo;

    public TaskRequestBalkGamesAll(Message msg) {
        this.handler = (UIHandler)msg.obj;
        pageNo = msg.arg2;
        status = msg.arg1;

    }

    @Override
    public void run() {
        loadBallGamesAllData();
    }

    /**
     * 加载开黑信息
     */
    public void loadBallGamesAllData() {
        final List<BasicNameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("1", "1"));
        if (!InternetUtils.isNetworkAvailable(application)) {
            handler.sendEmptyMessage(4);
            return;
        }
        String token = new SharUtil(application).getAccess_token();
        String url = UrlUtils.BASEURL + "v1/yz/yz_list.json?page=" + pageNo;
        String result = MyHttpRequest.getHttpPostBasic(url, params, token);
        if (result == null) {
            return;
        }
        // 判断网络地址是不是能用
        if (result.equals("0")) {
            handler.sendEmptyMessage(1);
            return;
        }

        Gson mGson = new Gson();
        try {
            YzListEntity entity = mGson.fromJson(result, YzListEntity.class);
            if (status == 1) {
                Message msg1 = new Message();
                msg1.obj = entity;
                msg1.what = 5;
                handler.sendMessage(msg1);

            } else if (status == 2) {
                Message msg2 = new Message();
                msg2.what = 6;
                msg2.obj = entity;
                handler.sendMessage(msg2);

            } else if (status == 0) {
                Message msg = new Message();
                msg.what = UIHandler.BALL_GAMES_NEW_LOAD_SUCCESS;
                msg.obj = entity;
                handler.sendMessage(msg);
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            handler.sendEmptyMessage(1);
        }

    }

}
