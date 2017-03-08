package com.jishang.bimeng.entity.dt.gs;

public class ClickEntiy {
	private String ucsp_id;
	private String ucsc_id;
	private ClickUserEntity click_users;
	
	
	
	
	public ClickEntiy() {
		super();
	}
	public ClickEntiy(String ucsp_id, String ucsc_id,
			ClickUserEntity click_users) {
		super();
		this.ucsp_id = ucsp_id;
		this.ucsc_id = ucsc_id;
		this.click_users = click_users;
	}
	public String getUcsp_id() {
		return ucsp_id;
	}
	public void setUcsp_id(String ucsp_id) {
		this.ucsp_id = ucsp_id;
	}
	public String getUcsc_id() {
		return ucsc_id;
	}
	public void setUcsc_id(String ucsc_id) {
		this.ucsc_id = ucsc_id;
	}
	public ClickUserEntity getClick_users() {
		return click_users;
	}
	public void setClick_users(ClickUserEntity click_users) {
		this.click_users = click_users;
	}
	@Override
	public String toString() {
		return "ClickEntiy [ucsp_id=" + ucsp_id + ", ucsc_id=" + ucsc_id
				+ ", click_users=" + click_users + "]";
	}
	
	
	
	
	

}
