package com.jishang.bimeng.entity.yuezhan.zhdianqiehuan;

import java.util.List;

public class ZhudianqhEntity {
	private int status;
	private String status_code;
	private List<Zhudianqh_ProvinceEntity> province;
	private List<Zhudianqh_CityEntity> city;
	private List<Zhudianqh_BsEntity> business;
	
	
	
	
	public ZhudianqhEntity() {
		super();
	}
	public ZhudianqhEntity(int status, String status_code,
			List<Zhudianqh_ProvinceEntity> province,
			List<Zhudianqh_CityEntity> city, List<Zhudianqh_BsEntity> business) {
		super();
		this.status = status;
		this.status_code = status_code;
		this.province = province;
		this.city = city;
		this.business = business;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getStatus_code() {
		return status_code;
	}
	public void setStatus_code(String status_code) {
		this.status_code = status_code;
	}
	public List<Zhudianqh_ProvinceEntity> getProvince() {
		return province;
	}
	public void setProvince(List<Zhudianqh_ProvinceEntity> province) {
		this.province = province;
	}
	public List<Zhudianqh_CityEntity> getCity() {
		return city;
	}
	public void setCity(List<Zhudianqh_CityEntity> city) {
		this.city = city;
	}
	public List<Zhudianqh_BsEntity> getBusiness() {
		return business;
	}
	public void setBusiness(List<Zhudianqh_BsEntity> business) {
		this.business = business;
	}
	@Override
	public String toString() {
		return "ZhudianqhEntity [status=" + status + ", status_code="
				+ status_code + ", province=" + province + ", city=" + city
				+ ", business=" + business + "]";
	}
	
	
	

}
