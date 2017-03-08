package com.jishang.bimeng.entity.yuezhan.yzlist;

import java.io.Serializable;

public class List_data_ctEntity implements Serializable{
	private String id;
	private String s_cityname;
	
	
	
	
	
	
	
	
	public List_data_ctEntity() {
		super();
	}
	public List_data_ctEntity(String id, String s_cityname) {
		super();
		this.id = id;
		this.s_cityname = s_cityname;
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
	@Override
	public String toString() {
		return "List_data_ctEntity [id=" + id + ", s_cityname=" + s_cityname
				+ "]";
	}

	
	
	
	
	
}
