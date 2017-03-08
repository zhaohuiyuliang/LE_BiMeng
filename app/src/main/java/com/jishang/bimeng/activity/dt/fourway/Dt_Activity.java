
package com.jishang.bimeng.activity.dt.fourway;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jishang.bimeng.R;
import com.jishang.bimeng.activity.dt.fourway.PullListView.OnLoadMoreListener;
import com.jishang.bimeng.activity.dt.fourway.PullListView.OnRefreshListener;
import com.jishang.bimeng.activity.dt.pinglun.ActionItem;
import com.jishang.bimeng.activity.dt.pinglun.TitlePopup;
import com.jishang.bimeng.activity.dt.pinglun.TitlePopup.OnItemOnClickListener;
import com.jishang.bimeng.activity.dt.pinglun.Util;
import com.jishang.bimeng.activity.pickphoto.view.PublishActivity;
import com.jishang.bimeng.entity.dt.gs.ClickEntiy;
import com.jishang.bimeng.entity.dt.gs.ComentEntity;
import com.jishang.bimeng.entity.dt.gs.DtgsEntity;
import com.jishang.bimeng.entity.dt.gs.UserCotentEntity;
import com.jishang.bimeng.entity.login.LogEntity;
import com.jishang.bimeng.parse.ParseUtil;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.ToastUtil;
import com.jishang.bimeng.utils.Internet.MyHttpRequest;
import com.jishang.bimeng.utils.url.UrlUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * "其他人动态"UI
 * 
 * @author wangliang Jul 19, 2016
 */
public class Dt_Activity extends Activity implements OnClickListener {

    private PullListView pullToRefreshListView;

    private RotateLayout rotateLayout;

