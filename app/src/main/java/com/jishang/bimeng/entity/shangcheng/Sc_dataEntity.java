package com.jishang.bimeng.entity.shangcheng;

import java.io.Serializable;

public class Sc_dataEntity implements Serializable{
	private String bs_id;
	private String name;
	private String bs_img;
	private String bg_img;
	private String price;
	private String classification;
	private String bs_province;
	private String bs_city;
	private String bs_wangba;
	private String bs_describe;
	private String bs_promotion_remarks;
	private String bs_status;
	private String bs_create_time;
	private Sc_data_prEntity province;
	private Sc_data_ctEntity city;
	private Sc_data_wbEntity wangba;
	
	
	
	
	public Sc_dataEntity() {
		super();
	}
	public Sc_dataEntity(String bs_id, String name, String bs_img,
			String bg_img, String price, String classification,
			String bs_province, String bs_city, String bs_wangba,
			String bs_describe, String bs_promotion_remarks, String bs_status,
			String bs_create_time, Sc_data_prEntity province,
			Sc_data_ctEntity city, Sc_data_wbEntity wangba) {
		super();
		this.bs_id = bs_id;
		this.name = name;
		this.bs_img = bs_img;
		this.bg_img = bg_img;
		this.price = price;
		this.classification = classification;
		this.bs_province = bs_province;
		this.bs_city = bs_city;
		this.bs_wangba = bs_wangba;
		this.bs_describe = bs_describe;
		this.bs_promotion_remarks = bs_promotion_remarks;
		this.bs_status = bs_status;
		this.bs_create_time = bs_create_time;
		this.province = province;
		this.city = city;
		this.wangba = wangba;
	}
	public String getBs_id() {
		return bs_id;
	}
	public void setBs_id(String bs_id) {
		this.bs_id = bs_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBs_img() {
		return bs_img;
	}
	public void setBs_img(String bs_img) {
		this.bs_img = bs_img;
	}
	public String getBg_img() {
		return bg_img;
	}
	public void setBg_img(String bg_img) {
		this.bg_img = bg_img;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getClassification() {
		return classification;
	}
	public void setClassification(String classification) {
		this.classification = classification;
	}
	public String getBs_province() {
		return bs_province;
	}
	public void setBs_province(String bs_province) {
		this.bs_province = bs_province;
	}
	public String getBs_city() {
		return bs_city;
	}
	public void setBs_city(String bs_city) {
		this.bs_city = bs_city;
	}
	public String getBs_wangba() {
		return bs_wangba;
	}
	public void setBs_wangba(String bs_wangba) {
		this.bs_wangba = bs_wangba;
	}
	public String getBs_describe() {
		return bs_describe;
	}
	public void setBs_describe(String bs_describe) {
		this.bs_describe = bs_describe;
	}
	public String getBs_promotion_remarks() {
		return bs_promotion_remarks;
	}
	public void setBs_promotion_remarks(String bs_promotion_remarks) {
		this.bs_promotion_remarks = bs_promotion_remarks;
	}
	public String getBs_status() {
		return bs_status;
	}
	public void setBs_status(String bs_status) {
		this.bs_status = bs_status;
	}
	public String getBs_create_time() {
		return bs_create_time;
	}
	public void setBs_create_time(String bs_create_time) {
		this.bs_create_time = bs_create_time;
	}
	public Sc_data_prEntity getProvince() {
		return province;
	}
	public void setProvince(Sc_data_prEntity province) {
		this.province = province;
	}
	public Sc_data_ctEntity getCity() {
		return city;
	}
	public void setCity(Sc_data_ctEntity city) {
		this.city = city;
	}
	public Sc_data_wbEntity getWangba() {
		return wangba;
	}
	public void setWangba(Sc_data_wbEntity wangba) {
		this.wangba = wangba;
	}
	@Override
	public String toString() {
		return "Sc_dataEntity [bs_id=" + bs_id + ", name=" + name + ", bs_img="
				+ bs_img + ", bg_img=" + bg_img + ", price=" + price
				+ ", classification=" + classification + ", bs_province="
				+ bs_province + ", bs_city=" + bs_city + ", bs_wangba="
				+ bs_wangba + ", bs_describe=" + bs_describe
				+ ", bs_promotion_remarks=" + bs_promotion_remarks
				+ ", bs_status=" + bs_status + ", bs_create_time="
				+ bs_create_time + ", province=" + province + ", city=" + city
				+ ", wangba=" + wangba + "]";
	}
	
	
	
	
	

	
	
}
