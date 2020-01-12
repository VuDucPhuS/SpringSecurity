package com.luv2code.springsecurity.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;

@Configuration
@EnableWebSecurity
public class DemoSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//add our users for in memory authentication
		UserBuilder users = User.withDefaultPasswordEncoder();
		
		auth.inMemoryAuthentication()
			.withUser(users.username("user").password("test123").roles("EMPLOYEE"))
			.withUser(users.username("manager").password("test123").roles("MANAGER"))
			.withUser(users.username("admin").password("test123").roles("ADMIN"));
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests() //Restrict access based on the HttpServletRequest
				.anyRequest().authenticated() //Any request to the app must be authenticated (ie logged in)
			.and() // + formLogin() --> Add login support for default URL /login
			.formLogin() // We are customizing the form login process
				.loginPage("/showMyLoginPage") //Show our custom form at the request mapping "/showMyLoginPage"
				.loginProcessingUrl("/authenticateTheUser") //Login form should POST data to this URL for processing (check user id and password)
				.permitAll(); //Allow every one to see login page. No need to be logged in
	}

	
}
