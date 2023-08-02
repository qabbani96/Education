package com.education.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.education.security.ServiceInterceptor;

@Configuration
public class ServiceInterceptorAppConfig implements WebMvcConfigurer{

	@Autowired
	ServiceInterceptor serviceInterceptor	;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(serviceInterceptor);
	}
}
