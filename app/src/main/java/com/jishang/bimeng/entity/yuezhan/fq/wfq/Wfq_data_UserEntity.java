package com.jishang.bimeng.entity.yuezhan.fq.wfq;

import java.io.Serializable;

public class Wfq_data_UserEntity implements Serializable{
	private String username;
	private String province;
	private String city;
	private String business;
	private String uid;
	private String head_img;
	private String phone;
	private String h_username;
	
	
	
	
	
	
	public Wfq_data_UserEntity() {
		super();
	}
	public Wfq_data_UserEntity(String username, String province, String city,
			String business, String uid, String head_img, String phone,
			String h_username) {
		super();
		this.username = username;
		this.province = province;
		this.city = city;
		this.business = business;
		this.uid = uid;
		this.head_img = head_img;
		this.phone = phone;
		this.h_username = h_username;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getBusiness() {
		return business;
	}
	public void setBusiness(String business) {
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getH_username() {
		return h_username;
	}
	public void setH_username(String h_username) {
		this.h_username = h_username;
	}
	@Override
	public String toString() {
		return "Wfq_data_UserEntity [username=" + username + ", province="
				+ province + ", city=" + city + ", business=" + business
				+ ", uid=" + uid + ", head_img=" + head_img + ", phone="
				+ phone + ", h_username=" + h_username + "]";
	}
	
	
	
	

}
