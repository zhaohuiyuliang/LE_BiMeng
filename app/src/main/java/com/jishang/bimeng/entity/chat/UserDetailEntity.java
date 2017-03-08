package com.jishang.bimeng.entity.chat;

import java.io.Serializable;
import java.util.List;

public class UserDetailEntity implements Serializable{
	private int status;
	private String status_code;
	private String errors;
	private UserDetail_dataEntity data;
	private String msg;
	
	
	
	
	public UserDetailEntity() {
		super();
	}
	public UserDetailEntity(int status, String status_code, String errors,
			UserDetail_dataEntity data, String msg) {
		super();
		this.status = status;
		this.status_code = status_code;
		this.errors = errors;
		this.data = data;
		this.msg = msg;
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
	public String getErrors() {
		return errors;
	}
	public void setErrors(String errors) {
		this.errors = errors;
	}
	public UserDetail_dataEntity getData() {
		return data;
	}
	public void setData(UserDetail_dataEntity data) {
		this.data = data;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	@Override
	public String toString() {
		return "UserDetailEntity [status=" + status + ", status_code="
				+ status_code + ", errors=" + errors + ", data=" + data
				+ ", msg=" + msg + "]";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
}
