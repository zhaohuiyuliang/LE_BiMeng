package com.jishang.bimeng.entity.hongbao.dian;

import java.io.Serializable;

public class DianEntity implements Serializable{

	private int status;
	private String status_code;
	private Dian_dataEntity data;
	
	
	
	
	public DianEntity() {
		super();
	}
	public DianEntity(int status, String status_code, Dian_dataEntity data) {
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
	public Dian_dataEntity getData() {
		return data;
	}
	public void setData(Dian_dataEntity data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "DianEntity [status=" + status + ", status_code=" + status_code
				+ ", data=" + data + "]";
	}
	
	
}
