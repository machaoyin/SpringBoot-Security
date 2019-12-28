package com.mcy.springbootsecurity.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mcy.springbootsecurity.custom.BaseEntity;
import org.springframework.data.annotation.CreatedBy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
/***
 * 角色及角色对应的菜单权限
 * @author
 *parent 为null时为角色，不为null时为权限
 */
public class SysRole extends BaseEntity<Integer> {
	private String name;	//名称
	private String code;	//代码
	@JsonIgnore
	private SysRole parent;
	private Integer idx;	//排序
	@JsonIgnore
	private List<SysRole> children = new ArrayList<>();
	
	@Column(length=20)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@ManyToOne
	@CreatedBy
	public SysRole getParent() {
		return parent;
	}

	public void setParent(SysRole parent) {
		this.parent = parent;
	}
	
	@OneToMany(cascade=CascadeType.ALL,mappedBy="parent")
	public List<SysRole> getChildren() {
		return children;
	}
	
	public void setChildren(List<SysRole> children) {
		this.children = children;
	}

	//获取父节点id
	@Transient
	public Integer getParentId() {
		return parent==null?null:parent.getId();
	}

	public Integer getIdx() {
		return idx;
	}

	public void setIdx(Integer idx) {
		this.idx = idx;
	}

	public SysRole(String name, String code, SysRole parent, Integer idx, List<SysRole> children) {
		this.name = name;
		this.code = code;
		this.parent = parent;
		this.idx = idx;
		this.children = children;
	}

	public SysRole(Integer id, String name, String code, SysRole parent, Integer idx, List<SysRole> children) {
		super(id);
		this.name = name;
		this.code = code;
		this.parent = parent;
		this.idx = idx;
		this.children = children;
	}

	public SysRole(Integer id) {
		super(id);
	}

	public SysRole(){}
}
