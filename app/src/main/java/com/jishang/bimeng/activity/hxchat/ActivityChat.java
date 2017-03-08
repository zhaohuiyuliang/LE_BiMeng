
package com.jishang.bimeng.activity.hxchat;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMMessage;
import com.easemob.chat.TextMessageBody;
import com.jishang.bimeng.R;
import com.jishang.bimeng.adapter.hxadapter.ExpandGridView;
import com.jishang.bimeng.adapter.hxadapter.ExpressionAdapter;
import com.jishang.bimeng.adapter.hxadapter.ExpressionPagerAdapter;
import com.jishang.bimeng.entity.chat.Fdlist.FriendEntity;
import com.jishang.bimeng.ui.BaseActivity;
import com.jishang.bimeng.ui.UIHandler;
import com.jishang.bimeng.ui.adapter.AdapterChats;
import com.jishang.bimeng.utils.huanxin.SmileUtils;

/**
 * 与好友聊天 UI
 * 
 * @author wangliang Jul 14, 2016
 */
public class ActivityChat extends BaseActivity implements OnClickListener {
    /**
     * 发送聊天内容按钮
     */
    private Button mBt_send;

    /**
     * 聊天内容编辑框
     */
    private EditText mEdt_content;

    private ListView mListChats;

    private String toChatUserName = null;// 444hm
                                         // 129228pwd:TX19iLxOKhd42HwF8A5KBsj6kkRYgrD6
                                         // 616618pwd:5qlzk7j_JfkIJ0dliP6orBdNSZocy_w_

    /**
     * 聊天内容适配器
     */
    private AdapterChats adapterChats;

    private ImageView biaoqing_normal, biaoqing_checked;

    private ViewPager expressionViewpager;

    private List<String> reslist;

    private View buttonSetModeKeyboard;

    private LinearLayout btnContainer;

    private View more;

    private LinearLayout emojiIconContainer;

    private RelativeLayout edittext_layout;

    private View buttonSetModeVoice;

    private View buttonPressToSpeak;

    private Intent intent;

