package com.jishang.bimeng.entity.regist;

import java.io.Serializable;

public class BarEntity implements Serializable {
	private String w_name;
	private String id;
	private String w_img;
	
	
	
	public BarEntity() {
		super();
	}
	public BarEntity(String w_name, String id, String w_img) {
		super();
		this.w_name = w_name;
		this.id = id;
		this.w_img = w_img;
	}
	public String getW_name() {
		return w_name;
	}
	public void setW_name(String w_name) {
		this.w_name = w_name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getW_img() {
		return w_img;
	}
	public void setW_img(String w_img) {
		this.w_img = w_img;
	}
	@Override
	public String toString() {
		return "BarEntity [w_name=" + w_name + ", id=" + id + ", w_img="
				+ w_img + "]";
	}
	

}
