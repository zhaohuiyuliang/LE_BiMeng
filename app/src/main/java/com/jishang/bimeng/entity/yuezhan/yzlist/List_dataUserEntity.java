
package com.jishang.bimeng.entity.yuezhan.yzlist;

import java.io.Serializable;

public class List_dataUserEntity implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -7282623137433262363L;

    public String username;

    public List_dataUser_pvEntity province;

    public List_dataUser_ctEntity city;

    public List_dataUser_bsEntity business;

    public String uid;

    public String head_img;

    public String h_username;

    public String phone;

    public String describetion_info;

    public List_dataUserEntity() {
        super();
    }

    public List_dataUserEntity(String username, List_dataUser_pvEntity province,
            List_dataUser_ctEntity city, List_dataUser_bsEntity business, String uid,
            String head_img, String h_username, String phone, String describetion_info) {
        super();
        this.username = username;
        this.province = province;
        this.city = city;
        this.business = business;
        this.uid = uid;
        this.head_img = head_img;
        this.h_username = h_username;
        this.phone = phone;
        this.describetion_info = describetion_info;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List_dataUser_pvEntity getProvince() {
        return province;
    }

    public void setProvince(List_dataUser_pvEntity province) {
        this.province = province;
    }

    public List_dataUser_ctEntity getCity() {
        return city;
    }

    public void setCity(List_dataUser_ctEntity city) {
        this.city = city;
    }

    public List_dataUser_bsEntity getBusiness() {
        return business;
    }

    public void setBusiness(List_dataUser_bsEntity business) {
        this.business = business;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getHead_img() {
        return head_img;
    }

    public void setHead_img(String head_img) {
        this.head_img = head_img;
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

    public String getDescribetion_info() {
        return describetion_info;
    }

    public void setDescribetion_info(String describetion_info) {
        this.describetion_info = describetion_info;
    }

    @Override
    public String toString() {
        return "List_dataUserEntity [username=" + username + ", province=" + province + ", city="
                + city + ", business=" + business + ", uid=" + uid + ", head_img=" + head_img
                + ", h_username=" + h_username + ", phone=" + phone + ", describetion_info="
                + describetion_info + "]";
    }

}
