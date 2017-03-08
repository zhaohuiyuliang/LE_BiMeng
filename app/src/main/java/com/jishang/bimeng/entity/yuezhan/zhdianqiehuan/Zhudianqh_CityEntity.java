package com.jishang.bimeng.entity.yuezhan.zhdianqiehuan;

import java.io.Serializable;

public class Zhudianqh_CityEntity implements Serializable{
	private String id;
	private String s_cityname;
	private String n_provid;
	private String s_state;
	
	
	
	
	public Zhudianqh_CityEntity() {
		super();
	}
	public Zhudianqh_CityEntity(String id, String s_cityname, String n_provid,
			String s_state) {
		super();
		this.id = id;
		this.s_cityname = s_cityname;
		this.n_provid = n_provid;
		this.s_state = s_state;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getS_cityname() {
		return s_cityname;
	}
	public void setS_cityname(String s_cityname) {
		this.s_cityname = s_cityname;
	}
	public String getN_provid() {
		return n_provid;
	}
	public void setN_provid(String n_provid) {
		this.n_provid = n_provid;
	}
	public String getS_state() {
		return s_state;
	}
	public void setS_state(String s_state) {
		this.s_state = s_state;
	}
	@Override
	public String toString() {
		return "Zhudianqh_CityEntity [id=" + id + ", s_cityname=" + s_cityname
				+ ", n_provid=" + n_provid + ", s_state=" + s_state + "]";
	}
	
	
	
	

	
	
}
