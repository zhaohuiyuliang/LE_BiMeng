package com.jishang.bimeng.entity.dt;

import java.io.Serializable;

public class Dt_pinglunEntity implements Serializable{
	private String message;
	private String username;
	private String ucmc_id;
	private String ucmp_id;
	private String uid;
	private String head_img;
	
	
	
	
	
	public Dt_pinglunEntity() {
		super();
	}
	public Dt_pinglunEntity(String message, String username, String ucmc_id,
			String ucmp_id, String uid, String head_img) {
		super();
		this.message = message;
		this.username = username;
		this.ucmc_id = ucmc_id;
		this.ucmp_id = ucmp_id;
		this.uid = uid;
		this.head_img = head_img;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUcmc_id() {
		return ucmc_id;
	}
	public void setUcmc_id(String ucmc_id) {
		this.ucmc_id = ucmc_id;
	}
	public String getUcmp_id() {
		return ucmp_id;
	}
	public void setUcmp_id(String ucmp_id) {
		this.ucmp_id = ucmp_id;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getHead_img() {
		return head_img;
	}
	public void setHead_img(String head_img) {
		this.head_img = head_img;
	}
	@Override
	public String toString() {
		return "Dt_pinglunEntity [message=" + message + ", username=" + username
				+ ", ucmc_id=" + ucmc_id + ", ucmp_id=" + ucmp_id + ", uid="
				+ uid + ", head_img=" + head_img + "]";
	}

}
