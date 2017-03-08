package com.jishang.bimeng.entity.yuezhan.zf;

import java.io.Serializable;

public class Zf_threeEntity implements Serializable{
	private int status;
	private String status_code;
	private String pay;
	private String errors;
	private ZfthreeDataEntity data;
	
	
	
	
	public Zf_threeEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Zf_threeEntity(int status, String status_code, String pay,
			String errors, ZfthreeDataEntity data) {
		super();
		this.status = status;
		this.status_code = status_code;
		this.pay = pay;
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
	public String getPay() {
		return pay;
	}
	public void setPay(String pay) {
		this.pay = pay;
	}
	public String getErrors() {
		return errors;
	}
	public void setErrors(String errors) {
		this.errors = errors;
	}
	public ZfthreeDataEntity getData() {
		return data;
	}
	public void setData(ZfthreeDataEntity data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "Zf_threeEntity [status=" + status + ", status_code="
				+ status_code + ", pay=" + pay + ", errors=" + errors
				+ ", data=" + data + "]";
	}
	
	
	
	
	

}
