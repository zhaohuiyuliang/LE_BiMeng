
package com.jishang.bimeng.entity.yuezhan.confirm;

import java.io.Serializable;

public class C_DataEntity implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 5928769095192819665L;

    private String need_persons;

    private String order_notice_sn;

    private String create_time;

    private String p_start_time;

    private String user_uid;

    private String f_uid;

    private String total_money;

    private String app_money;

    private String deal_id;

    private String deal_name;

    private String pay_or_get;

    private String pay_time;

    private String is_paid;

    private String p_id;

    private String one_peple_money;

    public C_DataEntity() {
        super();
    }

    public C_DataEntity(String need_persons, String order_notice_sn, String create_time,
            String p_start_time, String user_uid, String f_uid, String total_money,
            String app_money, String deal_id, String deal_name, String pay_or_get, String pay_time,
            String is_paid, String p_id, String one_peple_money) {
        super();
        this.need_persons = need_persons;
        this.order_notice_sn = order_notice_sn;
        this.create_time = create_time;
        this.p_start_time = p_start_time;
        this.user_uid = user_uid;
        this.f_uid = f_uid;
        this.total_money = total_money;
        this.app_money = app_money;
        this.deal_id = deal_id;
        this.deal_name = deal_name;
        this.pay_or_get = pay_or_get;
        this.pay_time = pay_time;
        this.is_paid = is_paid;
        this.p_id = p_id;
        this.one_peple_money = one_peple_money;
    }

    public String getNeed_persons() {
        return need_persons;
    }

    public void setNeed_persons(String need_persons) {
        this.need_persons = need_persons;
    }

    public String getOrder_notice_sn() {
        return order_notice_sn;
    }

    public void setOrder_notice_sn(String order_notice_sn) {
        this.order_notice_sn = order_notice_sn;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getP_start_time() {
        return p_start_time;
    }

    public void setP_start_time(String p_start_time) {
        this.p_start_time = p_start_time;
    }

    public String getUser_uid() {
        return user_uid;
    }

    public void setUser_uid(String user_uid) {
        this.user_uid = user_uid;
    }

    public String getF_uid() {
        return f_uid;
    }

    public void setF_uid(String f_uid) {
        this.f_uid = f_uid;
    }

    public String getTotal_money() {
        return total_money;
    }

    public void setTotal_money(String total_money) {
        this.total_money = total_money;
    }

    public String getApp_money() {
        return app_money;
    }

    public void setApp_money(String app_money) {
        this.app_money = app_money;
    }

    public String getDeal_id() {
        return deal_id;
    }

    public void setDeal_id(String deal_id) {
        this.deal_id = deal_id;
    }

    public String getDeal_name() {
        return deal_name;
    }

    public void setDeal_name(String deal_name) {
        this.deal_name = deal_name;
    }

    public String getPay_or_get() {
        return pay_or_get;
    }

    public void setPay_or_get(String pay_or_get) {
        this.pay_or_get = pay_or_get;
    }

    public String getPay_time() {
        return pay_time;
    }

    public void setPay_time(String pay_time) {
        this.pay_time = pay_time;
    }

    public String getIs_paid() {
        return is_paid;
    }

    public void setIs_paid(String is_paid) {
        this.is_paid = is_paid;
    }

    public String getP_id() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }

    public String getOne_peple_money() {
        return one_peple_money;
    }

    public void setOne_peple_money(String one_peple_money) {
        this.one_peple_money = one_peple_money;
    }

    @Override
    public String toString() {
        return "C_DataEntity [need_persons=" + need_persons + ", order_notice_sn="
                + order_notice_sn + ", create_time=" + create_time + ", p_start_time="
                + p_start_time + ", user_uid=" + user_uid + ", f_uid=" + f_uid + ", total_money="
                + total_money + ", app_money=" + app_money + ", deal_id=" + deal_id
                + ", deal_name=" + deal_name + ", pay_or_get=" + pay_or_get + ", pay_time="
                + pay_time + ", is_paid=" + is_paid + ", p_id=" + p_id + ", one_peple_money="
                + one_peple_money + "]";
    }

}
