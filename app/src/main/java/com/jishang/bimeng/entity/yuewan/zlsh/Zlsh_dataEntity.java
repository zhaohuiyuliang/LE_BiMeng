package com.jishang.bimeng.entity.yuewan.zlsh;

import java.io.Serializable;

public class Zlsh_dataEntity implements Serializable{

	private String tip_select;
	
	
	

	public Zlsh_dataEntity() {
		super();
	}

	public Zlsh_dataEntity(String tip_select) {
		super();
		this.tip_select = tip_select;
	}

	public String getTip_select() {
		return tip_select;
	}

	public void setTip_select(String tip_select) {
		this.tip_select = tip_select;
	}

	@Override
	public String toString() {
		return "Zlsh_dataEntity [tip_select=" + tip_select + "]";
	}

	
	
}
