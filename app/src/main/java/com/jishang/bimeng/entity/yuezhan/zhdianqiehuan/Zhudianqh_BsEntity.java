package com.jishang.bimeng.entity.yuezhan.zhdianqiehuan;

import java.io.Serializable;

public class Zhudianqh_BsEntity implements Serializable{
	private String id;
	private String w_name;
	private String w_img;
	private String w_provinceid;
	private String w_citid;
	private String wb_describe;
	
	
	
	
	public Zhudianqh_BsEntity() {
		super();
	}
	public Zhudianqh_BsEntity(String id, String w_name, String w_img,
			String w_provinceid, String w_citid, String wb_describe) {
		super();
		this.id = id;
		this.w_name = w_name;
		this.w_img = w_img;
		this.w_provinceid = w_provinceid;
		this.w_citid = w_citid;
		this.wb_describe = wb_describe;
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
	public String getW_img() {
		return w_img;
	}
	public void setW_img(String w_img) {
		this.w_img = w_img;
	}
	public String getW_provinceid() {
		return w_provinceid;
	}
	public void setW_provinceid(String w_provinceid) {
		this.w_provinceid = w_provinceid;
	}
	public String getW_citid() {
		return w_citid;
	}
	public void setW_citid(String w_citid) {
		this.w_citid = w_citid;
	}
	public String getWb_describe() {
		return wb_describe;
	}
	public void setWb_describe(String wb_describe) {
		this.wb_describe = wb_describe;
	}
	@Override
	public String toString() {
		return "Zhudianqh_BsEntity [id=" + id + ", w_name=" + w_name
				+ ", w_img=" + w_img + ", w_provinceid=" + w_provinceid
				+ ", w_citid=" + w_citid + ", wb_describe=" + wb_describe + "]";
	}
	

}
