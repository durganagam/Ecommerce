package com.ecom.user.management.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ecom.user.management.user.boundary.UserServiceInterceptor;

@Component
public class IntereceptorConfig implements WebMvcConfigurer {
	@Autowired
	UserServiceInterceptor userServiceInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// TODO Auto-generated method stub
		registry.addInterceptor(userServiceInterceptor);
	}
}
