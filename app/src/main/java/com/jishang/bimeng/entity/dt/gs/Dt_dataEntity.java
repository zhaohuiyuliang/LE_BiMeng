package com.jishang.bimeng.entity.dt.gs;

import java.io.Serializable;

public class Dt_dataEntity implements Serializable{
	private String uc_id;
	private String content;
	private String thumb_img;
	private String province_sort;
	private String city_sort;
	private String business_sort;
	private String created_time;
	private String u_id;
	private String is_status;
	private String is_delete;
	
	
	
	
	public Dt_dataEntity() {
		super();
	}
	public Dt_dataEntity(String uc_id, String content, String thumb_img,
			String province_sort, String city_sort, String business_sort,
			String created_time, String u_id, String is_status, String is_delete) {
		super();
		this.uc_id = uc_id;
		this.content = content;
		this.thumb_img = thumb_img;
		this.province_sort = province_sort;
		this.city_sort = city_sort;
		this.business_sort = business_sort;
		this.created_time = created_time;
		this.u_id = u_id;
		this.is_status = is_status;
		this.is_delete = is_delete;
	}
	public String getUc_id() {
		return uc_id;
	}
	public void setUc_id(String uc_id) {
		this.uc_id = uc_id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getThumb_img() {
		return thumb_img;
	}
	public void setThumb_img(String thumb_img) {
		this.thumb_img = thumb_img;
	}
	public String getProvince_sort() {
		return province_sort;
	}
	public void setProvince_sort(String province_sort) {
		this.province_sort = province_sort;
	}
	public String getCity_sort() {
		return city_sort;
	}
	public void setCity_sort(String city_sort) {
		this.city_sort = city_sort;
	}
	public String getBusiness_sort() {
		return business_sort;
	}
	public void setBusiness_sort(String business_sort) {
		this.business_sort = business_sort;
	}
	public String getCreated_time() {
		return created_time;
	}
	public void setCreated_time(String created_time) {
		this.created_time = created_time;
	}
	public String getU_id() {
		return u_id;
	}
	public void setU_id(String u_id) {
		this.u_id = u_id;
	}
	public String getIs_status() {
		return is_status;
	}
	public void setIs_status(String is_status) {
		this.is_status = is_status;
	}
	public String getIs_delete() {
		return is_delete;
	}
	public void setIs_delete(String is_delete) {
		this.is_delete = is_delete;
	}
	@Override
	public String toString() {
		return "Dt_dataEntity [uc_id=" + uc_id + ", content=" + content
				+ ", thumb_img=" + thumb_img + ", province_sort="
				+ province_sort + ", city_sort=" + city_sort
				+ ", business_sort=" + business_sort + ", created_time="
				+ created_time + ", u_id=" + u_id + ", is_status=" + is_status
				+ ", is_delete=" + is_delete + "]";
	}

	
	
	
}
