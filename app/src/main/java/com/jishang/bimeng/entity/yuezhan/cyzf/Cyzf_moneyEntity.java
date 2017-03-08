package com.jishang.bimeng.entity.yuezhan.cyzf;

public class Cyzf_moneyEntity {
	private String id;
	private String min_money;
	private String max_money;
	private String app_money;
	
	
	
	public Cyzf_moneyEntity() {
		super();
	}
	public Cyzf_moneyEntity(String id, String min_money, String max_money,
			String app_money) {
		super();
		this.id = id;
		this.min_money = min_money;
		this.max_money = max_money;
		this.app_money = app_money;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMin_money() {
		return min_money;
	}
	public void setMin_money(String min_money) {
		this.min_money = min_money;
	}
	public String getMax_money() {
		return max_money;
	}
	public void setMax_money(String max_money) {
		this.max_money = max_money;
	}
	public String getApp_money() {
		return app_money;
	}
	public void setApp_money(String app_money) {
		this.app_money = app_money;
	}
	@Override
	public String toString() {
		return "Cyzf_moneyEntity [id=" + id + ", min_money=" + min_money
				+ ", max_money=" + max_money + ", app_money=" + app_money + "]";
	}
	

	
	
	
}
