package com.poscoict.config.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@PropertySource({"classpath:com/poscoict/jblog/config/web/fileupload.properties","classpath:com/poscoict/jblog/config/web/assets.properties"})
public class FileuploadConfig extends WebMvcConfigurerAdapter {

	@Autowired
	private Environment env;
	
	// Multipart Resolver 
	@Bean
	public MultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		
		multipartResolver.setMaxUploadSize(env.getProperty("fileupload.MaxUploadSize",Long.class));
		multipartResolver.setMaxInMemorySize(env.getProperty("fileupload.MaxInMemorySize",Integer.class));
		multipartResolver.setDefaultEncoding(env.getProperty("fileupload.DefaultEncoding"));
		
		return multipartResolver;
	}

	// url resource mapping 
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		registry
		.addResourceHandler(env.getProperty("fileupload.resourceMapping"))
		.addResourceLocations("file:"+env.getProperty("fileupload.uploadLocation"));
		
		registry
		.addResourceHandler(env.getProperty("assetsCss.resourceMapping"))
		.addResourceLocations("classpath:"+env.getProperty("assetsCss.uploadLocation"));
		
		registry
		.addResourceHandler(env.getProperty("assetsImages.resourceMapping"))
		.addResourceLocations("classpath:"+env.getProperty("assetsImages.uploadLocation"));
		
		registry
		.addResourceHandler(env.getProperty("assetsJs.resourceMapping"))
		.addResourceLocations("classpath:"+env.getProperty("assetsJs.uploadLocation"));
	}
	
	
}
