package com.jishang.bimeng.entity.yuewan.list;

import java.io.Serializable;
import java.util.List;

public class YwList_dataEntity implements Serializable{
	private String username;
	private String head_img;
	private String sex;
	private String province;
	private String city;
	private String business;
	private String describetion_info;
	private String uid;
	private String u_integral;
	private String h_username;
	private String phone;
	private YwList_data_udEntity userdetails;
	private List<Ywlist_data_ugEntity> usergames;
	
	
	public YwList_dataEntity() {
		super();
	}
	public YwList_dataEntity(String username, String head_img, String sex,
			String province, String city, String business,
			String describetion_info, String uid, String u_integral,
			String h_username, String phone, YwList_data_udEntity userdetails,
			List<Ywlist_data_ugEntity> usergames) {
		super();
		this.username = username;
		this.head_img = head_img;
		this.sex = sex;
		this.province = province;
		this.city = city;
		this.business = business;
		this.describetion_info = describetion_info;
		this.uid = uid;
		this.u_integral = u_integral;
		this.h_username = h_username;
		this.phone = phone;
		this.userdetails = userdetails;
		this.usergames = usergames;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getHead_img() {
		return head_img;
	}
	public void setHead_img(String head_img) {
		this.head_img = head_img;
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
	public String getDescribetion_info() {
		return describetion_info;
	}
	public void setDescribetion_info(String describetion_info) {
		this.describetion_info = describetion_info;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getU_integral() {
		return u_integral;
	}
	public void setU_integral(String u_integral) {
		this.u_integral = u_integral;
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
	public YwList_data_udEntity getUserdetails() {
		return userdetails;
	}
	public void setUserdetails(YwList_data_udEntity userdetails) {
		this.userdetails = userdetails;
	}
	public List<Ywlist_data_ugEntity> getUsergames() {
		return usergames;
	}
	public void setUsergames(List<Ywlist_data_ugEntity> usergames) {
		this.usergames = usergames;
	}
	@Override
	public String toString() {
		return "YwList_dataEntity [username=" + username + ", head_img="
				+ head_img + ", sex=" + sex + ", province=" + province
				+ ", city=" + city + ", business=" + business
				+ ", describetion_info=" + describetion_info + ", uid=" + uid
				+ ", u_integral=" + u_integral + ", h_username=" + h_username
				+ ", phone=" + phone + ", userdetails=" + userdetails
				+ ", usergames=" + usergames + "]";
	}
	
	
	
	

}
