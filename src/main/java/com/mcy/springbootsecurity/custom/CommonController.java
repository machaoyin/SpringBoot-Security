package com.mcy.springbootsecurity.custom;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 公共Controller
 * @param <T>
 * @param <ID>
 * @param <Form>
 */
public class CommonController<T extends BaseEntity<ID>,ID,Form extends BaseForm<ID>>{
	//通过反射工具类GenericsUtils，获得到实体类
	@SuppressWarnings("unchecked")
	private Class<T> clazz = GenericsUtils.getSuperClassGenricType(getClass());
	
	@Autowired
	private CommonService<T, ID> baseService;

	//数据显示页面
	@RequestMapping(value="/manage")
	public void manage(ModelMap map) {
	
	}

	//修改和新增页面，共用的一个页面
	@RequestMapping(value="/edit")
	public void edit(Form form, ModelMap map) throws InstantiationException, IllegalAccessException {
		T model=clazz.newInstance();
		ID id = form.getId();
		if(id!=null) {
			model=baseService.findById(id);
		}
		map.put("model", model);
	}

	//数据保存方法
	@RequestMapping(value="/save")
	@ResponseBody
	public Object save(Form form)  {
		try {
			T model=clazz.newInstance();
			ID id = form.getId();
			if(id!=null) {
				model=baseService.findById(id);
			}
			BeanUtils.copyProperties(form, model,"id");
			baseService.save(model);
			return new AjaxResult("数据保存成功");
		} catch (Exception e) {
			return new AjaxResult(false,"数据保存失败");
		}
	}

	//删除方法
	@RequestMapping(value="/delete")
	@ResponseBody
	public Object delete(ID id) {
		try {
			baseService.deleteById(id);
			return new AjaxResult("数据删除成功");
		} catch (Exception e) {
			return new AjaxResult(false,"数据删除失败");
		}
	}
	
	//动态查询方法
	public Specification<T> buildSpec(Form form){
		return null;
	}

	//分页数据查询
	@RequestMapping(value="/page")
	@ResponseBody
	public Object page(TablePageable pageParam,Form form) {
		PageRequest pageable = pageParam.bulidPageRequest();
		Specification<T> spec = buildSpec(form);
		Page<T> page=baseService.findAll(spec, pageable);
		return AjaxResult.bulidPageResult(page);
	}

	//查询表中全部信息，用于树形数据表格
	@RequestMapping(value="/list")
	@ResponseBody
	public Object list(TablePageable pageParam,Form form) {
		Sort sort = pageParam.bulidSort();
		Specification<T> spec = buildSpec(form);
		return baseService.findAll(spec,sort);
	}
}
