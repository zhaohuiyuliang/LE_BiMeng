
package com.jishang.bimeng.net;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.os.Message;
import android.util.Log;
import android.util.Pair;

import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMMessage;
import com.easemob.chat.TextMessageBody;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jishang.bimeng.entity.chat.Fdlist.FdlistEntity;
import com.jishang.bimeng.entity.chat.Fdlist.FriendEntity;
import com.jishang.bimeng.ui.UIHandler;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.Internet.MyHttpRequest;
import com.jishang.bimeng.utils.db.PersonEntity;
import com.jishang.bimeng.utils.db.Person_Entity;
import com.jishang.bimeng.utils.url.UrlUtils;

/**
 * 请求历史会话
 * 
 * @author wangliang Jul 15, 2016
 */
public class TaskRequestConversations extends LoadData {
    UIHandler handler;

    public TaskRequestConversations(UIHandler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        conversationList.clear();
        conversationList.addAll(loadConversationsWithRecentChat());
        List<Person_Entity> listPerson_Entities = getListData();
        Message msg = new Message();
        msg.obj = listPerson_Entities;
        msg.what = UIHandler.REQUEST_CONVERSATION_SUCCESS;
        handler.sendMessage(msg);
    }

    private List<EMConversation> conversationList = new ArrayList<EMConversation>();

    public List<Person_Entity> getListData() {
        List<Person_Entity> person_entity = new ArrayList<Person_Entity>();
        for (int j = 0; j < conversationList.size(); j++) {
            EMConversation emConversation = conversationList.get(j);
            Person_Entity person = new Person_Entity();

            String[] requests = requestCon(emConversation.getUserName());
            person.setHuanid(emConversation.getUserName());
            person.setImg(requests[0]);
            person.setName(requests[1]);
            person.setWeidu(emConversation.getUnreadMsgCount());
            person.message_chat_end = emConversation.getLastMessage().toString();
            person.setWeidu(emConversation.getUnreadMsgCount());
            EMMessage.Type type = emConversation.getLastMessage().getType();
            switch (type) {
                case TXT:
                    TextMessageBody textMessageBody = (TextMessageBody)emConversation
                            .getLastMessage().getBody();
                    person.message_chat_end = textMessageBody.getMessage();
                    break;
                case FILE:// 文件
                    break;
                case IMAGE:// 图片
                    break;
                default:
                    break;
            }

            person.time_chat_end = getTime(emConversation.getLastMessage().getMsgTime());
            person_entity.add(person);
        }
        return person_entity;
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
        List<Pair<Long, EMConversation>> sortList = new ArrayList<Pair<Long, EMConversation>>();
        synchronized (conversations) {
            for (EMConversation conversation : conversations.values()) {
                if (conversation.getAllMessages().size() != 0) {
                    Log.e("+++", conversation.getLastMessage().getUserName());
                    sortList.add(new Pair<Long, EMConversation>(conversation.getLastMessage()
                            .getMsgTime(), conversation));
                }
            }
        }
        try {
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

    /**
     * 加载好友列表数据
     */
    public List<PersonEntity> loadFriends() {
        List<PersonEntity> listEntities = new ArrayList<PersonEntity>();
        String token = new SharUtil(application).getAccess_token();
        List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("11", "11"));
        String url = UrlUtils.CHECK_FRIENDS;
        String result = MyHttpRequest.getHttpPostBasic(url, params, token);
        try {
            if (result != null) {
                Gson mGson = new Gson();
                FdlistEntity entity = mGson.fromJson(result, FdlistEntity.class);
                if (entity.getStatus() == 1) {
                    List<FriendEntity> entities = entity.getData();
                    for (FriendEntity friendEntity : entities) {
                        PersonEntity personEntity = new PersonEntity();
                        personEntity.huanid = friendEntity.h_username;
                        personEntity.name = friendEntity.username;
                        personEntity.img = friendEntity.head_img;
                        listEntities.add(personEntity);
                    }
                } else if (entity.getStatus() == 0) {
                    handler.sendEmptyMessage(UIHandler.REQUEST_FRIENDS_FAILED);
                }

            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            handler.sendEmptyMessage(UIHandler.REQUEST_FRIENDS_FAILED);

        }

        return listEntities;
    }

    private String getTime(long time) {
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
        // 进行格式化
        String strs = sdf.format(date);
        return strs;
    }

    /**
     * 从比盟服务器获取头像及昵称，根据环信ID
     * 
     * @return
     */
    private String[] requestCon(String huanID) {
        String[] str = new String[2];
        String token = new SharUtil(application).getAccess_token();
        List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("h_username", huanID));
        String url = "http://apicms.gbimoo.com/v1/user/news_head_portrait.json";
        String result = MyHttpRequest.getHttpPostBasic(url, params, token);
        try {
            if (result != null) {
                Gson mGson = new Gson();
                FdEntity entity = mGson.fromJson(result, FdEntity.class);
                if (entity.getStatus() == 1) {
                    str[0] = entity.getData().head_img;
                    str[1] = entity.getData().username;
                }
            }
        } catch (JsonSyntaxException e) {

        }
        return str;
    }

    class FdEntity {
        private int status;

        private String status_code;

        private FriendEntity data;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getStatus_code() {
            return status_code;
        }

        public void setStatus_code(String status_code) {
            this.status_code = status_code;
        }

        public FriendEntity getData() {
            return data;
        }

        public void setData(FriendEntity data) {
            this.data = data;
        }

    }
}
