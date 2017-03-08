package com.jishang.bimeng.entity.chat.addlist;

import java.io.Serializable;

public class AdList_dataEntity implements Serializable{
	private String id;
	private String owner_uid;
	private String friend_uid;
	private String o_status;
	private String f_status;
	private String o_time;
	private String f_time;
	private String o_id;
	private String f_id;
	private String friend_phone;
	private String creat_at;
	private AdList_data_uEntity o_user;
	
	
	
	
	
	
	public AdList_dataEntity() {
		super();
	}
	public AdList_dataEntity(String id, String owner_uid, String friend_uid,
			String o_status, String f_status, String o_time, String f_time,
			String o_id, String f_id, String friend_phone, String creat_at,
			AdList_data_uEntity o_user) {
		super();
		this.id = id;
		this.owner_uid = owner_uid;
		this.friend_uid = friend_uid;
		this.o_status = o_status;
		this.f_status = f_status;
		this.o_time = o_time;
		this.f_time = f_time;
		this.o_id = o_id;
		this.f_id = f_id;
		this.friend_phone = friend_phone;
		this.creat_at = creat_at;
		this.o_user = o_user;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOwner_uid() {
		return owner_uid;
	}
	public void setOwner_uid(String owner_uid) {
		this.owner_uid = owner_uid;
	}
	public String getFriend_uid() {
		return friend_uid;
	}
	public void setFriend_uid(String friend_uid) {
		this.friend_uid = friend_uid;
	}
	public String getO_status() {
		return o_status;
	}
	public void setO_status(String o_status) {
		this.o_status = o_status;
	}
	public String getF_status() {
		return f_status;
	}
	public void setF_status(String f_status) {
		this.f_status = f_status;
	}
	public String getO_time() {
		return o_time;
	}
	public void setO_time(String o_time) {
		this.o_time = o_time;
	}
	public String getF_time() {
		return f_time;
	}
	public void setF_time(String f_time) {
		this.f_time = f_time;
	}
	public String getO_id() {
		return o_id;
	}
	public void setO_id(String o_id) {
		this.o_id = o_id;
	}
	public String getF_id() {
		return f_id;
	}
	public void setF_id(String f_id) {
		this.f_id = f_id;
	}
	public String getFriend_phone() {
		return friend_phone;
	}
	public void setFriend_phone(String friend_phone) {
		this.friend_phone = friend_phone;
	}
	public String getCreat_at() {
		return creat_at;
	}
	public void setCreat_at(String creat_at) {
		this.creat_at = creat_at;
	}
	public AdList_data_uEntity getO_user() {
		return o_user;
	}
	public void setO_user(AdList_data_uEntity o_user) {
		this.o_user = o_user;
	}
	@Override
	public String toString() {
		return "AdList_dataEntity [id=" + id + ", owner_uid=" + owner_uid
				+ ", friend_uid=" + friend_uid + ", o_status=" + o_status
				+ ", f_status=" + f_status + ", o_time=" + o_time + ", f_time="
				+ f_time + ", o_id=" + o_id + ", f_id=" + f_id
				+ ", friend_phone=" + friend_phone + ", creat_at=" + creat_at
				+ ", o_user=" + o_user + "]";
	}

	
	
	
}
