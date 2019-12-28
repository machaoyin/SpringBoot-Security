package com.mcy.springbootsecurity.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mcy.springbootsecurity.custom.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户表
 */
@Entity
public class SysUser extends BaseEntity<Integer> {
	private String username;	//账号
	private String password;	//密码
	private String name;		//姓名
	private String address;		//地址
	
	@JsonIgnore
	private List<SysRole> roles=new ArrayList<>();	//角色

	@Column(length=20,unique=true)
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	@Column(length=100)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column(length=20)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@ManyToMany(cascade=CascadeType.REFRESH,fetch=FetchType.EAGER)
	@JoinTable(name="sys_user_role",joinColumns=@JoinColumn(name="user_id"),inverseJoinColumns=@JoinColumn(name="role_id"))
	public List<SysRole> getRoles() {
		return roles;
	}
	public void setRoles(List<SysRole> roles) {
		this.roles = roles;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	//角色名称
	@Transient
	public String getRoleNames() {
		String str="";
		for (SysRole role : getRoles()) {
			str+=role.getName()+",";
		}
		if(str.length()>0) {
			str=str.substring(0, str.length()-1);
		}
		return str;
	}

	//角色代码
	@Transient
	public String getRoleCodes() {
		String str="";
		for (SysRole role : getRoles()) {
			str+=role.getCode()+",";
		}
		if(str.indexOf(",")>0) {
			str=str.substring(0,str.length()-1);
		}
		return str;
	}
	
}
