package com.jishang.bimeng.entity.dt.detail;

import java.io.Serializable;

public class Dt_dt_data_clEntity implements Serializable{
	private String ucsp_id;
	private String ucsc_id;
	private Dt_dt_data_cm_cuEntity click_users;
	
	
	
	
	
	
	
	
	
	public Dt_dt_data_clEntity() {
		super();
	}
	public Dt_dt_data_clEntity(String ucsp_id, String ucsc_id,
			Dt_dt_data_cm_cuEntity click_users) {
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
	public Dt_dt_data_cm_cuEntity getClick_users() {
		return click_users;
	}
	public void setClick_users(Dt_dt_data_cm_cuEntity click_users) {
		this.click_users = click_users;
	}
	@Override
	public String toString() {
		return "Dt_dt_data_clEntity [ucsp_id=" + ucsp_id + ", ucsc_id="
				+ ucsc_id + ", click_users=" + click_users + "]";
	}
	
	

	
	
}
