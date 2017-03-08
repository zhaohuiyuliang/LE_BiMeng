package com.jishang.bimeng.entity.yuezhan;

import java.io.Serializable;
import java.util.List;

public class Game_rt_DataEntity implements Serializable{
	private int yz_gm_id;
	private String gm_name;
	private String gm_img;
	private List<String> gm_server;
	private List<String> gm_pattern;
	private List<String> gm_grade;
	private List<String> gm_position;
	private String create_gm_time;
	
	
	
	
	public Game_rt_DataEntity() {
		super();
	}
	public Game_rt_DataEntity(int yz_gm_id, String gm_name, String gm_img,
			List<String> gm_server, List<String> gm_pattern,
			List<String> gm_grade, List<String> gm_position,
			String create_gm_time) {
		super();
		this.yz_gm_id = yz_gm_id;
		this.gm_name = gm_name;
		this.gm_img = gm_img;
		this.gm_server = gm_server;
		this.gm_pattern = gm_pattern;
		this.gm_grade = gm_grade;
		this.gm_position = gm_position;
		this.create_gm_time = create_gm_time;
	}
	public int getYz_gm_id() {
		return yz_gm_id;
	}
	public void setYz_gm_id(int yz_gm_id) {
		this.yz_gm_id = yz_gm_id;
	}
	public String getGm_name() {
		return gm_name;
	}
	public void setGm_name(String gm_name) {
		this.gm_name = gm_name;
	}
	public String getGm_img() {
		return gm_img;
	}
	public void setGm_img(String gm_img) {
		this.gm_img = gm_img;
	}
	public List<String> getGm_server() {
		return gm_server;
	}
	public void setGm_server(List<String> gm_server) {
		this.gm_server = gm_server;
	}
	public List<String> getGm_pattern() {
		return gm_pattern;
	}
	public void setGm_pattern(List<String> gm_pattern) {
		this.gm_pattern = gm_pattern;
	}
	public List<String> getGm_grade() {
		return gm_grade;
	}
	public void setGm_grade(List<String> gm_grade) {
		this.gm_grade = gm_grade;
	}
	public List<String> getGm_position() {
		return gm_position;
	}
	public void setGm_position(List<String> gm_position) {
		this.gm_position = gm_position;
	}
	public String getCreate_gm_time() {
		return create_gm_time;
	}
	public void setCreate_gm_time(String create_gm_time) {
		this.create_gm_time = create_gm_time;
	}
	@Override
	public String toString() {
		return "Game_rt_DataEntity [yz_gm_id=" + yz_gm_id + ", gm_name="
				+ gm_name + ", gm_img=" + gm_img + ", gm_server=" + gm_server
				+ ", gm_pattern=" + gm_pattern + ", gm_grade=" + gm_grade
				+ ", gm_position=" + gm_position + ", create_gm_time="
				+ create_gm_time + "]";
	}

	
	
	
	
	
	
	
	
	
}
