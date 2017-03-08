package com.jishang.bimeng.entity.yuezhan;

import java.io.Serializable;

public class PhotoEntity implements Serializable{
	private String gm_img;
	private String yz_gm_id;
	private String gm_name;
	
	
	
	
	public PhotoEntity() {
		super();
	}
	public PhotoEntity(String gm_img, String yz_gm_id, String gm_name) {
		super();
		this.gm_img = gm_img;
		this.yz_gm_id = yz_gm_id;
		this.gm_name = gm_name;
	}
	public String getGm_img() {
		return gm_img;
	}
	public void setGm_img(String gm_img) {
		this.gm_img = gm_img;
	}
	public String getYz_gm_id() {
		return yz_gm_id;
	}
	public void setYz_gm_id(String yz_gm_id) {
		this.yz_gm_id = yz_gm_id;
	}
	public String getGm_name() {
		return gm_name;
	}
	public void setGm_name(String gm_name) {
		this.gm_name = gm_name;
	}
	@Override
	public String toString() {
		return "PhotoEntity [gm_img=" + gm_img + ", yz_gm_id=" + yz_gm_id
				+ ", gm_name=" + gm_name + "]";
	}
	
	
	
	
	

}
