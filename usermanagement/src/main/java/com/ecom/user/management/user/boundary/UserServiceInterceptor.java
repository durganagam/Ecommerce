package com.ecom.user.management.user.boundary;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class UserServiceInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		String header = request.getHeader("website");
		if (!"ecom".equalsIgnoreCase(header))
			return false;
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}

}
