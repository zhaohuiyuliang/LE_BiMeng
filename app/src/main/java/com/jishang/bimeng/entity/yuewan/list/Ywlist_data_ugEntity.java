package com.jishang.bimeng.entity.yuewan.list;

import java.io.Serializable;

public class Ywlist_data_ugEntity implements Serializable{
	private String yw_title;
	private String yw_img;
	private String yw_server;
	private String yw_grade;
	private String yw_pattern;
	private String yw_position;
	private String ywg_uid;
	
	
	
	
	public Ywlist_data_ugEntity() {
		super();
	}
	public Ywlist_data_ugEntity(String yw_title, String yw_img,
			String yw_server, String yw_grade, String yw_pattern,
			String yw_position, String ywg_uid) {
		super();
		this.yw_title = yw_title;
		this.yw_img = yw_img;
		this.yw_server = yw_server;
		this.yw_grade = yw_grade;
		this.yw_pattern = yw_pattern;
		this.yw_position = yw_position;
		this.ywg_uid = ywg_uid;
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
	public String getYwg_uid() {
		return ywg_uid;
	}
	public void setYwg_uid(String ywg_uid) {
		this.ywg_uid = ywg_uid;
	}
	@Override
	public String toString() {
		return "Ywlist_data_ugEntity [yw_title=" + yw_title + ", yw_img="
				+ yw_img + ", yw_server=" + yw_server + ", yw_grade="
				+ yw_grade + ", yw_pattern=" + yw_pattern + ", yw_position="
				+ yw_position + ", ywg_uid=" + ywg_uid + "]";
	}
	

	
	
}
