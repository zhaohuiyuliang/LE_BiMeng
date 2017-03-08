
package com.jishang.bimeng.fragment.xiaoxi;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.jishang.bimeng.R;
import com.jishang.bimeng.utils.SharUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class Fragment_hydt extends Fragment implements OnClickListener {
    private View v;

    private SwipeRefreshLayout mList;

    private List<BasicNameValuePair> params;

    private Gson mGson;

    protected ImageLoader imageLoader_head;

    private DisplayImageOptions options_head; // 设置图片显示相关参数

    private Context mContext;

    private String token;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_hydt, null);
        initView(v);
        return v;
    }

    public void initView(View v) {
        mContext = getActivity();
        params = new ArrayList<BasicNameValuePair>();
        mGson = new Gson();
        imageLoader_head = ImageLoader.getInstance();
        options_head = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ic_stub) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.ic_empty) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.ic_error) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(10)) // 设置成圆角图片
                .build(); // 构建完成
        mList = (SwipeRefreshLayout)v.findViewById(R.id.id_swipe_ly);
        token = new SharUtil(mContext).getAccess_token();
    }

    @Override
    public void onClick(View v) {

    }

}
