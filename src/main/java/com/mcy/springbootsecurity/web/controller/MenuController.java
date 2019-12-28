package com.mcy.springbootsecurity.web.controller;

import com.mcy.springbootsecurity.custom.AjaxResult;
import com.mcy.springbootsecurity.custom.CommonController;
import com.mcy.springbootsecurity.entity.TbMenu;
import com.mcy.springbootsecurity.service.TbMenuService;
import com.mcy.springbootsecurity.web.form.TbMenuForm;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@Controller
@RequestMapping("/menu")
public class MenuController extends CommonController<TbMenu, Integer, TbMenuForm> {
    @Autowired
    private TbMenuService menuService;

    @Override
    public Object save(TbMenuForm form) {
        try {
            TbMenu model = new TbMenu();
            Integer id = form.getId();
            if(id != null) {
                model = menuService.findById(id);
            }
            //父级菜单id
            Integer parentId = form.getParentId();
            if(parentId == null) {
                model.setParent(null);
            }else {
                model.setParent(new TbMenu(parentId));
            }
            BeanUtils.copyProperties(form, model,"id", "parent");
            menuService.save(model);
            return new AjaxResult("数据保存成功！");
        } catch (Exception e) {
            return new AjaxResult(false,"数据保存失败");
        }
    }

    @Override
    public void edit(TbMenuForm form, ModelMap map) throws InstantiationException, IllegalAccessException {
        TbMenu model = new TbMenu();
        if(form.getId() != null) {
            model = menuService.findById(form.getId());
        }
        if(form.getParentId() != null) {
            model.setParent(new TbMenu(form.getParentId()));
        }
        map.put("model", model);
    }

    /***
     * combotree树形加载
     * @return
     */
    @RequestMapping(value="/treedata")
    @ResponseBody
    public Object treedata(Integer id) {
        Sort sort = Sort.by("idx");
        Specification<TbMenu> spec = buildSpec1();
        List<TbMenu> list = menuService.findAll(spec,sort);
        return buildTree(list,id);
    }

    private Object buildTree(List<TbMenu> list, Integer id) {
        List<HashMap<String, Object>> result=new ArrayList<>();
        for (TbMenu dept : list) {
            if(dept.getId()!=id) {
                HashMap<String, Object> node=new HashMap<>();
                node.put("id", dept.getId());
                node.put("text",dept.getName());
                node.put("pid", dept.getParentId());
                node.put("nodes",buildTree(dept.getChildren(), id));
                result.add(node);
            }
        }
        return result;
    }

    public Specification<TbMenu> buildSpec1() {
        Specification<TbMenu> specification = new Specification<TbMenu>() {

            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<TbMenu> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                HashSet<Predicate> rules=new HashSet<>();
                Predicate parent = cb.isNull(root.get("parent"));
                rules.add(parent);
                return cb.and(rules.toArray(new Predicate[rules.size()]));
            }

        };
        return specification;
    }
}
