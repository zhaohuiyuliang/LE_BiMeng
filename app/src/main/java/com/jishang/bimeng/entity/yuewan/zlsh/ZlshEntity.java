package com.jishang.bimeng.entity.yuewan.zlsh;

import java.io.Serializable;
import java.util.List;

public class ZlshEntity implements Serializable{
	private int status;
	private String status_code;
	private List<Zlsh_dataEntity> data;
	
	
	
	
	
	
	
	
	public ZlshEntity() {
		super();
	}
	public ZlshEntity(int status, String status_code, List<Zlsh_dataEntity> data) {
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
	public List<Zlsh_dataEntity> getData() {
		return data;
	}
	public void setData(List<Zlsh_dataEntity> data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "ZlshEntity [status=" + status + ", status_code=" + status_code
				+ ", data=" + data + "]";
	}

}
