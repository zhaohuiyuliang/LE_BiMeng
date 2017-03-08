package com.jishang.bimeng.entity.yuezhan.zhdianqiehuan;

import java.io.Serializable;

public class Zhudianqh_ProvinceEntity implements Serializable{
	private String id;
	private String s_provname;
	private String s_type;
	private String s_state;
	
	
	
	public Zhudianqh_ProvinceEntity() {
		super();
	}
	public Zhudianqh_ProvinceEntity(String id, String s_provname,
			String s_type, String s_state) {
		super();
		this.id = id;
		this.s_provname = s_provname;
		this.s_type = s_type;
		this.s_state = s_state;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getS_provname() {
		return s_provname;
	}
	public void setS_provname(String s_provname) {
		this.s_provname = s_provname;
	}
	public String getS_type() {
		return s_type;
	}
	public void setS_type(String s_type) {
		this.s_type = s_type;
	}
	public String getS_state() {
		return s_state;
	}
	public void setS_state(String s_state) {
		this.s_state = s_state;
	}
	@Override
	public String toString() {
		return "Zhudianqh_ProvinceEntity [id=" + id + ", s_provname="
				+ s_provname + ", s_type=" + s_type + ", s_state=" + s_state
				+ "]";
	}
	

}
