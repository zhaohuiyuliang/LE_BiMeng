package com.jishang.bimeng.entity.wode;

import java.io.Serializable;

public class Photo_dataEntity implements Serializable{
	private String head_img;
	
	
	
	

	public Photo_dataEntity() {
		super();
	}

	public Photo_dataEntity(String head_img) {
		super();
		this.head_img = head_img;
	}

	public String getHead_img() {
		return head_img;
	}

	public void setHead_img(String head_img) {
		this.head_img = head_img;
	}

	@Override
	public String toString() {
		return "Photo_dataEntity [head_img=" + head_img + "]";
	}

	
	
	
}
