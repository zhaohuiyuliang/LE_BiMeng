package com.jishang.bimeng.entity.chat;

import java.io.Serializable;

public class UserDetail_rsEntity implements Serializable{
	private int f_status;
	
	
	
	
	

	public UserDetail_rsEntity() {
		super();
	}

	public UserDetail_rsEntity(int f_status) {
		super();
		this.f_status = f_status;
	}

	public int getF_status() {
		return f_status;
	}

	public void setF_status(int f_status) {
		this.f_status = f_status;
	}

	@Override
	public String toString() {
		return "UserDetail_rsEntity [f_status=" + f_status + "]";
	}
	
	
	
	
	

}
