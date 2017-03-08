
package com.jishang.bimeng.net;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Message;

import com.alipay.sdk.app.PayTask;
import com.jishang.bimeng.ui.UIHandler;
import com.jishang.bimeng.zhifubao.SignUtils;

public class TaskAlipay extends LoadData {
    private UIHandler uiHandler;

    private Context context;

    ProjectRewardPayChooseResult projectRewardPayChooseResult;

    public TaskAlipay(Message msg, Context context) {
        uiHandler = (UIHandler)msg.obj;
        this.context = context;
        Bundle bundle = msg.getData();
        projectRewardPayChooseResult = (ProjectRewardPayChooseResult)bundle
                .getSerializable("projectRewardPayChooseResult");
    }

    @Override
    public void run() {
        Map<String, String> params = buildOrderParamMap("2016032901251868");
        String orderParam = buildOrderParam(params);
        String sign = getSign(params, projectRewardPayChooseResult.data.zfb.rsa_private);
        final String orderInfo = orderParam + "&" + sign;
        PayTask alipay = new PayTask((Activity)context);
        Map<String, String> result = alipay.payV2(orderInfo, true);
        Message msg = new Message();
        msg.what = UIHandler.UI_FRESH_ALIPAY_PAY_SUCCESS;
        msg.obj = result;
        uiHandler.sendMessage(msg);
    }

    /**
     * 对支付参数信息进行签名
     * 
     * @param map 待签名授权信息
     * @return
     */
    public String getSign(Map<String, String> map, String rsaKey) {
        List<String> keys = new ArrayList<String>(map.keySet());
        // key排序
        Collections.sort(keys);

        StringBuilder authInfo = new StringBuilder();
        for (int i = 0; i < keys.size() - 1; i++) {
            String key = keys.get(i);
            String value = map.get(key);
            authInfo.append(buildKeyValue(key, value, false));
            authInfo.append("&");
        }

        String tailKey = keys.get(keys.size() - 1);
        String tailValue = map.get(tailKey);
        authInfo.append(buildKeyValue(tailKey, tailValue, false));

        String oriSign = SignUtils.sign(authInfo.toString(), rsaKey);
        String encodedSign = "";

        try {
            encodedSign = URLEncoder.encode(oriSign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "sign=" + encodedSign;
    }

    /**
     * 构造支付订单参数信息
     * 
     * @param map 支付订单参数
     * @return
     */
    public String buildOrderParam(Map<String, String> map) {
        List<String> keys = new ArrayList<String>(map.keySet());

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keys.size() - 1; i++) {
            String key = keys.get(i);
            String value = map.get(key);
            sb.append(buildKeyValue(key, value, true));
            sb.append("&");
        }

        String tailKey = keys.get(keys.size() - 1);
        String tailValue = map.get(tailKey);
        sb.append(buildKeyValue(tailKey, tailValue, true));

        return sb.toString();
    }

    /**
     * 拼接键值对
     * 
     * @param key
     * @param value
     * @param isEncode
     * @return
     */
    private String buildKeyValue(String key, String value, boolean isEncode) {
        StringBuilder sb = new StringBuilder();
        sb.append(key);
        sb.append("=");
        if (isEncode) {
            try {
                sb.append(URLEncoder.encode(value, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                sb.append(value);
            }
        } else {
            sb.append(value);
        }
        return sb.toString();
    }

    /**
     * 构造支付订单参数列表 subject 商品的标题/交易标题/订单标题/订单关键字等。 out_trade_no 商户网站唯一订单号
     * total_amount 订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000] product_code
     * 销售产品码，商家和支付宝签约的产品码
     * 
     * @param app_id
     * @return
     */
    public Map<String, String> buildOrderParamMap(String app_id) {
        Map<String, String> keyValues = new HashMap<String, String>();

        keyValues.put("app_id", app_id);// 支付宝分配给开发者的应用ID
        keyValues.put("method", "alipay.trade.app.pay");// 接口名称
        keyValues.put("charset", "utf-8");// 请求使用的编码格式，如utf-8,gbk,gb2312等
        keyValues.put("sign_type", "RSA");// 商户生成签名字符串所使用的签名算法类型，目前支持RSA
        keyValues.put("timestamp", getTimestamp());// 发送请求的时间，格式"yyyy-MM-dd HH:mm:ss"
        keyValues.put("version", "1.0");// 调用的接口版本，固定为：1.0
        keyValues.put("notify_url", "https://api.xx.com/receive_notify.htm");// 支付宝服务器主动通知商户服务器里指定的页面http/https路径。建议商户使用https

        // 业务请求参数的集合，最大长度不限，除公共参数外所有请求参数都必须放在这个参数中传递，具体参照各产品快速接入文档
        keyValues.put("biz_content", "{\"timeout_express\":\"30m\","
                + "\"product_code\":\" QUICK_MSECURITY_PAY \"," + "\"total_amount\":\""
                + projectRewardPayChooseResult.data.money
                + "\","
                + // 订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]
                "\"subject\":\"" + projectRewardPayChooseResult.data.gift_name + "\","
                + "\"body\":\"" + projectRewardPayChooseResult.data.body + "\","
                + "\"out_trade_no\":\"" + projectRewardPayChooseResult.data.order_id + "\"}");// 商户网站唯一订单号

        return keyValues;
    }

    /**
     * 时间戳
     * 
     * @return
     */
    private String getTimestamp() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        return key;
    }

}
