package com.jishang.bimeng.entity.hongbao.chai;

import java.io.Serializable;

public class Chai_qoEntity implements Serializable{
	private String q_id;
	private String qb_money;
	private String qb_year_month;
	private String qb_uid;
	private String qb_status;
	private String hb_id;
	private String q_time;
	private Chai_qb_qbuserEntity qb_user;
	
	
	public Chai_qoEntity(String q_id, String qb_money, String qb_year_month,
			String qb_uid, String qb_status, String hb_id, String q_time,
			Chai_qb_qbuserEntity qb_user) {
		super();
		this.q_id = q_id;
		this.qb_money = qb_money;
		this.qb_year_month = qb_year_month;
		this.qb_uid = qb_uid;
		this.qb_status = qb_status;
		this.hb_id = hb_id;
		this.q_time = q_time;
		this.qb_user = qb_user;
	}
	
	public Chai_qoEntity() {
		super();
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
	public Chai_qb_qbuserEntity getQb_user() {
		return qb_user;
	}
	public void setQb_user(Chai_qb_qbuserEntity qb_user) {
		this.qb_user = qb_user;
	}
	@Override
	public String toString() {
		return "Chai_qoEntity [q_id=" + q_id + ", qb_money=" + qb_money
				+ ", qb_year_month=" + qb_year_month + ", qb_uid=" + qb_uid
				+ ", qb_status=" + qb_status + ", hb_id=" + hb_id + ", q_time="
				+ q_time + ", qb_user=" + qb_user + "]";
	}

	
	
	
	

}
