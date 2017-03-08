package com.jishang.bimeng.entity.yuezhan.wzf;

import java.io.Serializable;

public class Wzf_dataEntity implements Serializable{
	private String p_id;
	private String order_notice_sn;
	private String create_time;
	private String pay_time;
	private String p_start_time;
	private String pay_or_get;
	private String is_paid;
	private String user_uid;
	private String f_uid;
	private String one_peple_money;
	private String need_persons;
	private String total_money;
	private String app_money;
	private String outer_notice_sn;
	private String deal_id;
	private String deal_name;
	private String paid_send;
	
	private String ypn_id;
	private String yw_order_notice;
	private String one_money;
	private String num_time;
	private String pn_start_time;
	private String ypn_total_mondy;
	private String pnpw_uid;
	private String pnyw_uid;
	private String ywp_id;
	private String ypn_is_pay;
	private String ypn_pay_time;
	private String ywpn_status;
	private String yw_outer_notice;
	private String ypn_creatime;
	private Wzf_data_gameEntity game;
	
	
	
	
	
	
	
	
	public Wzf_dataEntity() {
		super();
	}
	public Wzf_dataEntity(String p_id, String order_notice_sn,
			String create_time, String pay_time, String p_start_time,
			String pay_or_get, String is_paid, String user_uid, String f_uid,
			String one_peple_money, String need_persons, String total_money,
			String app_money, String outer_notice_sn, String deal_id,
			String deal_name, String paid_send, String ypn_id,
			String yw_order_notice, String one_money, String num_time,
			String pn_start_time, String ypn_total_mondy, String pnpw_uid,
			String pnyw_uid, String ywp_id, String ypn_is_pay,
			String ypn_pay_time, String ywpn_status, String yw_outer_notice,
			String ypn_creatime, Wzf_data_gameEntity game) {
		super();
		this.p_id = p_id;
		this.order_notice_sn = order_notice_sn;
		this.create_time = create_time;
		this.pay_time = pay_time;
		this.p_start_time = p_start_time;
		this.pay_or_get = pay_or_get;
		this.is_paid = is_paid;
		this.user_uid = user_uid;
		this.f_uid = f_uid;
		this.one_peple_money = one_peple_money;
		this.need_persons = need_persons;
		this.total_money = total_money;
		this.app_money = app_money;
		this.outer_notice_sn = outer_notice_sn;
		this.deal_id = deal_id;
		this.deal_name = deal_name;
		this.paid_send = paid_send;
		this.ypn_id = ypn_id;
		this.yw_order_notice = yw_order_notice;
		this.one_money = one_money;
		this.num_time = num_time;
		this.pn_start_time = pn_start_time;
		this.ypn_total_mondy = ypn_total_mondy;
		this.pnpw_uid = pnpw_uid;
		this.pnyw_uid = pnyw_uid;
		this.ywp_id = ywp_id;
		this.ypn_is_pay = ypn_is_pay;
		this.ypn_pay_time = ypn_pay_time;
		this.ywpn_status = ywpn_status;
		this.yw_outer_notice = yw_outer_notice;
		this.ypn_creatime = ypn_creatime;
		this.game = game;
	}
	public String getP_id() {
		return p_id;
	}
	public void setP_id(String p_id) {
		this.p_id = p_id;
	}
	public String getOrder_notice_sn() {
		return order_notice_sn;
	}
	public void setOrder_notice_sn(String order_notice_sn) {
		this.order_notice_sn = order_notice_sn;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getPay_time() {
		return pay_time;
	}
	public void setPay_time(String pay_time) {
		this.pay_time = pay_time;
	}
	public String getP_start_time() {
		return p_start_time;
	}
	public void setP_start_time(String p_start_time) {
		this.p_start_time = p_start_time;
	}
	public String getPay_or_get() {
		return pay_or_get;
	}
	public void setPay_or_get(String pay_or_get) {
		this.pay_or_get = pay_or_get;
	}
	public String getIs_paid() {
		return is_paid;
	}
	public void setIs_paid(String is_paid) {
		this.is_paid = is_paid;
	}
	public String getUser_uid() {
		return user_uid;
	}
	public void setUser_uid(String user_uid) {
		this.user_uid = user_uid;
	}
	public String getF_uid() {
		return f_uid;
	}
	public void setF_uid(String f_uid) {
		this.f_uid = f_uid;
	}
	public String getOne_peple_money() {
		return one_peple_money;
	}
	public void setOne_peple_money(String one_peple_money) {
		this.one_peple_money = one_peple_money;
	}
	public String getNeed_persons() {
		return need_persons;
	}
	public void setNeed_persons(String need_persons) {
		this.need_persons = need_persons;
	}
	public String getTotal_money() {
		return total_money;
	}
	public void setTotal_money(String total_money) {
		this.total_money = total_money;
	}
	public String getApp_money() {
		return app_money;
	}
	public void setApp_money(String app_money) {
		this.app_money = app_money;
	}
	public String getOuter_notice_sn() {
		return outer_notice_sn;
	}
	public void setOuter_notice_sn(String outer_notice_sn) {
		this.outer_notice_sn = outer_notice_sn;
	}
	public String getDeal_id() {
		return deal_id;
	}
	public void setDeal_id(String deal_id) {
		this.deal_id = deal_id;
	}
	public String getDeal_name() {
		return deal_name;
	}
	public void setDeal_name(String deal_name) {
		this.deal_name = deal_name;
	}
	public String getPaid_send() {
		return paid_send;
	}
	public void setPaid_send(String paid_send) {
		this.paid_send = paid_send;
	}
	public String getYpn_id() {
		return ypn_id;
	}
	public void setYpn_id(String ypn_id) {
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
	public String getYpn_is_pay() {
		return ypn_is_pay;
	}
	public void setYpn_is_pay(String ypn_is_pay) {
		this.ypn_is_pay = ypn_is_pay;
	}
	public String getYpn_pay_time() {
		return ypn_pay_time;
	}
	public void setYpn_pay_time(String ypn_pay_time) {
		this.ypn_pay_time = ypn_pay_time;
	}
	public String getYwpn_status() {
		return ywpn_status;
	}
	public void setYwpn_status(String ywpn_status) {
		this.ywpn_status = ywpn_status;
	}
	public String getYw_outer_notice() {
		return yw_outer_notice;
	}
	public void setYw_outer_notice(String yw_outer_notice) {
		this.yw_outer_notice = yw_outer_notice;
	}
	public String getYpn_creatime() {
		return ypn_creatime;
	}
	public void setYpn_creatime(String ypn_creatime) {
		this.ypn_creatime = ypn_creatime;
	}
	public Wzf_data_gameEntity getGame() {
		return game;
	}
	public void setGame(Wzf_data_gameEntity game) {
		this.game = game;
	}
	@Override
	public String toString() {
		return "Wdh_dataEntity [p_id=" + p_id + ", order_notice_sn="
				+ order_notice_sn + ", create_time=" + create_time
				+ ", pay_time=" + pay_time + ", p_start_time=" + p_start_time
				+ ", pay_or_get=" + pay_or_get + ", is_paid=" + is_paid
				+ ", user_uid=" + user_uid + ", f_uid=" + f_uid
				+ ", one_peple_money=" + one_peple_money + ", need_persons="
				+ need_persons + ", total_money=" + total_money
				+ ", app_money=" + app_money + ", outer_notice_sn="
				+ outer_notice_sn + ", deal_id=" + deal_id + ", deal_name="
				+ deal_name + ", paid_send=" + paid_send + ", ypn_id=" + ypn_id
				+ ", yw_order_notice=" + yw_order_notice + ", one_money="
				+ one_money + ", num_time=" + num_time + ", pn_start_time="
				+ pn_start_time + ", ypn_total_mondy=" + ypn_total_mondy
				+ ", pnpw_uid=" + pnpw_uid + ", pnyw_uid=" + pnyw_uid
				+ ", ywp_id=" + ywp_id + ", ypn_is_pay=" + ypn_is_pay
				+ ", ypn_pay_time=" + ypn_pay_time + ", ywpn_status="
				+ ywpn_status + ", yw_outer_notice=" + yw_outer_notice
				+ ", ypn_creatime=" + ypn_creatime + ", game=" + game + "]";
	}
	
	
	
	
	
	
	
	
}
