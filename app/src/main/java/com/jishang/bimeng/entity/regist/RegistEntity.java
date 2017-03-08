package com.jishang.bimeng.entity.regist;

import java.io.Serializable;

public class RegistEntity implements Serializable {
	/**
	 * 
	 */
	private int status;
	private String status_code;
	private String errors;
	private Regist_dataEntity data;
	
	
	
	
	public RegistEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public RegistEntity(int status, String status_code, String errors,
			Regist_dataEntity data) {
		super();
		this.status = status;
		this.status_code = status_code;
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
	public String getErrors() {
		return errors;
	}
	public void setErrors(String errors) {
		this.errors = errors;
	}
	public Regist_dataEntity getData() {
		return data;
	}
	public void setData(Regist_dataEntity data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "RegistEntity [status=" + status + ", status_code="
				+ status_code + ", errors=" + errors + ", data=" + data + "]";
	}
	
	
	
	

}
