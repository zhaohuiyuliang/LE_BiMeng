package com.jishang.bimeng.entity.yuezhan.yzlist;

import java.io.Serializable;

public class List_data_bsEntity implements Serializable{
	private String id;
	private String w_name;
	
	
	
	
	
	
	
	
	
	
	public List_data_bsEntity() {
		super();
	}
	public List_data_bsEntity(String id, String w_name) {
		super();
		this.id = id;
		this.w_name = w_name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getW_name() {
		return w_name;
	}
	public void setW_name(String w_name) {
		this.w_name = w_name;
	}
	@Override
	public String toString() {
		return "List_data_bsEntity [id=" + id + ", w_name=" + w_name + "]";
	}

	
	
	
	
	
	
}
