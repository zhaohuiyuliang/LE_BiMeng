package com.jishang.bimeng.entity.yuewan.confirm;

import java.io.Serializable;

public class Yw_listConfrim_dataEntity implements Serializable{
	private String yw_order_notice;
	private String one_money;
	private String num_time;
	private String pn_start_time;
	private String ypn_total_mondy;
	private String pnpw_uid;
	private String pnyw_uid;
	private String ywp_id;
	private String ypn_creatime;
	private String ypn_pay_time;
	private String ypn_is_pay;
	private String ywpn_status;
	private String ypn_id;
	
	
	
	
	
	public Yw_listConfrim_dataEntity() {
		super();
	}
	public Yw_listConfrim_dataEntity(String yw_order_notice, String one_money,
			String num_time, String pn_start_time, String ypn_total_mondy,
			String pnpw_uid, String pnyw_uid, String ywp_id,
			String ypn_creatime, String ypn_pay_time, String ypn_is_pay,
			String ywpn_status, String ypn_id) {
		super();
		this.yw_order_notice = yw_order_notice;
		this.one_money = one_money;
		this.num_time = num_time;
		this.pn_start_time = pn_start_time;
		this.ypn_total_mondy = ypn_total_mondy;
		this.pnpw_uid = pnpw_uid;
		this.pnyw_uid = pnyw_uid;
		this.ywp_id = ywp_id;
		this.ypn_creatime = ypn_creatime;
		this.ypn_pay_time = ypn_pay_time;
		this.ypn_is_pay = ypn_is_pay;
		this.ywpn_status = ywpn_status;
		this.ypn_id = ypn_id;
	}
	public String getYw_order_notice() {
		return yw_order_notice;
	}
	public void setYw_order_notice(String yw_order_notice) {
		this.yw_order_notice = yw_order_notice;
	}
	public String getOne_money() {
		return one_money;
	}
	public void setOne_money(String one_money) {
		this.one_money = one_money;
	}
	public String getNum_time() {
		return num_time;
	}
	public void setNum_time(String num_time) {
		this.num_time = num_time;
	}
	public String getPn_start_time() {
		return pn_start_time;
	}
	public void setPn_start_time(String pn_start_time) {
		this.pn_start_time = pn_start_time;
	}
	public String getYpn_total_mondy() {
		return ypn_total_mondy;
	}
	public void setYpn_total_mondy(String ypn_total_mondy) {
		this.ypn_total_mondy = ypn_total_mondy;
	}
	public String getPnpw_uid() {
		return pnpw_uid;
	}
	public void setPnpw_uid(String pnpw_uid) {
		this.pnpw_uid = pnpw_uid;
	}
	public String getPnyw_uid() {
		return pnyw_uid;
	}
	public void setPnyw_uid(String pnyw_uid) {
		this.pnyw_uid = pnyw_uid;
	}
	public String getYwp_id() {
		return ywp_id;
	}
	public void setYwp_id(String ywp_id) {
		this.ywp_id = ywp_id;
	}
	public String getYpn_creatime() {
		return ypn_creatime;
	}
	public void setYpn_creatime(String ypn_creatime) {
		this.ypn_creatime = ypn_creatime;
	}
	public String getYpn_pay_time() {
		return ypn_pay_time;
	}
	public void setYpn_pay_time(String ypn_pay_time) {
		this.ypn_pay_time = ypn_pay_time;
	}
	public String getYpn_is_pay() {
		return ypn_is_pay;
	}
	public void setYpn_is_pay(String ypn_is_pay) {
		this.ypn_is_pay = ypn_is_pay;
	}
	public String getYwpn_status() {
		return ywpn_status;
	}
	public void setYwpn_status(String ywpn_status) {
		this.ywpn_status = ywpn_status;
	}
	public String getYpn_id() {
		return ypn_id;
	}
	public void setYpn_id(String ypn_id) {
		this.ypn_id = ypn_id;
	}
	@Override
	public String toString() {
		return "Yw_listConfrim_dataEntity [yw_order_notice=" + yw_order_notice
				+ ", one_money=" + one_money + ", num_time=" + num_time
				+ ", pn_start_time=" + pn_start_time + ", ypn_total_mondy="
				+ ypn_total_mondy + ", pnpw_uid=" + pnpw_uid + ", pnyw_uid="
				+ pnyw_uid + ", ywp_id=" + ywp_id + ", ypn_creatime="
				+ ypn_creatime + ", ypn_pay_time=" + ypn_pay_time
				+ ", ypn_is_pay=" + ypn_is_pay + ", ywpn_status=" + ywpn_status
				+ ", ypn_id=" + ypn_id + "]";
	}

	
	
}
