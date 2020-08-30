package com.ecom.user.management.user.entity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OneTimePasswordRepository extends CrudRepository<OneTimePassword, String> {
	public static final Map<String, OneTimePassword> OTP_MAP = new ConcurrentHashMap<String, OneTimePassword>();

	@SuppressWarnings("unchecked")
	@Override
	default <S extends OneTimePassword> S save(S entity) {
		return (S) OTP_MAP.put(entity.getUserId(), entity);
	}

	default OneTimePassword findByUserId(String userId) {
		return OTP_MAP.get(userId);
	}

	default void invalidateOtp(OneTimePassword entity) {
		entity.setOtp("0");
		save(entity);
	}
}
