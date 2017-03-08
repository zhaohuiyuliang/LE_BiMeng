package com.jishang.bimeng.entity.yuezhan.lianxi;

import java.io.Serializable;
import java.util.List;

public class LianxiEntity implements Serializable{
	private int status;
	private String status_code;
	private List<Lianxi_dataEntity> data;
	private String errors;
	
	
	
	public LianxiEntity() {
		super();
	}
	public LianxiEntity(int status, String status_code,
			List<Lianxi_dataEntity> data, String errors) {
		super();
		this.status = status;
		this.status_code = status_code;
		this.data = data;
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
	public List<Lianxi_dataEntity> getData() {
		return data;
	}
	public void setData(List<Lianxi_dataEntity> data) {
		this.data = data;
	}
	public String getErrors() {
		return errors;
	}
	public void setErrors(String errors) {
		this.errors = errors;
	}
	@Override
	public String toString() {
		return "LianxiEntity [status=" + status + ", status_code="
				+ status_code + ", data=" + data + ", errors=" + errors + "]";
	}
	
	
	
	
	

}
