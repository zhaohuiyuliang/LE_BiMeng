package com.jishang.bimeng.entity.yuezhan;

import java.io.Serializable;

public class Game_rtEntity implements Serializable{
	private int status;
	private String status_code;
	private Game_rt_DataEntity data;
	
	
	
	
	public Game_rtEntity() {
		super();
	}
	public Game_rtEntity(int status, String status_code, Game_rt_DataEntity data) {
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
	public Game_rt_DataEntity getData() {
		return data;
	}
	public void setData(Game_rt_DataEntity data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "Game_rtEntity [status=" + status + ", status_code="
				+ status_code + ", data=" + data + "]";
	}

	
	
	
	
}
