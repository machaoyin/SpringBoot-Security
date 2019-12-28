package com.mcy.springbootsecurity.custom;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseEntity<ID> {
	private ID id;

	@Id
	@GeneratedValue
	public ID getId() {
		return id;
	}

	public void setId(ID id) {
		this.id = id;
	}

	public BaseEntity() {
		super();
	}

	public BaseEntity(ID id) {
		super();
		this.id = id;
	}
}
