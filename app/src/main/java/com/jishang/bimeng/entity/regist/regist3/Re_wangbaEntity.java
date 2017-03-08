package com.jishang.bimeng.entity.regist.regist3;

import java.io.Serializable;

public class Re_wangbaEntity implements Serializable{
	private String province;
	private String city;
	private String business;
	private String business_name;
	
	
	
	
	
	public Re_wangbaEntity() {
		super();
	}
	public Re_wangbaEntity(String province, String city, String business,
			String business_name) {
		super();
		this.province = province;
		this.city = city;
		this.business = business;
		this.business_name = business_name;
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
	public String getBusiness_name() {
		return business_name;
	}
	public void setBusiness_name(String business_name) {
		this.business_name = business_name;
	}
	@Override
	public String toString() {
		return "Re_wangbaEntity [province=" + province + ", city=" + city
				+ ", business=" + business + ", business_name=" + business_name
				+ "]";
	}
	

	
	
	
}
