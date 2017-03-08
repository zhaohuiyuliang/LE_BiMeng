package com.jishang.bimeng.entity.hongbao.chai;

import java.io.Serializable;

public class Chai_qb_qbuserEntity implements Serializable{
	
	
	
	public Chai_qb_qbuserEntity() {
		super();
	}
	public Chai_qb_qbuserEntity(String username, String uid, String head_img) {
		super();
		this.username = username;
		this.uid = uid;
		this.head_img = head_img;
	}
	private String username;
	private String uid;
	private String head_img;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
		return "Chai_qb_qbuserEntity [username=" + username + ", uid=" + uid
				+ ", head_img=" + head_img + "]";
	}

	
	
}
