package com.jishang.bimeng.entity.shangcheng.zhifu;

import java.io.Serializable;

public class ScFirstEntity implements Serializable{
	private int status;
	private String status_code;
	private Sc_ft_dataEntity data;
	
	
	
	
	public ScFirstEntity() {
		super();
	}
	public ScFirstEntity(int status, String status_code, Sc_ft_dataEntity data) {
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
	public Sc_ft_dataEntity getData() {
		return data;
	}
	public void setData(Sc_ft_dataEntity data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "ScFirstEntity [status=" + status + ", status_code="
				+ status_code + ", data=" + data + "]";
	}
	
	
	

}
