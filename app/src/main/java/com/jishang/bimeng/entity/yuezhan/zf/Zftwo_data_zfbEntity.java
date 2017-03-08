package com.jishang.bimeng.entity.yuezhan.zf;

import java.io.Serializable;

public class Zftwo_data_zfbEntity implements Serializable{
	private String partner;
	private String seller;
	private String rsa_private;
	private String rsa_public;
	
	
	
	
	
	
	
	public Zftwo_data_zfbEntity() {
		super();
	}
	public Zftwo_data_zfbEntity(String partner, String seller,
			String rsa_private, String rsa_public) {
		super();
		this.partner = partner;
		this.seller = seller;
		this.rsa_private = rsa_private;
		this.rsa_public = rsa_public;
	}
	public String getPartner() {
		return partner;
	}
	public void setPartner(String partner) {
		this.partner = partner;
	}
	public String getSeller() {
		return seller;
	}
	public void setSeller(String seller) {
		this.seller = seller;
	}
	public String getRsa_private() {
		return rsa_private;
	}
	public void setRsa_private(String rsa_private) {
		this.rsa_private = rsa_private;
	}
	public String getRsa_public() {
		return rsa_public;
	}
	public void setRsa_public(String rsa_public) {
		this.rsa_public = rsa_public;
	}
	@Override
	public String toString() {
		return "Zftwo_data_zfbEntity [partner=" + partner + ", seller="
				+ seller + ", rsa_private=" + rsa_private + ", rsa_public="
				+ rsa_public + "]";
	}
	

}
