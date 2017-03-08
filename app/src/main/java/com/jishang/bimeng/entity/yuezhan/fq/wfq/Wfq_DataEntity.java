
package com.jishang.bimeng.entity.yuezhan.fq.wfq;

import java.io.Serializable;

/**
 * "我的开黑"对象类
 * 
 * @author wangliang Jul 15, 2016
 */
public class Wfq_DataEntity implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 907684259639828353L;

    private String yz_id;

    private String yz_title;

    private String yz_img;

    private String yz_server;

    private String yz_pattern;

    private String yz_grade;

    private String pay_get;

    private String need_persons;

    private String need_peple_item;

    private String pay_peple_money;

    private String pay_app_money;

    private String yz_created_at;

    private String y_join_status;

    private String y_start_status;

    private String y_status;

    private String y_pay_status;

    private String yzu_id;

    private String start_time;

    private String start_time_four;

    private String wait_time;

    private String game_continue_time;

    private String yz_remark;

    private Wfq_data_UserEntity user;

    public Wfq_DataEntity() {
        super();
    }

    public Wfq_DataEntity(String yz_id, String yz_title, String yz_img, String yz_server,
            String yz_pattern, String yz_grade, String pay_get, String need_persons,
            String need_peple_item, String pay_peple_money, String pay_app_money,
            String yz_created_at, String y_join_status, String y_start_status, String y_status,
            String y_pay_status, String yzu_id, String start_time, String start_time_four,
            String wait_time, String game_continue_time, String yz_remark, Wfq_data_UserEntity user) {
        super();
        this.yz_id = yz_id;
        this.yz_title = yz_title;
        this.yz_img = yz_img;
        this.yz_server = yz_server;
        this.yz_pattern = yz_pattern;
        this.yz_grade = yz_grade;
        this.pay_get = pay_get;
        this.need_persons = need_persons;
        this.need_peple_item = need_peple_item;
        this.pay_peple_money = pay_peple_money;
        this.pay_app_money = pay_app_money;
        this.yz_created_at = yz_created_at;
        this.y_join_status = y_join_status;
        this.y_start_status = y_start_status;
        this.y_status = y_status;
        this.y_pay_status = y_pay_status;
        this.yzu_id = yzu_id;
        this.start_time = start_time;
        this.start_time_four = start_time_four;
        this.wait_time = wait_time;
        this.game_continue_time = game_continue_time;
        this.yz_remark = yz_remark;
        this.user = user;
    }

    public String getYz_id() {
        return yz_id;
    }

    public void setYz_id(String yz_id) {
        this.yz_id = yz_id;
    }

    public String getYz_title() {
        return yz_title;
    }

    public void setYz_title(String yz_title) {
        this.yz_title = yz_title;
    }

    public String getYz_img() {
        return yz_img;
    }

    public void setYz_img(String yz_img) {
        this.yz_img = yz_img;
    }

    public String getYz_server() {
        return yz_server;
    }

    public void setYz_server(String yz_server) {
        this.yz_server = yz_server;
    }

    public String getYz_pattern() {
        return yz_pattern;
    }

    public void setYz_pattern(String yz_pattern) {
        this.yz_pattern = yz_pattern;
    }

    public String getYz_grade() {
        return yz_grade;
    }

    public void setYz_grade(String yz_grade) {
        this.yz_grade = yz_grade;
    }

    public String getPay_get() {
        return pay_get;
    }

    public void setPay_get(String pay_get) {
        this.pay_get = pay_get;
    }

    public String getNeed_persons() {
        return need_persons;
    }

    public void setNeed_persons(String need_persons) {
        this.need_persons = need_persons;
    }

    public String getNeed_peple_item() {
        return need_peple_item;
    }

    public void setNeed_peple_item(String need_peple_item) {
        this.need_peple_item = need_peple_item;
    }

    public String getPay_peple_money() {
        return pay_peple_money;
    }

    public void setPay_peple_money(String pay_peple_money) {
        this.pay_peple_money = pay_peple_money;
    }

    public String getPay_app_money() {
        return pay_app_money;
    }

    public void setPay_app_money(String pay_app_money) {
        this.pay_app_money = pay_app_money;
    }

    public String getYz_created_at() {
        return yz_created_at;
    }

    public void setYz_created_at(String yz_created_at) {
        this.yz_created_at = yz_created_at;
    }

    public String getY_join_status() {
        return y_join_status;
    }

    public void setY_join_status(String y_join_status) {
        this.y_join_status = y_join_status;
    }

    public String getY_start_status() {
        return y_start_status;
    }

    public void setY_start_status(String y_start_status) {
        this.y_start_status = y_start_status;
    }

    public String getY_status() {
        return y_status;
    }

    public void setY_status(String y_status) {
        this.y_status = y_status;
    }

    public String getY_pay_status() {
        return y_pay_status;
    }

    public void setY_pay_status(String y_pay_status) {
        this.y_pay_status = y_pay_status;
    }

    public String getYzu_id() {
        return yzu_id;
    }

    public void setYzu_id(String yzu_id) {
        this.yzu_id = yzu_id;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getStart_time_four() {
        return start_time_four;
    }

    public void setStart_time_four(String start_time_four) {
        this.start_time_four = start_time_four;
    }

    public String getWait_time() {
        return wait_time;
    }

    public void setWait_time(String wait_time) {
        this.wait_time = wait_time;
    }

    public String getGame_continue_time() {
        return game_continue_time;
    }

    public void setGame_continue_time(String game_continue_time) {
        this.game_continue_time = game_continue_time;
    }

    public String getYz_remark() {
        return yz_remark;
    }

    public void setYz_remark(String yz_remark) {
        this.yz_remark = yz_remark;
    }

    public Wfq_data_UserEntity getUser() {
        return user;
    }

    public void setUser(Wfq_data_UserEntity user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Wfq_DataEntity [yz_id=" + yz_id + ", yz_title=" + yz_title + ", yz_img=" + yz_img
                + ", yz_server=" + yz_server + ", yz_pattern=" + yz_pattern + ", yz_grade="
                + yz_grade + ", pay_get=" + pay_get + ", need_persons=" + need_persons
                + ", need_peple_item=" + need_peple_item + ", pay_peple_money=" + pay_peple_money
                + ", pay_app_money=" + pay_app_money + ", yz_created_at=" + yz_created_at
                + ", y_join_status=" + y_join_status + ", y_start_status=" + y_start_status
                + ", y_status=" + y_status + ", y_pay_status=" + y_pay_status + ", yzu_id="
                + yzu_id + ", start_time=" + start_time + ", start_time_four=" + start_time_four
                + ", wait_time=" + wait_time + ", game_continue_time=" + game_continue_time
                + ", yz_remark=" + yz_remark + ", user=" + user + "]";
    }

}
