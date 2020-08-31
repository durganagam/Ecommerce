package com.ecom.user.management.user.entity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ecom.user.management.user.entity.model.UserStatus;

@Repository
public interface UserLoginRepository extends CrudRepository<UserLogin, String> {
	public static final Map<String, UserLogin> LOGIN_MAP = new ConcurrentHashMap<String, UserLogin>();

	@Override
	default <S extends UserLogin> S save(S entity) {
		LOGIN_MAP.put(entity.getUserId(), entity);
		return entity;
	}

	default UserLogin findByUserId(String userId) {
		return LOGIN_MAP.get(userId);
	}
	
	default UserLogin findByMobileNo(String mobileNo) {
		return LOGIN_MAP.values().stream()
		  .filter(userLogin -> userLogin.getLoginId().equals(mobileNo))
		  .findAny()
		  .orElse(null);
	}
	
	default UserStatus isActiveUser(String userId) {
		return LOGIN_MAP.get(userId).getStatus();
	}

}
