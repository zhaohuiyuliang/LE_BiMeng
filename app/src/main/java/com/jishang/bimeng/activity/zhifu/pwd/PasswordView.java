package com.jishang.bimeng.activity.zhifu.pwd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jishang.bimeng.R;

/**
 * Belong to the Project 鈥斺�� MyPayUI
 * Created by WangJ on 2015/11/25 15:39.
 */
public class PasswordView extends RelativeLayout implements View.OnClickListener {
    Context context;

    private String strPassword;     //杈撳叆鐨勫瘑鐮�
    private TextView[] tvList;      //鐢ㄦ暟缁勪繚瀛�6涓猅extView锛屼负浠�涔堢敤鏁扮粍锛�
                                    //鍥犱负灏�6涓緭鍏ユ涓嶄細鍙樹簡锛岀敤鏁扮粍鍐呭瓨鐢宠鍥哄畾绌洪棿锛屾瘮List鐪佺┖闂达紙鑷繁璁や负锛�
    private GridView gridView;    //鐢℅rideView甯冨眬閿洏锛屽叾瀹炲苟涓嶆槸鐪熸鐨勯敭鐩橈紝鍙槸妯℃嫙閿洏鐨勫姛鑳�
    private ArrayList<Map<String, String>> valueList;    //鏈変汉鍙兘鏈夌枒闂紝涓轰綍杩欓噷涓嶇敤鏁扮粍浜嗭紵
                                                       //鍥犱负瑕佺敤Adapter涓�傞厤锛岀敤鏁扮粍涓嶈兘寰�adapter涓～鍏�

    private ImageView imgCancel;
    private TextView tvForget;
    private int currentIndex = -1;    //鐢ㄤ簬璁板綍褰撳墠杈撳叆瀵嗙爜鏍间綅缃�

    public PasswordView(Context context) {
        this(context, null);
    }

    public PasswordView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        View view = View.inflate(context, R.layout.layout_popup_bottom, null);
        
        valueList = new ArrayList<Map<String, String>>();
        tvList = new TextView[6];
        
        imgCancel = (ImageView) view.findViewById(R.id.img_cancel);
        imgCancel.setOnClickListener(this);

        tvForget = (TextView) view.findViewById(R.id.tv_forgetPwd);
        tvForget.setOnClickListener(this);
        
        tvList[0] = (TextView) view.findViewById(R.id.tv_pass1);
        tvList[1] = (TextView) view.findViewById(R.id.tv_pass2);
        tvList[2] = (TextView) view.findViewById(R.id.tv_pass3);
        tvList[3] = (TextView) view.findViewById(R.id.tv_pass4);
        tvList[4] = (TextView) view.findViewById(R.id.tv_pass5);
        tvList[5] = (TextView) view.findViewById(R.id.tv_pass6);

        gridView = (GridView) view.findViewById(R.id.gv_keybord);

        setView();
        
        addView(view);      //蹇呴』瑕侊紝涓嶇劧涓嶆樉绀烘帶浠�
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_cancel:
                Toast.makeText(context, "Cancel", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_forgetPwd:
                Toast.makeText(context, "Forget", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void setView() {
    	/* 鍒濆鍖栨寜閽笂搴旇鏄剧ず鐨勬暟瀛� */
        for (int i = 1; i < 13; i++) {
            Map<String, String> map = new HashMap<String, String>();
            if (i < 10) {
                map.put("name", String.valueOf(i));
            } else if (i == 10) {
                map.put("name", "");
            } else if (i == 11) {
                map.put("name", String.valueOf(0));
            } else if (i == 12) {
                map.put("name", "X");
            }
            valueList.add(map);
        }

        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position < 11 && position != 9) {    //鐐瑰嚮0~9鎸夐挳
                    if (currentIndex >= -1 && currentIndex < 5) {      //鍒ゆ柇杈撳叆浣嶇疆鈥斺�斺�斺�旇灏忓績鏁扮粍瓒婄晫
                        tvList[++currentIndex].setText(valueList.get(position).get("name"));
                    }
                } else {
                    if (position == 11) {      //鐐瑰嚮閫�鏍奸敭
                        if (currentIndex - 1 >= -1) {      //鍒ゆ柇鏄惁鍒犻櫎瀹屾瘯鈥斺�斺�斺�旇灏忓績鏁扮粍瓒婄晫
                            tvList[currentIndex--].setText("");
                        }
                    }
                }
            }
        });
    }

    //璁剧疆鐩戝惉鏂规硶锛屽湪绗�6浣嶈緭鍏ュ畬鎴愬悗瑙﹀彂
    public void setOnFinishInput(final OnPasswordInputFinish pass) {
        tvList[5].addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 1) {
                    strPassword = "";     //姣忔瑙﹀彂閮借鍏堝皢strPassword缃┖锛屽啀閲嶆柊鑾峰彇锛岄伩鍏嶇敱浜庤緭鍏ュ垹闄ゅ啀杈撳叆閫犳垚娣蜂贡
                    for (int i = 0; i < 6; i++) {
                        strPassword += tvList[i].getText().toString().trim();
                    }
                    pass.inputFinish();    //鎺ュ彛涓瀹炵幇鐨勬柟娉曪紝瀹屾垚瀵嗙爜杈撳叆瀹屾垚鍚庣殑鍝嶅簲閫昏緫
                }
            }
        });
    }

    /* 鑾峰彇杈撳叆鐨勫瘑鐮� */
    public String getStrPassword() {
        return strPassword;
    }

    /* 鏆撮湶鍙栨秷鏀粯鐨勬寜閽紝鍙互鐏垫椿鏀瑰彉鍝嶅簲 */
    public ImageView getCancelImageView() {
        return imgCancel;
    }

    /* 鏆撮湶蹇樿瀵嗙爜鐨勬寜閽紝鍙互鐏垫椿鏀瑰彉鍝嶅簲 */
    public TextView getForgetTextView() {
        return tvForget;
    }

    //GrideView鐨勯�傞厤鍣�
    BaseAdapter adapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return valueList.size();
        }

        @Override
        public Object getItem(int position) {
            return valueList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.item_gride, null);
                viewHolder = new ViewHolder();
                viewHolder.btnKey = (TextView) convertView.findViewById(R.id.btn_keys);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.btnKey.setText(valueList.get(position).get("name"));
            if(position == 9){
                viewHolder.btnKey.setBackgroundResource(R.drawable.selector_key_del);
                viewHolder.btnKey.setEnabled(false);
            }
            if(position == 11){
                viewHolder.btnKey.setBackgroundResource(R.drawable.selector_key_del);
            }

            return convertView;
        }
    };

    /**
     * 瀛樻斁鎺т欢
     */
    public final class ViewHolder {
        public TextView btnKey;
    }
}
