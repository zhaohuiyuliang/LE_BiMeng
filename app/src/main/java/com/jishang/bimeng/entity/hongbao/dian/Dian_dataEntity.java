package com.jishang.bimeng.entity.hongbao.dian;

import java.io.Serializable;

public class Dian_dataEntity implements Serializable{
	private String q_id;
	private String qb_money;
	private String qb_year_month;
	private String qb_uid;
	private String qb_status;
	private String hb_id;
	private String q_time;
	private String s_time;
	private String jiange_number;
	
	
	
	
	public Dian_dataEntity() {
		super();
	}
	public Dian_dataEntity(String q_id, String qb_money, String qb_year_month,
			String qb_uid, String qb_status, String hb_id, String q_time,
			String s_time, String jiange_number) {
		super();
		this.q_id = q_id;
		this.qb_money = qb_money;
		this.qb_year_month = qb_year_month;
		this.qb_uid = qb_uid;
		this.qb_status = qb_status;
		this.hb_id = hb_id;
		this.q_time = q_time;
		this.s_time = s_time;
		this.jiange_number = jiange_number;
	}
	public String getQ_id() {
		return q_id;
	}
	public void setQ_id(String q_id) {
		this.q_id = q_id;
	}
	public String getQb_money() {
		return qb_money;
	}
	public void setQb_money(String qb_money) {
		this.qb_money = qb_money;
	}
	public String getQb_year_month() {
		return qb_year_month;
	}
	public void setQb_year_month(String qb_year_month) {
		this.qb_year_month = qb_year_month;
	}
	public String getQb_uid() {
		return qb_uid;
	}
	public void setQb_uid(String qb_uid) {
		this.qb_uid = qb_uid;
	}
	public String getQb_status() {
		return qb_status;
	}
	public void setQb_status(String qb_status) {
		this.qb_status = qb_status;
	}
	public String getHb_id() {
		return hb_id;
	}
	public void setHb_id(String hb_id) {
		this.hb_id = hb_id;
	}
	public String getQ_time() {
		return q_time;
	}
	public void setQ_time(String q_time) {
		this.q_time = q_time;
	}
	public String getS_time() {
		return s_time;
	}
	public void setS_time(String s_time) {
		this.s_time = s_time;
	}
	public String getJiange_number() {
		return jiange_number;
	}
	public void setJiange_number(String jiange_number) {
		this.jiange_number = jiange_number;
	}
	@Override
	public String toString() {
		return "Dian_dataEntity [q_id=" + q_id + ", qb_money=" + qb_money
				+ ", qb_year_month=" + qb_year_month + ", qb_uid=" + qb_uid
				+ ", qb_status=" + qb_status + ", hb_id=" + hb_id + ", q_time="
				+ q_time + ", s_time=" + s_time + ", jiange_number="
				+ jiange_number + "]";
	}
	
	
	
	
	

	
	
	
}
