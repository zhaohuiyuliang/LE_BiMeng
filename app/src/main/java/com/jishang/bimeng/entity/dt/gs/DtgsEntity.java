package com.jishang.bimeng.entity.dt.gs;

import java.util.List;

public class DtgsEntity {
	private String status;
	private String status_code;
	private List<UserCotentEntity> user_content;
	private List<Dt_dataEntity> data;
	
	
	
	
	public DtgsEntity() {
		super();
	}
	public DtgsEntity(String status, String status_code,
			List<UserCotentEntity> user_content, List<Dt_dataEntity> data) {
		super();
		this.status = status;
		this.status_code = status_code;
		this.user_content = user_content;
		this.data = data;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatus_code() {
		return status_code;
	}
	public void setStatus_code(String status_code) {
		this.status_code = status_code;
	}
	public List<UserCotentEntity> getUser_content() {
		return user_content;
	}
	public void setUser_content(List<UserCotentEntity> user_content) {
		this.user_content = user_content;
	}
	public List<Dt_dataEntity> getData() {
		return data;
	}
	public void setData(List<Dt_dataEntity> data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "DtgsEntity [status=" + status + ", status_code=" + status_code
				+ ", user_content=" + user_content + ", data=" + data + "]";
	}
	
	
	

	
	
}
