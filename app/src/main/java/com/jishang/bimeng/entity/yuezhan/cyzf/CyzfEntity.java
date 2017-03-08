package com.jishang.bimeng.entity.yuezhan.cyzf;

import java.util.List;

public class CyzfEntity {
	private int status;
	private String status_code;
	private List<Cyzf_PpEntity> peple;
	private List<Cyzf_moneyEntity> money;
	private List<Cyzf_wtEntity> waittime;
	private List<Cyzf_ctEntity> continuetime;
	
	
	
	
	public CyzfEntity() {
		super();
	}
	public CyzfEntity(int status, String status_code,
			List<Cyzf_PpEntity> peple, List<Cyzf_moneyEntity> money,
			List<Cyzf_wtEntity> waittime, List<Cyzf_ctEntity> continuetime) {
		super();
		this.status = status;
		this.status_code = status_code;
		this.peple = peple;
		this.money = money;
		this.waittime = waittime;
		this.continuetime = continuetime;
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
	public List<Cyzf_PpEntity> getPeple() {
		return peple;
	}
	public void setPeple(List<Cyzf_PpEntity> peple) {
		this.peple = peple;
	}
	public List<Cyzf_moneyEntity> getMoney() {
		return money;
	}
	public void setMoney(List<Cyzf_moneyEntity> money) {
		this.money = money;
	}
	public List<Cyzf_wtEntity> getWaittime() {
		return waittime;
	}
	public void setWaittime(List<Cyzf_wtEntity> waittime) {
		this.waittime = waittime;
	}
	public List<Cyzf_ctEntity> getContinuetime() {
		return continuetime;
	}
	public void setContinuetime(List<Cyzf_ctEntity> continuetime) {
		this.continuetime = continuetime;
	}
	@Override
	public String toString() {
		return "CyzfEntity [status=" + status + ", status_code=" + status_code
				+ ", peple=" + peple + ", money=" + money + ", waittime="
				+ waittime + ", continuetime=" + continuetime + "]";
	}
	

}
