
package com.jishang.bimeng.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Spannable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.BufferType;

import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMMessage;
import com.easemob.chat.TextMessageBody;
import com.jishang.bimeng.R;
import com.jishang.bimeng.entity.chat.Fdlist.FriendEntity;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.huanxin.SmileUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * 聊天列表适配器
 * 
 * @author wangliang Jul 14, 2016
 */
public class AdapterChats extends BaseAdapter {
    TextView textname, tvcontent;

    ImageView imgv_;

    protected ImageLoader imageLoader_head;

    protected ImageLoader imageLoader_head_my;

    private DisplayImageOptions options_head; // 设置图片显示相关参数

    private Context context;

    private EMConversation conversation;

    private FriendEntity entity = new FriendEntity();

    public AdapterChats(Context context, Intent intent) {
        this.context = context;
        imageLoader_head = ImageLoader.getInstance();
        imageLoader_head_my = ImageLoader.getInstance();
        options_head = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ic_stub) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.ic_empty) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.ic_error) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(10)) // 设置成圆角图片
                .build(); // 构建完成
        entity = (FriendEntity)intent.getSerializableExtra("entity");
        String toChatUserName = entity.getH_username();
        conversation = EMChatManager.getInstance().getConversation(toChatUserName);
        // 设置所有消息可读
        conversation.markAllMessagesAsRead();
    }

    @Override
    public int getCount() {
        if (conversation != null && conversation.getAllMessages() != null) {
            return conversation.getAllMessages().size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (conversation != null && conversation.getAllMessages() != null) {
            return conversation.getAllMessages().get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        EMMessage message = conversation.getAllMessages().get(position);
        TextMessageBody body = (TextMessageBody)message.getBody();
        if (message.direct == EMMessage.Direct.RECEIVE) {
            if (message.getType() == EMMessage.Type.TXT) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_chat_friend, null);
            }
        } else if (message.getType() == EMMessage.Type.TXT) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_chat_my, null);
        }
        tvcontent = (TextView)convertView.findViewById(R.id.listview_item1_tv);
        imgv_ = (ImageView)convertView.findViewById(R.id.listview_item_imgv_head);
        if (message.direct == EMMessage.Direct.RECEIVE) {
            Log.e("+++imgv", entity.getHead_img());
            if (message.getType() == EMMessage.Type.TXT) {
                imageLoader_head.displayImage(entity.getHead_img(), imgv_, options_head);

            }
        } else {
            String imgv = new SharUtil(context).getHead_img();
            Log.e("---imgv", (imgv == null) + "");

            if (message.getType() == EMMessage.Type.TXT) {
                imageLoader_head.displayImage(imgv, imgv_, options_head);
            }
        }

        // 用户名
        // textname.setText(message.getFrom());

        Spannable span = SmileUtils.getSmiledText(context, body.getMessage());
        // 设置内容
        tvcontent.setText(span, BufferType.SPANNABLE);
        return convertView;
    }

    class ViewHolder {
        TextView tvname;

        TextView tvcontent;

        ImageView imgv;
    }

}
