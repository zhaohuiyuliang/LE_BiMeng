package com.jishang.bimeng.entity.regist;

import java.io.Serializable;

public class YzmEntity implements Serializable{
	private int status;
	private String status_code;
	private String verify_code;
	private String errors;
	
	
	
	public YzmEntity() {
		super();
	}
	public YzmEntity(int status, String status_code, String verify_code,
			String errors) {
		super();
		this.status = status;
		this.status_code = status_code;
		this.verify_code = verify_code;
		this.errors = errors;
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
	public String getVerify_code() {
		return verify_code;
	}
	public void setVerify_code(String verify_code) {
		this.verify_code = verify_code;
	}
	public String getErrors() {
		return errors;
	}
	public void setErrors(String errors) {
		this.errors = errors;
	}
	@Override
	public String toString() {
		return "YzmEntity [status=" + status + ", status_code=" + status_code
				+ ", verify_code=" + verify_code + ", errors=" + errors + "]";
	}
	
	
	
	
	
	
	

}
