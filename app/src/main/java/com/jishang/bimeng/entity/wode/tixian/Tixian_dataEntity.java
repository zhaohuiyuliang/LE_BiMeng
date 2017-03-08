package com.jishang.bimeng.entity.wode.tixian;

import java.io.Serializable;

public class Tixian_dataEntity implements Serializable{
	private String alipay_code;
	private String full_name;
	private String e_money;
	private String e_uid;
	private String e_order_no;
	private String apply_time;
	private String apply_status;
	private String back_money_status;
	private String e_id;
	
	
	
	
	
	
	
	
	public Tixian_dataEntity() {
		super();
	}
	public Tixian_dataEntity(String alipay_code, String full_name,
			String e_money, String e_uid, String e_order_no, String apply_time,
			String apply_status, String back_money_status, String e_id) {
		super();
		this.alipay_code = alipay_code;
		this.full_name = full_name;
		this.e_money = e_money;
		this.e_uid = e_uid;
		this.e_order_no = e_order_no;
		this.apply_time = apply_time;
		this.apply_status = apply_status;
		this.back_money_status = back_money_status;
		this.e_id = e_id;
	}
	public String getAlipay_code() {
		return alipay_code;
	}
	public void setAlipay_code(String alipay_code) {
		this.alipay_code = alipay_code;
	}
	public String getFull_name() {
		return full_name;
	}
	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}
	public String getE_money() {
		return e_money;
	}
	public void setE_money(String e_money) {
		this.e_money = e_money;
	}
	public String getE_uid() {
		return e_uid;
	}
	public void setE_uid(String e_uid) {
		this.e_uid = e_uid;
	}
	public String getE_order_no() {
		return e_order_no;
	}
	public void setE_order_no(String e_order_no) {
		this.e_order_no = e_order_no;
	}
	public String getApply_time() {
		return apply_time;
	}
	public void setApply_time(String apply_time) {
		this.apply_time = apply_time;
	}
	public String getApply_status() {
		return apply_status;
	}
	public void setApply_status(String apply_status) {
		this.apply_status = apply_status;
	}
	public String getBack_money_status() {
		return back_money_status;
	}
	public void setBack_money_status(String back_money_status) {
		this.back_money_status = back_money_status;
	}
	public String getE_id() {
		return e_id;
	}
	public void setE_id(String e_id) {
		this.e_id = e_id;
	}
	@Override
	public String toString() {
		return "Tixian_dataEntity [alipay_code=" + alipay_code + ", full_name="
				+ full_name + ", e_money=" + e_money + ", e_uid=" + e_uid
				+ ", e_order_no=" + e_order_no + ", apply_time=" + apply_time
				+ ", apply_status=" + apply_status + ", back_money_status="
				+ back_money_status + ", e_id=" + e_id + "]";
	}

	
	
	
	
	
	
}
