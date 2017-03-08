package com.jishang.bimeng.entity.yuezhan.zf.zfb;

import java.io.Serializable;

public class ZfbEntity implements Serializable{
	private int status;
	private String status_code;
	private String pay;
	private Zfb_dataEntity data;
	private String errors;
	
	
	
	
	
	
	public ZfbEntity() {
		super();
	}
	public ZfbEntity(int status, String status_code, String pay,
			Zfb_dataEntity data, String errors) {
		super();
		this.status = status;
		this.status_code = status_code;
		this.pay = pay;
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
	public String getPay() {
		return pay;
	}
	public void setPay(String pay) {
		this.pay = pay;
	}
	public Zfb_dataEntity getData() {
		return data;
	}
	public void setData(Zfb_dataEntity data) {
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
		return "ZfbEntity [status=" + status + ", status_code=" + status_code
				+ ", pay=" + pay + ", data=" + data + ", errors=" + errors
				+ "]";
	}
	
	
	
	
	
	
	
	
	
	
	

}
