package com.jishang.bimeng.entity.yuewan.list;

import java.io.Serializable;

public class YwList_data_udEntity implements Serializable{
	private String age;
	private String ud_uid;
	private String w_tip;
	
	
	
	
	public YwList_data_udEntity() {
		super();
	}
	public YwList_data_udEntity(String age, String ud_uid, String w_tip) {
		super();
		this.age = age;
		this.ud_uid = ud_uid;
		this.w_tip = w_tip;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getUd_uid() {
		return ud_uid;
	}
	public void setUd_uid(String ud_uid) {
		this.ud_uid = ud_uid;
	}
	public String getW_tip() {
		return w_tip;
	}
	public void setW_tip(String w_tip) {
		this.w_tip = w_tip;
	}
	@Override
	public String toString() {
		return "YwList_data_udEntity [age=" + age + ", ud_uid=" + ud_uid
				+ ", w_tip=" + w_tip + "]";
	}

	
	
}
