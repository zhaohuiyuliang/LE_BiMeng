
package com.jishang.bimeng.net;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.os.Message;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jishang.bimeng.entity.gengxin.VerEntity;
import com.jishang.bimeng.ui.UIHandler;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.Internet.MyHttpRequest;
import com.jishang.bimeng.utils.url.UrlUtils;

/**
 * 版本更新检查
 * 
 * @author wangliang Jul 16, 2016
 */
public class TaskVersionUpdateCheck extends LoadData {
    private UIHandler uiHandler;

    public TaskVersionUpdateCheck(UIHandler uiHandler) {
        this.uiHandler = uiHandler;
    }

    @Override
    public void run() {
        versionUpdateCheck();
    }

    /**
     * 版本更新检查
     */
    private void versionUpdateCheck() {
        List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("1", "1"));
        String token = new SharUtil(application).getAccess_token();
        String url = UrlUtils.BASEURL + "v1/new/android.json";
        String result = MyHttpRequest.getHttpPostBasic(url, params, token);
        if (result != null) {
            try {
                Gson mGson = new Gson();
                VerEntity entity = mGson.fromJson(result, VerEntity.class);
                if (entity.getStatus() == 0) {
                    Message msg = new Message();
                    msg.what = 0;
                    msg.obj = entity.getErrors();
                    uiHandler.sendMessage(msg);
                } else if (entity.getStatus() == 1) {
                    Message msg = new Message();
                    msg.what = UIHandler.HAVE_NEW_VERSION;
                    msg.obj = entity;
                    uiHandler.sendMessage(msg);
                } else {

                }
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
                uiHandler.sendEmptyMessage(UIHandler.NETWORK_ERRORS);
            }
        }
    }

}
