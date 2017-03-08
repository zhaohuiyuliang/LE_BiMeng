package com.jishang.bimeng.entity.yuewan.fq;

import java.io.Serializable;
import java.util.List;

public class Yw_fqEntity implements Serializable{

	private int status;
	private String status_code;
	List<Yw_fqDataEntity> data;
	
	
	
	
	
	
	
	
	
	public Yw_fqEntity() {
		super();
	}
	public Yw_fqEntity(int status, String status_code,
			List<Yw_fqDataEntity> data) {
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
	public List<Yw_fqDataEntity> getData() {
		return data;
	}
	public void setData(List<Yw_fqDataEntity> data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "Yw_fqEntity [status=" + status + ", status_code=" + status_code
				+ ", data=" + data + "]";
	}
	
	
	
}
