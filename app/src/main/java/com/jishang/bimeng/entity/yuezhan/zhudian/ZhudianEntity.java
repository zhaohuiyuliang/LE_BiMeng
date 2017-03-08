package com.jishang.bimeng.entity.yuezhan.zhudian;

import java.io.Serializable;

public class ZhudianEntity implements Serializable{
	private int status;
	private String status_code;
	private ZhudianDataEntity data;
	
	
	
	
	
	
	
	
	
	public ZhudianEntity() {
		super();
	}
	public ZhudianEntity(int status, String status_code, ZhudianDataEntity data) {
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
	public ZhudianDataEntity getData() {
		return data;
	}
	public void setData(ZhudianDataEntity data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "ZhudianEntity [status=" + status + ", status_code="
				+ status_code + ", data=" + data + "]";
	}
	
	

}