    private TextView mTv_username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        chatHandler = new ChatHandler(this);
        initView();
        addListener();
        serHintSize();
    }

    private void serHintSize() {
        // 新建一个可以添加属性的文本对象
        SpannableString ss = new SpannableString(getResources().getString(
                R.string.input_new_message));

        // 新建一个属性对象,设置文字的大小
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(14, true);

        // 附加属性到文本
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // 设置hint
        mEdt_content.setHint(new SpannedString(ss)); // 一定要进行转换,否则属性会消失
    }

    public void initView() {
        intent = getIntent();
        FriendEntity entity = (FriendEntity)intent.getSerializableExtra("entity");
        toChatUserName = entity.getH_username();
        ((TextView)findViewById(R.id.tv_back_to_activity)).setText("");
        // 与好友聊天内容列表控件View
        mListChats = (ListView)findViewById(R.id.list_chat);
        // 发送内容按钮
        mBt_send = (Button)findViewById(R.id.activity_main_send);
        // 聊天内容编辑框
        mEdt_content = (EditText)findViewById(R.id.activity_main_content);
        biaoqing_normal = (ImageView)findViewById(R.id.activity_main_biaoqing_normal);
        biaoqing_checked = (ImageView)findViewById(R.id.activity_main_biaoqing_checked);
        expressionViewpager = (ViewPager)findViewById(R.id.activity_main_expressionViewpager);

        buttonSetModeKeyboard = findViewById(R.id.btn_set_mode_keyboard);
        btnContainer = (LinearLayout)findViewById(R.id.ll_btn_container);
        edittext_layout = (RelativeLayout)findViewById(R.id.edittext_layout);
        buttonSetModeVoice = findViewById(R.id.btn_set_mode_voice);
        buttonPressToSpeak = findViewById(R.id.btn_press_to_speak);
        mTv_username = (TextView)findViewById(R.id.activity_main_demo_username);
        mTv_username.setText(entity.getUsername());
        emojiIconContainer = (LinearLayout)findViewById(R.id.ll_face_container);
        more = findViewById(R.id.more);
        // 适配器，适配聊天内容列表控件
        adapterChats = new AdapterChats(this, intent);
        mListChats.setAdapter(adapterChats);
        mListChats.setSelection(mListChats.getCount());
        // 只有注册了广播才能接收到新消息，目前离线消息，在线消息都是走接收消息的广播（离线消息目前无法监听，在登录以后，接收消息广播会执行一次拿到所有的离线消息）

        // 表情list
        reslist = getExpressionRes(35);
        // 初始化表情viewPager
        List<View> views = new ArrayList<View>();
        View gv1 = getGridChildView(1);
        View gv2 = getGridChildView(2);
        views.add(gv1);
        views.add(gv2);
        expressionViewpager.setAdapter(new ExpressionPagerAdapter(views));

    }

    /**
     * 显示语音图标按钮
     * 
     * @param view
     */
    public void setModeVoice(View view) {
        edittext_layout.setVisibility(View.GONE);
        more.setVisibility(View.GONE);
        view.setVisibility(View.GONE);
        buttonSetModeKeyboard.setVisibility(View.VISIBLE);
        buttonPressToSpeak.setVisibility(View.VISIBLE);
        btnContainer.setVisibility(View.VISIBLE);
        emojiIconContainer.setVisibility(View.GONE);

    }

    /**
     * 显示键盘图标
     * 
     * @param view
     */
    public void setModeKeyboard(View view) {
        edittext_layout.setVisibility(View.VISIBLE);
        more.setVisibility(View.GONE);
        view.setVisibility(View.GONE);
        buttonSetModeVoice.setVisibility(View.VISIBLE);
        mEdt_content.requestFocus();
        buttonPressToSpeak.setVisibility(View.GONE);

    }

    public List<String> getExpressionRes(int getSum) {
        List<String> reslist = new ArrayList<String>();
        for (int x = 1; x <= getSum; x++) {
            String filename = "ee_" + x;

            reslist.add(filename);

        }
        return reslist;

    }

    public void addListener() {
        mEdt_content.setOnClickListener(new OnClickListener() {// 不加这个监听会报错，再一次点击输入框会崩溃

                    @Override
                    public void onClick(View v) {

                        biaoqing_normal.setVisibility(View.VISIBLE);
                        biaoqing_checked.setVisibility(View.INVISIBLE);
                        more.setVisibility(View.GONE);
                        emojiIconContainer.setVisibility(View.GONE);
                        btnContainer.setVisibility(View.GONE);
                    }
                });
        mBt_send.setOnClickListener(this);
        biaoqing_normal.setOnClickListener(this);
        biaoqing_checked.setOnClickListener(this);
    }

    /**
     * 获取表情的GridView的子view
     * 
     * @param i
     * @return
     */
    private View getGridChildView(int i) {
        View view = View.inflate(this, R.layout.expression_gridview_demo, null);
        ExpandGridView gv = (ExpandGridView)view.findViewById(R.id.gridview);
        List<String> list = new ArrayList<String>();
        if (i == 1) {
            List<String> list1 = reslist.subList(0, 20);
            list.addAll(list1);
        } else if (i == 2) {
            list.addAll(reslist.subList(20, reslist.size()));
        }
        list.add("delete_expression");
        final ExpressionAdapter expressionAdapter = new ExpressionAdapter(this, 1, list);
        gv.setAdapter(expressionAdapter);
        gv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Toast.makeText(MainActivity_demo.this, position+"",
                // 0).show();
                String filename = expressionAdapter.getItem(position);
                try {
                    // 文字输入框可见时，才可输入表情
                    // 按住说话可见，不让输入表情
                    if (buttonSetModeKeyboard.getVisibility() != View.VISIBLE) {

                        if (filename != "delete_expression") { // 不是删除键，显示表情
                            // 这里用的反射，所以混淆的时候不要混淆SmileUtils这个类
                            Class clz = Class
                                    .forName("com.jishang.bimeng.utils.huanxin.SmileUtils");
                            Field field = clz.getField(filename);
                            mEdt_content.append(SmileUtils.getSmiledText(ActivityChat.this,
                                    (String)field.get(null)));
                        } else if (!TextUtils.isEmpty(mEdt_content.getText())) {// 删除文字或者表情

                            int selectionStart = mEdt_content.getSelectionStart();// 获取光标的位置
                            if (selectionStart > 0) {
                                String body = mEdt_content.getText().toString();
                                String tempStr = body.substring(0, selectionStart);
                                int i = tempStr.lastIndexOf("[");// 获取最后一个表情的位置
                                if (i != -1) {
                                    CharSequence cs = tempStr.substring(i, selectionStart);
                                    if (SmileUtils.containsKey(cs.toString()))
                                        mEdt_content.getEditableText().delete(i, selectionStart);
                                    else
                                        mEdt_content.getEditableText().delete(selectionStart - 1,
                                                selectionStart);
                                } else {
                                    mEdt_content.getEditableText().delete(selectionStart - 1,
                                            selectionStart);
                                }
                            }

                        }
                    }
                } catch (Exception e) {
                }

            }
        });
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_main_send:// 发送聊天按钮
                sendChatContent();
                break;
            case R.id.activity_main_biaoqing_normal:
                biaoqing_normal.setVisibility(View.INVISIBLE);
                biaoqing_checked.setVisibility(View.VISIBLE);

                more.setVisibility(View.VISIBLE);
                btnContainer.setVisibility(View.GONE);
                emojiIconContainer.setVisibility(View.VISIBLE);

                break;
            case R.id.activity_main_biaoqing_checked:
                biaoqing_normal.setVisibility(View.VISIBLE);
                biaoqing_checked.setVisibility(View.INVISIBLE);

                more.setVisibility(View.GONE);
                btnContainer.setVisibility(View.GONE);
                emojiIconContainer.setVisibility(View.VISIBLE);

                break;
            default:
                break;

        }
    }

    /**
     * 返回
     * 
     * @param view
     */
    public void back(View view) {
        finish();
    }

    /**
     * 发送聊天内容
     */
    public void sendChatContent() {
        if (TextUtils.isEmpty(mEdt_content.getText().toString())) {
            return;
        }
        // Toast.makeText(MainActivity_demo.this, "点击有效", 0).show();
        // 获取到与聊天人的会话对象。参数userName为聊天人的userId或者groupId，后文中的userName皆是如此
        EMConversation conversation = EMChatManager.getInstance().getConversation(toChatUserName);
        // 创建一条文本消息
        EMMessage message = EMMessage.createSendMessage(EMMessage.Type.TXT);
        // 如果是群聊，设置chatType,默认是单聊
        // 设置消息body
        TextMessageBody txtBody = new TextMessageBody(mEdt_content.getText().toString());
        message.addBody(txtBody);
        // 设置接收人
        message.setReceipt(toChatUserName);
        // 把消息加入到此会话对象中
        conversation.addMessage(message);
        adapterChats.notifyDataSetChanged();
        mListChats.setSelection(mListChats.getCount() - 1);
        mEdt_content.setText("");
        // 发送消息
        EMChatManager.getInstance().sendMessage(message, new EMCallBack() {

            @Override
            public void onError(int arg0, String arg1) {
                // 聊天内容发送失败
            }

            @Override
            public void onProgress(int arg0, String arg1) {

            }

            @Override
            public void onSuccess() {
                // 聊天内容发送成功
            }
        });
    }

    public class ChatHandler extends UIHandler {

        public ChatHandler(Activity activity) {
            super(activity);
        }

        @Override
        public void onMessage(Message paramMessage) {
            switch (paramMessage.what) {
                case REFRESH_UI:
                    EMConversation conversation = EMChatManager.getInstance().getConversation(
                            toChatUserName);
                    conversation.markAllMessagesAsRead();
                    adapterChats.notifyDataSetChanged();
                    mListChats.setSelection(mListChats.getCount());
                    break;

                default:
                    break;
            }
        }
    }

    private ChatHandler chatHandler;

    @Override
    protected void onResume() {
        super.onResume();
        application.setUIHandler(chatHandler);
        chatHandler.setComponentName(chatHandler.getClass().getName());
    }

    @Override
    protected void onPause() {
        super.onPause();
        application.setUIHandler(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
