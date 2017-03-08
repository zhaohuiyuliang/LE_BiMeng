
package com.jishang.bimeng.entity.hongbao;

import java.io.Serializable;

public class Hongbao_dataEntity implements Serializable {
    private String red_id;

    private String one_bag_money;

    private String one_bag_person;

    private String max_q_money;

    private String year_months;

    private String f_start_time;

    public String q_later_time;

    private String q_limit_time;

    private String jiange_number;

    private String hb_status;

    private String hb_invalid;

    private String hb_province;

    private String hb_city;

    private String hb_business;

    private String hb_level;

    private String redbag_rid;

    private String make_bao_time;

    public Hongbao_dataEntity() {
        super();
    }

    public Hongbao_dataEntity(String red_id, String one_bag_money, String one_bag_person,
            String max_q_money, String year_months, String f_start_time, String q_later_time,
            String q_limit_time, String jiange_number, String hb_status, String hb_invalid,
            String hb_province, String hb_city, String hb_business, String hb_level,
            String redbag_rid, String make_bao_time) {
        super();
        this.red_id = red_id;
        this.one_bag_money = one_bag_money;
        this.one_bag_person = one_bag_person;
        this.max_q_money = max_q_money;
        this.year_months = year_months;
        this.f_start_time = f_start_time;
        this.q_later_time = q_later_time;
        this.q_limit_time = q_limit_time;
        this.jiange_number = jiange_number;
        this.hb_status = hb_status;
        this.hb_invalid = hb_invalid;
        this.hb_province = hb_province;
        this.hb_city = hb_city;
        this.hb_business = hb_business;
        this.hb_level = hb_level;
        this.redbag_rid = redbag_rid;
        this.make_bao_time = make_bao_time;
    }

    public String getRed_id() {
        return red_id;
    }

    public void setRed_id(String red_id) {
        this.red_id = red_id;
    }

    public String getOne_bag_money() {
        return one_bag_money;
    }

    public void setOne_bag_money(String one_bag_money) {
        this.one_bag_money = one_bag_money;
    }

    public String getOne_bag_person() {
        return one_bag_person;
    }

    public void setOne_bag_person(String one_bag_person) {
        this.one_bag_person = one_bag_person;
    }

    public String getMax_q_money() {
        return max_q_money;
    }

    public void setMax_q_money(String max_q_money) {
        this.max_q_money = max_q_money;
    }

    public String getYear_months() {
        return year_months;
    }

    public void setYear_months(String year_months) {
        this.year_months = year_months;
    }

    public String getF_start_time() {
        return f_start_time;
    }

    public void setF_start_time(String f_start_time) {
        this.f_start_time = f_start_time;
    }

    public String getQ_later_time() {
        return q_later_time;
    }

    public void setQ_later_time(String q_later_time) {
        this.q_later_time = q_later_time;
    }

    public String getQ_limit_time() {
        return q_limit_time;
    }

    public void setQ_limit_time(String q_limit_time) {
        this.q_limit_time = q_limit_time;
    }

    public String getJiange_number() {
        return jiange_number;
    }

    public void setJiange_number(String jiange_number) {
        this.jiange_number = jiange_number;
    }

    public String getHb_status() {
        return hb_status;
    }

    public void setHb_status(String hb_status) {
        this.hb_status = hb_status;
    }

    public String getHb_invalid() {
        return hb_invalid;
    }

    public void setHb_invalid(String hb_invalid) {
        this.hb_invalid = hb_invalid;
    }

    public String getHb_province() {
        return hb_province;
    }

    public void setHb_province(String hb_province) {
        this.hb_province = hb_province;
    }

    public String getHb_city() {
        return hb_city;
    }

    public void setHb_city(String hb_city) {
        this.hb_city = hb_city;
    }

    public String getHb_business() {
        return hb_business;
    }

    public void setHb_business(String hb_business) {
        this.hb_business = hb_business;
    }

    public String getHb_level() {
        return hb_level;
    }

    public void setHb_level(String hb_level) {
        this.hb_level = hb_level;
    }

    public String getRedbag_rid() {
        return redbag_rid;
    }

    public void setRedbag_rid(String redbag_rid) {
        this.redbag_rid = redbag_rid;
    }

    public String getMake_bao_time() {
        return make_bao_time;
    }

    public void setMake_bao_time(String make_bao_time) {
        this.make_bao_time = make_bao_time;
    }

    @Override
    public String toString() {
        return "Hongbao_dataEntity [red_id=" + red_id + ", one_bag_money=" + one_bag_money
                + ", one_bag_person=" + one_bag_person + ", max_q_money=" + max_q_money
                + ", year_months=" + year_months + ", f_start_time=" + f_start_time
                + ", q_later_time=" + q_later_time + ", q_limit_time=" + q_limit_time
                + ", jiange_number=" + jiange_number + ", hb_status=" + hb_status + ", hb_invalid="
                + hb_invalid + ", hb_province=" + hb_province + ", hb_city=" + hb_city
                + ", hb_business=" + hb_business + ", hb_level=" + hb_level + ", redbag_rid="
                + redbag_rid + ", make_bao_time=" + make_bao_time + "]";
    }

}
