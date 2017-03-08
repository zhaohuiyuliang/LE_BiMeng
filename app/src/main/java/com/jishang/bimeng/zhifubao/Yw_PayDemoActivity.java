
package com.jishang.bimeng.zhifubao;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jishang.bimeng.R;
import com.jishang.bimeng.activity.base.BaseActivity;
import com.jishang.bimeng.entity.yuezhan.zf.ZftwoEntity;
import com.jishang.bimeng.entity.yuezhan.zf.zfb.ZfbEntity;
import com.jishang.bimeng.utils.SharUtil;
import com.jishang.bimeng.utils.ToastUtil;
import com.jishang.bimeng.utils.Internet.MyHttpRequest;
import com.jishang.bimeng.utils.url.UrlUtils;

public class Yw_PayDemoActivity extends BaseActivity implements OnClickListener {

    private String paytner;

    private String seller;

    private String rsa_private;

    private String rsa_public;

    private String price;

    private String name;

    private String body;

    private ZftwoEntity.Data entity;

    private Intent intent;

    private List<BasicNameValuePair> params;

    private TextView mTv_name, mTv_price, mTv_body, mTv_barname;

    private static final int SDK_PAY_FLAG = 1;

    private Context mContext;

    private int zf_status = 1;

    private String token;

    private Gson mGson;

    @Override
    public int initResource() {
        return R.layout.activity_pay_external;
    }

    @Override
    public void initView() {
        mContext = this;
        mTv_name = (TextView)findViewById(R.id.pay_external_name);
        mTv_body = (TextView)findViewById(R.id.pay_external_body);
        mTv_price = (TextView)findViewById(R.id.pay_external_price);
        mTv_barname = (TextView)findViewById(R.id.pay_extrnal_barname);
        params = new ArrayList<BasicNameValuePair>();
        intent = getIntent();
        entity = (ZftwoEntity.Data)intent.getSerializableExtra("entity");
        mGson = new Gson();

        token = new SharUtil(mContext).getAccess_token();
        paytner = entity.getZfb().getPartner();
        seller = entity.getZfb().getSeller();
        rsa_private = entity.getZfb().getRsa_private();
        rsa_public = entity.getZfb().getRsa_public();
        price = entity.getMoney();
        name = entity.getGift_name();
        body = entity.getBody();
        mTv_barname.setText("鏀粯瀹濇敮浠�");

    }

    @Override
    public void initData() {
        mTv_name.setText(entity.getGift_name());
        mTv_body.setText(entity.getBody());
        mTv_price.setText(entity.getMoney());
    }

