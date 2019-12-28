package com.mcy.springbootsecurity.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mcy.springbootsecurity.custom.BaseEntity;
import org.springframework.data.annotation.CreatedBy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单表
 * @author
 *
 */
@Entity
public class TbMenu extends BaseEntity<Integer> {
	private String name;
	private String url;
	private Integer idx;
	@JsonIgnore
	private TbMenu parent;
	@JsonIgnore
	private List<TbMenu> children=new ArrayList<>();

	@Column(unique=true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getIdx() {
		return idx;
	}

	public void setIdx(Integer idx) {
		this.idx = idx;
	}

	@ManyToOne
	@CreatedBy
	public TbMenu getParent() {
		return parent;
	}

	public void setParent(TbMenu parent) {
		this.parent = parent;
	}

	@OneToMany(cascade=CascadeType.ALL,mappedBy="parent")
	@OrderBy(value="idx")
	public List<TbMenu> getChildren() {
		return children;
	}

	public void setChildren(List<TbMenu> children) {
		this.children = children;
	}

	public TbMenu(Integer id) {
		super(id);
	}

	public TbMenu(){
		super();
	}

	public TbMenu(String name, String url, Integer idx, TbMenu parent, List<TbMenu> children) {
		this.name = name;
		this.url = url;
		this.idx = idx;
		this.parent = parent;
		this.children = children;
	}

	public TbMenu(Integer integer, String name, String url, Integer idx, TbMenu parent, List<TbMenu> children) {
		super(integer);
		this.name = name;
		this.url = url;
		this.idx = idx;
		this.parent = parent;
		this.children = children;
	}

	@Transient
	public Integer getParentId() {
		return parent==null?null:parent.getId();
	}
}
