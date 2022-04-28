package com.arturrustamhanov.spring.security.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;

import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;

@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;



    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

//      записывам что инфу о юзерах содержит БД а инфа о подкл к этой БД содержит dataSource
        auth.jdbcAuthentication().dataSource(dataSource);

//        UserBuilder userBuilder = User.withDefaultPasswordEncoder();
//
//        auth.inMemoryAuthentication()
//                .withUser(userBuilder.username("zaur").password("zaur").roles("MANAGER","HR"))
//                .withUser(userBuilder.username("artur").password("artur").roles("IT"))
//                .withUser(userBuilder.username("elena").password("elena").roles("HR"));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//       запрос автор-ции для url "/"                       прописываем у каких должностей есть доступ
        http.authorizeRequests().antMatchers("/").hasAnyRole("MANAGER","HR","EMPLOYEE")
//              доступ к определенным страницам для опред-ых должностей
                .antMatchers("/hr_info/**").hasRole("HR")
                .antMatchers("/manager_info/**").hasRole("MANAGER")
//              лог и пароль заврашивать у всех
                .and().formLogin().permitAll();
    }
}
