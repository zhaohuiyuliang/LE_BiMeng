package com.jishang.bimeng.entity.chat;

import java.io.Serializable;

public class UserDetail_data_BsEntity implements Serializable{
	private String id;
	private String w_name;
	
	
	
	
	public UserDetail_data_BsEntity() {
		super();
	}
	public UserDetail_data_BsEntity(String id, String w_name) {
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
		return "UserDetail_data_BsEntity [id=" + id + ", w_name=" + w_name
				+ "]";
	}

	
	
	
}
