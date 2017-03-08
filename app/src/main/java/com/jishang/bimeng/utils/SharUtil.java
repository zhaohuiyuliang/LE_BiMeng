
package com.jishang.bimeng.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * @author kangming 共享参数工具类
 */
public class SharUtil {

    private SharedPreferences sp;

    private Editor editor;

    public SharUtil(Context context) {
        sp = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    /**
     * 我的钱包中的余额/零钱
     * 
     * @return
     */
    public String getLingqian() {
        return sp.getString("lingqian", "");
    }

    public void setLingqian(String lingqian) {
        editor.putString("lingqian", lingqian);
        editor.commit();
    }

    /**
     * 参与"我发起的开黑"的人数
     * 
     * @param list
     */
    public String getList() {
        return sp.getString("list", "");
    }

    /**
     * 参与"我发起的开黑"的人数
     * 
     * @param list
     */
    public void setList(String list) {
        editor.putString("list", list);
        editor.commit();
    }

    public int getPosition() {
        return sp.getInt("position", 0);
    }

    public void setPosition(int position) {
        editor.putInt("position", position);
        editor.commit();
    }

    public int getSex() {
        return sp.getInt("sex", 0);
    }

    public void setSex(int sex) {
        editor.putInt("sex", sex);
        editor.commit();
    }

    public String getFlist() {
        return sp.getString("flist", null);
    }

    public void setFlist(String flist) {
        editor.putString("flist", flist);
        editor.commit();
    }

    public String getProvince() {
        return sp.getString("province", null);
    }

    public void setProvince(String province) {
        editor.putString("province", province);
        editor.commit();
    }

    public String getCity() {
        return sp.getString("city", null);
    }

    public void setCity(String city) {
        editor.putString("city", city);
        editor.commit();
    }

    public String getBusiness() {
        return sp.getString("business", null);
    }

    public void setBusiness(String business) {
        editor.putString("business", business);
        editor.commit();
    }

    /* 储存订单信息 */

    // 支付来源，0为约战，1为商城,2为充值
    public String getPaymethod() {
        return sp.getString("paymethod", "0");
    }

    public void setPaymethod(String paymethod) {
        editor.putString("paymethod", paymethod);
        editor.commit();
    }

    public String getP_id() {
        return sp.getString("p_id", null);
    }

    public void setP_id(String p_id) {
        editor.putString("p_id", p_id);
        editor.commit();
    }

    public String getOrder_notice_sn() {
        return sp.getString("order_notice_sn", null);
    }

    public void setOrder_notice_sn(String order_notice_sn) {
        editor.putString("order_notice_sn", order_notice_sn);
        editor.commit();
    }

    public String getChange() {
        return sp.getString("change", null);
    }

    public void setChange(String change) {
        editor.putString("change", change);
        editor.commit();
    }

    public String getIncome() {
        return sp.getString("income", null);
    }

    public void setIncome(String income) {
        editor.putString("income", income);
        editor.commit();
    }

    public String getThird() {
        return sp.getString("third", null);
    }

    public void setThird(String third) {
        editor.putString("third", third);
        editor.commit();
    }

    /* 储存订单信息 */

    public String getDescribetion_info() {
        return sp.getString("describetion_info", null);
    }

    public void setDescribetion_info(String describetion_info) {
        editor.putString("describetion_info", describetion_info);
        editor.commit();
    }

    public String getHead_img() {
        return sp.getString("head_img", null);
    }

    public void setHead_img(String head_img) {
        editor.putString("head_img", head_img);
        editor.commit();
    }

    /* 储存环信用户名 */
    public String getH_username() {
        return sp.getString("h_username", null);
    }

    public void setH_username(String h_username) {
        editor.putString("h_username", h_username);
        editor.commit();
    }

    /* 储存环信用户名 */

    /* 储存环信密码 */
    public String getH_password() {
        return sp.getString("h_password", null);
    }

    /* */
    public void setH_password(String h_password) {
        editor.putString("h_password", h_password);
        editor.commit();
    }

    /* 储存环信密码 */

    public String getAccess_token() {
        return sp.getString("access_token", null);
    }

    public void setAccess_token(String access_token) {
        editor.putString("access_token", access_token);
        editor.commit();
    }

    public String getPhone() {
        return sp.getString("phone", null);
    }

    public void setPhone(String phone) {
        editor.putString("phone", phone);
        editor.commit();
    }

    /* 储存用户名 */

    public String getUserPhone() {
        return sp.getString("userPhone", null);
    }

    public void setUserPhone(String userPhone) {
        editor.putString("userPhone", userPhone);
        editor.commit();
    }

    public String getUserName() {
        return sp.getString("userName", null);
    }

    public void setUserName(String userName) {
        editor.putString("userName", userName);
        editor.commit();
    }

    /* 储存密码 */
    public String getUserPwd() {
        return sp.getString("userPwd", null);
    }

    public void setUserPwd(String userPwd) {
        editor.putString("userPwd", userPwd);
        editor.commit();
    }

    /* 储存uid */
    public String getUid() {
        return sp.getString("uid", null);
    }

    public void setUid(String uid) {
        editor.putString("uid", uid);
        editor.commit();
    }

    /* 判断是否第一次登陆 */
    public Boolean getIsFirst() {
        return sp.getBoolean("isFirst", true);
    }

    public void setIsFirst(Boolean isFirst) {
        editor.putBoolean("isFirst", isFirst);
        editor.commit();
    }

}
