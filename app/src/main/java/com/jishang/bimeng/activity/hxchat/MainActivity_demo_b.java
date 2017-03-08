
package com.jishang.bimeng.activity.hxchat;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import android.widget.Toast;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChat;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMMessage;
import com.easemob.chat.EMMessage.ChatType;
import com.easemob.chat.TextMessageBody;
import com.jishang.bimeng.R;
import com.jishang.bimeng.adapter.hxadapter.ExpandGridView;
import com.jishang.bimeng.adapter.hxadapter.ExpressionAdapter;
import com.jishang.bimeng.adapter.hxadapter.ExpressionPagerAdapter;
import com.jishang.bimeng.entity.chat.Fdlist.FriendEntity;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.huanxin.SmileUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class MainActivity_demo_b extends Activity implements OnClickListener {

    private Button mBt_send;

    private EditText mEdt_content;

    private ListView mList;

    private EMConversation conversation;

    private String toChatUserName = null;// 444hm
                                         // 129228pwd:TX19iLxOKhd42HwF8A5KBsj6kkRYgrD6
                                         // 616618pwd:5qlzk7j_JfkIJ0dliP6orBdNSZocy_w_

    private DataAdapter adapter;

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

    private Context context;

    private Intent intent;

    private TextView mTv_username;

    NewMessageBroadcastReceiver msgReceiver;

    protected ImageLoader imageLoader_head;

    protected ImageLoader imageLoader_head_my;

    private DisplayImageOptions options_head; // 设置图片显示相关参数

    private FriendEntity entity = new FriendEntity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initView();
        addListener();
    }

    public void initView() {
        context = this;
        intent = getIntent();
        // Log.e("---", (String) intent.getSerializableExtra("entity"));
        entity = (FriendEntity)intent.getSerializableExtra("entity");
        toChatUserName = entity.getH_username();

        imageLoader_head = ImageLoader.getInstance();
        imageLoader_head_my = ImageLoader.getInstance();
        options_head = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ic_stub) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.ic_empty) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.ic_error) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(10)) // 设置成圆角图片
                .build(); // 构建完成
        mBt_send = (Button)findViewById(R.id.activity_main_send);
        mEdt_content = (EditText)findViewById(R.id.activity_main_content);
        biaoqing_normal = (ImageView)findViewById(R.id.activity_main_biaoqing_normal);
        biaoqing_checked = (ImageView)findViewById(R.id.activity_main_biaoqing_checked);
        mList = (ListView)findViewById(R.id.list_chat);
        expressionViewpager = (ViewPager)findViewById(R.id.activity_main_expressionViewpager);
        conversation = EMChatManager.getInstance().getConversation(toChatUserName);
        buttonSetModeKeyboard = findViewById(R.id.btn_set_mode_keyboard);
        btnContainer = (LinearLayout)findViewById(R.id.ll_btn_container);
        edittext_layout = (RelativeLayout)findViewById(R.id.edittext_layout);
        buttonSetModeVoice = findViewById(R.id.btn_set_mode_voice);
        buttonPressToSpeak = findViewById(R.id.btn_press_to_speak);
        mTv_username = (TextView)findViewById(R.id.activity_main_demo_username);
        mTv_username.setText(entity.getUsername());
        emojiIconContainer = (LinearLayout)findViewById(R.id.ll_face_container);
        more = findViewById(R.id.more);
        adapter = new DataAdapter();
        mList.setAdapter(adapter);
        if (adapter != null) {
            adapter.notifyDataSetChanged();
            mList.setSelection(mList.getCount());
        }

        // 只有注册了广播才能接收到新消息，目前离线消息，在线消息都是走接收消息的广播（离线消息目前无法监听，在登录以后，接收消息广播会执行一次拿到所有的离线消息）
        msgReceiver = new NewMessageBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter(EMChatManager.getInstance()
                .getNewMessageBroadcastAction());
        intentFilter.setPriority(3);
        registerReceiver(msgReceiver, intentFilter);
        EMChat.getInstance().setAppInited();

        // 表情list
        reslist = getExpressionRes(35);
        // 初始化表情viewpager
        List<View> views = new ArrayList<View>();
        View gv1 = getGridChildView(1);
        View gv2 = getGridChildView(2);
        views.add(gv1);
        views.add(gv2);
        expressionViewpager.setAdapter(new ExpressionPagerAdapter(views));

        // 接收添加好友广播
        /*
         * //注册一个好友请求等的BroadcastReceiver IntentFilter inviteIntentFilter = new
         * IntentFilter
         * (EMChatManager.getInstance().getContactInviteEventBroadcastAction());
         * registerReceiver(contactInviteReceiver, inviteIntentFilter);
         */

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
        // mEditTextContent.setVisibility(View.VISIBLE);
        mEdt_content.requestFocus();
        // buttonSend.setVisibility(View.VISIBLE);
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
     * 获取表情的gridview的子view
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
                        // Log.e("11", "111");

                        if (filename != "delete_expression") { // 不是删除键，显示表情
                            // Log.e("11", "222");
                            // 这里用的反射，所以混淆的时候不要混淆SmileUtils这个类
                            Class clz = Class
                                    .forName("com.jishang.bimeng.utils.huanxin.SmileUtils");
                            Field field = clz.getField(filename);
                            mEdt_content.append(SmileUtils.getSmiledText(MainActivity_demo_b.this,
                                    (String)field.get(null)));
                        } else { // 删除文字或者表情
                            if (!TextUtils.isEmpty(mEdt_content.getText())) {

                                int selectionStart = mEdt_content.getSelectionStart();// 获取光标的位置
                                if (selectionStart > 0) {
                                    String body = mEdt_content.getText().toString();
                                    String tempStr = body.substring(0, selectionStart);
                                    int i = tempStr.lastIndexOf("[");// 获取最后一个表情的位置
                                    if (i != -1) {
                                        CharSequence cs = tempStr.substring(i, selectionStart);
                                        if (SmileUtils.containsKey(cs.toString()))
                                            mEdt_content.getEditableText()
                                                    .delete(i, selectionStart);
                                        else
                                            mEdt_content.getEditableText().delete(
                                                    selectionStart - 1, selectionStart);
                                    } else {
                                        mEdt_content.getEditableText().delete(selectionStart - 1,
                                                selectionStart);
                                    }
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
            case R.id.activity_main_send:
                SendMsg();
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

    public void SendMsg() {
        // Toast.makeText(MainActivity_demo.this, "点击有效", 0).show();
        // 获取到与聊天人的会话对象。参数username为聊天人的userid或者groupid，后文中的username皆是如此
        EMConversation conversation = EMChatManager.getInstance().getConversation(toChatUserName);
        // 创建一条文本消息
        EMMessage message = EMMessage.createSendMessage(EMMessage.Type.TXT);
        // 如果是群聊，设置chattype,默认是单聊
        // 设置消息body
        TextMessageBody txtBody = new TextMessageBody(mEdt_content.getText().toString());
        message.addBody(txtBody);
        // 设置接收人
        message.setReceipt(toChatUserName);
        // 把消息加入到此会话对象中
        conversation.addMessage(message);
        adapter.notifyDataSetChanged();
        mList.setSelection(mList.getCount() - 1);
        mEdt_content.setText("");
        // 发送消息
        EMChatManager.getInstance().sendMessage(message, new EMCallBack() {

            @Override
            public void onError(int arg0, String arg1) {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Toast.makeText(MainActivity_demo_b.this, "发送失败", 0).show();

                    }
                });
            }

            @Override
            public void onProgress(int arg0, String arg1) {

            }

            @Override
            public void onSuccess() {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Toast.makeText(MainActivity_demo_b.this, "发送成功", 0).show();

                    }
                });
            }
        });
    }

    private class NewMessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            // 注销广播
            abortBroadcast();

            // 消息id（每条消息都会生成唯一的一个id，目前是SDK生成）
            String msgId = intent.getStringExtra("msgid");
            // 发送方
            String username = intent.getStringExtra("from");
            // 收到这个广播的时候，message已经在db和内存里了，可以通过id获取mesage对象
            EMMessage message = EMChatManager.getInstance().getMessage(msgId);
            EMConversation conversation = EMChatManager.getInstance().getConversation(username);
            // 如果是群聊消息，获取到group id
            if (message.getChatType() == ChatType.GroupChat) {
                username = message.getTo();
            }
            if (!username.equals(username)) {
                // 消息不是发给当前会话，return
                return;
            }
            /*
             * //如果是当前会话的消息，刷新聊天页面 if(username.equals(toChatUserName)){
             * //声音和震动提示有新消息 new HXNotifier().viberateAndPlayTone(message);
             * }else{ //如果消息不是和当前聊天ID的消息 new HXNotifier().onNewMsg(message); }
             */
            conversation.addMessage(message);
            adapter.notifyDataSetChanged();
            mList.setAdapter(adapter);
            mList.setSelection(mList.getCount() - 1);
        }
    }

    private class DataAdapter extends BaseAdapter {
        TextView textname;

        @Override
        public int getCount() {
            return conversation.getAllMessages().size();
        }

        @Override
        public Object getItem(int position) {
            return conversation.getAllMessages().get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            EMMessage message = conversation.getAllMessages().get(position);
            TextMessageBody body = (TextMessageBody)message.getBody();
            if (convertView == null) {
                viewHolder = new ViewHolder();
                if (message.direct == EMMessage.Direct.RECEIVE) {
                    Log.e("message.direct", message.direct + "");
                    if (message.getType() == EMMessage.Type.TXT) {
                        convertView = LayoutInflater.from(MainActivity_demo_b.this).inflate(
                                R.layout.item_chat_friend, null);

                    }
                } else {
                    if (message.getType() == EMMessage.Type.TXT) {
                        convertView = LayoutInflater.from(MainActivity_demo_b.this).inflate(
                                R.layout.item_chat_my, null);
                    }
                }
                // viewHolder.tvname = (TextView) convertView
                // .findViewById(R.id.listview_item_tv);
                viewHolder.tvcontent = (TextView)convertView.findViewById(R.id.listview_item1_tv);
                viewHolder.imgv = (ImageView)convertView.findViewById(R.id.listview_item_imgv_head);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder)convertView.getTag();
            }
            if (message.direct == EMMessage.Direct.RECEIVE) {
                Log.e("+++imgv", entity.getHead_img());
                if (message.getType() == EMMessage.Type.TXT) {
                    imageLoader_head.displayImage(entity.getHead_img(), viewHolder.imgv,
                            options_head);

                }
            } else {
                String imgv = new SharUtil(MainActivity_demo_b.this).getHead_img();
                Log.e("---imgv", (imgv == null) + "");

                if (message.getType() == EMMessage.Type.TXT) {
                    imageLoader_head.displayImage(imgv, viewHolder.imgv, options_head);
                }
            }

            // textname = (TextView) convertView
            // .findViewById(R.id.listview_item_tv);
            // viewHolder.tvname.setText(message.getFrom());
            // TextView tv = (TextView) convertView
            // .findViewById(R.id.listview_item1_tv);
            // Log.e("message", body.getMessage());
            // tv.setText(body.getMessage());
            Spannable span = SmileUtils.getSmiledText(context, body.getMessage());
            // 设置内容
            viewHolder.tvcontent.setText(span, BufferType.SPANNABLE);
            return convertView;
        }

    }

    class ViewHolder {
        TextView tvname;

        TextView tvcontent;

        ImageView imgv;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(msgReceiver);

    }
}
