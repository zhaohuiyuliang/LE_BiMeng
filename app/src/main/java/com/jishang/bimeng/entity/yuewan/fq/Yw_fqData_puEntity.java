package com.jishang.bimeng.entity.yuewan.fq;

import java.io.Serializable;

public class Yw_fqData_puEntity implements Serializable{
	private String username;
	private String province;
	private String city;
	private String business;
	private String uid;
	private String head_img;
	
	
	
	
	public Yw_fqData_puEntity() {
		super();
	}
	public Yw_fqData_puEntity(String username, String province, String city,
			String business, String uid, String head_img) {
		super();
		this.username = username;
		this.province = province;
		this.city = city;
		this.business = business;
		this.uid = uid;
		this.head_img = head_img;
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
	@Override
	public String toString() {
		return "Yw_fqData_puEntity [username=" + username + ", province="
				+ province + ", city=" + city + ", business=" + business
				+ ", uid=" + uid + ", head_img=" + head_img + "]";
	}
	
	

}
