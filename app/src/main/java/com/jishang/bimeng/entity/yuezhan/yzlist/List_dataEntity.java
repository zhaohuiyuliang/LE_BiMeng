
package com.jishang.bimeng.entity.yuezhan.yzlist;

import java.io.Serializable;
import java.util.List;

public class List_dataEntity implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -7418582310390756078L;

    private String yz_id;

    /**
     * 游戏名称
     */
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

    private String created_at;

    private String y_join_status;

    private String y_status;

    private String y_pay_status;

    private String yzu_id;

    private String start_time;

    private String wait_time;

    public String game_continue_time;

    public String yz_remark;

    public String username = "";

    public String h_username;

    public String phone;

    public List_data_prEntity province;

    public List_data_ctEntity city;

    public List_data_bsEntity business;

    public String head_img;

    public List_dataUserEntity user;

    public List<List_data_comentEntity> comment_to_people;

    public List_dataEntity() {
        super();
    }

    public List_dataEntity(String yz_id, String yz_title, String yz_img, String yz_server,
            String yz_pattern, String yz_grade, String pay_get, String need_persons,
            String need_peple_item, String pay_peple_money, String pay_app_money,
            String created_at, String y_join_status, String y_status, String y_pay_status,
            String yzu_id, String start_time, String wait_time, String game_continue_time,
            String yz_remark, String username, String h_username, String phone,
            List_data_prEntity province, List_data_ctEntity city, List_data_bsEntity business,
            String head_img, List_dataUserEntity user,
            List<List_data_comentEntity> comment_to_people) {
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
        this.created_at = created_at;
        this.y_join_status = y_join_status;
        this.y_status = y_status;
        this.y_pay_status = y_pay_status;
        this.yzu_id = yzu_id;
        this.start_time = start_time;
        this.wait_time = wait_time;
        this.game_continue_time = game_continue_time;
        this.yz_remark = yz_remark;
        this.username = username;
        this.h_username = h_username;
        this.phone = phone;
        this.province = province;
        this.city = city;
        this.business = business;
        this.head_img = head_img;
        this.user = user;
        this.comment_to_people = comment_to_people;
    }

    public String getYz_id() {
        return yz_id;
    }

    public void setYz_id(String yz_id) {
        this.yz_id = yz_id;
    }

    /**
     * 游戏名称
     * 
     * @return
     */
    public String getYz_title() {
        return yz_title;
    }

    /**
     * 游戏名称
     */
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

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getY_join_status() {
        return y_join_status;
    }

    public void setY_join_status(String y_join_status) {
        this.y_join_status = y_join_status;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getH_username() {
        return h_username;
    }

    public void setH_username(String h_username) {
        this.h_username = h_username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List_data_prEntity getProvince() {
        return province;
    }

    public void setProvince(List_data_prEntity province) {
        this.province = province;
    }

    public List_data_ctEntity getCity() {
        return city;
    }

    public void setCity(List_data_ctEntity city) {
        this.city = city;
    }

    public List_data_bsEntity getBusiness() {
        return business;
    }

    public void setBusiness(List_data_bsEntity business) {
        this.business = business;
    }

    public String getHead_img() {
        return head_img;
    }

    public void setHead_img(String head_img) {
        this.head_img = head_img;
    }

    public List_dataUserEntity getUser() {
        return user;
    }

    public void setUser(List_dataUserEntity user) {
        this.user = user;
    }

    public List<List_data_comentEntity> getComment_to_people() {
        return comment_to_people;
    }

    public void setComment_to_people(List<List_data_comentEntity> comment_to_people) {
        this.comment_to_people = comment_to_people;
    }

    @Override
    public String toString() {
        return "List_dataEntity [yz_id=" + yz_id + ", yz_title=" + yz_title + ", yz_img=" + yz_img
                + ", yz_server=" + yz_server + ", yz_pattern=" + yz_pattern + ", yz_grade="
                + yz_grade + ", pay_get=" + pay_get + ", need_persons=" + need_persons
                + ", need_peple_item=" + need_peple_item + ", pay_peple_money=" + pay_peple_money
                + ", pay_app_money=" + pay_app_money + ", created_at=" + created_at
                + ", y_join_status=" + y_join_status + ", y_status=" + y_status + ", y_pay_status="
                + y_pay_status + ", yzu_id=" + yzu_id + ", start_time=" + start_time
                + ", wait_time=" + wait_time + ", game_continue_time=" + game_continue_time
                + ", yz_remark=" + yz_remark + ", username=" + username + ", h_username="
                + h_username + ", phone=" + phone + ", province=" + province + ", city=" + city
                + ", business=" + business + ", head_img=" + head_img + ", user=" + user
                + ", comment_to_people=" + comment_to_people + "]";
    }

}
