package com.jishang.bimeng.entity.yuezhan.cyzf;

public class Cyzf_ctEntity {
	private String id;
	private String continue_key;
	private String continue_value;
	
	
	
	
	public Cyzf_ctEntity() {
		super();
	}
	public Cyzf_ctEntity(String id, String continue_key, String continue_value) {
		super();
		this.id = id;
		this.continue_key = continue_key;
		this.continue_value = continue_value;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContinue_key() {
		return continue_key;
	}
	public void setContinue_key(String continue_key) {
		this.continue_key = continue_key;
	}
	public String getContinue_value() {
		return continue_value;
	}
	public void setContinue_value(String continue_value) {
		this.continue_value = continue_value;
	}
	@Override
	public String toString() {
		return "Cyzf_ctEntity [id=" + id + ", continue_key=" + continue_key
				+ ", continue_value=" + continue_value + "]";
	}

	
	
	
}
