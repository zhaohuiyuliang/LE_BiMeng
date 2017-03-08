package com.jishang.bimeng.entity.yuezhan.zf;

import java.io.Serializable;

public class Zf_llistEntity implements Serializable{
	private int status;
	private String status_code;
	private Zf_list_DataEntity data;
	
	
	
	public Zf_llistEntity() {
		super();
	}
	public Zf_llistEntity(int status, String status_code,
			Zf_list_DataEntity data) {
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
	public Zf_list_DataEntity getData() {
		return data;
	}
	public void setData(Zf_list_DataEntity data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "Zf_llistEntity [status=" + status + ", status_code="
				+ status_code + ", data=" + data + "]";
	}
	
	
	
}
