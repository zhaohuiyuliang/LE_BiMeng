package com.jishang.bimeng.entity.regist;

import java.io.Serializable;

public class Regist_dataEntity implements Serializable{
	private String phone;
	private String username;
	private String sex;
	private String province;
	private String city;
	private String business;
	private String last_login_time;
	private String last_login_ip;
	private String head_img;
	private String cover_img;
	private String access_token;
	private String h_password;
	private String uid;
	private String h_username;
	private String status;
	private String ud_checked;
	private String created_at;
	private String updated_at;
	private String id;
	
	
	
	
	public Regist_dataEntity() {
		super();
	}
	public Regist_dataEntity(String phone, String username, String sex,
			String province, String city, String business,
			String last_login_time, String last_login_ip, String head_img,
			String cover_img, String access_token, String h_password,
			String uid, String h_username, String status, String ud_checked,
			String created_at, String updated_at, String id) {
		super();
		this.phone = phone;
		this.username = username;
		this.sex = sex;
		this.province = province;
		this.city = city;
		this.business = business;
		this.last_login_time = last_login_time;
		this.last_login_ip = last_login_ip;
		this.head_img = head_img;
		this.cover_img = cover_img;
		this.access_token = access_token;
		this.h_password = h_password;
		this.uid = uid;
		this.h_username = h_username;
		this.status = status;
		this.ud_checked = ud_checked;
		this.created_at = created_at;
		this.updated_at = updated_at;
		this.id = id;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
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
	public String getLast_login_time() {
		return last_login_time;
	}
	public void setLast_login_time(String last_login_time) {
		this.last_login_time = last_login_time;
	}
	public String getLast_login_ip() {
		return last_login_ip;
	}
	public void setLast_login_ip(String last_login_ip) {
		this.last_login_ip = last_login_ip;
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
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public String getH_password() {
		return h_password;
	}
	public void setH_password(String h_password) {
		this.h_password = h_password;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getH_username() {
		return h_username;
	}
	public void setH_username(String h_username) {
		this.h_username = h_username;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUd_checked() {
		return ud_checked;
	}
	public void setUd_checked(String ud_checked) {
		this.ud_checked = ud_checked;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public String getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "Regist_dataEntity [phone=" + phone + ", username=" + username
				+ ", sex=" + sex + ", province=" + province + ", city=" + city
				+ ", business=" + business + ", last_login_time="
				+ last_login_time + ", last_login_ip=" + last_login_ip
				+ ", head_img=" + head_img + ", cover_img=" + cover_img
				+ ", access_token=" + access_token + ", h_password="
				+ h_password + ", uid=" + uid + ", h_username=" + h_username
				+ ", status=" + status + ", ud_checked=" + ud_checked
				+ ", created_at=" + created_at + ", updated_at=" + updated_at
				+ ", id=" + id + "]";
	}
	
	
	
	

}
