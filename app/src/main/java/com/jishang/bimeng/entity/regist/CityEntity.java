package com.jishang.bimeng.entity.regist;

public class CityEntity {
	private String cant_code;
	private String cant_name;
	private String super_code;
	
	
	public CityEntity() {
		super();
	}
	public CityEntity(String cant_code, String cant_name, String super_code) {
		super();
		this.cant_code = cant_code;
		this.cant_name = cant_name;
		this.super_code = super_code;
	}
	public String getCant_code() {
		return cant_code;
	}
	public void setCant_code(String cant_code) {
		this.cant_code = cant_code;
	}
	public String getCant_name() {
		return cant_name;
	}
	public void setCant_name(String cant_name) {
		this.cant_name = cant_name;
	}
	public String getSuper_code() {
		return super_code;
	}
	public void setSuper_code(String super_code) {
		this.super_code = super_code;
	}
	@Override
	public String toString() {
		return "CityEntity [cant_code=" + cant_code + ", cant_name="
				+ cant_name + ", super_code=" + super_code + "]";
	}

}
