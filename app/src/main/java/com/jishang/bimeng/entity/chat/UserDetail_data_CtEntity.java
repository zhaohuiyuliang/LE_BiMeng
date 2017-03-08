package com.jishang.bimeng.entity.chat;

import java.io.Serializable;

public class UserDetail_data_CtEntity implements Serializable{
	private String id;
	private String s_cityname;
	
	
	
	
	public UserDetail_data_CtEntity() {
		super();
	}
	public UserDetail_data_CtEntity(String id, String s_cityname) {
		super();
		this.id = id;
		this.s_cityname = s_cityname;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getS_cityname() {
		return s_cityname;
	}
	public void setS_cityname(String s_cityname) {
		this.s_cityname = s_cityname;
	}
	@Override
	public String toString() {
		return "UserDetail_data_CtEntity [id=" + id + ", s_cityname="
				+ s_cityname + "]";
	}

	
	
	
}
