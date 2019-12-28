package com.mcy.springbootsecurity.web.form;

import com.mcy.springbootsecurity.custom.BaseForm;
import com.mcy.springbootsecurity.entity.SysRole;

public class SysRoleForm extends BaseForm<Integer> {
    private String name;
    private String code;
    private SysRole parent;
    private Integer parentId;
    private Integer idx;
    private String permissionCode;

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

    public SysRole getParent() {
        return parent;
    }

    public void setParent(SysRole parent) {
        this.parent = parent;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getIdx() {
        return idx;
    }

    public void setIdx(Integer idx) {
        this.idx = idx;
    }

    public String getPermissionCode() {
        return permissionCode;
    }

    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }
}
