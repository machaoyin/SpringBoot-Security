package com.mcy.springbootsecurity.web.form;

import com.mcy.springbootsecurity.custom.BaseForm;

public class SysUserForm extends BaseForm<Integer> {
    private String username;	//账号
    private String password;	//密码
    private String name;		//姓名
    private String address;     //地址
    private String roles;       //权限

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
}
