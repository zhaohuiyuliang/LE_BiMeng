package com.jishang.bimeng.entity.yuezhan.yzlist;

import java.io.Serializable;
import java.util.List;

public class List_data_prEntity implements Serializable{
	private String id;
	private String s_provname;
	
	
	
	
	
	
	
	
	
	
	public List_data_prEntity() {
		super();
	}
	public List_data_prEntity(String id, String s_provname) {
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
		return "List_data_prEntity [id=" + id + ", s_provname=" + s_provname
				+ "]";
	}
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	
	
}
