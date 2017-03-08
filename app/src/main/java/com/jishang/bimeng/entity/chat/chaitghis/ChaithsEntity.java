package com.jishang.bimeng.entity.chat.chaitghis;

import java.io.Serializable;
import java.util.List;

public class ChaithsEntity implements Serializable {
	private int status;
	private int status_code;
	private String errors;
	private Chaiths_dataEntity data;
	
	
	
	
	
	
	public ChaithsEntity() {
		super();
	}
	public ChaithsEntity(int status, int status_code, String errors,
			Chaiths_dataEntity data) {
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
	public int getStatus_code() {
		return status_code;
	}
	public void setStatus_code(int status_code) {
		this.status_code = status_code;
	}
	public String getErrors() {
		return errors;
	}
	public void setErrors(String errors) {
		this.errors = errors;
	}
	public Chaiths_dataEntity getData() {
		return data;
	}
	public void setData(Chaiths_dataEntity data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "ChaithsEntity [status=" + status + ", status_code="
				+ status_code + ", errors=" + errors + ", data=" + data + "]";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	
	
	
	
}
