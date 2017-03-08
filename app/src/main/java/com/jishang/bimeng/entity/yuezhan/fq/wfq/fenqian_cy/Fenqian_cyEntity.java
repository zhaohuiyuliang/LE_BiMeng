package com.jishang.bimeng.entity.yuezhan.fq.wfq.fenqian_cy;

import java.io.Serializable;

public class Fenqian_cyEntity implements Serializable{
	private int status;
	private String status_code;
	private String errors;
	private Fenqian_cyDataEntity data;
	
	
	
	
	
	
	
	
	
	
	public Fenqian_cyEntity() {
		super();
	}
	public Fenqian_cyEntity(int status, String status_code, String errors,
			Fenqian_cyDataEntity data) {
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
	public Fenqian_cyDataEntity getData() {
		return data;
	}
	public void setData(Fenqian_cyDataEntity data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "Fenqian_cyEntity [status=" + status + ", status_code="
				+ status_code + ", errors=" + errors + ", data=" + data + "]";
	}
	
	
	
	
	
	
	
	

}
