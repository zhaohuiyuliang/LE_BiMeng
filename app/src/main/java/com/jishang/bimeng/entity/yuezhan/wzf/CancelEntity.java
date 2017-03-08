package com.jishang.bimeng.entity.yuezhan.wzf;

public class CancelEntity {
	private int status;
	private String status_code;
	private String data;
	
	
	
	public CancelEntity() {
		super();
	}
	public CancelEntity(int status, String status_code, String data) {
		super();
		this.status = status;
		this.status_code = status_code;
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
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "CancelEntity [status=" + status + ", status_code="
				+ status_code + ", data=" + data + "]";
	}
	

}
