package com.mcy.springbootsecurity.security;

import com.mcy.springbootsecurity.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SysUserService userService;

    /**
     * 用户认证操作
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //添加用户，并给予权限
        auth.inMemoryAuthentication().withUser("aaa").password("{noop}1234").roles("DIY");
        //设置认证方式
        auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
    }

    /**
     * 用户授权操作
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();    //安全器令牌
        http.formLogin()
                //登录请求被拦截
                .loginPage("/login").permitAll()
                //设置默认登录成功跳转页面
                .successForwardUrl("/main")
                .failureUrl("/login?error");   //登录失败的页面
        http.authorizeRequests().antMatchers("/static/**", "/assets/**").permitAll();    //文件下的所有都能访问
        http.authorizeRequests().antMatchers("/webjars/**").permitAll();
        http.logout().logoutUrl("/logout").permitAll();     //退出
        http.authorizeRequests().anyRequest().authenticated();    //除此之外的都必须通过请求验证才能访问
    }
}