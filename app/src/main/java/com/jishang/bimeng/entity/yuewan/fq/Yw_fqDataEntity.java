package com.jishang.bimeng.entity.yuewan.fq;

import java.io.Serializable;

public class Yw_fqDataEntity implements Serializable{
	private String yw_id;
	private String yw_title;
	private String yw_img;
	private String yw_server;
	private String yw_grade;
	private String yw_pattern;
	private String yw_position;
	private String yw_start_four_time;
	private String yw_start_time;
	private String yw_num_time;
	private String item_money;
	private String total_money;
	private String pw_uid;
	private String yw_uid;
	private String is_pay;
	private String is_play;
	private String yw_creatime;
	private String yw_remarks;
	private String yw_status;
	private String yw_province;
	private String yw_city;
	private String yw_business;
	
	private Yw_fqData_YuEntity yw_user;
	private Yw_fqData_puEntity pw_user;
	
	
	
	
	
	public Yw_fqDataEntity() {
		super();
	}
	public Yw_fqDataEntity(String yw_id, String yw_title, String yw_img,
			String yw_server, String yw_grade, String yw_pattern,
			String yw_position, String yw_start_four_time,
			String yw_start_time, String yw_num_time, String item_money,
			String total_money, String pw_uid, String yw_uid, String is_pay,
			String is_play, String yw_creatime, String yw_remarks,
			String yw_status, String yw_province, String yw_city,
			String yw_business, Yw_fqData_YuEntity yw_user,
			Yw_fqData_puEntity pw_user) {
		super();
		this.yw_id = yw_id;
		this.yw_title = yw_title;
		this.yw_img = yw_img;
		this.yw_server = yw_server;
		this.yw_grade = yw_grade;
		this.yw_pattern = yw_pattern;
		this.yw_position = yw_position;
		this.yw_start_four_time = yw_start_four_time;
		this.yw_start_time = yw_start_time;
		this.yw_num_time = yw_num_time;
		this.item_money = item_money;
		this.total_money = total_money;
		this.pw_uid = pw_uid;
		this.yw_uid = yw_uid;
		this.is_pay = is_pay;
		this.is_play = is_play;
		this.yw_creatime = yw_creatime;
		this.yw_remarks = yw_remarks;
		this.yw_status = yw_status;
		this.yw_province = yw_province;
		this.yw_city = yw_city;
		this.yw_business = yw_business;
		this.yw_user = yw_user;
		this.pw_user = pw_user;
	}
	public String getYw_id() {
		return yw_id;
	}
	public void setYw_id(String yw_id) {
		this.yw_id = yw_id;
	}
	public String getYw_title() {
		return yw_title;
	}
	public void setYw_title(String yw_title) {
		this.yw_title = yw_title;
	}
	public String getYw_img() {
		return yw_img;
	}
	public void setYw_img(String yw_img) {
		this.yw_img = yw_img;
	}
	public String getYw_server() {
		return yw_server;
	}
	public void setYw_server(String yw_server) {
		this.yw_server = yw_server;
	}
	public String getYw_grade() {
		return yw_grade;
	}
	public void setYw_grade(String yw_grade) {
		this.yw_grade = yw_grade;
	}
	public String getYw_pattern() {
		return yw_pattern;
	}
	public void setYw_pattern(String yw_pattern) {
		this.yw_pattern = yw_pattern;
	}
	public String getYw_position() {
		return yw_position;
	}
	public void setYw_position(String yw_position) {
		this.yw_position = yw_position;
	}
	public String getYw_start_four_time() {
		return yw_start_four_time;
	}
	public void setYw_start_four_time(String yw_start_four_time) {
		this.yw_start_four_time = yw_start_four_time;
	}
	public String getYw_start_time() {
		return yw_start_time;
	}
	public void setYw_start_time(String yw_start_time) {
		this.yw_start_time = yw_start_time;
	}
	public String getYw_num_time() {
		return yw_num_time;
	}
	public void setYw_num_time(String yw_num_time) {
		this.yw_num_time = yw_num_time;
	}
	public String getItem_money() {
		return item_money;
	}
	public void setItem_money(String item_money) {
		this.item_money = item_money;
	}
	public String getTotal_money() {
		return total_money;
	}
	public void setTotal_money(String total_money) {
		this.total_money = total_money;
	}
	public String getPw_uid() {
		return pw_uid;
	}
	public void setPw_uid(String pw_uid) {
		this.pw_uid = pw_uid;
	}
	public String getYw_uid() {
		return yw_uid;
	}
	public void setYw_uid(String yw_uid) {
		this.yw_uid = yw_uid;
	}
	public String getIs_pay() {
		return is_pay;
	}
	public void setIs_pay(String is_pay) {
		this.is_pay = is_pay;
	}
	public String getIs_play() {
		return is_play;
	}
	public void setIs_play(String is_play) {
		this.is_play = is_play;
	}
	public String getYw_creatime() {
		return yw_creatime;
	}
	public void setYw_creatime(String yw_creatime) {
		this.yw_creatime = yw_creatime;
	}
	public String getYw_remarks() {
		return yw_remarks;
	}
	public void setYw_remarks(String yw_remarks) {
		this.yw_remarks = yw_remarks;
	}
	public String getYw_status() {
		return yw_status;
	}
	public void setYw_status(String yw_status) {
		this.yw_status = yw_status;
	}
	public String getYw_province() {
		return yw_province;
	}
	public void setYw_province(String yw_province) {
		this.yw_province = yw_province;
	}
	public String getYw_city() {
		return yw_city;
	}
	public void setYw_city(String yw_city) {
		this.yw_city = yw_city;
	}
	public String getYw_business() {
		return yw_business;
	}
	public void setYw_business(String yw_business) {
		this.yw_business = yw_business;
	}
	public Yw_fqData_YuEntity getYw_user() {
		return yw_user;
	}
	public void setYw_user(Yw_fqData_YuEntity yw_user) {
		this.yw_user = yw_user;
	}
	public Yw_fqData_puEntity getPw_user() {
		return pw_user;
	}
	public void setPw_user(Yw_fqData_puEntity pw_user) {
		this.pw_user = pw_user;
	}
	@Override
	public String toString() {
		return "Yw_fqDataEntity [yw_id=" + yw_id + ", yw_title=" + yw_title
				+ ", yw_img=" + yw_img + ", yw_server=" + yw_server
				+ ", yw_grade=" + yw_grade + ", yw_pattern=" + yw_pattern
				+ ", yw_position=" + yw_position + ", yw_start_four_time="
				+ yw_start_four_time + ", yw_start_time=" + yw_start_time
				+ ", yw_num_time=" + yw_num_time + ", item_money=" + item_money
				+ ", total_money=" + total_money + ", pw_uid=" + pw_uid
				+ ", yw_uid=" + yw_uid + ", is_pay=" + is_pay + ", is_play="
				+ is_play + ", yw_creatime=" + yw_creatime + ", yw_remarks="
				+ yw_remarks + ", yw_status=" + yw_status + ", yw_province="
				+ yw_province + ", yw_city=" + yw_city + ", yw_business="
				+ yw_business + ", yw_user=" + yw_user + ", pw_user=" + pw_user
				+ "]";
	}
	
	
	
	

}
