package com.jishang.bimeng.entity.shangcheng.goumai;

import java.io.Serializable;

public class GoumaiEntity implements Serializable{
	private int status;
	private String status_code;
	private GoumaiDataEntity data;
	
	
	
	
	
	public GoumaiEntity() {
		super();
	}
	public GoumaiEntity(int status, String status_code, GoumaiDataEntity data) {
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
	public GoumaiDataEntity getData() {
		return data;
	}
	public void setData(GoumaiDataEntity data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "GoumaiEntity [status=" + status + ", status_code="
				+ status_code + ", data=" + data + "]";
	}
	

}
