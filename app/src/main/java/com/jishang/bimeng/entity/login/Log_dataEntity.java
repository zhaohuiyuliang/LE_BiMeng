package com.jishang.bimeng.entity.login;

import java.io.Serializable;

public class Log_dataEntity implements Serializable{
	private String phone;
	private String username;
	private String uid;
	private String access_token;
	private String head_img;
	private String h_username;
	private String h_password;
	private String cover_img;
	private String describetion_info;
	private int sex;
	private String province;
	private String city;
	private String business;
	private String s_provname;
	private String s_cityname;
	private String w_name;
	
	
	
	
	
	
	
	
	
	public Log_dataEntity() {
		super();
	}
	public Log_dataEntity(String phone, String username, String uid,
			String access_token, String head_img, String h_username,
			String h_password, String cover_img, String describetion_info,
			int sex, String province, String city, String business,
			String s_provname, String s_cityname, String w_name) {
		super();
		this.phone = phone;
		this.username = username;
		this.uid = uid;
		this.access_token = access_token;
		this.head_img = head_img;
		this.h_username = h_username;
		this.h_password = h_password;
		this.cover_img = cover_img;
		this.describetion_info = describetion_info;
		this.sex = sex;
		this.province = province;
		this.city = city;
		this.business = business;
		this.s_provname = s_provname;
		this.s_cityname = s_cityname;
		this.w_name = w_name;
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
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
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
	public String getH_password() {
		return h_password;
	}
	public void setH_password(String h_password) {
		this.h_password = h_password;
	}
	public String getCover_img() {
		return cover_img;
	}
	public void setCover_img(String cover_img) {
		this.cover_img = cover_img;
	}
	public String getDescribetion_info() {
		return describetion_info;
	}
	public void setDescribetion_info(String describetion_info) {
		this.describetion_info = describetion_info;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
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
	public String getS_provname() {
		return s_provname;
	}
	public void setS_provname(String s_provname) {
		this.s_provname = s_provname;
	}
	public String getS_cityname() {
		return s_cityname;
	}
	public void setS_cityname(String s_cityname) {
		this.s_cityname = s_cityname;
	}
	public String getW_name() {
		return w_name;
	}
	public void setW_name(String w_name) {
		this.w_name = w_name;
	}
	@Override
	public String toString() {
		return "Log_dataEntity [phone=" + phone + ", username=" + username
				+ ", uid=" + uid + ", access_token=" + access_token
				+ ", head_img=" + head_img + ", h_username=" + h_username
				+ ", h_password=" + h_password + ", cover_img=" + cover_img
				+ ", describetion_info=" + describetion_info + ", sex=" + sex
				+ ", province=" + province + ", city=" + city + ", business="
				+ business + ", s_provname=" + s_provname + ", s_cityname="
				+ s_cityname + ", w_name=" + w_name + "]";
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
