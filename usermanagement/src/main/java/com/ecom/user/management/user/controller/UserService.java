package com.ecom.user.management.user.controller;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;

import com.ecom.user.management.exceptions.EcommerceException;
import com.ecom.user.management.exceptions.InvalidInputException;
import com.ecom.user.management.user.entity.Name;
import com.ecom.user.management.user.entity.User;
import com.ecom.user.management.user.entity.UserLoginRepository;
import com.ecom.user.management.user.entity.UserRepository;
import com.ecom.user.management.user.entity.dto.UserCache;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserLoginRepository userLoginRepository;

	public String createUser(final Name name, final String mobileNo) {
		try {
			if (isValidMobileNumber(mobileNo)) {
				final String generateOtp = generateOtp(mobileNo);
				if (!StringUtils.isEmpty(generateOtp)) {
					String userId = UUID.randomUUID().toString();
					getUserCache(userId, name, generateOtp);
					return userId;
				}
			}
		} catch (InvalidInputException exception) {
			throw new EcommerceException(exception.getMessage());
		}
		throw new EcommerceException("Unable process the request! Please try after some time");
	}

	@CacheEvict(key = "#userId", condition = "#result = true")
	public boolean verifyOtp(final String otp, final String userId) {
		if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(otp)) {
			throw new EcommerceException("Unable process the request! Please try after some time");
		}
		UserCache userCache = getUserCache(userId, null, null);
		if (userRepository.findByMobileNo(userCache.getMobileNumber()) != null) {
			throw new EcommerceException("Mobile number already registered with us! please login");
		}
		if (userCache != null && otp.equals(userCache.getOneTimePassword())) {
			long seconds = userCache.getOtpCreatedDate().until(LocalDateTime.now(), ChronoUnit.SECONDS);
			if (seconds <= 100) {
				persistUser(userCache, userId);
				return true;
			} else
				throw new EcommerceException("One time password expired! Please resend again");
		}
		throw new EcommerceException("Please enter the correct one time password");
	}

	public void resendOtp(final String userId) {
		if (StringUtils.isEmpty(userId)) {
			throw new EcommerceException("Unable process the request! Please try after some time");
		}
		UserCache userCache = getUserCache(userId, null, null);
		if (userCache != null) {
			final String generateOtp = generateOtp(userCache.getMobileNumber());
			if (!StringUtils.isEmpty(generateOtp)) {
				getUserCache(userId, userCache.getName(), generateOtp);
			}
		}
		throw new EcommerceException("Unable resend One time password.");
	}

	@Cacheable(value = "userCache", key = "#userId")
	private UserCache getUserCache(String userId, final Name name, final String generateOtp) {
		UserCache cache = new UserCache();
		cache.setName(name);
		cache.setOneTimePassword(generateOtp);
		cache.setOtpCreatedDate(LocalDateTime.now());
		return cache;
	}

	private boolean isValidMobileNumber(final String mobileNumber) throws InvalidInputException {
		if (!mobileNumber.matches("\\d{10}")) {
			throw new InvalidInputException("Invalid Mobile Number!");
		}
		if (userRepository.findByUserId(mobileNumber)) {
			throw new InvalidInputException("User already registerd. Please login.");
		}
		return true;
	}

	private void persistUser(final UserCache userCache, final String userId) {
		User user = new User(userCache.getName());
		user.setMobileNo(userCache.getMobileNumber());
		user.setUserId(userId);
		userRepository.save(user);
	}

	private String generateOtp(final String mobileNumber) {
		return WebClient.create("http://localhost:8081/sms").post().bodyValue(mobileNumber)
				.accept(MediaType.APPLICATION_JSON).retrieve()
				.onStatus(httpStatus -> !HttpStatus.OK.equals(httpStatus),
						response -> response.bodyToMono(String.class)
								.map(body -> new EcommerceException("Please try again.")))
				.bodyToMono(String.class).block();
	}

	public String userLogin(String mobileNumber) {
		User user = userRepository.findByMobileNo(mobileNumber);
		if (user != null) {
			final String generateOtp = generateOtp(mobileNumber);
			if (!StringUtils.isEmpty(generateOtp)) {
				String userId = UUID.randomUUID().toString();
				getUserCache(userId, user.getName(), generateOtp);
				return userId;
			}
		}
		throw new EcommerceException("No records found! Please register");
	}
}
