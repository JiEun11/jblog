package com.poscoict.config.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.poscoict.jblog.security.AuthInterceptor;
import com.poscoict.jblog.security.LoginInterceptor;
import com.poscoict.jblog.security.LogoutInterceptor;

@Configuration
public class SecurityConfig extends WebMvcConfigurerAdapter {

	// Argument Resolver
	
	// Interceptors
	
	@Bean
	public HandlerInterceptor loginInterceptor() {
		return new LoginInterceptor();
	}
	
	@Bean
	public HandlerInterceptor logoutInterceptor() {
		return new LogoutInterceptor();
	}
	
	@Bean
	public HandlerInterceptor authInterceptor() {
		return new AuthInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry
		.addInterceptor(loginInterceptor())
		.addPathPatterns("/user/login");
		
		registry
		.addInterceptor(logoutInterceptor())
		.addPathPatterns("/user/logout");
		
		registry
		.addInterceptor(authInterceptor())
		.addPathPatterns("/**")
		.excludePathPatterns("/user/login")
		.excludePathPatterns("/user/logout")
		.excludePathPatterns("/assets/**");
	}
	
	
	
}
