package com.jishang.bimeng.entity.chat;

import java.io.Serializable;

/**
 * @author kangming
 * 解析添加好友后的实体类
 *
 */
public class AddFriendEntity implements Serializable{
	private int status;
	private String status_code;
	private String owner_username;
	private String friend_username;
	private String o_id;
	private String o_status;
	
	
	
	public AddFriendEntity() {
		super();
	}
	public AddFriendEntity(int status, String status_code,
			String owner_username, String friend_username, String o_id,
			String o_status) {
		super();
		this.status = status;
		this.status_code = status_code;
		this.owner_username = owner_username;
		this.friend_username = friend_username;
		this.o_id = o_id;
		this.o_status = o_status;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getStatus_code() {
		return status_code;
	}
	public void setStatus_code(String status_code) {
		this.status_code = status_code;
	}
	public String getOwner_username() {
		return owner_username;
	}
	public void setOwner_username(String owner_username) {
		this.owner_username = owner_username;
	}
	public String getFriend_username() {
		return friend_username;
	}
	public void setFriend_username(String friend_username) {
		this.friend_username = friend_username;
	}
	public String getO_id() {
		return o_id;
	}
	public void setO_id(String o_id) {
		this.o_id = o_id;
	}
	public String getO_status() {
		return o_status;
	}
	public void setO_status(String o_status) {
		this.o_status = o_status;
	}
	@Override
	public String toString() {
		return "AddFriendEntity [status=" + status + ", status_code="
				+ status_code + ", owner_username=" + owner_username
				+ ", friend_username=" + friend_username + ", o_id=" + o_id
				+ ", o_status=" + o_status + "]";
	}
	

}
