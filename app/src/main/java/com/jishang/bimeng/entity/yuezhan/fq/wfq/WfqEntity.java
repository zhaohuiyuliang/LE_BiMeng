package com.jishang.bimeng.entity.yuezhan.fq.wfq;

import java.io.Serializable;
import java.util.List;

public class WfqEntity implements Serializable{
	private int status;
	private String status_code;
	List<Wfq_DataEntity> data;
	
	
	public WfqEntity() {
		super();
	}
	public WfqEntity(int status, String status_code, List<Wfq_DataEntity> data) {
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
	public List<Wfq_DataEntity> getData() {
		return data;
	}
	public void setData(List<Wfq_DataEntity> data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "WfqEntity [status=" + status + ", status_code=" + status_code
				+ ", data=" + data + "]";
	}
	

}
