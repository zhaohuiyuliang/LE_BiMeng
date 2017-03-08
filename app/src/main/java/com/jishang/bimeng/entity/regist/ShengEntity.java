package com.jishang.bimeng.entity.regist;

import java.io.Serializable;

public class ShengEntity implements Serializable{
	private String sheng;
	private String shi;
	private String xian;
	
	
	
	public ShengEntity() {
		super();
	}
	public ShengEntity(String sheng, String shi, String xian) {
		super();
		this.sheng = sheng;
		this.shi = shi;
		this.xian = xian;
	}
	public String getSheng() {
		return sheng;
	}
	public void setSheng(String sheng) {
		this.sheng = sheng;
	}
	public String getShi() {
		return shi;
	}
	public void setShi(String shi) {
		this.shi = shi;
	}
	public String getXian() {
		return xian;
	}
	public void setXian(String xian) {
		this.xian = xian;
	}
	@Override
	public String toString() {
		return "ShengEntity [sheng=" + sheng + ", shi=" + shi + ", xian="
				+ xian + "]";
	}

}
