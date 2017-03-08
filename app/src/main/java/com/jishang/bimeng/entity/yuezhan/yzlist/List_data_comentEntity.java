package com.jishang.bimeng.entity.yuezhan.yzlist;

import java.io.Serializable;

public class List_data_comentEntity implements Serializable{
	private String yzc_id;
	private String yzc_uid;
	private String yzf_uid;
	private String c_attribute;
	private String yzc_yzid;
	private String content;
	private String c_status;
	private String yzc_time;
	private List_data_coment_userEntity comment_user;
	
	
	
	
	
	public List_data_comentEntity() {
		super();
	}
	public List_data_comentEntity(String yzc_id, String yzc_uid,
			String yzf_uid, String c_attribute, String yzc_yzid,
			String content, String c_status, String yzc_time,
			List_data_coment_userEntity comment_user) {
		super();
		this.yzc_id = yzc_id;
		this.yzc_uid = yzc_uid;
		this.yzf_uid = yzf_uid;
		this.c_attribute = c_attribute;
		this.yzc_yzid = yzc_yzid;
		this.content = content;
		this.c_status = c_status;
		this.yzc_time = yzc_time;
		this.comment_user = comment_user;
	}
	public String getYzc_id() {
		return yzc_id;
	}
	public void setYzc_id(String yzc_id) {
		this.yzc_id = yzc_id;
	}
	public String getYzc_uid() {
		return yzc_uid;
	}
	public void setYzc_uid(String yzc_uid) {
		this.yzc_uid = yzc_uid;
	}
	public String getYzf_uid() {
		return yzf_uid;
	}
	public void setYzf_uid(String yzf_uid) {
		this.yzf_uid = yzf_uid;
	}
	public String getC_attribute() {
		return c_attribute;
	}
	public void setC_attribute(String c_attribute) {
		this.c_attribute = c_attribute;
	}
	public String getYzc_yzid() {
		return yzc_yzid;
	}
	public void setYzc_yzid(String yzc_yzid) {
		this.yzc_yzid = yzc_yzid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getC_status() {
		return c_status;
	}
	public void setC_status(String c_status) {
		this.c_status = c_status;
	}
	public String getYzc_time() {
		return yzc_time;
	}
	public void setYzc_time(String yzc_time) {
		this.yzc_time = yzc_time;
	}
	public List_data_coment_userEntity getComment_user() {
		return comment_user;
	}
	public void setComment_user(List_data_coment_userEntity comment_user) {
		this.comment_user = comment_user;
	}
	@Override
	public String toString() {
		return "List_data_comentEntity [yzc_id=" + yzc_id + ", yzc_uid="
				+ yzc_uid + ", yzf_uid=" + yzf_uid + ", c_attribute="
				+ c_attribute + ", yzc_yzid=" + yzc_yzid + ", content="
				+ content + ", c_status=" + c_status + ", yzc_time=" + yzc_time
				+ ", comment_user=" + comment_user + "]";
	}
	
	
	
	

}
