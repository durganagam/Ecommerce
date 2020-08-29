package com.ecom.user.management.user.entity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
	public static final Map<String, User> USER_MAP = new ConcurrentHashMap<String, User>();

	@SuppressWarnings("unchecked")
	@Override
	default <S extends User> S save(S entity) {
		return (S) USER_MAP.put(entity.getMobileNo(), entity);
	}

	default boolean findByUserId(String id) {
		return USER_MAP.containsKey(id);
	}

}
