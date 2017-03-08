
package com.jishang.bimeng.activity.wode.yzsz;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jishang.bimeng.R;
import com.jishang.bimeng.activity.base.BaseActivity;
import com.jishang.bimeng.entity.yuezhan.GameEntity;
import com.jishang.bimeng.entity.yuezhan.Game_rtEntity;
import com.jishang.bimeng.entity.yuezhan.PhotoEntity;
import com.jishang.bimeng.entity.yuezhan.fq.wfq.fenqian.Fenqian_confimEntity;
import com.jishang.bimeng.horizontal.HorizontalListView;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.ToastUtil;
import com.jishang.bimeng.utils.Internet.MyHttpRequest;
import com.jishang.bimeng.utils.url.UrlUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class YwgameSetting extends BaseActivity implements OnClickListener {
    private TextView mTv_barname, mTv_gamename;

    private HorizontalListViewAdapter adapter;

    private HorizontalListView mList;

    private List<PhotoEntity> entities = new ArrayList<PhotoEntity>();

    private String token;

    private List<BasicNameValuePair> params;

    private Gson mGson;

    private Context mContext;

    protected ImageLoader imageLoader_head;

    private DisplayImageOptions options_head; // 设置图片显示相关参数

    private MySpAdapter<String> adaptersp;

    private MySpAdapter<String> ptadapter;

    private MySpAdapter<String> djadapter;

    private MySpAdapter<String> posiadapter;

    private List<String> servers = new ArrayList<String>();

    private List<String> pattern = new ArrayList<String>();

    private List<String> grade = new ArrayList<String>();

    private List<String> positions = new ArrayList<String>();

    private Spinner mSp_fwq, mSp_ms, mSp_dj, mSp_position;

    /**
     * 游戏名称
     */
    private String yw_title;

    /**
     * 游戏服务器
     */
    private String yw_server;

    /**
     * 游戏模式
     */
    private String yw_pattern;

    /**
     * 游戏等级限制
     */
    private String yw_grade;

    /**
     * 位置
     */
    private String yw_position;

    /**
     * 游戏图标
     */
    private String yw_img;

    private TextView mTv_confirm;

    @Override
    public int initResource() {
        return R.layout.activity_yw_gamesetting;
    }

    @Override
    public void initView() {
        mContext = this;
        token = new SharUtil(mContext).getAccess_token();
        mGson = new Gson();
        params = new ArrayList<BasicNameValuePair>();

        options_head = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ic_stub) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.ic_empty) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.ic_error) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(10)) // 设置成圆角图片
                .build(); // 构建完成
        imageLoader_head = ImageLoader.getInstance();
        mTv_barname = (TextView)findViewById(R.id.activity_yw_gamesetting_tv_name);
        mTv_gamename = (TextView)findViewById(R.id.activity_yw_gamesetting_tv_gamename);
        mList = (HorizontalListView)findViewById(R.id.activity_yw_gamesetting_gamelist);
        mSp_fwq = (Spinner)findViewById(R.id.fragment_yuezhan_spiner_fwq);
        mSp_ms = (Spinner)findViewById(R.id.fragment_yuezhan_spiner_ms);
        mSp_dj = (Spinner)findViewById(R.id.fragment_yuezhan_spiner_djxz);
        mSp_position = (Spinner)findViewById(R.id.fragment_yuezhan_spiner_position);
        mTv_confirm = (TextView)findViewById(R.id.activity_yw_gamesetting_confirm);

        djadapter = new MySpAdapter<String>(grade);
        ptadapter = new MySpAdapter<String>(pattern);
        adaptersp = new MySpAdapter<String>(servers);
        posiadapter = new MySpAdapter<String>(positions);

        mSp_fwq.setAdapter(adaptersp);
        mSp_ms.setAdapter(ptadapter);
        mSp_dj.setAdapter(djadapter);
        mSp_position.setAdapter(posiadapter);

        mTv_barname.setText("游戏设置");
        adapter = new HorizontalListViewAdapter();
        mList.setAdapter(adapter);
    }

    @Override
    public void initData() {
        getMsg();
    }

    public void getMsg() {
        params.add(new BasicNameValuePair("1", "1"));
        new Thread() {
            public void run() {

                String url = UrlUtils.BASEURL + "v1/yz/game_list.json";
                String result = MyHttpRequest.getHttpPostBasic(url, params, token);
                if (result != null) {
                    // Log.e("result---", result.toString());
                    Message msg = new Message();
                    msg.what = 0;
                    msg.obj = result;
                    handler.sendMessage(msg);

                }

            };
        }.start();

    }

    @Override
    public void addListener() {
        mList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String pid = entities.get(position).getYz_gm_id();
                getRmsg(pid);

            }
        });

        mSp_fwq.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                yw_server = servers.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mSp_ms.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                yw_pattern = pattern.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        mSp_dj.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                yw_grade = grade.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        mSp_position.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                yw_position = positions.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        mTv_confirm.setOnClickListener(this);

    }

    /**
     * @param servers 游戏服务器
     * @param pattern 游戏模式
     * @param grade 游戏等级限制
     */
    public void initset(List<String> servers, List<String> pattern, List<String> grade,
            List<String> position) {
        yw_server = servers.get(0).toString();
        yw_pattern = pattern.get(0).toString();
        yw_grade = grade.get(0).toString();
        yw_position = position.get(0).toString();

    }

    public void getRmsg(final String pid) {
        params.add(new BasicNameValuePair("1", "1"));
        new Thread() {
            public void run() {
                String url = UrlUtils.BASEURL + "v1/yz/game_by_id.json?yz_gm_id=" + pid;
                String result = MyHttpRequest.getHttpPostBasic(url, params, token);
                if (result != null) {
                    Log.e("result---", result.toString());
                    Message msg = new Message();
                    msg.what = 1;
                    msg.obj = result;
                    handler.sendMessage(msg);
                }

            };
        }.start();

    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    String result = (String)msg.obj;
                    GameEntity entity = mGson.fromJson(result, GameEntity.class);
                    entities = entity.getGame_list();
                    // Log.e("entity", entities.toString());
                    adapter.notifyDataSetChanged();
                    break;
                case 1:
                    String result1 = (String)msg.obj;
                    Game_rtEntity entity2 = mGson.fromJson(result1, Game_rtEntity.class);
                    // Log.e("entity2", entity2.toString());
                    servers = entity2.getData().getGm_server();
                    pattern = entity2.getData().getGm_pattern();
                    grade = entity2.getData().getGm_grade();
                    positions = entity2.getData().getGm_position();
                    mTv_gamename.setText(entity2.getData().getGm_name());

                    yw_title = entity2.getData().getGm_name();
                    yw_img = entity2.getData().getGm_img();
                    setNull();
                    initset(servers, pattern, grade, positions);

                    adaptersp.refreshAdapter(servers);
                    ptadapter.refreshAdapter(pattern);
                    djadapter.refreshAdapter(grade);
                    posiadapter.refreshAdapter(positions);
                    break;
                case 2:
                    ToastUtil.Toast(mContext, "提交成功");
                    finish();
                    break;
                case 3:
                    ToastUtil.Toast(mContext, "网络出错");
                    break;

            }
        };
    };

    /**
     * 强行将上一次的内容制空
     */
    public void setNull() {
        djadapter.clear();
        ptadapter.clear();
        adaptersp.clear();
        posiadapter.clear();

    }

    /**
     * @author kangming
     * @param <T>利用泛型制作一个万能的适配器
     */
    public class MySpAdapter<T> extends BaseAdapter {
        protected List<T> mDatas;

        public MySpAdapter(List<T> mDatas) {
            this.mDatas = mDatas;
        }

        @Override
        public int getCount() {
            if (mDatas.size() != 0) {
                return mDatas.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        // 向list中添加数据
        public void refreshAdapter(List<T> arrayList) {
            mDatas.addAll(arrayList);
            notifyDataSetChanged();
        }

        // 清空list集合
        public void clear() {
            mDatas.clear();
            notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder1 vh1 = null;
            if (convertView == null) {
                vh1 = new ViewHolder1();
                convertView = View.inflate(mContext, R.layout.yuezhan_sp_item, null);
                vh1.name = (TextView)convertView.findViewById(R.id.yuezhan_sp_item_name);
                convertView.setTag(vh1);
            } else {
                vh1 = (ViewHolder1)convertView.getTag();

            }
            vh1.name.setText(mDatas.get(position).toString());

            return convertView;
        }

    }

    class ViewHolder1 {
        TextView name;
    }

    public class HorizontalListViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (entities.size() == 0) {
                return 0;
            }
            return entities.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder vh = null;
            if (convertView == null) {
                vh = new ViewHolder();
                convertView = View.inflate(mContext, R.layout.horizontallistview_item, null);
                vh.im = (ImageView)convertView.findViewById(R.id.iv_pic);
                convertView.setTag(vh);
            } else {
                vh = (ViewHolder)convertView.getTag();

            }
            imageLoader_head.displayImage(entities.get(position).getGm_img(), vh.im, options_head);
            return convertView;
        }
    }

    public class ViewHolder {
        private ImageView im;
    }

    public void back(View v) {
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_yw_gamesetting_confirm:
                putMsg();
                break;

        }

    }

    public void putMsg() {
        Log.e("-----", yw_title + "  " + yw_img + " " + yw_server + " " + yw_grade + " "
                + yw_pattern + "  " + yw_position);
        params.add(new BasicNameValuePair("yw_title", yw_title));
        params.add(new BasicNameValuePair("yw_img", yw_img));
        params.add(new BasicNameValuePair("yw_server", yw_server));
        params.add(new BasicNameValuePair("yw_grade", yw_grade));
        params.add(new BasicNameValuePair("yw_pattern", yw_pattern));
        params.add(new BasicNameValuePair("yw_position", yw_position));
        new Thread() {
            public void run() {
                String url = UrlUtils.BASEURL + "v1/personal_game/create.json";
                String result = MyHttpRequest.getHttpPostBasic(url, params, token);
                if (result != null) {
                    Log.e("result---", result.toString());
                    try {
                        Fenqian_confimEntity entity = mGson.fromJson(result,
                                Fenqian_confimEntity.class);
                        // Log.e("entity", entity.toString());
                        if (entity.getStatus() == 1) {
                            handler.sendEmptyMessage(2);
                        } else {
                            handler.sendEmptyMessage(3);
                        }
                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                        handler.sendEmptyMessage(3);
                    }
                }

            };
        }.start();
    }

}
