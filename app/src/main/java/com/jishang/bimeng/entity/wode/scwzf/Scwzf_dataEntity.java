package com.jishang.bimeng.entity.wode.scwzf;

import java.io.Serializable;

public class Scwzf_dataEntity implements Serializable{
	private String bp_id;
	private String money;
	private String bus_id;
	private String bp_uid;
	private String bp_order_notice;
	private String is_pay;
	private String bp_status;
	private String pay_time;
	private String bp_create_time;
	private String redeem_code;
	private String redeem_status;
	private String use_redeem_time;
	private Scwzf_data_bsEntity business;
	
	
	
	
	public Scwzf_dataEntity() {
		super();
	}
	public Scwzf_dataEntity(String bp_id, String money, String bus_id,
			String bp_uid, String bp_order_notice, String is_pay,
			String bp_status, String pay_time, String bp_create_time,
			String redeem_code, String redeem_status, String use_redeem_time,
			Scwzf_data_bsEntity business) {
		super();
		this.bp_id = bp_id;
		this.money = money;
		this.bus_id = bus_id;
		this.bp_uid = bp_uid;
		this.bp_order_notice = bp_order_notice;
		this.is_pay = is_pay;
		this.bp_status = bp_status;
		this.pay_time = pay_time;
		this.bp_create_time = bp_create_time;
		this.redeem_code = redeem_code;
		this.redeem_status = redeem_status;
		this.use_redeem_time = use_redeem_time;
		this.business = business;
	}
	public String getBp_id() {
		return bp_id;
	}
	public void setBp_id(String bp_id) {
		this.bp_id = bp_id;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getBus_id() {
		return bus_id;
	}
	public void setBus_id(String bus_id) {
		this.bus_id = bus_id;
	}
	public String getBp_uid() {
		return bp_uid;
	}
	public void setBp_uid(String bp_uid) {
		this.bp_uid = bp_uid;
	}
	public String getBp_order_notice() {
		return bp_order_notice;
	}
	public void setBp_order_notice(String bp_order_notice) {
		this.bp_order_notice = bp_order_notice;
	}
	public String getIs_pay() {
		return is_pay;
	}
	public void setIs_pay(String is_pay) {
		this.is_pay = is_pay;
	}
	public String getBp_status() {
		return bp_status;
	}
	public void setBp_status(String bp_status) {
		this.bp_status = bp_status;
	}
	public String getPay_time() {
		return pay_time;
	}
	public void setPay_time(String pay_time) {
		this.pay_time = pay_time;
	}
	public String getBp_create_time() {
		return bp_create_time;
	}
	public void setBp_create_time(String bp_create_time) {
		this.bp_create_time = bp_create_time;
	}
	public String getRedeem_code() {
		return redeem_code;
	}
	public void setRedeem_code(String redeem_code) {
		this.redeem_code = redeem_code;
	}
	public String getRedeem_status() {
		return redeem_status;
	}
	public void setRedeem_status(String redeem_status) {
		this.redeem_status = redeem_status;
	}
	public String getUse_redeem_time() {
		return use_redeem_time;
	}
	public void setUse_redeem_time(String use_redeem_time) {
		this.use_redeem_time = use_redeem_time;
	}
	public Scwzf_data_bsEntity getBusiness() {
		return business;
	}
	public void setBusiness(Scwzf_data_bsEntity business) {
		this.business = business;
	}
	@Override
	public String toString() {
		return "Scwzf_dataEntity [bp_id=" + bp_id + ", money=" + money
				+ ", bus_id=" + bus_id + ", bp_uid=" + bp_uid
				+ ", bp_order_notice=" + bp_order_notice + ", is_pay=" + is_pay
				+ ", bp_status=" + bp_status + ", pay_time=" + pay_time
				+ ", bp_create_time=" + bp_create_time + ", redeem_code="
				+ redeem_code + ", redeem_status=" + redeem_status
				+ ", use_redeem_time=" + use_redeem_time + ", business="
				+ business + "]";
	}
	

}
