
package com.jishang.bimeng.fragment.main;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jishang.bimeng.R;
import com.jishang.bimeng.activity.wode.MyActivity;
import com.jishang.bimeng.activity.yuezhan.ActivityYuezhan;
import com.jishang.bimeng.activity.yuezhan.MyYuezhanActivity;
import com.jishang.bimeng.activity.yuezhan.yzlist.ActivityBallGamesDetail;
import com.jishang.bimeng.activity.yuezhan.zhudian.ActivityMainStroe;
import com.jishang.bimeng.activity.yuezhan.zhudian.QiehuanZdActivity;
import com.jishang.bimeng.entity.wode.MyDataEntity;
import com.jishang.bimeng.entity.yuezhan.yzlist.List_dataEntity;
import com.jishang.bimeng.entity.yuezhan.yzlist.List_msgEntity;
import com.jishang.bimeng.entity.yuezhan.yzlist.YzListEntity;
import com.jishang.bimeng.entity.yuezhan.yzlist.lunbo.LunBoEntity;
import com.jishang.bimeng.fragment.base.BaseFragment;
import com.jishang.bimeng.ui.UIHandler;
import com.jishang.bimeng.ui.adapter.AdapterBallGamesAll;
import com.jishang.bimeng.ui.adapter.AdapterBallGamesAll.IModelBallGames;
import com.jishang.bimeng.ui.adapter.AdapterBallGamesViewPager;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.ToastUtil;

/**
 * "约战/开黑"UI
 * 
 * @author wangliang Jul 13, 2016
 */
