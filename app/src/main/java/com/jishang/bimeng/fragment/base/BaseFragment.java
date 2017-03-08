
package com.jishang.bimeng.fragment.base;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jishang.bimeng.BimmoApplication;
import com.jishang.bimeng.entity.wode.MyEntity;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.Internet.MyHttpRequest;
import com.jishang.bimeng.utils.url.UrlUtils;

public abstract class BaseFragment extends Fragment {
    private View view;

    public Context context;

    public BimmoApplication application = BimmoApplication.getApplication();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = initView(inflater);
        return view;
    }

    /* 初始化UI的方法 */
    public abstract View initView(LayoutInflater inflater);

    /* 初始化数据的方法 */
    public abstract void initData(Bundle savedInstanceState);

    /**/
    public void loadData() {
    }

    public Handler handler;

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    /**
     * 加载我的数据
     */
    public void loadMyData() {
        new Thread() {
            public void run() {
                Gson mGson = new Gson();
                String token = new SharUtil(getActivity()).getAccess_token();
                List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
                params.add(new BasicNameValuePair("1", "1"));
                String url = UrlUtils.BASEURL + "v1/user/change_income_integral.json";
                String result = MyHttpRequest.getHttpPostBasic(url, params, token);
                try {
                    if (result != null) {
                        MyEntity entity = mGson.fromJson(result, MyEntity.class);
                        if (entity.getStatus() == 1) {
                            Message msg = new Message();
                            msg.obj = entity.getData();
                            msg.what = MY_DATA_LOAD_SUCCESS;
                            if (handler != null) {
                                handler.sendMessage(msg);
                            }
                        }
                    }
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
            };
        }.start();
    }

    public abstract void refreshUI();

    public static final int MY_DATA_LOAD_SUCCESS = 102;

}
