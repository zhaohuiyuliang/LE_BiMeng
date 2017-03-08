package com.jishang.bimeng.entity.yuezhan.fq.wfq.fenqian_cy;

import java.io.Serializable;

public class Fenqian_cyDataEntity implements Serializable{
	private String i_pay_or_get;
	private String promoter_uid;
	private String join_peple_uid;
	private String yuezhan_id;
	private String pay_get_money;
	private String get_is_pay;
	private String money_status;
	private Fenqian_cyData_puEntity promoter_user;
	private Fenqian_cyData_juEntity join_user;
	
	
	
	public Fenqian_cyDataEntity() {
		super();
	}
	public Fenqian_cyDataEntity(String i_pay_or_get, String promoter_uid,
			String join_peple_uid, String yuezhan_id, String pay_get_money,
			String get_is_pay, String money_status,
			Fenqian_cyData_puEntity promoter_user,
			Fenqian_cyData_juEntity join_user) {
		super();
		this.i_pay_or_get = i_pay_or_get;
		this.promoter_uid = promoter_uid;
		this.join_peple_uid = join_peple_uid;
		this.yuezhan_id = yuezhan_id;
		this.pay_get_money = pay_get_money;
		this.get_is_pay = get_is_pay;
		this.money_status = money_status;
		this.promoter_user = promoter_user;
		this.join_user = join_user;
	}
	public String getI_pay_or_get() {
		return i_pay_or_get;
	}
	public void setI_pay_or_get(String i_pay_or_get) {
		this.i_pay_or_get = i_pay_or_get;
	}
	public String getPromoter_uid() {
		return promoter_uid;
	}
	public void setPromoter_uid(String promoter_uid) {
		this.promoter_uid = promoter_uid;
	}
	public String getJoin_peple_uid() {
		return join_peple_uid;
	}
	public void setJoin_peple_uid(String join_peple_uid) {
		this.join_peple_uid = join_peple_uid;
	}
	public String getYuezhan_id() {
		return yuezhan_id;
	}
	public void setYuezhan_id(String yuezhan_id) {
		this.yuezhan_id = yuezhan_id;
	}
	public String getPay_get_money() {
		return pay_get_money;
	}
	public void setPay_get_money(String pay_get_money) {
		this.pay_get_money = pay_get_money;
	}
	public String getGet_is_pay() {
		return get_is_pay;
	}
	public void setGet_is_pay(String get_is_pay) {
		this.get_is_pay = get_is_pay;
	}
	public String getMoney_status() {
		return money_status;
	}
	public void setMoney_status(String money_status) {
		this.money_status = money_status;
	}
	public Fenqian_cyData_puEntity getPromoter_user() {
		return promoter_user;
	}
	public void setPromoter_user(Fenqian_cyData_puEntity promoter_user) {
		this.promoter_user = promoter_user;
	}
	public Fenqian_cyData_juEntity getJoin_user() {
		return join_user;
	}
	public void setJoin_user(Fenqian_cyData_juEntity join_user) {
		this.join_user = join_user;
	}
	@Override
	public String toString() {
		return "Fenqian_cyDataEntity [i_pay_or_get=" + i_pay_or_get
				+ ", promoter_uid=" + promoter_uid + ", join_peple_uid="
				+ join_peple_uid + ", yuezhan_id=" + yuezhan_id
				+ ", pay_get_money=" + pay_get_money + ", get_is_pay="
				+ get_is_pay + ", money_status=" + money_status
				+ ", promoter_user=" + promoter_user + ", join_user="
				+ join_user + "]";
	}
	
	

}
