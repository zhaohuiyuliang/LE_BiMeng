package com.jishang.bimeng.entity.yuezhan.yzlist;

import java.io.Serializable;

public class List_data_coment_userEntity implements Serializable{
	private String username;
	private String head_img;
	private String uid;
	
	
	
	
	
	
	
	
	public List_data_coment_userEntity() {
		super();
	}
	public List_data_coment_userEntity(String username, String head_img,
			String uid) {
		super();
		this.username = username;
		this.head_img = head_img;
		this.uid = uid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getHead_img() {
		return head_img;
	}
	public void setHead_img(String head_img) {
		this.head_img = head_img;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	@Override
	public String toString() {
		return "List_data_coment_userEntity [username=" + username
				+ ", head_img=" + head_img + ", uid=" + uid + "]";
	}

	
	
	
}