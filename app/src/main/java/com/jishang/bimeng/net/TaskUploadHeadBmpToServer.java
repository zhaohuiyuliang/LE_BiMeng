
package com.jishang.bimeng.net;

import java.io.File;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jishang.bimeng.entity.wode.PhotoEntity;
import com.jishang.bimeng.ui.UIHandler;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.ToastUtil;
import com.jishang.bimeng.utils.url.UrlUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

/**
 * 上传头像到服务器
 * 
 * @author wangliang Jul 16, 2016
 */
public class TaskUploadHeadBmpToServer extends LoadData {
    String filePath = "";

    UIHandler handler;

    public TaskUploadHeadBmpToServer(UIHandler handler, String filePath) {
        this.filePath = filePath;
        this.handler = handler;
    }

    @Override
    public void run() {
        uploadHeadToServer();
    }

    /**
     * 上传头像到服务器
     */
    private void uploadHeadToServer() {
        if (TextUtils.isEmpty(filePath)) {
            return;
        }
        final String URL = UrlUtils.BASEURL + "v1/user/update_personal.json";
        RequestParams params = new RequestParams();
        SharUtil mshSharUtil = new SharUtil(application);
        File tempFile = new File(filePath);
        params.addBodyParameter("head_img", tempFile);
        String token = mshSharUtil.getAccess_token();
        /* 添加请求头 */
        params.addHeader("Authorization", "Bearer" + " " + token);// 认证token
        params.addHeader("User-Agent", "imgfornote");

        HttpUtils httpUtils = new HttpUtils(10000);
        httpUtils.send(HttpMethod.POST, URL, params, new RequestCallBack<String>() {

            @Override
            public void onFailure(HttpException e, String msg) {
                Toast.makeText(application, "上传失败，检查一下服务器地址是否正确", Toast.LENGTH_SHORT).show();
                Log.i("MainActivity", e.getExceptionCode() + "=====" + msg);
            }

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (result != null) {
                    Log.e("result", result);
                    Gson mGson = new Gson();
                    PhotoEntity entity = mGson.fromJson(result, PhotoEntity.class);
                    if (entity.getStatus() == 1) {
                        ToastUtil.Toast(application, "修改成功");
                        new SharUtil(application).setHead_img(entity.getData().getHead_img());
                    } else {
                        // {"status":0,"error_code":400,"errors":"个人描述不能为空。"}
                    }
                }

            }
        });
    }
}
