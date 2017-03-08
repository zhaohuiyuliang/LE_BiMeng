package com.jishang.bimeng.entity.wode.scwzf;

import java.io.Serializable;
import java.util.List;

public class ScwzfEntity implements Serializable{
	private int status;
	private String status_code;
	private List<Scwzf_dataEntity> data;
	
	
	
	
	
	
	public ScwzfEntity() {
		super();
	}
	public ScwzfEntity(int status, String status_code,
			List<Scwzf_dataEntity> data) {
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
	public List<Scwzf_dataEntity> getData() {
		return data;
	}
	public void setData(List<Scwzf_dataEntity> data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "ScwzfEntity [status=" + status + ", status_code=" + status_code
				+ ", data=" + data + "]";
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
