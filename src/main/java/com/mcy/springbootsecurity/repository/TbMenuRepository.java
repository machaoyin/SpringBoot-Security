package com.mcy.springbootsecurity.repository;

import com.mcy.springbootsecurity.custom.CommonRepository;
import com.mcy.springbootsecurity.entity.TbMenu;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TbMenuRepository extends CommonRepository<TbMenu, Integer> {
    List<TbMenu> findByParentIsNullOrderByIdx();

    TbMenu findByName(String name);
}
