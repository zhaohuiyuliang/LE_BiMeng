package com.jishang.bimeng.entity.wode.tixian;

import java.io.Serializable;

public class TixianEntity implements Serializable{
	private int status;
	private String status_code;
	private String errors;
	private Tixian_dataEntity data;
	
	
	
	
	
	
	
	
	public TixianEntity() {
		super();
	}
	public TixianEntity(int status, String status_code, String errors,
			Tixian_dataEntity data) {
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
	public Tixian_dataEntity getData() {
		return data;
	}
	public void setData(Tixian_dataEntity data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "TixianEntity [status=" + status + ", status_code="
				+ status_code + ", errors=" + errors + ", data=" + data + "]";
	}
	
	
	
	
	
	
	
	

	
	
	
	
}
