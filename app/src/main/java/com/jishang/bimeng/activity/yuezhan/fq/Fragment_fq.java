
package com.jishang.bimeng.activity.yuezhan.fq;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jishang.bimeng.BimmoApplication;
import com.jishang.bimeng.R;
import com.jishang.bimeng.entity.tonyong.TYEntity;
import com.jishang.bimeng.entity.yuezhan.fq.wfq.Wfq_DataEntity;
import com.jishang.bimeng.entity.yuezhan.fq.wfq.fenqian.FenqianEntity;
import com.jishang.bimeng.ui.UIHandler;
import com.jishang.bimeng.ui.adapter.AdapterMyLaunchBallGames;
import com.jishang.bimeng.ui.adapter.AdapterMyLaunchBallGames.IModelMyLaunchBallGames;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.ToastUtil;
import com.jishang.bimeng.utils.Internet.MyHttpRequest;
import com.jishang.bimeng.utils.url.UrlUtils;

/**
 * 我发起的"开黑"UI
 * 
 * @author wangliang Jul 15, 2016
 */
public class Fragment_fq extends Fragment implements IModelMyLaunchBallGames {

    private Context mContext;

    private AdapterMyLaunchBallGames adtapter;

    private ExpandableListView mElist;

    private RelativeLayout mRl_pinlun;

    private Button mBt_pinlun;

    private EditText mEdt_content;

    BimmoApplication application = BimmoApplication.getApplication();

    // 我未查看的参与人数
    String ballGameNum;

    public Fragment_fq(String ballGameNum) {
        this.ballGameNum = ballGameNum;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fq_yuezhan, null);
        mContext = getActivity();
        uiHandler = new FragmentFQUIHandler(getActivity());
        initView(view);
        initData();
        addListener();
        return view;

    }

    public void initView(View view) {

        mRl_pinlun = (RelativeLayout)view.findViewById(R.id.activity_fq_yuezhan_pinlun);
        mBt_pinlun = (Button)view.findViewById(R.id.activity_fq_yuezhan_bt_pinlun);
        mEdt_content = (EditText)view.findViewById(R.id.activity_fq_yuezhan_content);

        mElist = (ExpandableListView)view.findViewById(R.id.activity_fq_yuezhan_ep);
        adtapter = new AdapterMyLaunchBallGames(getActivity(), this, ballGameNum);
        mElist.setAdapter(adtapter);

    }

    public void initData() {

        application.getController().doRequestMyLaunchBallGame(uiHandler);
        application.getController().doRequestMyLaunchBallGameBefore(uiHandler);

    }

    public void addListener() {
        final String content = mEdt_content.getText().toString().trim();
        mBt_pinlun.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // Pinlun(entity.getYz_id(), content);
            }
        });
    }

    FragmentFQUIHandler uiHandler;

    class FragmentFQUIHandler extends UIHandler {
        public FragmentFQUIHandler(Activity activity) {
            super(activity);
        }

        public void onMessage(android.os.Message msg) {
            switch (msg.what) {
                case REQUEST_MY_LAUNCH_BALL_GAMES_SUCCESS: {// 我发起的开黑
                    List<Wfq_DataEntity> entities_wfq = (ArrayList<Wfq_DataEntity>)msg.obj;
                    adtapter.setDataWfq(entities_wfq);
                    mElist.expandGroup(0);
                }
                    break;
                case REQUEST_MY_LAUNCH_BALL_GAMES_BEFORE_SUCCESS: {// 我曾经发起的开黑
                    List<Wfq_DataEntity> entities_wcjfq = (ArrayList<Wfq_DataEntity>)msg.obj;

                    adtapter.setDataWcjfq(entities_wcjfq);
                }
                    break;
                case 2: {// 开始游戏
                    FenqianEntity entity = (FenqianEntity)msg.obj;
                    Intent intent = new Intent(mContext, FenqianActivity.class);
                    intent.putExtra("entity", entity);
                    startActivity(intent);
                }
                    break;
                case 3:
                    String erros = (String)msg.obj;
                    ToastUtil.Toast(mContext, erros);
                    break;
                case 4:
                    mRl_pinlun.setVisibility(View.GONE);
                    ToastUtil.Toast(mContext, "评论成功");
                    mEdt_content.setText("");
                    break;
                case 5:
                    ToastUtil.Toast(mContext, "网络异常");
                    break;
                case 6:
                    String erros1 = (String)msg.obj;
                    ToastUtil.Toast(mContext, erros1);
                    break;
                default:
                    break;
            }

        };
    };

    public void Pinlun(final String yz_id, final String content) {

        new Thread() {
            public void run() {
                List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
                params.add(new BasicNameValuePair("1", "1"));
                params.add(new BasicNameValuePair("yz_id", yz_id));
                params.add(new BasicNameValuePair("content", content));
                String url = UrlUtils.BASEURL + "v1/yz/comment.json";
                String token = new SharUtil(application).getAccess_token();

                String result = MyHttpRequest.getHttpPostBasic(url, params, token);
                if (result != null) {
                    try {
                        Gson mGson = new Gson();
                        TYEntity entity = mGson.fromJson(result, TYEntity.class);
                        if (entity.getStatus() == 0) {
                            Message msg = new Message();
                            msg.what = 6;
                            msg.obj = entity.getErrors();
                            uiHandler.sendMessage(msg);

                        } else if (entity.getStatus() == 1) {
                            uiHandler.sendEmptyMessage(4);

                        }
                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                        uiHandler.sendEmptyMessage(5);
                    }

                }
            };
        }.start();
    }

    /**
     * 开始游戏
     * 
     * @param yz_id
     */
    public void startGame(final String yz_id) {

        new Thread() {
            public void run() {
                List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
                params.add(new BasicNameValuePair("yz_id", yz_id));
                String url = UrlUtils.BASEURL + "v1/yz/start_game.json";
                String token = new SharUtil(application).getAccess_token();

                String result = MyHttpRequest.getHttpPostBasic(url, params, token);
                if (result != null) {
                    try {
                        Gson mGson = new Gson();
                        FenqianEntity entity = mGson.fromJson(result, FenqianEntity.class);
                        if (entity.getStatus() == 1) {
                            Message msg = new Message();
                            msg.what = 2;
                            msg.obj = entity;
                            uiHandler.sendMessage(msg);
                        } else if (entity.getStatus() == 0) {
                            Message msg = new Message();
                            msg.what = 3;
                            msg.obj = entity.getErrors();
                            uiHandler.sendMessage(msg);
                        }

                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                    }
                }
            };
        }.start();
    }

    @Override
    public void setOnClickListener(Wfq_DataEntity entity, String type) {
        if (type.equals("开始游戏")) {
            if (entity.getPay_get().equals("0")) {
                startGame(entity.getYz_id());
            } else if (entity.getPay_get().equals("1")) {

            }
        } else if (type.equals("举报发起人")) {

        } else if (type.equals("查看参与人")) {
            Intent intent = new Intent(getActivity(), LianxiActivity.class);
            intent.putExtra("entity", entity);
            getActivity().startActivity(intent);
        } else if (type.equals("评论开黑")) {
        }
    }

}
