package com.jishang.bimeng.entity.wode.wzf;

import java.io.Serializable;

public class Wdh_dataBu_wbEntity implements Serializable{
	private String id;
	private String w_name;
	
	
	
	
	
	
	
	
	
	
	
	public Wdh_dataBu_wbEntity() {
		super();
	}
	public Wdh_dataBu_wbEntity(String id, String w_name) {
		super();
		this.id = id;
		this.w_name = w_name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getW_name() {
		return w_name;
	}
	public void setW_name(String w_name) {
		this.w_name = w_name;
	}
	@Override
	public String toString() {
		return "Wdh_dataBu_wbEntity [id=" + id + ", w_name=" + w_name + "]";
	}
	
	
	
	
	

}
