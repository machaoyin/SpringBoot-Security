package com.mcy.springbootsecurity.repository;

import com.mcy.springbootsecurity.custom.CommonRepository;
import com.mcy.springbootsecurity.entity.SysUser;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUserRepository extends CommonRepository<SysUser, Integer> {
    SysUser findByUsername(String username);
}
