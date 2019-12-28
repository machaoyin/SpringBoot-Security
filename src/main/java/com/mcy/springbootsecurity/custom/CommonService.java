package com.mcy.springbootsecurity.custom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * 公共Service
 * @param <T>
 * @param <ID>
 */
public class CommonService<T,ID> {
	@Autowired(required = false)
	private CommonRepository<T, ID> baseDAO;
	
	public List<T> findAll(){
		return baseDAO.findAll();
	}
	
	public T findById(ID id) {
		Optional<T> optional = baseDAO.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;
	}
	
	@Transactional
	public void save(T entity) {
		baseDAO.save(entity);
	}
	
	public void delete(T entity) {
		baseDAO.delete(entity);
	}
	
	@Transactional
	public void deleteById(ID id) {
		baseDAO.deleteById(id);
	}
	
	public List<T> findAll(Sort sort){
		return baseDAO.findAll(sort);
	}
	
	public List<T> findAll(Specification<T> spec){
		return baseDAO.findAll(spec);
	}
	
	public Page<T> findAll(Pageable pageable){
		return baseDAO.findAll(pageable);
	}
	
	public Page<T> findAll(Specification<T> spec, Pageable pageable){
		return baseDAO.findAll(spec,pageable);
	}
	
	public List<T> findAll(Specification<T> spec, Sort sort){
		return baseDAO.findAll(spec,sort);
	}
}
