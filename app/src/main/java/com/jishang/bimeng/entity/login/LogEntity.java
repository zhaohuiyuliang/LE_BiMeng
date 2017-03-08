package com.jishang.bimeng.entity.login;

import java.io.Serializable;

/**
 * @author kangming 登录实体类
 *
 */
public class LogEntity implements Serializable {
	private int status;
	private String status_code;
	private String errors;
	private Log_dataEntity data;
	
	
	
	
	
	public LogEntity() {
		super();
	}
	public LogEntity(int status, String status_code, String errors,
			Log_dataEntity data) {
		super();
		this.status = status;
		this.status_code = status_code;
		this.errors = errors;
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
	public Log_dataEntity getData() {
		return data;
	}
	public void setData(Log_dataEntity data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "LogEntity [status=" + status + ", status_code=" + status_code
				+ ", errors=" + errors + ", data=" + data + "]";
	}
	
	
	
	
	

	
	
	

}
