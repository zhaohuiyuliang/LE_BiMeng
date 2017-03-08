package com.jishang.bimeng.entity.regist;

import java.io.Serializable;

public class Regist_provinceEntity implements Serializable{
	
	private String id;
	private String s_provname;
	
	
	public Regist_provinceEntity() {
		super();
	}
	public Regist_provinceEntity(String id, String s_provname) {
		super();
		this.id = id;
		this.s_provname = s_provname;
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
	@Override
	public String toString() {
		return "Regist_provinceEntity [id=" + id + ", s_provname=" + s_provname
				+ "]";
	}
	

}
