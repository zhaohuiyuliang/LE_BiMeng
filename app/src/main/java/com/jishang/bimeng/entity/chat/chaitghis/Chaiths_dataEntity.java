package com.jishang.bimeng.entity.chat.chaitghis;

import java.io.Serializable;

public class Chaiths_dataEntity implements Serializable{
	private String username;
	private String head_img;
	private String h_username;
	
	
	
	
	
	
	
	
	public Chaiths_dataEntity() {
		super();
	}
	public Chaiths_dataEntity(String username, String head_img,
			String h_username) {
		super();
		this.username = username;
		this.head_img = head_img;
		this.h_username = h_username;
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
	public String getH_username() {
		return h_username;
	}
	public void setH_username(String h_username) {
		this.h_username = h_username;
	}
	@Override
	public String toString() {
		return "Chaiths_dataEntity [username=" + username + ", head_img="
				+ head_img + ", h_username=" + h_username + "]";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	
	
	
	
}
