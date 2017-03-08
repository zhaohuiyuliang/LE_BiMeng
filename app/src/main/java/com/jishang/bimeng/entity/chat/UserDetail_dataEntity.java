package com.jishang.bimeng.entity.chat;

import java.io.Serializable;

public class UserDetail_dataEntity implements Serializable{
	private String id;
	private String username;
	private String phone;
	private String uid;
	private String head_img;
	private String cover_img;
	private String sex;
	private String h_username;
	private String describetion_info;
	private UserDetail_data_PrEntity province;
	private UserDetail_data_CtEntity city;
	private UserDetail_data_BsEntity business;
	
	
	
	
	
	public UserDetail_dataEntity() {
		super();
	}
	public UserDetail_dataEntity(String id, String username, String phone,
			String uid, String head_img, String cover_img, String sex,
			String h_username, String describetion_info,
			UserDetail_data_PrEntity province, UserDetail_data_CtEntity city,
			UserDetail_data_BsEntity business) {
		super();
		this.id = id;
		this.username = username;
		this.phone = phone;
		this.uid = uid;
		this.head_img = head_img;
		this.cover_img = cover_img;
		this.sex = sex;
		this.h_username = h_username;
		this.describetion_info = describetion_info;
		this.province = province;
		this.city = city;
		this.business = business;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
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
	public String getCover_img() {
		return cover_img;
	}
	public void setCover_img(String cover_img) {
		this.cover_img = cover_img;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getH_username() {
		return h_username;
	}
	public void setH_username(String h_username) {
		this.h_username = h_username;
	}
	public String getDescribetion_info() {
		return describetion_info;
	}
	public void setDescribetion_info(String describetion_info) {
		this.describetion_info = describetion_info;
	}
	public UserDetail_data_PrEntity getProvince() {
		return province;
	}
	public void setProvince(UserDetail_data_PrEntity province) {
		this.province = province;
	}
	public UserDetail_data_CtEntity getCity() {
		return city;
	}
	public void setCity(UserDetail_data_CtEntity city) {
		this.city = city;
	}
	public UserDetail_data_BsEntity getBusiness() {
		return business;
	}
	public void setBusiness(UserDetail_data_BsEntity business) {
		this.business = business;
	}
	@Override
	public String toString() {
		return "UserDetail_dataEntity [id=" + id + ", username=" + username
				+ ", phone=" + phone + ", uid=" + uid + ", head_img="
				+ head_img + ", cover_img=" + cover_img + ", sex=" + sex
				+ ", h_username=" + h_username + ", describetion_info="
				+ describetion_info + ", province=" + province + ", city="
				+ city + ", business=" + business + "]";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	
}
