
package com.jishang.bimeng.fragment.main;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jishang.bimeng.R;
import com.jishang.bimeng.activity.TestActivity;
import com.jishang.bimeng.activity.addfd.fdlist.FdListActivity;
import com.jishang.bimeng.activity.dt.ActivityMydt;
import com.jishang.bimeng.activity.dt.fourway.PullListView;
import com.jishang.bimeng.activity.dt.fourway.PullListView.OnLoadMoreListener;
import com.jishang.bimeng.activity.dt.fourway.PullListView.OnRefreshListener;
import com.jishang.bimeng.activity.dt.fourway.RotateLayout;
import com.jishang.bimeng.activity.dt.pinglun.TitlePopup;
import com.jishang.bimeng.activity.hxchat.ActivityChatHistory;
import com.jishang.bimeng.activity.pickphoto.view.PublishActivity;
import com.jishang.bimeng.activity.yuezhan.zhudian.QiehuanZdActivity;
import com.jishang.bimeng.entity.dt.gs.ClickEntiy;
import com.jishang.bimeng.entity.dt.gs.ComentEntity;
import com.jishang.bimeng.entity.dt.gs.DtgsEntity;
import com.jishang.bimeng.entity.dt.gs.UserCotentEntity;
import com.jishang.bimeng.entity.tonyong.TYEntity;
import com.jishang.bimeng.fragment.base.BaseFragment;
import com.jishang.bimeng.utils.InternetUtils;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.ToastUtil;
import com.jishang.bimeng.utils.Internet.MyHttpRequest;
import com.jishang.bimeng.utils.dialog.DialogUtils;
import com.jishang.bimeng.utils.url.UrlUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class Fragment_xiaoxi_3 extends BaseFragment implements OnClickListener {

    private PullListView pullToRefreshListView;

    private RotateLayout rotateLayout;

    private TextView mTv_hydt, mTv_wodt, mTv_fbdt, mTv_username, mTv_name;

    private List<BasicNameValuePair> params;

    private String[] imageUrls_head; // 鍥剧墖璺緞

    private String[] imageUrls_content; // 鍥剧墖璺緞

    private DisplayImageOptions options_head; // 璁剧疆鍥剧墖鏄剧ず鐩稿叧鍙傛暟

    private DisplayImageOptions options_cotnent; // 璁剧疆鍥剧墖鏄剧ず鐩稿叧鍙傛暟

    protected ImageLoader imageLoader_head;

    protected ImageLoader imageLoader_content;

    private int pageNo = 1;

    private int isRed = 0;// 0涓虹孩鑹茬殑锛�1涓虹伆鑹�

    private int hui = 1;

    private ImageView mImg_headimg;

    /**
     * 0:鍒濆鍒锋柊锛�1锛氫笅鎷夛紱2锛氫笂鎷夈��
     */
    private int status = 0;//

    private List<UserCotentEntity> entities = new ArrayList<UserCotentEntity>();

    private List<UserCotentEntity> entities_ = new ArrayList<UserCotentEntity>();

    private ItemListAdapter adapter;

    private TitlePopup titlePopup;

    private EditText mEdt_pinglun;

    private RelativeLayout mRl_pinglun;

    private Button mBt_send;

    private TextView mTv_pinglun;

    private Gson gson;

    private RelativeLayout mRl_qiehuan, mRl_publish;

    @Override
    public View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_message, null);
        initView(view);
        addListener();
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    public void initView(View view) {

        gson = new Gson();
        imageLoader_head = ImageLoader.getInstance();
        imageLoader_content = ImageLoader.getInstance();
        // 浣跨敤DisplayImageOptions.Builder()鍒涘缓DisplayImageOptions
        options_head = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ic_stub) // 璁剧疆鍥剧墖涓嬭浇鏈熼棿鏄剧ず鐨勫浘鐗�
                .showImageForEmptyUri(R.drawable.ic_empty) // 璁剧疆鍥剧墖Uri涓虹┖鎴栨槸閿欒鐨勬椂鍊欐樉绀虹殑鍥剧墖
                .showImageOnFail(R.drawable.ic_error) // 璁剧疆鍥剧墖鍔犺浇鎴栬В鐮佽繃绋嬩腑鍙戠敓閿欒鏄剧ず鐨勫浘鐗�
                .cacheInMemory(true) // 璁剧疆涓嬭浇鐨勫浘鐗囨槸鍚︾紦瀛樺湪鍐呭瓨涓�
                .cacheOnDisk(true) // 璁剧疆涓嬭浇鐨勫浘鐗囨槸鍚︾紦瀛樺湪SD鍗′腑
                .displayer(new RoundedBitmapDisplayer(20)) // 璁剧疆鎴愬渾瑙掑浘鐗�
                .build(); // 鏋勫缓瀹屾垚

        options_cotnent = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ic_stub) // 璁剧疆鍥剧墖涓嬭浇鏈熼棿鏄剧ず鐨勫浘鐗�
                .showImageForEmptyUri(R.drawable.ic_empty) // 璁剧疆鍥剧墖Uri涓虹┖鎴栨槸閿欒鐨勬椂鍊欐樉绀虹殑鍥剧墖
                .showImageOnFail(R.drawable.ic_error) // 璁剧疆鍥剧墖鍔犺浇鎴栬В鐮佽繃绋嬩腑鍙戠敓閿欒鏄剧ず鐨勫浘鐗�
                .cacheInMemory(true) // 璁剧疆涓嬭浇鐨勫浘鐗囨槸鍚︾紦瀛樺湪鍐呭瓨涓�
                .cacheOnDisk(true) // 璁剧疆涓嬭浇鐨勫浘鐗囨槸鍚︾紦瀛樺湪SD鍗′腑
                .displayer(new RoundedBitmapDisplayer(1)) // 璁剧疆鎴愬渾瑙掑浘鐗�
                .build(); // 鏋勫缓瀹屾垚
        params = new ArrayList<BasicNameValuePair>();
        pullToRefreshListView = (PullListView)view.findViewById(R.id.refreshlistview);
        rotateLayout = (RotateLayout)view.findViewById(R.id.rotateLayout);
        View pullView = LayoutInflater.from(getActivity()).inflate(R.layout.headlayout, null);
        mTv_hydt = (TextView)pullView.findViewById(R.id.head_haoyoudt);
        mTv_wodt = (TextView)pullView.findViewById(R.id.head_wodehy);
        mTv_fbdt = (TextView)pullView.findViewById(R.id.head_fbdt);
        mTv_username = (TextView)pullView.findViewById(R.id.user_name);
        mTv_username.setText(new SharUtil(getActivity()).getUserName());
        mTv_name = (TextView)view.findViewById(R.id.fragment_xiaoxi_name);
        mImg_headimg = (ImageView)pullView.findViewById(R.id.headlayout_headimg);

        mRl_qiehuan = (RelativeLayout)view.findViewById(R.id.fragment_xiaoxi_qiehuan);
        mRl_publish = (RelativeLayout)view.findViewById(R.id.fragment_xiaoxi_publish);

        mRl_pinglun = (RelativeLayout)view.findViewById(R.id.commentLinear);
        mEdt_pinglun = (EditText)view.findViewById(R.id.commentEdit);
        mBt_send = (Button)view.findViewById(R.id.commentButton);

        pullToRefreshListView.setPullHeaderView(pullView);
        pullToRefreshListView.setPullHeaderViewHeight(100);
        pullToRefreshListView.setRotateLayout(rotateLayout);
        pullToRefreshListView.setCacheColorHint(Color.TRANSPARENT);
        pullToRefreshListView.setDividerHeight(0);
        adapter = new ItemListAdapter();
        pullToRefreshListView.setAdapter(adapter);
        mTv_name.setText("娑堟伅");
        String imgurl = new SharUtil(getActivity()).getHead_img();
        imageLoader_head.displayImage(imgurl, mImg_headimg, options_head);
        getMsg();

    }

    public void addListener() {
        mTv_fbdt.setOnClickListener(this);
        mTv_hydt.setOnClickListener(this);
        mTv_wodt.setOnClickListener(this);
        mTv_username.setOnClickListener(this);
        mRl_qiehuan.setOnClickListener(this);
        mRl_publish.setOnClickListener(this);
        mImg_headimg.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ActivityMydt.class);
                startActivity(intent);

            }
        });
        // 涓婃媺鍔犺浇鏇村
        pullToRefreshListView.setOnLoadMoreListener(new OnLoadMoreListener<ListView>() {

            @Override
            public void onLoadMore(PullListView refreshView) {
                // TODO Auto-generated method stub
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        status = 2;
                        pageNo++;
                        if (pageNo > 13) {
                            Toast.makeText(getActivity(), "娌℃湁鏇村", 0).show();
                        } else {
                            getMsg();
                            // pullToRefreshListView.onCompleteRefresh();
                        }

                        //
                    }
                }, 500);
            }

        });
        // 涓嬫媺鍒锋柊
        pullToRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {

            @Override
            public void onRefresh(PullListView refreshView) {
                // TODO Auto-generated method stub
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        status = 1;
                        getMsg();

                    }
                }, 500);
            }
        });
    }

    public void getMsg() {
        DialogUtils.getInstance().createNotifier(getActivity(), "姝ｅ湪鍔犺浇涓�");
        // final String token=new SharUtil(DtActivity.this).getAccess_token();
        final String token = new SharUtil(getActivity()).getAccess_token();
        params.add(new BasicNameValuePair("111", "1"));
        Log.e("pageno", pageNo + "");
        new Thread() {
            public void run() {
                if (!InternetUtils.isNetworkAvailable(getActivity())) {
                    handler.sendEmptyMessage(7);
                    return;
                }
                String url = UrlUtils.BASEURL + "v1/user_content/select_by_business.json?page="
                        + pageNo + "";
                String result = MyHttpRequest.getHttpPostBasic(url, params, token);

                // 鍒ゆ柇缃戠粶鍦板潃鏄笉鏄兘鐢�
                if (result.equals("0")) {
                    handler.sendEmptyMessage(6);
                    return;
                }
                if (result != null) {
                    Log.e("result", result.toString());
                    if (status == 1) {
                        entities_.clear();
                        entities.clear();
                        try {
                            DtgsEntity entity = gson.fromJson(result, DtgsEntity.class);
                            entities = entity.getUser_content();
                            imageUrls_head = new String[entities.size()];
                            imageUrls_content = new String[entities.size()];
                            entities_.addAll(entities);
                            for (int i = 0; i < entities.size(); i++) {
                                imageUrls_head[i] = entities.get(i).getUser_post().getHead_img();
                                imageUrls_content[i] = entities.get(i).getThumb_img();
                            }
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                            handler.sendEmptyMessage(6);
                        }
                    } else if (status == 2) {
                        entities.clear();
                        try {
                            DtgsEntity entity = gson.fromJson(result, DtgsEntity.class);
                            entities = entity.getUser_content();
                            entities_.addAll(entities);
                            imageUrls_head = new String[entities_.size()];
                            imageUrls_content = new String[entities_.size()];
                            for (int i = 0; i < entities_.size(); i++) {
                                imageUrls_head[i] = entities_.get(i).getUser_post().getHead_img();
                                imageUrls_content[i] = entities_.get(i).getThumb_img();
                            }
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                            handler.sendEmptyMessage(6);
                        }

                    } else if (status == 0) {
                        entities_.clear();
                        entities.clear();
                        DtgsEntity entity = null;
                        try {
                            entity = gson.fromJson(result, DtgsEntity.class);
                            entities = entity.getUser_content();
                            imageUrls_head = new String[entities.size()];
                            imageUrls_content = new String[entities.size()];
                            entities_.addAll(entities);
                            for (int i = 0; i < entities.size(); i++) {
                                imageUrls_head[i] = entities.get(i).getUser_post().getHead_img();
                                imageUrls_content[i] = entities.get(i).getThumb_img();
                            }
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                            handler.sendEmptyMessage(6);

                        }

                    }

                    switch (status) {
                        case 0:

                            // handler.sendEmptyMessageDelayed(0, 500);
                            handler.sendEmptyMessage(0);
                            break;
                        case 1:
                            // handler.sendEmptyMessageDelayed(1, 500);
                            handler.sendEmptyMessage(1);
                            break;
                        case 2:
                            // handler.sendEmptyMessageDelayed(2, 500);
                            handler.sendEmptyMessage(2);
                            break;

                    }

                }
            };
        }.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_fbdt:
                Intent intent7 = new Intent(getActivity(), FdListActivity.class);
                startActivity(intent7);
                break;
            case R.id.head_haoyoudt:
                Intent intent2 = new Intent(getActivity(), TestActivity.class);
                startActivity(intent2);
                break;
            case R.id.head_wodehy:
                // Toast.makeText(getActivity(), "鎴戠殑濂藉弸", 0).show();
                Intent intent9 = new Intent(getActivity(), ActivityChatHistory.class);
                startActivity(intent9);
                break;
            case R.id.fragment_xiaoxi_qiehuan:
                Intent intent3 = new Intent(getActivity(), QiehuanZdActivity.class);
                intent3.putExtra("back", "消息");
                startActivity(intent3);
                break;
            case R.id.fragment_xiaoxi_publish:
                Intent intent = new Intent(getActivity(), PublishActivity.class);
                startActivity(intent);

                break;

        }

    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            DialogUtils.getInstance().cancel();
            // String result = (String) msg.obj;
            switch (msg.what) {
                case 0:
                    adapter.notifyDataSetChanged();
                    break;
                case 1:
                    adapter.notifyDataSetChanged();
                    pullToRefreshListView.onCompleteRefresh();
                    pageNo = 1;
                    break;
                case 2:
                    adapter.notifyDataSetChanged();
                    pullToRefreshListView.onCompleteRefresh();
                    break;
                case 3:
                    ToastUtil.Toast(getActivity(), "璇勮鎴愬姛");
                    mRl_pinglun.setVisibility(View.GONE);
                    mEdt_pinglun.setText("");
                    getMsg();

                    break;
                case 4:
                    isRed = 1;
                    adapter.notifyDataSetChanged();
                    break;

                case 5:
                    isRed = 0;
                    adapter.notifyDataSetChanged();
                    break;
                case 6:
                    ToastUtil.Toast(context, "缃戠粶閿欒");
                    break;
                case 7:
                    ToastUtil.Toast(getActivity(), "鎮ㄨ繕娌℃湁鑱旂綉锛岃鍏堣仈缃�");
                    break;

            }
        };
    };

    public class ItemListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (entities_.size() == 0) {
                return 0;
            }
            return entities_.size();
        }

        @Override
        public Object getItem(int position) {
            return imageUrls_head[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.content_item,
                        null);
                viewHolder = new ViewHolder();
                viewHolder.image_head = (ImageView)convertView
                        .findViewById(R.id.content_item_username_imgv_headimg);
                viewHolder.img_content = (ImageView)convertView
                        .findViewById(R.id.content_item_username_imgv_contentimg);
                viewHolder.content = (TextView)convertView
                        .findViewById(R.id.content_item_tv_content);
                viewHolder.username = (TextView)convertView
                        .findViewById(R.id.content_item_tv_username);
                viewHolder.img_pinglun = (ImageView)convertView
                        .findViewById(R.id.content_item_username_imgv_pinglun);
                viewHolder.mTv_pinglun = (TextView)convertView
                        .findViewById(R.id.content_item_username_tv_pinglun);
                viewHolder.imgv_dianzan = (ImageView)convertView
                        .findViewById(R.id.content_item_username_imgv_dianzan_red);
                viewHolder.mTv_dianzan = (TextView)convertView
                        .findViewById(R.id.content_item_username_tv_dianzan);
                viewHolder.imgv_dianzan_huise = (ImageView)convertView
                        .findViewById(R.id.content_item_username_imgv_dianzan_huise);
                viewHolder.mRl_pinglundz = (LinearLayout)convertView
                        .findViewById(R.id.content_item_pinglundz);
                viewHolder.mTv_fenge = (TextView)convertView.findViewById(R.id.content_iten_fenge);
                // mTv_pinglun = (TextView) convertView
                // .findViewById(R.id.content_item_username_tv_pinglun);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder)convertView.getTag();
            }

            /**
             * imageUrl 鍥剧墖鐨刄rl鍦板潃 imageView 鎵胯浇鍥剧墖鐨処mageView鎺т欢 options
             * DisplayImageOptions閰嶇疆鏂囦欢
             */

            imageLoader_head.displayImage(imageUrls_head[position], viewHolder.image_head,
                    options_head);

            // viewHolder.text.setText("Item " + (position + 1)); //
            // TextView璁剧疆鏂囨湰
            final UserCotentEntity entity = entities_.get(position);
            String img = entity.getThumb_img();
            if (img != null & !img.equals("")) {
                imageLoader_content.displayImage(imageUrls_content[position],
                        viewHolder.img_content, options_cotnent);
            } else {
                viewHolder.img_content.setVisibility(View.GONE);
            }
            // final Dt_pinglunEntity entity_pinglun = entities_pinglun
            // .get(position);
            viewHolder.content.setText(entity.getContent());
            viewHolder.username.setText(entity.getUser_post().getUsername());
            List<ComentEntity> comments = entities_.get(position).getComments();
            List<ClickEntiy> click = entities_.get(position).getClicks();
            if (comments != null && !comments.isEmpty()) {
                viewHolder.mRl_pinglundz.setVisibility(View.VISIBLE);
                viewHolder.mTv_pinglun.setVisibility(View.VISIBLE);
                viewHolder.mTv_fenge.setVisibility(View.VISIBLE);
                String pinglun = "";
                for (int i = 0; i < comments.size(); i++) {
                    if ((i < comments.size() - 1)) {
                        pinglun += comments.get(i).getComment_users().getUsername() + ":"
                                + comments.get(i).getMessage() + "\r\n";
                    } else if (i == (comments.size() - 1)) {
                        pinglun += comments.get(i).getComment_users().getUsername() + ":"
                                + comments.get(i).getMessage();
                    }
                }
                viewHolder.mTv_pinglun.setText(pinglun);
                if (click.isEmpty() || click == null) {
                    viewHolder.mTv_dianzan.setVisibility(View.GONE);
                    viewHolder.mTv_fenge.setVisibility(View.GONE);
                }
            } else {
                viewHolder.mTv_pinglun.setVisibility(View.GONE);
            }

            if (!click.isEmpty() && click != null) {
                viewHolder.mRl_pinglundz.setVisibility(View.VISIBLE);
                viewHolder.mTv_dianzan.setVisibility(View.VISIBLE);
                String dianzan = "";
                for (int i = 0; i < click.size(); i++) {
                    if (i < comments.size() - 1) {
                        dianzan += click.get(i).getClick_users().getUsername() + ",";
                    } else if (i == (comments.size() - 1)) {
                        dianzan += click.get(i).getClick_users().getUsername();
                    }
                }
                viewHolder.mTv_dianzan.setText("鈾�" + dianzan);
                if (comments != null && !comments.isEmpty()) {
                    viewHolder.mTv_fenge.setVisibility(View.VISIBLE);
                }
                Log.e("comments", comments.toString());
            }

            mBt_send.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    Putpinglun(entity.getUc_id());

                }
            });

            viewHolder.img_pinglun.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    mRl_pinglun.setVisibility(View.VISIBLE);
                }
            });
            if (isRed == 0) {
                viewHolder.imgv_dianzan.setVisibility(View.VISIBLE);
                viewHolder.imgv_dianzan_huise.setVisibility(View.GONE);
            } else if (isRed == 1) {

                viewHolder.imgv_dianzan.setVisibility(View.GONE);
                viewHolder.imgv_dianzan_huise.setVisibility(View.VISIBLE);
            }
            viewHolder.imgv_dianzan.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    isRed = 0;
                    Dianzan(entity.getUc_id());

                }
            });
            viewHolder.imgv_dianzan_huise.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {

                    Dianzan_huise(entity.getUc_id());
                }
            });
            return convertView;
        }

    }

    /**
     * @param ucid 璇勮鎺ュ彛
     */
    public void Putpinglun(String ucid) {
        final String token = new SharUtil(getActivity()).getAccess_token();
        String content = mEdt_pinglun.getText().toString().trim();
        params.add(new BasicNameValuePair("message", content));
        params.add(new BasicNameValuePair("ucmc_id", ucid));
        new Thread() {
            public void run() {
                String url = UrlUtils.BASEURL + "v1/user_content/comment.json?";
                String result = MyHttpRequest.getHttpPostBasic(url, params, token);
                if (result != null) {
                    try {
                        TYEntity entity = gson.fromJson(result, TYEntity.class);
                        if (entity.getStatus() == 1) {
                            handler.sendEmptyMessage(3);
                        }
                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                    }
                }
            };
        }.start();
    }

    public void Dianzan_huise(final String ucid) {
        final String token = new SharUtil(getActivity()).getAccess_token();
        params.add(new BasicNameValuePair("1", "1"));
        new Thread() {
            public void run() {
                String url = UrlUtils.BASEURL + "v1/user_content/del_click.json?ucsc_id=" + ucid;
                String result = MyHttpRequest.getHttpPostBasic(url, params, token);
                if (result != null) {
                    Log.e("result", result.toString());
                    handler.sendEmptyMessage(5);
                }

            };
        }.start();
    }

    /**
     * @param ucid 鐐硅禐鎺ュ彛
     */
    public void Dianzan(String ucid) {
        final String token = new SharUtil(getActivity()).getAccess_token();
        params.add(new BasicNameValuePair("ucsc_id", ucid));
        Log.e("ucid", ucid);
        new Thread() {
            public void run() {

                String url = UrlUtils.BASEURL + "v1/user_content/click.json";
                String result = MyHttpRequest.getHttpPostBasic(url, params, token);
                if (result != null) {
                    Log.e("result", result.toString());
                    handler.sendEmptyMessage(4);
                }

            };
        }.start();
    }

    public class ViewHolder {
        public ImageView image_head;

        public TextView username, mTv_fenge;

        public TextView content;

        public ImageView img_content;

        public ImageView img_pinglun;

        public TextView mTv_pinglun;

        public TextView ucid;

        public ImageView imgv_dianzan;

        public TextView mTv_dianzan;

        public ImageView imgv_dianzan_huise;

        public LinearLayout mRl_pinglundz;
    }

    @Override
    public void refreshUI() {
        // TODO Auto-generated method stub

    }

}
