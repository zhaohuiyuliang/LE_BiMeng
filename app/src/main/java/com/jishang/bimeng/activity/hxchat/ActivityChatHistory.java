
package com.jishang.bimeng.activity.hxchat;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.easemob.chat.EMMessage;
import com.easemob.chat.TextMessageBody;
import com.jishang.bimeng.R;
import com.jishang.bimeng.activity.base.BaseActivity;
import com.jishang.bimeng.entity.chat.Fdlist.FriendEntity;
import com.jishang.bimeng.entity.chat.chaitghis.Chaiths_dataEntity;
import com.jishang.bimeng.ui.UIHandler;
import com.jishang.bimeng.ui.adapter.ChatAllHistoryAdapter;
import com.jishang.bimeng.ui.adapter.ChatAllHistoryAdapter.IModelChatHistory;
import com.jishang.bimeng.utils.ToastUtil;
import com.jishang.bimeng.utils.db.Person_Entity;

/**
 * 历史聊天 UI
 * 
 * @author wangliang Jul 16, 2016
 */
public class ActivityChatHistory extends BaseActivity implements OnClickListener, IModelChatHistory {
    /**
     * 历史联系人列表
     */
    private ListView mListChatHistroys;

    private ChatAllHistoryAdapter adapterChatHistroy;

    @Override
    public int initResource() {
        return R.layout.activity_hxhistory;
    }

    @Override
    public void initView() {
        chatHistoryHander = new ChatHistoryHander(this);
        mListChatHistroys = (ListView)findViewById(R.id.activity_hxhistory_list);
        adapterChatHistroy = new ChatAllHistoryAdapter(this, this);
        mListChatHistroys.setAdapter(adapterChatHistroy);

        Intent intent = getIntent();
        if (intent != null) {
            String back = intent.getStringExtra("back");
            ((TextView)findViewById(R.id.tv_back_to_activity)).setText(back);
        }
        uiHandler = new ChatHistroyHandler(this);

    }

    @Override
    public void initData() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        application.setUIHandler(chatHistoryHander);
        loadConversations();
    }

    @Override
    protected void onPause() {
        super.onPause();
        application.setUIHandler(null);
    }

    /**
     * 刷新页面
     */
    public void loadConversations() {
        application.getController().doRequestConversation(uiHandler);

    }

    @Override
    public void addListener() {
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
                    digest = "[%1$s的位置]";
                    digest = String.format(digest, message.getFrom());
                    return digest;
                } else {
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

    public void back(View v) {
        finish();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case 200:

                break;
            case 201:

                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    class ChatHistroyHandler extends UIHandler {
        ActivityChatHistory chatHistoryActivity = (ActivityChatHistory)getActivity();

        public ChatHistroyHandler(Activity activity) {
            super(activity);
        }

        public void onMessage(android.os.Message msg) {
            switch (msg.what) {
                case REQUEST_CONVERSATION_SUCCESS: {// 获取历史会话成功
                    List<Person_Entity> listEntities = (ArrayList<Person_Entity>)msg.obj;
                    adapterChatHistroy.setData(listEntities);
                }
                    break;
                case REQUEST_CONVERSATION_FAILED:// 获取历史会话失败

                    break;
                case 0: {
                    String erros = (String)msg.obj;
                    ToastUtil.Toast(chatHistoryActivity, erros);
                }
                    break;
                case 1: {
                    Chaiths_dataEntity entity = (Chaiths_dataEntity)msg.obj;
                    Intent intent = new Intent(chatHistoryActivity, PeopleActivity.class);
                    intent.putExtra("entity", entity);
                    startActivityForResult(intent, 100);
                }
                    break;
                case 2:
                    ToastUtil.Toast(chatHistoryActivity, "网络错误");
                    break;
                default:
                    break;

            }
        };
    };

    @Override
    public void setOnClickListener(Person_Entity entity_) {
        FriendEntity entity = new FriendEntity();
        entity.setH_username(entity_.getHuanid());
        entity.setHead_img(entity_.getImg());
        entity.setUsername(entity_.getName());
        Intent intent = new Intent(this, ActivityChat.class);
        intent.putExtra("entity", entity);
        startActivity(intent);
    }

    private ChatHistoryHander chatHistoryHander;

    public class ChatHistoryHander extends UIHandler {

        public ChatHistoryHander(Activity activity) {
            super(activity);
        }

        @Override
        public void onMessage(Message paramMessage) {
            switch (paramMessage.what) {
                case REFRESH_UI:
                    loadConversations();
                    break;

                default:
                    break;
            }

        }

    }

}
