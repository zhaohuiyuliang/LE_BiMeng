package com.jishang.bimeng.entity.wode;

import java.io.Serializable;

public class PhotoEntity implements Serializable{
	private int status;
	private String status_code;
	private Photo_dataEntity data;
	
	
	
	
	
	
	
	public PhotoEntity() {
		super();
	}
	public PhotoEntity(int status, String status_code, Photo_dataEntity data) {
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
	public Photo_dataEntity getData() {
		return data;
	}
	public void setData(Photo_dataEntity data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "PhotoEntity [status=" + status + ", status_code=" + status_code
				+ ", data=" + data + "]";
	}
	
	
	
	

}
