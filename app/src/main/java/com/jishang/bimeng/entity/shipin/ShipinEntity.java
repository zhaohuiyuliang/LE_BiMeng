package com.jishang.bimeng.entity.shipin;

import java.io.Serializable;
import java.util.List;

public class ShipinEntity implements Serializable{
	private int status;
	private String status_code;
	private String errors;
	private Shipin_paEntity pagination;
	private  List<Shipin_dataEntity> data;
	
	
	
	
	
	
	
	
	public ShipinEntity() {
		super();
	}
	public ShipinEntity(int status, String status_code, String errors,
			Shipin_paEntity pagination, List<Shipin_dataEntity> data) {
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
	public Shipin_paEntity getPagination() {
		return pagination;
	}
	public void setPagination(Shipin_paEntity pagination) {
		this.pagination = pagination;
	}
	public List<Shipin_dataEntity> getData() {
		return data;
	}
	public void setData(List<Shipin_dataEntity> data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "ShipinEntity [status=" + status + ", status_code="
				+ status_code + ", errors=" + errors + ", pagination="
				+ pagination + ", data=" + data + "]";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	
	

}