    private TextView mTv_hydt, mTv_wodt, mTv_fbdt, mTv_username;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dt1);
        initView();
        addListener();

    }

    public void initView() {

        gson = new Gson();
        imageLoader_head = ImageLoader.getInstance();
        imageLoader_content = ImageLoader.getInstance();
        options_head = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ic_stub) // 璁剧疆鍥剧墖涓嬭浇鏈熼棿鏄剧ず鐨勫浘鐗�
                .showImageForEmptyUri(R.drawable.ic_empty) // 璁剧疆鍥剧墖Uri涓虹┖鎴栨槸閿欒鐨勬椂鍊欐樉绀虹殑鍥剧墖
                .showImageOnFail(R.drawable.ic_error) // 璁剧疆鍥剧墖鍔犺浇鎴栬В鐮佽繃绋嬩腑鍙戠敓閿欒鏄剧ず鐨勫浘鐗�
                .cacheInMemory(true) // 璁剧疆涓嬭浇鐨勫浘鐗囨槸鍚︾紦瀛樺湪鍐呭瓨涓�
                .cacheOnDisk(true) // 璁剧疆涓嬭浇鐨勫浘鐗囨槸鍚︾紦瀛樺湪SD鍗′腑
                .displayer(new RoundedBitmapDisplayer(10)) // 璁剧疆鎴愬渾瑙掑浘鐗�
                .build(); // 鏋勫缓瀹屾垚

        options_cotnent = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ic_stub) // 璁剧疆鍥剧墖涓嬭浇鏈熼棿鏄剧ず鐨勫浘鐗�
                .showImageForEmptyUri(R.drawable.ic_empty) // 璁剧疆鍥剧墖Uri涓虹┖鎴栨槸閿欒鐨勬椂鍊欐樉绀虹殑鍥剧墖
                .showImageOnFail(R.drawable.ic_error) // 璁剧疆鍥剧墖鍔犺浇鎴栬В鐮佽繃绋嬩腑鍙戠敓閿欒鏄剧ず鐨勫浘鐗�
                .cacheInMemory(true) // 璁剧疆涓嬭浇鐨勫浘鐗囨槸鍚︾紦瀛樺湪鍐呭瓨涓�
                .cacheOnDisk(true) // 璁剧疆涓嬭浇鐨勫浘鐗囨槸鍚︾紦瀛樺湪SD鍗′腑
                .displayer(new RoundedBitmapDisplayer(1)) // 璁剧疆鎴愬渾瑙掑浘鐗�
                .build(); // 鏋勫缓瀹屾垚
        params = new ArrayList<BasicNameValuePair>();
        pullToRefreshListView = (PullListView)findViewById(R.id.refreshlistview);
        rotateLayout = (RotateLayout)findViewById(R.id.rotateLayout);
        View pullView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.headlayout,
                null);
        mTv_hydt = (TextView)pullView.findViewById(R.id.head_haoyoudt);
        mTv_wodt = (TextView)pullView.findViewById(R.id.head_wodehy);
        mTv_fbdt = (TextView)pullView.findViewById(R.id.head_fbdt);
        mTv_username = (TextView)pullView.findViewById(R.id.user_name);
        mTv_username.setText(new SharUtil(Dt_Activity.this).getUserName());

        mRl_pinglun = (RelativeLayout)findViewById(R.id.commentLinear);
        mEdt_pinglun = (EditText)findViewById(R.id.commentEdit);
        mBt_send = (Button)findViewById(R.id.commentButton);

        pullToRefreshListView.setPullHeaderView(pullView);
        pullToRefreshListView.setPullHeaderViewHeight(100);
        pullToRefreshListView.setRotateLayout(rotateLayout);
        pullToRefreshListView.setCacheColorHint(Color.TRANSPARENT);
        pullToRefreshListView.setDividerHeight(0);
        adapter = new ItemListAdapter();
        pullToRefreshListView.setAdapter(adapter);
        getMsg();

        titlePopup = new TitlePopup(this, Util.dip2px(this, 165), Util.dip2px(this, 40));
        titlePopup.addAction(new ActionItem(this, "璧�", R.drawable.circle_praise));
        titlePopup.addAction(new ActionItem(this, "璇勮", R.drawable.circle_comment));

    }

    public void addListener() {
        mTv_fbdt.setOnClickListener(this);
        mTv_hydt.setOnClickListener(this);
        mTv_wodt.setOnClickListener(this);
        mTv_username.setOnClickListener(this);
        // 涓婃媺鍔犺浇鏇村
        pullToRefreshListView.setOnLoadMoreListener(new OnLoadMoreListener<ListView>() {

            @Override
            public void onLoadMore(PullListView refreshView) {
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        status = 2;
                        pageNo++;
                        if (pageNo > 13) {
                            Toast.makeText(Dt_Activity.this, "娌℃湁鏇村", 0).show();
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
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        status = 1;
                        getMsg();

                    }
                }, 500);
            }
        });
    }

    public void getMsg() {
        final String token = new SharUtil(Dt_Activity.this).getAccess_token();
        params.add(new BasicNameValuePair("111", "1"));
        Log.e("pageno", pageNo + "");
        new Thread() {
            public void run() {
                String url = UrlUtils.BASEURL + "v1/user_content/select_by_business.json?page="
                        + pageNo + "";
                String result = MyHttpRequest.getHttpPostBasic(url, params, token);
                if (result != null) {
                    Log.e("result", result.toString());
                    if (status == 1) {
                        entities_.clear();
                        entities.clear();
                        DtgsEntity entity = gson.fromJson(result, DtgsEntity.class);
                        entities = entity.getUser_content();
                        imageUrls_head = new String[entities.size()];
                        imageUrls_content = new String[entities.size()];
                        entities_.addAll(entities);
                        for (int i = 0; i < entities.size(); i++) {
                            imageUrls_head[i] = entities.get(i).getUser_post().getHead_img();
                            imageUrls_content[i] = entities.get(i).getThumb_img();
                        }
                    } else if (status == 2) {
                        entities.clear();
                        DtgsEntity entity = gson.fromJson(result, DtgsEntity.class);
                        entities = entity.getUser_content();
                        entities_.addAll(entities);
                        imageUrls_head = new String[entities_.size()];
                        imageUrls_content = new String[entities_.size()];
                        for (int i = 0; i < entities_.size(); i++) {
                            imageUrls_head[i] = entities_.get(i).getUser_post().getHead_img();
                            imageUrls_content[i] = entities_.get(i).getThumb_img();
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

                        }

                    }

                    switch (status) {
                        case 0:

                            handler.sendEmptyMessageDelayed(0, 500);
                            break;
                        case 1:
                            handler.sendEmptyMessageDelayed(1, 500);
                            break;
                        case 2:
                            handler.sendEmptyMessageDelayed(2, 500);
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
                Intent intent = new Intent(Dt_Activity.this, PublishActivity.class);
                startActivity(intent);

                break;
            case R.id.head_haoyoudt:
                Toast.makeText(Dt_Activity.this, "濂藉弸鍔ㄦ��", 0).show();
                break;
            case R.id.head_wodehy:
                Toast.makeText(Dt_Activity.this, "鎴戠殑濂藉弸", 0).show();
                break;

        }

    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            // String result = (String) msg.obj;
            switch (msg.what) {
                case 0:
                    /*
                     * entities = ParseUtil.getBanner_dt(result); imageUrls_head
                     * = new String[entities.size()]; imageUrls_content = new
                     * String[entities.size()]; for (int i = 0; i <
                     * entities.size(); i++) { imageUrls_head[i] =
                     * entities.get(i).getHeadimg(); imageUrls_content[i] =
                     * entities.get(i).getThumb_img(); }
                     */
                    // Log.e("entities", entities.toString());

                    // sv.smoothScrollTo(0, 0);
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
                    ToastUtil.Toast(Dt_Activity.this, "璇勮鎴愬姛");
                    mRl_pinglun.setVisibility(View.GONE);
                    // new
                    // ViewHolder().mTv_pinglun.setText(mEdt_pinglun.getText().toString());
                    String renming = new SharUtil(Dt_Activity.this).getUserName();
                    // mTv_pinglun.setText(renming + "璇�:"
                    // + mEdt_pinglun.getText().toString());
                    // Log.e("1111", renming + "璇�:"
                    // + mEdt_pinglun.getText().toString());
                    mEdt_pinglun.setText("");
                    break;
                case 4:
                    isRed = 1;
                    adapter.notifyDataSetChanged();
                    break;

                case 5:
                    isRed = 0;
                    adapter.notifyDataSetChanged();
                    break;

            }
        };
    };

    public class ItemListAdapter extends BaseAdapter {

        /*
         * List<DtgsEntity> entities; public ItemListAdapter(List<DtgsEntity>
         * entities) { this.entities = entities; }
         */

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
                convertView = getLayoutInflater().inflate(R.layout.content_item, null);
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
            imageLoader_content.displayImage(imageUrls_content[position], viewHolder.img_content,
                    options_cotnent);
            imageLoader_head.displayImage(imageUrls_head[position], viewHolder.image_head,
                    options_head);

            // viewHolder.text.setText("Item " + (position + 1)); //
            // TextView璁剧疆鏂囨湰
            final UserCotentEntity entity = entities_.get(position);
            // final Dt_pinglunEntity entity_pinglun = entities_pinglun
            // .get(position);
            viewHolder.content.setText(entity.getContent());
            viewHolder.username.setText(entity.getUser_post().getUsername());
            List<ComentEntity> comments = entities_.get(position).getComments();
            if (comments != null && !comments.equals("")) {
                viewHolder.mTv_pinglun.setVisibility(View.VISIBLE);
            }

            String pinglun = "";
            for (int i = 0; i < comments.size(); i++) {
                pinglun += comments.get(i).getComment_users().getUsername() + ":"
                        + comments.get(i).getMessage() + "\r\n";
            }
            viewHolder.mTv_pinglun.setText(pinglun);
            List<ClickEntiy> click = entities_.get(position).getClicks();
            viewHolder.mTv_dianzan.setVisibility(View.VISIBLE);
            String dianzan = "";
            for (int i = 0; i < click.size(); i++) {
                dianzan += click.get(i).getClick_users().getUsername() + ",";
            }
            viewHolder.mTv_dianzan.setText("鈾�" + dianzan);
            Log.e("comments", comments.toString());
            // viewHolder.mTv_pinglun
            /*
             * if (entity_pinglun.getMessage() != null) {
             * viewHolder.mTv_pinglun.
             * setText(entity.getUsername()+":"+entity_pinglun.getMessage());
             * }else{ viewHolder.mTv_pinglun.setText(""); }
             */
            titlePopup.setItemOnClickListener(new OnItemOnClickListener() {

                @Override
                public void onItemClick(ActionItem item, int position) {

                    switch (position) {
                        case 0:
                            ToastUtil.Toast(Dt_Activity.this, "鎴戞槸鐐硅禐");

                            break;

                        case 1:
                            // ToastUtil.Toast(Dt_Activity.this, "鎴戞槸璇勮");
                            mRl_pinglun.setVisibility(View.VISIBLE);
                            break;

                    }

                }
            });
            mBt_send.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    Putpinglun(entity.getUc_id());

                }
            });

            viewHolder.img_pinglun.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // titlePopup.setAnimationStyle(R.style.cricleBottomAnimation);
                    // titlePopup.show(v);
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
        final String token = new SharUtil(Dt_Activity.this).getAccess_token();
        String content = mEdt_pinglun.getText().toString().trim();
        params.add(new BasicNameValuePair("message", content));
        params.add(new BasicNameValuePair("ucmc_id", ucid));
        new Thread() {
            public void run() {
                String url = UrlUtils.BASEURL + "v1/user_content/comment.json?";
                String result = MyHttpRequest.getHttpPostBasic(url, params, token);
                if (result != null) {
                    LogEntity entity = ParseUtil.getBanner_pinglun(result);
                    if (entity.getStatus() == 1) {
                        handler.sendEmptyMessage(3);
                    }
                    // Log.e("result", result.toString());
                }
            };
        }.start();
    }

    public void Dianzan_huise(final String ucid) {
        final String token = new SharUtil(Dt_Activity.this).getAccess_token();
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
        final String token = new SharUtil(Dt_Activity.this).getAccess_token();
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

        public TextView username;

        public TextView content;

        public ImageView img_content;

        public ImageView img_pinglun;

        public TextView mTv_pinglun;

        public TextView ucid;

        public ImageView imgv_dianzan;

        public TextView mTv_dianzan;

        public ImageView imgv_dianzan_huise;
    }

    public void back(View view) {
        finish();
    }

}
