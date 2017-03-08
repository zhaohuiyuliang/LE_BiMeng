package com.jishang.bimeng.entity.yuezhan.zhudian;

import java.io.Serializable;

public class ZhudianDataEntity implements Serializable{
	private String w_name;
	private String w_img;
	private String detailed_address;
	private String kf_name;
	private String kf_phone;
	private String huanjing;
	private String price;
	private String machine;
	private String be_careful;
	
	
	
	
	
	
	public ZhudianDataEntity() {
		super();
	}
	public ZhudianDataEntity(String w_name, String w_img,
			String detailed_address, String kf_name, String kf_phone,
			String huanjing, String price, String machine, String be_careful) {
		super();
		this.w_name = w_name;
		this.w_img = w_img;
		this.detailed_address = detailed_address;
		this.kf_name = kf_name;
		this.kf_phone = kf_phone;
		this.huanjing = huanjing;
		this.price = price;
		this.machine = machine;
		this.be_careful = be_careful;
	}
	public String getW_name() {
		return w_name;
	}
	public void setW_name(String w_name) {
		this.w_name = w_name;
	}
	public String getW_img() {
		return w_img;
	}
	public void setW_img(String w_img) {
		this.w_img = w_img;
	}
	public String getDetailed_address() {
		return detailed_address;
	}
	public void setDetailed_address(String detailed_address) {
		this.detailed_address = detailed_address;
	}
	public String getKf_name() {
		return kf_name;
	}
	public void setKf_name(String kf_name) {
		this.kf_name = kf_name;
	}
	public String getKf_phone() {
		return kf_phone;
	}
	public void setKf_phone(String kf_phone) {
		this.kf_phone = kf_phone;
	}
	public String getHuanjing() {
		return huanjing;
	}
	public void setHuanjing(String huanjing) {
		this.huanjing = huanjing;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getMachine() {
		return machine;
	}
	public void setMachine(String machine) {
		this.machine = machine;
	}
	public String getBe_careful() {
		return be_careful;
	}
	public void setBe_careful(String be_careful) {
		this.be_careful = be_careful;
	}
	@Override
	public String toString() {
		return "ZhudianDataEntity [w_name=" + w_name + ", w_img=" + w_img
				+ ", detailed_address=" + detailed_address + ", kf_name="
				+ kf_name + ", kf_phone=" + kf_phone + ", huanjing=" + huanjing
				+ ", price=" + price + ", machine=" + machine + ", be_careful="
				+ be_careful + "]";
	}
	
	
	
	

	
	
}
