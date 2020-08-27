package com.ecom.user.management.user.controller;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.ecom.user.management.user.entity.Name;
import com.ecom.user.management.user.entity.User;

@Service
public class UserService {
	private static final Map<String, User> USER_MAP = new ConcurrentHashMap<String, User>();

	public User createUser(final Name name, final String mobileNo) {
		final User user = new User(name);
		user.setMobileNo(mobileNo);

		user.setUserId(UUID.randomUUID().toString());
		return USER_MAP.putIfAbsent(mobileNo, user);

	}

}
