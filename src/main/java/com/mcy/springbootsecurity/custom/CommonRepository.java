package com.mcy.springbootsecurity.custom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * 公共Repository
 * @param <T>
 * @param <ID>
 */
@NoRepositoryBean
public interface CommonRepository<T,ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

	
}
