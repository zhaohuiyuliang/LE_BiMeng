package com.jishang.bimeng.entity.yuezhan.zf.zfb;

import java.io.Serializable;

public class Zfb_dataEntity implements Serializable{
	private String change;
	private String income;
	private String third;
	private String zf_uid;
	private String zf_attribute;
	private String zf_order_notice;
	private String pd_time;
	private String dt_id;
	
	
	
	
	
	
	
	
	
	public Zfb_dataEntity() {
		super();
	}
	public Zfb_dataEntity(String change, String income, String third,
			String zf_uid, String zf_attribute, String zf_order_notice,
			String pd_time, String dt_id) {
		super();
		this.change = change;
		this.income = income;
		this.third = third;
		this.zf_uid = zf_uid;
		this.zf_attribute = zf_attribute;
		this.zf_order_notice = zf_order_notice;
		this.pd_time = pd_time;
		this.dt_id = dt_id;
	}
	public String getChange() {
		return change;
	}
	public void setChange(String change) {
		this.change = change;
	}
	public String getIncome() {
		return income;
	}
	public void setIncome(String income) {
		this.income = income;
	}
	public String getThird() {
		return third;
	}
	public void setThird(String third) {
		this.third = third;
	}
	public String getZf_uid() {
		return zf_uid;
	}
	public void setZf_uid(String zf_uid) {
		this.zf_uid = zf_uid;
	}
	public String getZf_attribute() {
		return zf_attribute;
	}
	public void setZf_attribute(String zf_attribute) {
		this.zf_attribute = zf_attribute;
	}
	public String getZf_order_notice() {
		return zf_order_notice;
	}
	public void setZf_order_notice(String zf_order_notice) {
		this.zf_order_notice = zf_order_notice;
	}
	public String getPd_time() {
		return pd_time;
	}
	public void setPd_time(String pd_time) {
		this.pd_time = pd_time;
	}
	public String getDt_id() {
		return dt_id;
	}
	public void setDt_id(String dt_id) {
		this.dt_id = dt_id;
	}
	@Override
	public String toString() {
		return "Zfb_dataEntity [change=" + change + ", income=" + income
				+ ", third=" + third + ", zf_uid=" + zf_uid + ", zf_attribute="
				+ zf_attribute + ", zf_order_notice=" + zf_order_notice
				+ ", pd_time=" + pd_time + ", dt_id=" + dt_id + "]";
	}

	
	
}
