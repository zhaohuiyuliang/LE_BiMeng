
package com.jishang.bimeng.fragment.xiaoxi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.BufferType;

import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMMessage;
import com.easemob.chat.TextMessageBody;
import com.jishang.bimeng.R;
import com.jishang.bimeng.activity.hxchat.ActivityChat;
import com.jishang.bimeng.entity.chat.Fdlist.FriendEntity;
import com.jishang.bimeng.utils.DateUtils;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.huanxin.SmileUtils;

public class Fragment_wdxx extends Fragment implements OnClickListener {
    private View v;

    private ListView mList;

    private List<EMConversation> conversationList = new ArrayList<EMConversation>();

    private ChatAllHistoryAdapter adapter;

    private Context mContext;

    public static FragmentActivity instance = null;

    // private RelativeLayout mRl_addfs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_wdxx, null);
        initView(v);
        initData();
        addListener();
        return v;
    }

    public void initView(View v) {
        instance = getActivity();
        mList = (ListView)v.findViewById(R.id.activity_hxhistory_list);
        // mRl_addfs=(RelativeLayout)v.findViewById(R.id.activity_hxhistory_addfs);
        mContext = getActivity();

    }

    public void initData() {
        conversationList.clear();
        conversationList.addAll(loadConversationsWithRecentChat());
        adapter = new ChatAllHistoryAdapter();
        mList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void addListener() {
        // mRl_addfs.setOnClickListener(this);
        mList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EMConversation conversation = conversationList.get(position);
                String username = conversation.getUserName();
                FriendEntity entity = new FriendEntity();
                entity.setH_username(username);
                entity.setHead_img(new SharUtil(mContext).getHead_img());
                Intent intent = new Intent(mContext, ActivityChat.class);
                intent.putExtra("entity", entity);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        // switch (v.getId()) {
        // case R.id.activity_hxhistory_addfs:
        // Intent intent4 = new Intent(mContext,
        // SearchFriendActivity.class);
        // startActivity(intent4);
        // break;

        // }
    }

    /**
     * 获取所有会话
     * 
     * @param context
     * @return +
     */
    private List<EMConversation> loadConversationsWithRecentChat() {
        // 获取所有会话，包括陌生人
        Hashtable<String, EMConversation> conversations = EMChatManager.getInstance()
                .getAllConversations();
        // 过滤掉messages size为0的conversation
        /**
         * 如果在排序过程中有新消息收到，lastMsgTime会发生变化 影响排序过程，Collection.sort会产生异常
         * 保证Conversation在Sort过程中最后一条消息的时间不变 避免并发问题
         */
        Log.e("---", conversations.toString());
        List<Pair<Long, EMConversation>> sortList = new ArrayList<Pair<Long, EMConversation>>();
        synchronized (conversations) {
            for (EMConversation conversation : conversations.values()) {
                if (conversation.getAllMessages().size() != 0) {
                    // if(conversation.getType() !=
                    // EMConversationType.ChatRoom){
                    Log.e("+++", conversation.getLastMessage().getUserName());
                    sortList.add(new Pair<Long, EMConversation>(conversation.getLastMessage()
                            .getMsgTime(), conversation));
                    // }
                }
            }
        }
        try {
            // Internal is TimSort algorithm, has bug
            sortConversationByLastChatTime(sortList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<EMConversation> list = new ArrayList<EMConversation>();
        for (Pair<Long, EMConversation> sortItem : sortList) {
            list.add(sortItem.second);
        }
        return list;
    }

    /**
     * 根据最后一条消息的时间排序
     * 
     * @param usernames
     */
    private void sortConversationByLastChatTime(List<Pair<Long, EMConversation>> conversationList) {
        Collections.sort(conversationList, new Comparator<Pair<Long, EMConversation>>() {
            @Override
            public int compare(final Pair<Long, EMConversation> con1,
                    final Pair<Long, EMConversation> con2) {

                if (con1.first == con2.first) {
                    return 0;

                } else if (con2.first > con1.first) {
                    return 1;
                } else {
                    return -1;
                }
            }

        });
    }

    class ChatAllHistoryAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (conversationList.size() == 0) {
                return 0;
            }
            return conversationList.size();
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
            Viewhoder vh = null;
            if (convertView == null) {
                vh = new Viewhoder();
                convertView = View.inflate(mContext, R.layout.item_chat_history, null);
                vh.avatar = (ImageView)convertView.findViewById(R.id.hxhistoty_item_head);
                vh.name = (TextView)convertView.findViewById(R.id.hxhistoty_item_name);
                vh.time = (TextView)convertView.findViewById(R.id.hxhistoty_item_head_time);
                vh.message = (TextView)convertView.findViewById(R.id.hxhistoty_item_head_message);
                convertView.setTag(vh);

            } else {
                vh = (Viewhoder)convertView.getTag();
            }
            EMConversation conversation = conversationList.get(position);
            String username = conversation.getUserName();
            EMMessage lastMessage = conversation.getLastMessage();
            vh.name.setText(username);
            vh.message.setText(
                    SmileUtils.getSmiledText(getContext(),
                            getMessageDigest(lastMessage, getContext())), BufferType.SPANNABLE);
            vh.time.setText(DateUtils.getTimestampString(new Date(lastMessage.getMsgTime())));

            return convertView;
        }

    }

    public class Viewhoder {
        /** 和谁的聊天记录 */
        TextView name;

        /** 消息未读数 */
        TextView unreadLabel;

        /** 最后一条消息的内容 */
        TextView message;

        /** 最后一条消息的时间 */
        TextView time;

        /** 用户头像 */
        ImageView avatar;

        /** 最后一条消息的发送状态 */
        View msgState;

        /** 整个list中每一行总布局 */
        RelativeLayout list_item_layout;
    }

    /**
     * 根据消息内容和消息类型获取消息内容提示
     * 
     * @param message
     * @param context
     * @return
     */
    private String getMessageDigest(EMMessage message, Context context) {
        String digest = "";
        switch (message.getType()) {
            case LOCATION: // 位置消息
                if (message.direct == EMMessage.Direct.RECEIVE) {
                    // 从sdk中提到了ui中，使用更简单不犯错的获取string的方法
                    // digest = EasyUtils.getAppResourceString(context,
                    // "location_recv");
                    digest = "[%1$s的位置]";
                    digest = String.format(digest, message.getFrom());
                    return digest;
                } else {
                    // digest = EasyUtils.getAppResourceString(context,
                    // "location_prefix");
                    digest = "我的位置";
                }
                break;
            case IMAGE: // 图片消息
                digest = "图片";
                break;
            case VOICE:// 语音消息
                digest = "语音";
                break;
            case VIDEO: // 视频消息
                digest = "视频";
                break;
            case TXT: // 文本消息
                if (!message.getBooleanAttribute("is_voice_call", false)) {
                    TextMessageBody txtBody = (TextMessageBody)message.getBody();
                    digest = txtBody.getMessage();
                } else {
                    TextMessageBody txtBody = (TextMessageBody)message.getBody();
                    digest = "[语音通话]";
                }
                break;
            case FILE: // 普通文件消息
                break;
            default:
                return "";
        }

        return digest;
    }

    public Context getContext() {
        return mContext;
    }

    /**
     * 刷新页面
     */
    public void refresh() {
        conversationList.clear();
        conversationList.addAll(loadConversationsWithRecentChat());
        if (adapter != null)
            adapter.notifyDataSetChanged();
    }

}
