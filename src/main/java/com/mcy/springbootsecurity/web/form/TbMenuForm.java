package com.mcy.springbootsecurity.web.form;

import com.mcy.springbootsecurity.custom.BaseForm;
import com.mcy.springbootsecurity.entity.TbMenu;

public class TbMenuForm extends BaseForm<Integer> {
    private String name;
    private String url;
    private Integer idx;
    private TbMenu parent;
    private Integer parentId;

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

    public TbMenu getParent() {
        return parent;
    }

    public void setParent(TbMenu parent) {
        this.parent = parent;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
}
