package com.dac.welang.init;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	DataSource dataSource;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/register").permitAll()
				.antMatchers("/contact", "/contact/**").permitAll()
				.antMatchers("/resetPassword", "/resetPassword/**").permitAll()
		        .antMatchers("/connect/**","/auto_login").permitAll()
		        .anyRequest().fullyAuthenticated()
				.and().formLogin()
				.loginPage("/login").permitAll().and().logout().permitAll();
		
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()  
        .usersByUsernameQuery("select email,password,enabled from user_account where email=?")  
        .authoritiesByUsernameQuery("select email, 'ROLE_USER' from user_account where email=?")  
        .dataSource(dataSource);  
	}
}