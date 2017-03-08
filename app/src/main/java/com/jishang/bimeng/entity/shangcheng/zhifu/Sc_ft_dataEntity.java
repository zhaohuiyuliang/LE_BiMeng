package com.jishang.bimeng.entity.shangcheng.zhifu;

import java.io.Serializable;

public class Sc_ft_dataEntity implements Serializable{
	private String bp_id;
	private String bus_id;
	private String bp_order_notice;
	private String money;
	private String change;
	private String income;
	private String uid;
	
	private int change_id;
	private int income_id;
	private int third_id;
	
	
	
	
	public Sc_ft_dataEntity() {
		super();
	}
	public Sc_ft_dataEntity(String bp_id, String bus_id,
			String bp_order_notice, String money, String change, String income,
			String uid, int change_id, int income_id, int third_id) {
		super();
		this.bp_id = bp_id;
		this.bus_id = bus_id;
		this.bp_order_notice = bp_order_notice;
		this.money = money;
		this.change = change;
		this.income = income;
		this.uid = uid;
		this.change_id = change_id;
		this.income_id = income_id;
		this.third_id = third_id;
	}
	public String getBp_id() {
		return bp_id;
	}
	public void setBp_id(String bp_id) {
		this.bp_id = bp_id;
	}
	public String getBus_id() {
		return bus_id;
	}
	public void setBus_id(String bus_id) {
		this.bus_id = bus_id;
	}
	public String getBp_order_notice() {
		return bp_order_notice;
	}
	public void setBp_order_notice(String bp_order_notice) {
		this.bp_order_notice = bp_order_notice;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
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
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public int getChange_id() {
		return change_id;
	}
	public void setChange_id(int change_id) {
		this.change_id = change_id;
	}
	public int getIncome_id() {
		return income_id;
	}
	public void setIncome_id(int income_id) {
		this.income_id = income_id;
	}
	public int getThird_id() {
		return third_id;
	}
	public void setThird_id(int third_id) {
		this.third_id = third_id;
	}
	@Override
	public String toString() {
		return "Sc_ft_dataEntity [bp_id=" + bp_id + ", bus_id=" + bus_id
				+ ", bp_order_notice=" + bp_order_notice + ", money=" + money
				+ ", change=" + change + ", income=" + income + ", uid=" + uid
				+ ", change_id=" + change_id + ", income_id=" + income_id
				+ ", third_id=" + third_id + "]";
	}
	
	
	
	
	
	

	
	
	
}
