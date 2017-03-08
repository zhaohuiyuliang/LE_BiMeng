package com.jishang.bimeng.entity.wode.wzf;

import java.io.Serializable;

public class Wdh_dataBu_pvEntity implements Serializable{
	private String id;
	private String s_provname;
	
	
	
	
	
	
	
	
	
	public Wdh_dataBu_pvEntity() {
		super();
	}
	public Wdh_dataBu_pvEntity(String id, String s_provname) {
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
		return "Wdh_dataBu_pvEntity [id=" + id + ", s_provname=" + s_provname
				+ "]";
	}

	
	
	
	
}
