package com.jishang.bimeng.entity.regist;

import java.io.Serializable;

public class Regist_cityEntity implements Serializable{
	private String id;
	private String s_cityname;
	private String n_provid;
	
	
	public Regist_cityEntity() {
		super();
	}
	public Regist_cityEntity(String id, String s_cityname, String n_provid) {
		super();
		this.id = id;
		this.s_cityname = s_cityname;
		this.n_provid = n_provid;
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
	@Override
	public String toString() {
		return "Regist_cityEntity [id=" + id + ", s_cityname=" + s_cityname
				+ ", n_provid=" + n_provid + "]";
	}
	
	
	
	
	
	
	

}
