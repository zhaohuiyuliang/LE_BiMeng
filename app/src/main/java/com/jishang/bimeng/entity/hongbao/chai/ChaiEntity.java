package com.jishang.bimeng.entity.hongbao.chai;

import java.io.Serializable;

public class ChaiEntity implements Serializable{
	private int status;
	private String status_code;
	private Chai_qoEntity q_one;
	private String mst;
	private String errors;
	
	
	
	
	
	
	
	public ChaiEntity() {
		super();
	}
	public ChaiEntity(int status, String status_code, Chai_qoEntity q_one,
			String mst, String errors) {
		super();
		this.status = status;
		this.status_code = status_code;
		this.q_one = q_one;
		this.mst = mst;
		this.errors = errors;
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
	public Chai_qoEntity getQ_one() {
		return q_one;
	}
	public void setQ_one(Chai_qoEntity q_one) {
		this.q_one = q_one;
	}
	public String getMst() {
		return mst;
	}
	public void setMst(String mst) {
		this.mst = mst;
	}
	public String getErrors() {
		return errors;
	}
	public void setErrors(String errors) {
		this.errors = errors;
	}
	@Override
	public String toString() {
		return "ChaiEntity [status=" + status + ", status_code=" + status_code
				+ ", q_one=" + q_one + ", mst=" + mst + ", errors=" + errors
				+ "]";
	}

	
	
	
}
