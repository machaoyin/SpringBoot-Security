package com.mcy.springbootsecurity.service;

import com.mcy.springbootsecurity.custom.CommonService;
import com.mcy.springbootsecurity.entity.SysRole;
import com.mcy.springbootsecurity.entity.SysUser;
import com.mcy.springbootsecurity.repository.SysUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 需要实现UserDetailsService接口
 * 因为在SpringSecurity中配置的相关参数需要是UserDetailsService类的数据
 */
@Service
public class SysUserService extends CommonService<SysUser, Integer> implements UserDetailsService {
    @Autowired
    private SysUserRepository userRepository;
    @Autowired
    private SysRoleService roleService;

    /**
     * 重写UserDetailsService接口中的loadUserByUsername方法，通过该方法查询对应的用户
     * 返回对象UserDetails是SpringSecurity的一个核心接口。
     * 其中定义了一些可以获取用户名，密码，权限等与认证相关信息的方法。
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //调用持久层接口findByUsername方法查询用户。
        SysUser user = userRepository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("用户名不存在");
        }
        //创建List集合，用来保存用户菜单权限，GrantedAuthority对象代表赋予当前用户的权限
        List<GrantedAuthority> authorities = new ArrayList<>();
        //获得当前用户角色集合
        List<SysRole> roles = user.getRoles();
        List<SysRole> haveRoles=new ArrayList<>();
        for (SysRole role : roles) {
            haveRoles.add(role);
            List<SysRole> children = roleService.findByParent(role);
            children.removeAll(haveRoles);
            haveRoles.addAll(children);
        }
        for(SysRole role: haveRoles){
            //将关联对象role的name属性保存为用户的认证权限
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        //此处返回的是org.springframework.security.core.userdetails.User类，该类是SpringSecurity内部的实现
        //org.springframework.security.core.userdetails.User类实现了UserDetails接口
        return new User(user.getUsername(), user.getPassword(), authorities);
    }

    public SysUser findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
