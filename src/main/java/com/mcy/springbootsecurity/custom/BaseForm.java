package com.mcy.springbootsecurity.custom;

public class BaseForm<ID> {
	private ID id;
	private String search;
	
	public ID getId() {
		return id;
	}

	public void setId(ID id) {
		this.id = id;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}
	
	
}
