
package com.jishang.bimeng.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jishang.bimeng.R;
import com.jishang.bimeng.entity.chat.chaitghis.ChaithsEntity;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.Internet.MyHttpRequest;
import com.jishang.bimeng.utils.db.Person_Entity;
import com.jishang.bimeng.utils.url.UrlUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class ChatAllHistoryAdapter extends BaseAdapter {
    private List<Person_Entity> enties_;

    private Context context;

    IModelChatHistory model;

    protected ImageLoader imageLoader_head;

    private DisplayImageOptions options_head; // 设置图片显示相关参数

    public ChatAllHistoryAdapter(Context context, IModelChatHistory model) {
        this.context = context;
        this.model = model;
        imageLoader_head = ImageLoader.getInstance();
        options_head = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ic_stub) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.ic_empty) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.ic_error) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(10)) // 设置成圆角图片
                .build(); // 构建完成
    }

    public void setData(List<Person_Entity> enties_) {
        this.enties_ = enties_;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (enties_ != null) {
            return enties_.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (enties_ != null) {
            return enties_.get(position);
        }
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Viewhoder vh = null;
        if (convertView == null) {
            vh = new Viewhoder();
            convertView = View.inflate(context, R.layout.item_chat_history, null);
            vh.avatar = (ImageView)convertView.findViewById(R.id.hxhistoty_item_head);
            vh.name = (TextView)convertView.findViewById(R.id.hxhistoty_item_name);
            vh.time_chat_end = (TextView)convertView.findViewById(R.id.hxhistoty_item_head_time);
            vh.message_end = (TextView)convertView.findViewById(R.id.hxhistoty_item_head_message);
            vh.unreadLabel = (TextView)convertView
                    .findViewById(R.id.hxhistoty_item_head_msg_number);
            convertView.setTag(vh);
        } else {
            vh = (Viewhoder)convertView.getTag();
        }
        final Person_Entity entity = enties_.get(position);
        final String username = entity.getName();

        if (entity.getWeidu() > 0) {
            // 显示与此用户的消息未读数
            vh.unreadLabel.setText(String.valueOf(entity.getWeidu()));
            vh.unreadLabel.setVisibility(View.VISIBLE);
        } else {
            vh.unreadLabel.setVisibility(View.GONE);
        }
        vh.name.setText(username);
        vh.message_end.setText(entity.message_chat_end);
        vh.time_chat_end.setText(entity.time_chat_end);
        imageLoader_head.displayImage(entity.getImg(), vh.avatar, options_head);
        vh.avatar.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                getImgMsg(entity.getHuanid());
            }
        });
        convertView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                model.setOnClickListener(entity);
            }
        });
        return convertView;
    }

    public interface IModelChatHistory {
        void setOnClickListener(Person_Entity entity);
    }

    public void getImgMsg(final String h_username) {
        new Thread() {
            public void run() {
                List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
                params.add(new BasicNameValuePair("h_username", h_username));
                SharUtil mSharutil = new SharUtil(context);
                String token = mSharutil.getAccess_token();
                String url = UrlUtils.BASEURL + "v1/user/news_head_portrait.json";
                String result = MyHttpRequest.getHttpPostBasic(url, params, token);
                if (result != null) {
                    Log.e("result", result.toString());
                    try {
                        Gson mGson = new Gson();
                        ChaithsEntity entity = mGson.fromJson(result, ChaithsEntity.class);
                        if (entity.getStatus() == 0) {
                            Message msg = new Message();
                            msg.what = 0;
                            msg.obj = entity.getErrors();
                            // handler.sendMessage(msg);
                        } else if (entity.getStatus() == 1) {
                            Message msg = new Message();
                            msg.what = 1;
                            msg.obj = entity.getData();
                            // handler.sendMessage(msg);
                        }
                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                        // handler.sendEmptyMessage(2);
                    }
                }
            };
        }.start();
    }

    class Viewhoder {
        /** 和谁的聊天记录 */
        TextView name;

        /** 消息未读数 */
        TextView unreadLabel;

        /** 最后一条消息的内容 */
        TextView message_end;

        /** 最后一条消息的时间 */
        TextView time_chat_end;

        /** 用户头像 */
        ImageView avatar;

        /** 最后一条消息的发送状态 */
        View msgState;

        /** 整个list中每一行总布局 */
        RelativeLayout list_item_layout;
    }

}
