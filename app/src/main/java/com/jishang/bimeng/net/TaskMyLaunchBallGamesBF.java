
package com.jishang.bimeng.net;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jishang.bimeng.entity.yuezhan.fq.wfq.WfqEntity;
import com.jishang.bimeng.entity.yuezhan.fq.wfq.Wfq_DataEntity;
import com.jishang.bimeng.ui.UIHandler;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.Internet.MyHttpRequest;
import com.jishang.bimeng.utils.url.UrlUtils;

/**
 * 我曾经发起的开黑列表
 * 
 * @author wangliang Jul 17, 2016
 */
public class TaskMyLaunchBallGamesBF extends LoadData {
    UIHandler uiHandler;

    public TaskMyLaunchBallGamesBF(UIHandler uiHandler) {
        this.uiHandler = uiHandler;
    }

    @Override
    public void run() {
        loadMycjfq();
    }

    /**
     * 我曾经发起的"开黑"
     */
    public void loadMycjfq() {
        String token = new SharUtil(application).getAccess_token();
        List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("1", "1"));
        String url = UrlUtils.BASEURL + "v1/yz/my_creat_yz_past.json";
        String resusult = MyHttpRequest.getHttpPostBasic(url, params, token);
        if (resusult != null) {
            Log.e("result--发起cj", resusult.toString());
            try {
                Gson mGson = new Gson();
                WfqEntity entity = mGson.fromJson(resusult, WfqEntity.class);
                List<Wfq_DataEntity> entities_wcjfq = entity.getData();
                Message msg = new Message();
                msg.obj = entities_wcjfq;
                msg.what = UIHandler.REQUEST_MY_LAUNCH_BALL_GAMES_BEFORE_SUCCESS;
                uiHandler.sendMessage(msg);
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            }

        }
    }
}
