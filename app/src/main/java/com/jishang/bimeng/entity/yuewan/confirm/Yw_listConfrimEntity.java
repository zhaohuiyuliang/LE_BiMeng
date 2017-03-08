package com.jishang.bimeng.entity.yuewan.confirm;

import java.io.Serializable;

public class Yw_listConfrimEntity implements Serializable{
	private int status;
	private String status_code;
	private String errors;
	private Yw_listConfrim_dataEntity data;
	
	
	
	
	
	public Yw_listConfrimEntity() {
		super();
	}
	public Yw_listConfrimEntity(int status, String status_code, String errors,
			Yw_listConfrim_dataEntity data) {
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
	public Yw_listConfrim_dataEntity getData() {
		return data;
	}
	public void setData(Yw_listConfrim_dataEntity data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "Yw_listConfrimEntity [status=" + status + ", status_code="
				+ status_code + ", errors=" + errors + ", data=" + data + "]";
	}
	
	
	
	
	
	
	
	
	

}
