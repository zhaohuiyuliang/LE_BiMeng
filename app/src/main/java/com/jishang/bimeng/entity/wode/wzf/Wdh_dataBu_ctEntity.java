package com.jishang.bimeng.entity.wode.wzf;

import java.io.Serializable;

public class Wdh_dataBu_ctEntity implements Serializable{
	private String id;
	private String s_cityname;
	
	
	
	
	
	
	public Wdh_dataBu_ctEntity() {
		super();
	}
	public Wdh_dataBu_ctEntity(String id, String s_cityname) {
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
		return "Wdh_dataBu_ctEntity [id=" + id + ", s_cityname=" + s_cityname
				+ "]";
	}

	
	
	
	
	
	
	
	
	
	
	
}
