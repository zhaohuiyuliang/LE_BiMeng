
package com.jishang.bimeng.net;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.os.Message;
import android.text.TextUtils;

import com.jishang.bimeng.ui.UIHandler;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.Internet.MyHttpRequest;
import com.jishang.bimeng.utils.url.UrlUtils;

/**
 * 请求发起的所有的"开黑"信息
 * 
 * @author wangliang Jul 15, 2016
 */
public class TaskLaunchBallGamesInfo extends LoadData {
    private UIHandler handler;

    public TaskLaunchBallGamesInfo(UIHandler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        getMsg();
    }

    public void getMsg() {
        final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("1", "1"));
        String token = new SharUtil(application).getAccess_token();
        String url = UrlUtils.BASEURL + "v1/yz/game_list.json";
        String result = MyHttpRequest.getHttpPostBasic(url, params, token);
        if (!TextUtils.isEmpty(result)) {
            Message msg = new Message();
            msg.what = UIHandler.REQUEST_GAMES_ALL_SUCCESS;
            msg.obj = result;
            handler.sendMessage(msg);
        } else {

        }

    }

}
