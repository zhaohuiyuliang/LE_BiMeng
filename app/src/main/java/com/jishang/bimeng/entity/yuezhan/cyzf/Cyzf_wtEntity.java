package com.jishang.bimeng.entity.yuezhan.cyzf;

public class Cyzf_wtEntity {
	private String id;
	private String time_key;
	private String time_value;
	
	
	
	
	public Cyzf_wtEntity() {
		super();
	}
	public Cyzf_wtEntity(String id, String time_key, String time_value) {
		super();
		this.id = id;
		this.time_key = time_key;
		this.time_value = time_value;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTime_key() {
		return time_key;
	}
	public void setTime_key(String time_key) {
		this.time_key = time_key;
	}
	public String getTime_value() {
		return time_value;
	}
	public void setTime_value(String time_value) {
		this.time_value = time_value;
	}
	@Override
	public String toString() {
		return "Cyzf_wtEntity [id=" + id + ", time_key=" + time_key
				+ ", time_value=" + time_value + "]";
	}
	
	
	
	

}
