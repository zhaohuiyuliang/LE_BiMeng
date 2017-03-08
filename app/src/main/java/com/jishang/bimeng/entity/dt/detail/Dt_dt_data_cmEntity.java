package com.jishang.bimeng.entity.dt.detail;

import java.io.Serializable;

public class Dt_dt_data_cmEntity implements Serializable{
	private String ucmc_id;
	private String message;
	private String ucmp_id;
	private Dt_dt_data_cm_usEntity comment_users;
	
	
	
	
	
	
	
	
	
	
	
	public Dt_dt_data_cmEntity() {
		super();
	}
	public Dt_dt_data_cmEntity(String ucmc_id, String message, String ucmp_id,
			Dt_dt_data_cm_usEntity comment_users) {
		super();
		this.ucmc_id = ucmc_id;
		this.message = message;
		this.ucmp_id = ucmp_id;
		this.comment_users = comment_users;
	}
	public String getUcmc_id() {
		return ucmc_id;
	}
	public void setUcmc_id(String ucmc_id) {
		this.ucmc_id = ucmc_id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getUcmp_id() {
		return ucmp_id;
	}
	public void setUcmp_id(String ucmp_id) {
		this.ucmp_id = ucmp_id;
	}
	public Dt_dt_data_cm_usEntity getComment_users() {
		return comment_users;
	}
	public void setComment_users(Dt_dt_data_cm_usEntity comment_users) {
		this.comment_users = comment_users;
	}
	@Override
	public String toString() {
		return "Dt_dt_data_cmEntity [ucmc_id=" + ucmc_id + ", message="
				+ message + ", ucmp_id=" + ucmp_id + ", comment_users="
				+ comment_users + "]";
	}

	
	
	
}