    /**
     * call alipay sdk pay. 璋冪敤SDK鏀粯
     */
    public void pay(View v) {
        String orderInfo = getOrderInfo(name, body, price);

        /**
         * 鐗瑰埆娉ㄦ剰锛岃繖閲岀殑绛惧悕閫昏緫闇�瑕佹斁鍦ㄦ湇鍔＄锛屽垏鍕垮皢绉侀挜娉勯湶鍦ㄤ唬鐮佷腑锛�
         */
        String sign = sign(orderInfo);
        try {
            /**
             * 浠呴渶瀵箂ign 鍋歎RL缂栫爜
             */
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        /**
         * 瀹屾暣鐨勭鍚堟敮浠樺疂鍙傛暟瑙勮寖鐨勮鍗曚俊鎭�
         */
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 鏋勯�燩ayTask 瀵硅薄
                PayTask alipay = new PayTask((Activity)mContext);
                // 璋冪敤鏀粯鎺ュ彛锛岃幏鍙栨敮浠樼粨鏋�
                String result = alipay.pay(payInfo, true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 蹇呴』寮傛璋冪敤
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * sign the order info. 瀵硅鍗曚俊鎭繘琛岀鍚�
     * 
     * @param content 寰呯鍚嶈鍗曚俊鎭�
     */
    private String sign(String content) {
        return SignUtils.sign(content, rsa_private);
    }

    /**
     * get the sign type we use. 鑾峰彇绛惧悕鏂瑰紡
     */
    private String getSignType() {
        return "sign_type=\"RSA\"";
    }

    /**
     * create the order info. 鍒涘缓璁㈠崟淇℃伅
     */
    private String getOrderInfo(String subject, String body, String price) {

        // 绛剧害鍚堜綔鑰呰韩浠絀D
        String orderInfo = "partner=" + "\"" + paytner + "\"";

        // 绛剧害鍗栧鏀粯瀹濊处鍙�
        orderInfo += "&seller_id=" + "\"" + seller + "\"";

        // 鍟嗘埛缃戠珯鍞竴璁㈠崟鍙�
        orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

        // 鍟嗗搧鍚嶇О
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 鍟嗗搧璇︽儏
        orderInfo += "&body=" + "\"" + body + "\"";

        // 鍟嗗搧閲戦
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 鏈嶅姟鍣ㄥ紓姝ラ�氱煡椤甸潰璺緞
        orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm" + "\"";

        // 鏈嶅姟鎺ュ彛鍚嶇О锛� 鍥哄畾鍊�
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 鏀粯绫诲瀷锛� 鍥哄畾鍊�
        orderInfo += "&payment_type=\"1\"";

        // 鍙傛暟缂栫爜锛� 鍥哄畾鍊�
        orderInfo += "&_input_charset=\"utf-8\"";

        // 璁剧疆鏈粯娆句氦鏄撶殑瓒呮椂鏃堕棿
        // 榛樿30鍒嗛挓锛屼竴鏃﹁秴鏃讹紝璇ョ瑪浜ゆ槗灏变細鑷姩琚叧闂��
        // 鍙栧�艰寖鍥达細1m锝�15d銆�
        // m-鍒嗛挓锛宧-灏忔椂锛宒-澶╋紝1c-褰撳ぉ锛堟棤璁轰氦鏄撲綍鏃跺垱寤猴紝閮藉湪0鐐瑰叧闂級銆�
        // 璇ュ弬鏁版暟鍊间笉鎺ュ彈灏忔暟鐐癸紝濡�1.5h锛屽彲杞崲涓�90m銆�
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token涓虹粡杩囧揩鐧绘巿鏉冭幏鍙栧埌鐨刟lipay_open_id,甯︿笂姝ゅ弬鏁扮敤鎴峰皢浣跨敤鎺堟潈鐨勮处鎴疯繘琛屾敮浠�
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 鏀粯瀹濆鐞嗗畬璇锋眰鍚庯紝褰撳墠椤甸潰璺宠浆鍒板晢鎴锋寚瀹氶〉闈㈢殑璺緞锛屽彲绌�
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 璋冪敤閾惰鍗℃敮浠橈紝闇�閰嶇疆姝ゅ弬鏁帮紝鍙備笌绛惧悕锛� 鍥哄畾鍊�
        // 锛堥渶瑕佺绾︺�婃棤绾块摱琛屽崱蹇嵎鏀粯銆嬫墠鑳戒娇鐢級
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

    /**
     * get the out_trade_no for an order.
     * 鐢熸垚鍟嗘埛璁㈠崟鍙凤紝璇ュ�煎湪鍟嗘埛绔簲淇濇寔鍞竴锛堝彲鑷畾涔夋牸寮忚鑼冿級
     */
    private String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }

    @Override
    public void addListener() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case 1:

                break;

            default:
                break;
        }
    }

    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String)msg.obj);
                    /**
                     * 鍚屾杩斿洖鐨勭粨鏋滃繀椤绘斁缃埌鏈嶅姟绔繘琛岄獙璇侊紙楠岃瘉鐨勮鍒欒鐪媓ttps://doc.open.
                     * alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 寤鸿鍟嗘埛渚濊禆寮傛閫氱煡
                     */
                    String resultInfo = payResult.getResult();// 鍚屾杩斿洖闇�瑕侀獙璇佺殑淇℃伅

                    String resultStatus = payResult.getResultStatus();
                    // 鍒ゆ柇resultStatus
                    // 涓衡��9000鈥濆垯浠ｈ〃鏀粯鎴愬姛锛屽叿浣撶姸鎬佺爜浠ｈ〃鍚箟鍙弬鑰冩帴鍙ｆ枃妗�
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // Toast.makeText(mContext, "鏀粯鎴愬姛",
                        // Toast.LENGTH_SHORT).show();
                        zf_status = 1;
                    } else {
                        // 鍒ゆ柇resultStatus 涓洪潪"9000"鍒欎唬琛ㄥ彲鑳芥敮浠樺け璐�
                        // "8000"浠ｈ〃鏀粯缁撴灉鍥犱负鏀粯娓犻亾鍘熷洜鎴栬�呯郴缁熷師鍥犺繕鍦ㄧ瓑寰呮敮浠樼粨鏋滅‘璁わ紝鏈�缁堜氦鏄撴槸鍚︽垚鍔熶互鏈嶅姟绔紓姝ラ�氱煡涓哄噯锛堝皬姒傜巼鐘舵�侊級
                        if (TextUtils.equals(resultStatus, "8000")) {
                            // Toast.makeText(mContext, "鏀粯缁撴灉纭涓�",
                            // Toast.LENGTH_SHORT)
                            // .show();

                        } else {
                            // 鍏朵粬鍊煎氨鍙互鍒ゆ柇涓烘敮浠樺け璐ワ紝鍖呮嫭鐢ㄦ埛涓诲姩鍙栨秷鏀粯锛屾垨鑰呯郴缁熻繑鍥炵殑閿欒
                            // Toast.makeText(mContext, "鏀粯澶辫触",
                            // Toast.LENGTH_SHORT)
                            // .show();
                            zf_status = 0;

                        }
                    }
                    putMsg(zf_status);
                    break;
                }
                case 2:
                    String erros = (String)msg.obj;
                    ToastUtil.Toast(mContext, erros);
                    break;
                case 3:
                    ToastUtil.Toast(mContext, "鏀粯鎴愬姛");
                    break;
            }
        };
    };

    public void putMsg(int zf_status) {
        String ypn_id = entity.getYpn_id();
        String uid = entity.getUid();
        String order_notice_sn = entity.getOrder_id();
        String change = entity.getChange();
        String income = entity.getIncome();
        String third = entity.getMoney();
        params.add(new BasicNameValuePair("ypn_id", ypn_id));
        params.add(new BasicNameValuePair("uid", uid));
        params.add(new BasicNameValuePair("yw_order_notice", order_notice_sn));
        params.add(new BasicNameValuePair("change", change));
        params.add(new BasicNameValuePair("income", income));
        params.add(new BasicNameValuePair("third", third));
        params.add(new BasicNameValuePair("zf_status", zf_status + ""));
        new Thread() {
            public void run() {
                String url = UrlUtils.BASEURL + "v1/yw/thrid_pay_success_fail.json";
                String result = MyHttpRequest.getHttpPostBasic(url, params, token);
                if (result != null) {
                    // Log.e("result", result.toString());
                    try {
                        ZfbEntity entity = mGson.fromJson(result, ZfbEntity.class);
                        if (entity.getStatus() == 0) {
                            Message msg = new Message();
                            msg.obj = entity.getErrors();
                            msg.what = 2;
                            mHandler.sendMessage(msg);
                        } else if (entity.getStatus() == 1) {
                            mHandler.sendEmptyMessage(3);

                        }
                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                    }

                }
            };
        }.start();
    }

    public void back(View v) {
        finish();
    }

}
