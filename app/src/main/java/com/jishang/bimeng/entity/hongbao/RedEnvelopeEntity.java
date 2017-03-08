package com.jishang.bimeng.entity.hongbao;

import java.io.Serializable;
import java.util.List;

public class RedEnvelopeEntity implements Serializable{
	private int status;
	private String status_code;
    List<Hongbao_dataEntity> data;
    
    
    
    
    
    
    
    
	public RedEnvelopeEntity() {
		super();
	}
	public RedEnvelopeEntity(int status, String status_code,
			List<Hongbao_dataEntity> data) {
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
	public List<Hongbao_dataEntity> getData() {
		return data;
	}
	public void setData(List<Hongbao_dataEntity> data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "HongbaoEntity [status=" + status + ", status_code="
				+ status_code + ", data=" + data + "]";
	}
    
    
}
