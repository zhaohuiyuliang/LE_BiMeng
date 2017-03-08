package com.jishang.bimeng.entity.chat;

import java.io.Serializable;

public class UserDetail_data_PrEntity implements Serializable{
	private String id;
	private String s_provname;
	
	
	
	public UserDetail_data_PrEntity() {
		super();
	}
	public UserDetail_data_PrEntity(String id, String s_provname) {
		super();
		this.id = id;
		this.s_provname = s_provname;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getS_provname() {
		return s_provname;
	}
	public void setS_provname(String s_provname) {
		this.s_provname = s_provname;
	}
	@Override
	public String toString() {
		return "UserDetail_data_PrEntity [id=" + id + ", s_provname="
				+ s_provname + "]";
	}
	
	
	
	

}
