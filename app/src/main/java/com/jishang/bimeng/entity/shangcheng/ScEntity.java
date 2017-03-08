package com.jishang.bimeng.entity.shangcheng;

import java.io.Serializable;
import java.util.List;

public class ScEntity implements Serializable{
	private int status;
	private String status_code;
	private String errors;
	private Sc_paEntity pagination;
	private List<Sc_dataEntity> data;
	
	
	
	
	
	
	
	
	
	
	public ScEntity() {
		super();
	}
	public ScEntity(int status, String status_code, String errors,
			Sc_paEntity pagination, List<Sc_dataEntity> data) {
		super();
		this.status = status;
		this.status_code = status_code;
		this.errors = errors;
		this.pagination = pagination;
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
	public String getErrors() {
		return errors;
	}
	public void setErrors(String errors) {
		this.errors = errors;
	}
	public Sc_paEntity getPagination() {
		return pagination;
	}
	public void setPagination(Sc_paEntity pagination) {
		this.pagination = pagination;
	}
	public List<Sc_dataEntity> getData() {
		return data;
	}
	public void setData(List<Sc_dataEntity> data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "ScEntity [status=" + status + ", status_code=" + status_code
				+ ", errors=" + errors + ", pagination=" + pagination
				+ ", data=" + data + "]";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
