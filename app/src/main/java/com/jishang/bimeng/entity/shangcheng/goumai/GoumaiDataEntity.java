package com.jishang.bimeng.entity.shangcheng.goumai;

import java.io.Serializable;

public class GoumaiDataEntity implements Serializable{
	private String bus_id;
	private String money;
	private String bp_uid;
	private String bp_order_notice;
	private String bp_create_time;
	private String redeem_code;
	private String bp_status;
	private String is_pay;
	private String redeem_status;
	private String bp_id;
	private String name;
	private String bs_describe;
	
	
	
	
	
	
	public GoumaiDataEntity() {
		super();
	}
	public GoumaiDataEntity(String bus_id, String money, String bp_uid,
			String bp_order_notice, String bp_create_time, String redeem_code,
			String bp_status, String is_pay, String redeem_status,
			String bp_id, String name, String bs_describe) {
		super();
		this.bus_id = bus_id;
		this.money = money;
		this.bp_uid = bp_uid;
		this.bp_order_notice = bp_order_notice;
		this.bp_create_time = bp_create_time;
		this.redeem_code = redeem_code;
		this.bp_status = bp_status;
		this.is_pay = is_pay;
		this.redeem_status = redeem_status;
		this.bp_id = bp_id;
		this.name = name;
		this.bs_describe = bs_describe;
	}
	public String getBus_id() {
		return bus_id;
	}
	public void setBus_id(String bus_id) {
		this.bus_id = bus_id;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
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
	public String getBp_status() {
		return bp_status;
	}
	public void setBp_status(String bp_status) {
		this.bp_status = bp_status;
	}
	public String getIs_pay() {
		return is_pay;
	}
	public void setIs_pay(String is_pay) {
		this.is_pay = is_pay;
	}
	public String getRedeem_status() {
		return redeem_status;
	}
	public void setRedeem_status(String redeem_status) {
		this.redeem_status = redeem_status;
	}
	public String getBp_id() {
		return bp_id;
	}
	public void setBp_id(String bp_id) {
		this.bp_id = bp_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBs_describe() {
		return bs_describe;
	}
	public void setBs_describe(String bs_describe) {
		this.bs_describe = bs_describe;
	}
	@Override
	public String toString() {
		return "GoumaiDataEntity [bus_id=" + bus_id + ", money=" + money
				+ ", bp_uid=" + bp_uid + ", bp_order_notice=" + bp_order_notice
				+ ", bp_create_time=" + bp_create_time + ", redeem_code="
				+ redeem_code + ", bp_status=" + bp_status + ", is_pay="
				+ is_pay + ", redeem_status=" + redeem_status + ", bp_id="
				+ bp_id + ", name=" + name + ", bs_describe=" + bs_describe
				+ "]";
	}
	
	
	
	
	
	
	

}
