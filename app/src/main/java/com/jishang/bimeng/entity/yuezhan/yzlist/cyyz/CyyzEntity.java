package com.jishang.bimeng.entity.yuezhan.yzlist.cyyz;

import java.io.Serializable;

public class CyyzEntity implements Serializable{
	private int status;
	private String status_code;
	private String errors;
	private Cyyz_dataEntity data;
	
	
	
	public CyyzEntity() {
		super();
	}
	public CyyzEntity(int status, String status_code, String errors,
			Cyyz_dataEntity data) {
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
	public Cyyz_dataEntity getData() {
		return data;
	}
	public void setData(Cyyz_dataEntity data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "CyyzEntity [status=" + status + ", status_code=" + status_code
				+ ", errors=" + errors + ", data=" + data + "]";
	}
	
	
	
	

	
	
	
}
