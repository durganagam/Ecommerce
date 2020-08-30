package com.ecom.user.management.user.controller;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.ecom.user.management.exceptions.EcommerceException;
import com.ecom.user.management.user.entity.Name;
import com.ecom.user.management.user.entity.OneTimePassword;
import com.ecom.user.management.user.entity.OneTimePasswordRepository;
import com.ecom.user.management.user.entity.User;
import com.ecom.user.management.user.entity.UserLogin;
import com.ecom.user.management.user.entity.UserLoginRepository;
import com.ecom.user.management.user.entity.UserRepository;
import com.ecom.user.management.user.entity.model.UserStatus;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserLoginRepository userLoginRepository;

	@Autowired
	OneTimePasswordRepository oneTimePasswordRepository;

	public User createUser(final Name name, final String mobileNo) {
		if (!isUserExists(mobileNo)) {
			final User user = new User(name);
			user.setMobileNo(mobileNo);
			user.setUserId(UUID.randomUUID().toString());

			UserLogin userLogin = new UserLogin();
			userLogin.setLoginId(user.getMobileNo());
			userLogin.setUserId(user.getUserId());
			userLogin.setStatus(UserStatus.INACTIVE);

			userLoginRepository.save(userLogin);
			User persistedUser = userRepository.save(user);

			String generateOtp = generateOtpforRegistration(mobileNo);

			OneTimePassword otp = new OneTimePassword();
			otp.setOtp(generateOtp);
			otp.setUserId(user.getUserId());
			otp.setCreatedDate(new Date());

			oneTimePasswordRepository.save(otp);

			return persistedUser;

		}
		throw new EcommerceException("User already registerd. Please login.");
	}

	private boolean isUserExists(final String mobileNo) {
		return userRepository.findByUserId(mobileNo);
	}

	private String generateOtpforRegistration(final String mobileNumber) {
		return WebClient.create("http://localhost:8081/sms").post().bodyValue(mobileNumber)
				.accept(MediaType.APPLICATION_JSON).retrieve()
				.onStatus(httpStatus -> !HttpStatus.OK.equals(httpStatus),
						response -> response.bodyToMono(String.class)
								.map(body -> new EcommerceException("Please try again.")))
				.bodyToMono(String.class).block();
	}

	public boolean verifyOtp(final String otp, final String userId) {
		OneTimePassword oneTimePwd = oneTimePasswordRepository.findByUserId(userId);
		if (oneTimePwd != null && otp.equals(oneTimePwd.getOtp())) {
			Long duration = new Date().getTime() - oneTimePwd.getCreatedDate().getTime();
			if (TimeUnit.MILLISECONDS.toSeconds(duration) <= 180) {
				oneTimePasswordRepository.invalidateOtp(oneTimePwd);
				return true;
			} else
				throw new EcommerceException("Otp is expire. Please resend again");
		}
		throw new EcommerceException("Otp doesn't match");
	}

}
