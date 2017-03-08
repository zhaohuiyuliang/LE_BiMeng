package com.jishang.bimeng.entity.yuezhan.confirm;

import java.io.Serializable;


public class ConfirmEntity implements Serializable{
	private int status;
	private String status_code;
	private int error_code;
	private String errors;
	private C_DataEntity data;
	
	
	
	
	
	public ConfirmEntity() {
		super();
	}
	public ConfirmEntity(int status, String status_code, int error_code,
			String errors, C_DataEntity data) {
		super();
		this.status = status;
		this.status_code = status_code;
		this.error_code = error_code;
		this.errors = errors;
		this.data = data;
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
	public int getError_code() {
		return error_code;
	}
	public void setError_code(int error_code) {
		this.error_code = error_code;
	}
	public String getErrors() {
		return errors;
	}
	public void setErrors(String errors) {
		this.errors = errors;
	}
	public C_DataEntity getData() {
		return data;
	}
	public void setData(C_DataEntity data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "ConfirmEntity [status=" + status + ", status_code="
				+ status_code + ", error_code=" + error_code + ", errors="
				+ errors + ", data=" + data + "]";
	}
	
	
	
	

	
	
	
}
