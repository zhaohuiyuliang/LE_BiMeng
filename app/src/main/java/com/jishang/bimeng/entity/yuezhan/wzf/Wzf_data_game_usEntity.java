package com.jishang.bimeng.entity.yuezhan.wzf;

import java.io.Serializable;

public class Wzf_data_game_usEntity implements Serializable{
	private String username;
	private String province;
	private String city;
	private String business;
	private String uid;
	private String head_img;
	private String h_username;
	private String phone;
	
	
	
	
	
	
	
	
	
	public Wzf_data_game_usEntity() {
		super();
	}
	public Wzf_data_game_usEntity(String username, String province,
			String city, String business, String uid, String head_img,
			String h_username, String phone) {
		super();
		this.username = username;
		this.province = province;
		this.city = city;
		this.business = business;
		this.uid = uid;
		this.head_img = head_img;
		this.h_username = h_username;
		this.phone = phone;
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
	@Override
	public String toString() {
		return "Wzf_data_game_usEntity [username=" + username + ", province="
				+ province + ", city=" + city + ", business=" + business
				+ ", uid=" + uid + ", head_img=" + head_img + ", h_username="
				+ h_username + ", phone=" + phone + "]";
	}

	
	
	
	
	
	
	
}
