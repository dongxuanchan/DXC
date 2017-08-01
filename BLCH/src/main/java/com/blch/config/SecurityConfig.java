package com.blch.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

/**
 * Created with IntelliJ IDEA.
 * User: cxdong
 * Date: 4/25/17
 * Time: 12:12 PM
 * To change this template use File | Settings | File Templates.
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  @Autowired
  DataSource dataSource;

  @Autowired
  public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {

    auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(passwordEncoder())
      .usersByUsernameQuery(
        "select username,password, enabled from blch_users where username=?")
      .authoritiesByUsernameQuery(
        "select username, role from blch_user_roles where username=?")
    .and();
    //.inMemoryAuthentication().withUser("dba").password("123456").roles("DBA");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .formLogin().loginPage("/dangnhap").successForwardUrl("/").failureUrl("/dangnhap?loi")
      .usernameParameter("tendangnhap").passwordParameter("matkhau")
      .and()
      .logout().logoutSuccessUrl("/dangnhap?thoat")
      .and()
      .exceptionHandling().accessDeniedPage("/403")
      //.and()
      //.csrf()
      .and()
      .authorizeRequests()
      .antMatchers("/rs/**","/dangnhap","/","/h2-console/**").permitAll()
      .antMatchers("/user/**").hasRole("USER")
      .antMatchers("/admin/**").hasRole("ADMIN")
      //.antMatchers("/console/**").hasRole("ADMIN");
      .anyRequest().authenticated();

    //this scope to enable the access to H2 Database Console
    //if disable this scope please remember:
    // 1. enable csrf() in  setting line above
    // 2. remove  "/","/h2-console/**" in matchers permitAll() in  setting line above
    http.csrf().disable();
    http.headers().frameOptions().disable();
  }

  @Bean
  public PasswordEncoder passwordEncoder(){
    PasswordEncoder encoder = new BCryptPasswordEncoder();
    return encoder;
  }

}
