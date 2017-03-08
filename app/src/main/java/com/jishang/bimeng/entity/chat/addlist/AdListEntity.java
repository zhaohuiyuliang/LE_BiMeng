package com.jishang.bimeng.entity.chat.addlist;

import java.io.Serializable;
import java.util.List;

public class AdListEntity implements Serializable{
	private int status;
	private String status_code;
	private String errors;
	private List<AdList_dataEntity> data;
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public AdListEntity() {
		super();
	}
	public AdListEntity(int status, String status_code, String errors,
			List<AdList_dataEntity> data) {
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
	public List<AdList_dataEntity> getData() {
		return data;
	}
	public void setData(List<AdList_dataEntity> data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "AdListEntity [status=" + status + ", status_code="
				+ status_code + ", errors=" + errors + ", data=" + data + "]";
	}
	
	
	
	
	

	
	
	
}
