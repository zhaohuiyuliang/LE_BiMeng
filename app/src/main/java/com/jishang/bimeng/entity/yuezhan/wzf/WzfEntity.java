package com.jishang.bimeng.entity.yuezhan.wzf;

import java.io.Serializable;
import java.util.List;

public class WzfEntity implements Serializable{
	private int status;
	private String status_code;
	private List<Wzf_dataEntity> data;
	
	
	
	public WzfEntity() {
		super();
	}
	public WzfEntity(int status, String status_code, List<Wzf_dataEntity> data) {
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
	public List<Wzf_dataEntity> getData() {
		return data;
	}
	public void setData(List<Wzf_dataEntity> data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "WdhEntity [status=" + status + ", status_code=" + status_code
				+ ", data=" + data + "]";
	}
	
	

}
