package com.jt.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.jt.interceptor.UserInterceptor;

@Configuration
public class MvcConfigurer implements WebMvcConfigurer{
	
	//开启匹配后缀型配置
	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
		
		configurer.setUseSuffixPatternMatch(true);
	}
	@Autowired
	private  UserInterceptor ui;
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(ui).addPathPatterns("/cart/**","/order/**");
		WebMvcConfigurer.super.addInterceptors(registry);
	}
}
