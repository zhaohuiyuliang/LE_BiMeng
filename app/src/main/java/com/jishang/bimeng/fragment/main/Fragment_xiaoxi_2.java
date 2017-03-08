
package com.jishang.bimeng.fragment.main;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jishang.bimeng.R;
import com.jishang.bimeng.fragment.base.BaseFragment;
import com.jishang.bimeng.fragment.xiaoxi.Fragment_hydt;
import com.jishang.bimeng.fragment.xiaoxi.Fragment_txl;
import com.jishang.bimeng.fragment.xiaoxi.Fragment_wdxx;
import com.jishang.bimeng.utils.ToastUtil;
import com.jishang.bimeng.utils.dialog.DialogUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class Fragment_xiaoxi_2 extends BaseFragment implements OnClickListener {

    private RelativeLayout mRl_qiehuan, mRl_publish;

    private TextView mTv_name, mTv_hydt, mTv_wdxx, mTv_txl;

    private List<BasicNameValuePair> params;

    private Gson mGson;

    protected ImageLoader imageLoader_head;

    private DisplayImageOptions options_head; // 璁剧疆鍥剧墖鏄剧ず鐩稿叧鍙傛暟

    private Context mContext;

    private Fragment_hydt mFrag_hydt;

    private Fragment_wdxx mFrag_wdxx;

    private Fragment_txl mFrag_txl;

    @Override
    public View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_xiaoxi_b1, null);
        initView(view);
        addListener();
        setDefaultFragment();
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    public void initView(View view) {
        mTv_name = (TextView)view.findViewById(R.id.fragment_xiaoxi_name);
        mTv_hydt = (TextView)view.findViewById(R.id.fragment_xiao_hydt);
        mTv_wdxx = (TextView)view.findViewById(R.id.fragment_xiao_wdxx);
        mTv_txl = (TextView)view.findViewById(R.id.fragment_xiao_txl);

        mContext = getActivity();
        params = new ArrayList<BasicNameValuePair>();
        mGson = new Gson();
        imageLoader_head = ImageLoader.getInstance();
        options_head = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ic_stub) // 璁剧疆鍥剧墖涓嬭浇鏈熼棿鏄剧ず鐨勫浘鐗�
                .showImageForEmptyUri(R.drawable.ic_empty) // 璁剧疆鍥剧墖Uri涓虹┖鎴栨槸閿欒鐨勬椂鍊欐樉绀虹殑鍥剧墖
                .showImageOnFail(R.drawable.ic_error) // 璁剧疆鍥剧墖鍔犺浇鎴栬В鐮佽繃绋嬩腑鍙戠敓閿欒鏄剧ず鐨勫浘鐗�
                .cacheInMemory(true) // 璁剧疆涓嬭浇鐨勫浘鐗囨槸鍚︾紦瀛樺湪鍐呭瓨涓�
                .cacheOnDisk(true) // 璁剧疆涓嬭浇鐨勫浘鐗囨槸鍚︾紦瀛樺湪SD鍗′腑
                .displayer(new RoundedBitmapDisplayer(10)) // 璁剧疆鎴愬渾瑙掑浘鐗�
                .build(); // 鏋勫缓瀹屾垚
        mTv_name.setText("濂藉弸鍔ㄦ��");

    }

    public void setDefaultFragment() {
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        mFrag_hydt = new Fragment_hydt();
        transaction.replace(R.id.fragment_xiaoxi_fragment, mFrag_hydt);
        transaction.commit();

    }

    public void addListener() {

        mTv_hydt.setOnClickListener(this);
        mTv_txl.setOnClickListener(this);
        mTv_wdxx.setOnClickListener(this);
    }

    public void getMsg() {

    }

    @Override
    public void onClick(View v) {
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        switch (v.getId()) {
            case R.id.fragment_xiao_hydt:
                // ToastUtil.Toast(mContext, "濂藉弸鍔ㄦ��");
                if (mFrag_hydt == null) {
                    mFrag_hydt = new Fragment_hydt();
                }
                transaction.replace(R.id.fragment_xiaoxi_fragment, mFrag_hydt);
                break;
            case R.id.fragment_xiao_wdxx:
                // ToastUtil.Toast(mContext, "鎴戠殑娑堟伅");
                if (mFrag_wdxx == null) {
                    mFrag_wdxx = new Fragment_wdxx();
                }
                transaction.replace(R.id.fragment_xiaoxi_fragment, mFrag_wdxx);
                break;
            case R.id.fragment_xiao_txl:
                // ToastUtil.Toast(mContext, "閫氳褰�");
                if (mFrag_txl == null) {
                    mFrag_txl = new Fragment_txl();
                }
                transaction.replace(R.id.fragment_xiaoxi_fragment, mFrag_txl);
                break;

        }
        transaction.commit();

    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            DialogUtils.getInstance().cancel();
            // String result = (String) msg.obj;
            switch (msg.what) {
                case 7:
                    ToastUtil.Toast(getActivity(), "鎮ㄨ繕娌℃湁鑱旂綉锛岃鍏堣仈缃�");
                    break;

            }
        };
    };

    @Override
    public void refreshUI() {
        // TODO Auto-generated method stub

    }

}