public class FragmentBallGames extends BaseFragment implements OnClickListener,
        SwipeRefreshLayout.OnRefreshListener, IModelBallGames {

    /* 轮播图 */
    private ViewPager viewPager;

    private ScheduledExecutorService scheduledExecutorService;

    private AdapterBallGamesAll adapter;

    /**
     * 有人参与"我发起的开黑"红点提示
     */
    private TextView mTv_fq_num;

    /**
     * "我的钱包"余额有变化时，红点提示
     */
    private TextView mTv_num;

    private LinearLayout mLin_fq, mLin_wodeyz, mLin_zhudian;

    private RelativeLayout test, mRl_cy;

    private Context mContext;

    private AdapterBallGamesViewPager adapter_vp;

    private YzListEntity entity = new YzListEntity();

    private int pageNo = 0;

    public UIHandler uiHandler;

    @Override
    public View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_yuezhan, null);
        mContext = getActivity();
        initView(view);
        initData(null);
        addListener();
        uiHandler = new FragmentBallHandler(getActivity());
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void onResume() {
        super.onResume();
        setHandler(uiHandler);
        refreshUI();
    }

    public void refreshUI() {
        loadMyData();
        pageNo = 1;
        application.getController().doRequestBallGamesAll(uiHandler, 0, pageNo);
        application.getController().doRequestAD(uiHandler);
    }

    private SwipeRefreshLayout mSwipeLayout;

    private ListView mListView;

    public void initView(View view) {

        TextView mTv_name = (TextView)view.findViewById(R.id.activity_yzlist_tv_name);
        mRl_cy = (RelativeLayout)view.findViewById(R.id.fragment_change_main_store);
        test = (RelativeLayout)view.findViewById(R.id.fragment_yuezhan_my);
        mLin_fq = (LinearLayout)view.findViewById(R.id.fragment_yuezhan_lin_fq);
        mLin_wodeyz = (LinearLayout)view.findViewById(R.id.lLyout_my_ball_games);
        mLin_zhudian = (LinearLayout)view.findViewById(R.id.fragment_yuezhan_lin_zhudian);
        mTv_fq_num = (TextView)view.findViewById(R.id.fragment_yuezhan_fq_num);
        mTv_num = (TextView)view.findViewById(R.id.fragment_yuezhan_msg_number);
        mSwipeLayout = (SwipeRefreshLayout)view.findViewById(R.id.id_swipe_ly);
        mListView = (ListView)view.findViewById(R.id.listview_news);

        mTv_name.setText(R.string.ball_games);

        List<View> dots = new ArrayList<>();
        dots.add(view.findViewById(R.id.v_dot0));
        dots.add(view.findViewById(R.id.v_dot1));
        dots.add(view.findViewById(R.id.v_dot2));
        dots.add(view.findViewById(R.id.v_dot3));
        dots.add(view.findViewById(R.id.v_dot4));

        viewPager = (ViewPager)view.findViewById(R.id.vp);
        // 设置一个监听器，当ViewPager中的页面改变时调用
        viewPager.setOnPageChangeListener(new MyPageChangeListener(dots));

        adapter = new AdapterBallGamesAll(getActivity(), null, this);
        mListView.setAdapter(adapter);

        adapter_vp = new AdapterBallGamesViewPager(getActivity(), null);
        viewPager.setAdapter(adapter_vp);
    }

    public void addListener() {
        test.setOnClickListener(this);
        mRl_cy.setOnClickListener(this);

        mLin_fq.setOnClickListener(this);
        mLin_wodeyz.setOnClickListener(this);
        mLin_zhudian.setOnClickListener(this);
        mSwipeLayout.setOnRefreshListener(this);

    }

    @Override
    public void onStart() {
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        // 当Activity显示出来后，每两秒钟切换一次图片显示
        scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 4, TimeUnit.SECONDS);
        super.onStart();
    }

    /**
     * 换行切换任务
     * 
     * @author Administrator
     */
    private class ScrollTask implements Runnable {

        public void run() {
            synchronized (viewPager) {
                List<LunBoEntity.Data> entities_photo = adapter_vp.getDatas();
                if (entities_photo != null) {
                    int currentItem = viewPager.getCurrentItem();
                    currentItem = (currentItem + 1) % entities_photo.size();
                    Message msg = new Message();
                    msg.what = 2;
                    msg.arg1 = currentItem;
                    uiHandler.sendMessage(msg);
                } else {

                }
            }
        }

    }

    @Override
    public void onStop() {
        // 当Activity不可见的时候停止切换
        scheduledExecutorService.shutdown();
        super.onStop();
    }

    @Override
    public void onRefresh() {
        // 加载最新开黑数据
        pageNo = 1;
        application.getController().doRequestBallGamesAll(uiHandler, 1, pageNo);
    }

    /**
     * 当ViewPager中页面的状态发生改变时调用
     * 
     * @author Administrator
     */
    private class MyPageChangeListener implements OnPageChangeListener {
        private int oldPosition = 0;

        private List<View> dots;

        public MyPageChangeListener(List<View> dots) {
            this.dots = dots;
        }

        /**
         * This method will be invoked when a new page becomes selected.
         * position: Position index of the new selected page.
         */
        public void onPageSelected(int position) {
            if (dots != null) {
                dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
                dots.get(position).setBackgroundResource(R.drawable.dot_focused);
            }
            oldPosition = position;
        }

        public void onPageScrollStateChanged(int arg0) {

        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }
    }

    class FragmentBallHandler extends UIHandler {
        public FragmentBallHandler(Activity activity) {
            super(activity);
        }

        public void onMessage(Message msg) {
            switch (msg.what) {
                case MY_DATA_LOAD_SUCCESS: {// 我的数据加载成功
                    MyDataEntity data = (MyDataEntity)msg.obj;
                    String lingqian = new SharUtil(context).getLingqian();
                    String income = data.getIncome();
                    float num = Float.valueOf(income);
                    if (num == 0) {
                        mTv_num.setVisibility(View.GONE);
                    } else if (lingqian.compareTo(income) == 0) {
                        mTv_num.setVisibility(View.GONE);
                    } else {
                        mTv_num.setVisibility(View.VISIBLE);
                    }
                }
                    break;
                case BALL_GAMES_NEW_LOAD_SUCCESS: {// 所有"开黑信息"列表加载成功
                    YzListEntity entities = (YzListEntity)msg.obj;
                    entity = entities;
                    List<List_dataEntity> listEntitie = entities.getYz_list();
                    adapter.setData(listEntitie);
                    // 把参加我的开黑人数的值存放入缓存
                    String fq_num = entities.getMessage().getYz_fq_num();
                    // 我已查看的开黑人数
                    String me_look_ball_game = new SharUtil(mContext).getList();
                    if ("0".compareTo(fq_num) == 0) {
                        mTv_fq_num.setVisibility(View.GONE);
                    } else if (me_look_ball_game.compareTo(fq_num) == 0) {
                        mTv_fq_num.setVisibility(View.GONE);
                    } else {
                        mTv_fq_num.setVisibility(View.VISIBLE);
                    }
                }
                    break;
                case 1:
                    ToastUtil.Toast(getActivity(), "网络错误");
                    break;
                case 2: {
                    int currentItem = msg.arg1;
                    viewPager.setCurrentItem(currentItem);// 切换当前显示的图片
                }
                    break;
                case 4:
                    ToastUtil.Toast(mContext, "您还没有联网，请先联网");
                    break;
                case 5: {
                    // 下拉结果
                    YzListEntity entities = (YzListEntity)msg.obj;
                    entity = entities;
                    List<List_dataEntity> listEntitie = entities.getYz_list();
                    mSwipeLayout.setRefreshing(false);
                    pageNo = 1;
                    adapter.setData(listEntitie);
                    // 把参加我的开黑人数的值存放入缓存
                    String fq_num = entities.getMessage().getYz_fq_num();
                    // 我已查看的开黑人数
                    String me_look_ball_game = new SharUtil(mContext).getList();
                    if ("0".compareTo(fq_num) == 0) {
                        mTv_fq_num.setVisibility(View.GONE);
                    } else if (me_look_ball_game.compareTo(fq_num) == 0) {
                        mTv_fq_num.setVisibility(View.GONE);
                    } else {
                        mTv_fq_num.setVisibility(View.VISIBLE);
                    }
                }
                    break;
                case 6: {
                    // 上拉加载更多开黑
                    YzListEntity entities = (YzListEntity)msg.obj;
                    List<List_dataEntity> newDataEntities = entities.getYz_list();
                    List<List_dataEntity> oldDataEntities = adapter.getData();
                    if (newDataEntities != null) {
                        oldDataEntities.addAll(newDataEntities);
                        adapter.setData(oldDataEntities);
                    } else {
                        adapter.setData(newDataEntities);
                    }
                    // 把参加我的开黑人数的值存放入缓存
                    String fq_num = entities.getMessage().getYz_fq_num();
                    // 我已查看的开黑人数
                    String me_look_ball_game = new SharUtil(mContext).getList();
                    if ("0".compareTo(fq_num) == 0) {
                        mTv_fq_num.setVisibility(View.GONE);
                    } else if (me_look_ball_game.compareTo(fq_num) == 0) {
                        mTv_fq_num.setVisibility(View.GONE);
                    } else {
                        mTv_fq_num.setVisibility(View.VISIBLE);
                    }
                }
                    break;
                case UI_FRESH_AD: {
                    LunBoEntity entity = (LunBoEntity)msg.obj;
                    List<LunBoEntity.Data> entities_photo = entity.getData();
                    if (entity.getStatus() == 1) {
                        adapter_vp.setData(entities_photo);
                    } else {

                    }
                }
                    break;
                default:
                    break;

            }
        };
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_yuezhan_my: {// 我的
                Intent intent = new Intent(mContext, MyActivity.class);
                List_msgEntity entity1 = entity.getMessage();
                intent.putExtra("entity", entity1);
                startActivityForResult(intent, 0);
            }

                break;
            case R.id.fragment_yuezhan_lin_fq: {// 发起开黑
                Intent intent = new Intent(mContext, ActivityYuezhan.class);
                startActivity(intent);
            }
                break;
            case R.id.lLyout_my_ball_games: {// 我的开黑
                Intent intent = new Intent(mContext, MyYuezhanActivity.class);
                if (mTv_fq_num.getVisibility() == View.VISIBLE) {// 有我未查看的参与我的开黑的人
                    intent.putExtra("getYz_fq_num", entity.getMessage().getYz_fq_num());
                }
                startActivity(intent);
            }

                break;
            case R.id.fragment_yuezhan_lin_zhudian: {// 我的主店
                Intent intent = new Intent(mContext, ActivityMainStroe.class);
                startActivity(intent);
            }
                break;
            case R.id.fragment_change_main_store: {// 切换主店
                Intent intent = new Intent(mContext, QiehuanZdActivity.class);
                intent.putExtra("back", "开黑");
                startActivity(intent);
            }
                break;
            default:
                break;

        }
    }

    @Override
    public void setOnClickListener(List_dataEntity entity) {
        Intent intent = new Intent(mContext, ActivityBallGamesDetail.class);
        intent.putExtra("entity", entity);
        startActivity(intent);
    }
}
