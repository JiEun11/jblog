package com.poscoict.jblog.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.poscoict.jblog.interceptor.BlogInterceptor;
import com.poscoict.jblog.security.AuthInterceptor;
import com.poscoict.jblog.security.LoginInterceptor;
import com.poscoict.jblog.security.LogoutInterceptor;

@SpringBootConfiguration
@PropertySource("classpath:com/poscoict/jblog/config/WebConfig.properties")
public class WebConfig implements WebMvcConfigurer {

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

	@Bean
	public HandlerInterceptor blogInterceptor() {
		return new BlogInterceptor();
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loginInterceptor()).addPathPatterns("/user/login");

		registry.addInterceptor(logoutInterceptor()).addPathPatterns("/user/logout");

		registry.addInterceptor(authInterceptor())
			.addPathPatterns("/**")
			.excludePathPatterns("/user/login")
			.excludePathPatterns("/user/logout")
			.excludePathPatterns("/assets/**");
		
		registry
		.addInterceptor(blogInterceptor())
		.addPathPatterns("/**")
		.excludePathPatterns("/")
		.excludePathPatterns("/user/**")
		.excludePathPatterns("/main/**")
		.excludePathPatterns("/assets/**");
	}

	
}
