
package com.jishang.bimeng.net;

import android.content.Context;
import android.os.Message;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jishang.bimeng.entity.yuezhan.yzlist.lunbo.LunBoEntity;
import com.jishang.bimeng.ui.UIHandler;
import com.jishang.bimeng.utils.Internet.MyHttpRequest;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.url.UrlUtils;

import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class TaskLoadAD extends LoadData {
    private UIHandler uiHandler;

    private Context context;

    public TaskLoadAD(UIHandler uiHandler) {
        this.uiHandler = uiHandler;
    }

    @Override
    public void run() {
        loadPhoto();
    }

    public void loadPhoto() {
        final List<BasicNameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("1", "1"));
        new Thread() {
            public void run() {
                String token = new SharUtil(application).getAccess_token();
                String url = UrlUtils.BASEURL + "v1/advertisement/list.json";
                String result = MyHttpRequest.getHttpPostBasic(url, params, token);
                try {
                    if (result != null) {
                        Gson mGson = new Gson();
                        LunBoEntity entity = mGson.fromJson(result, LunBoEntity.class);
                        Message msg = new Message();
                        msg.what = UIHandler.UI_FRESH_AD;
                        msg.obj = entity;
                        uiHandler.sendMessage(msg);

                    }
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }

            };
        }.start();

    }

}
