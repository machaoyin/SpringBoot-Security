package com.mcy.springbootsecurity.web.controller;

import com.mcy.springbootsecurity.custom.AjaxResult;
import com.mcy.springbootsecurity.custom.CommonController;
import com.mcy.springbootsecurity.entity.SysRole;
import com.mcy.springbootsecurity.entity.SysUser;
import com.mcy.springbootsecurity.service.SysRoleService;
import com.mcy.springbootsecurity.service.SysUserService;
import com.mcy.springbootsecurity.web.form.SysUserForm;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Controller
@RequestMapping(value="/user")
public class SysUserController extends CommonController<SysUser, Integer, SysUserForm> {
    @Autowired
    private SysUserService userService;
    @Autowired
    private SysRoleService roleService;

    @Override
    public void edit(SysUserForm form, ModelMap map) throws InstantiationException, IllegalAccessException {
        List<SysRole> roles = roleService.findByParentIsNull();
        map.put("roles", roles);
        super.edit(form, map);
    }

    @Override
    public Object save(SysUserForm form) {
        try {
            SysUser model = new SysUser();
            Integer id = form.getId();
            if (id != null) {
                model = userService.findById(id);
            }else{
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                model.setPassword(encoder.encode("123456"));
            }
            BeanUtils.copyProperties(form, model, "id", "password");
            List<SysRole> roles = new ArrayList<>();
            for (String name : form.getRoles().split(",")) {
                roles.add(roleService.findByName(name));
            }
            model.setRoles(roles);
            userService.save(model);
            return new AjaxResult("数据保存成功");
        } catch (Exception e) {
            return new AjaxResult(false, "数据保存失败");
        }
    }

    @Override
    public Specification<SysUser> buildSpec(SysUserForm form) {
        Specification<SysUser> spec = new Specification<SysUser>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<SysUser> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                HashSet<Predicate> rules = new HashSet<>();
                if (StringUtils.hasText(form.getSearch())) {
                    Predicate name = cb.like(root.get("name"), "%" + form.getSearch() + "%");
                    Predicate username = cb.like(root.get("username"), "%" + form.getSearch() + "%");
                    Predicate address = cb.like(root.get("address"), "%" + form.getSearch() + "%");
                    Predicate or = cb.or(name, username, address);
                    rules.add(or);
                }
                return cb.and(rules.toArray(new Predicate[rules.size()]));
            }
        };
        return spec;
    }
}
